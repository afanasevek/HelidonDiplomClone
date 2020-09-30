package ru.afanasev.helidon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;


import io.helidon.common.reactive.Subscribable;
import io.helidon.config.Config;
import io.helidon.dbclient.DbClient;
import io.helidon.media.common.MediaSupport;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.security.Security;
import io.helidon.security.spi.SecurityProvider;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import io.netty.handler.codec.json.JsonObjectDecoder;
import ru.afanasev.helidon.object.Post;
import ru.afanasev.helidon.object.dto.CaptchaResponse;
import ru.afanasev.helidon.service.CaptchaService;

public class Application {

	
	public static void main(String[] args) {

		

		JsonpSupport jsonpSupport = JsonpSupport.create();
		
		Routing routing = Routing.builder()
				.register(new CaptchaService())
				.build();
				
		WebServer server = WebServer.builder(routing)
				.port(8080)
				.addMediaSupport(jsonpSupport)
				.build();
		server.start();
	}
	private void getAllPosts() {
		List<Post> listPosts = new ArrayList<>();
		Config config = Config.create();
		DbClient dbClient = DbClient.create(config.get("db"));
		dbClient.execute(exec -> exec.namedGet("select-post-bi-id", 1)).thenAccept(row ->{
			Post post = new Post();
			post.setId(row.get().column("id").as(Integer.class));
			post.setText(row.get().column("text").as(String.class));
			listPosts.add(post);
		});
	}
}
