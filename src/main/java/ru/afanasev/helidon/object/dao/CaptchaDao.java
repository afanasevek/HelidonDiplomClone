package ru.afanasev.helidon.object.dao;

import io.helidon.dbclient.DbClient;

public class CaptchaDao {

	public void InsertCaptcha(DbClient dbClient, String code, String secretv2, String getDate) {

		dbClient.execute(exec -> exec.namedGet("insert-captcha", code, secretv2, getDate));
	}
}
