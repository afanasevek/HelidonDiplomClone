package ru.afanasev.helidon.object.mapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;

import org.apache.commons.io.FileUtils;

import com.github.cage.Cage;
import com.github.cage.GCage;

public class CaptchaMapper {
	
	private static final JsonBuilderFactory JSON_FACTORY = Json.createBuilderFactory(Collections.emptyMap()); 

	
	public static JsonObject CaptchaResponseToJson(String image, String secret) {
		
		JsonObject json = JSON_FACTORY.createObjectBuilder()
				.add("secret", secret)
				.add("image", image)
				.build();
		return json;
		
	}
	
}
