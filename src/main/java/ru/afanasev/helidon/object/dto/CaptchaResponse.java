package ru.afanasev.helidon.object.dto;

public class CaptchaResponse {
	private String code;
	private String secretCode;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSecretCode() {
		return secretCode;
	}
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
	
	
}
