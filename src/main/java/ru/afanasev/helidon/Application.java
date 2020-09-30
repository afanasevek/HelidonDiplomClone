package ru.afanasev.helidon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import io.helidon.common.reactive.Subscribable;
import io.helidon.config.ClasspathConfigSource;
import io.helidon.config.Config;
import io.helidon.config.ConfigSources;
import io.helidon.config.spi.ConfigSource;
import io.helidon.dbclient.DbClient;
import io.helidon.media.common.MediaSupport;
import io.helidon.media.jsonp.JsonpSupport;
import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerConfiguration;
import io.helidon.webserver.WebServer;
import io.netty.handler.codec.json.JsonObjectDecoder;
import liquibase.pro.packaged.iD;
import ru.afanasev.helidon.object.dao.CaptchaDao;
import ru.afanasev.helidon.object.dto.CaptchaResponse;
import ru.afanasev.helidon.service.CaptchaService;

public class Application {

	public static void main(String[] args) {

		final ConfigSource CONFIG_SOURCE = ConfigSources
				.classpath("application.yaml")
				.build();

		final Config CONFIG = Config
				.create(CONFIG_SOURCE);

		final int PORT = CONFIG
				.get("server.port")
				.as(Integer.class)
				.get();

		final CaptchaDao CAPTCHA_DAO = new CaptchaDao();

		final JsonpSupport JSONSUPPORT = JsonpSupport
				.create();

		final DbClient DB_CLIENT = DbClient
				.create(CONFIG.get("db"));

		Routing routing = Routing
				.builder()
				.register(new CaptchaService(DB_CLIENT, CAPTCHA_DAO))
				.build();

		WebServer
		.builder(routing)
		.port(PORT)
		.addMediaSupport(JSONSUPPORT)
		.build()
		.start();
		

	}

}
