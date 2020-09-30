package ru.afanasev.helidon.service;

import java.util.UUID;

public class Utils {
	public static String generateUUID() {
		UUID secret = UUID.randomUUID();
		return secret.toString();
	}
}
