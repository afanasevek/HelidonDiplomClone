package ru.afanasev.helidon.service;

import io.helidon.dbclient.DbClient;
import io.helidon.webserver.Routing.Rules;
import net.bytebuddy.utility.RandomString;
import ru.afanasev.helidon.object.dao.CaptchaDao;
import ru.afanasev.helidon.object.mapper.CaptchaMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import javax.json.JsonObject;

import org.apache.commons.io.FileUtils;

import com.github.cage.Cage;
import com.github.cage.GCage;

import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class CaptchaService implements Service {

	private static final String FIRSTNAME = "captcha/";
	private static final String LASTNAME = ".img";
	private static final String PREFIX = "data:image/png;base64, ";

	private final DbClient dbClient;
	private final CaptchaDao captchaDao;

	public CaptchaService(DbClient dbClient, CaptchaDao captchaDao) {

		this.dbClient = dbClient;
		this.captchaDao = captchaDao;
	}

	@Override
	public void update(Rules rules) {
		rules.get("/api/auth/captcha", (req, res) -> {
			try {
				getHandler(req, res);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

	}

	private void getHandler(ServerRequest request, ServerResponse response) throws Exception {
		String secret = Utils.generateUUID();
		JsonObject json = CaptchaMapper.CaptchaResponseToJson(getCaptcha(secret), secret);
		response.send(json);

	}

	private String getCaptcha(String secretv2) throws Exception {

		Cage cage = new GCage();
		String name = getImageName();
		String code;
		OutputStream os = new FileOutputStream(name, false);
		try {
			code = cage.getTokenGenerator().next();
			cage.draw(code, os);
		} finally {
			os.close();
		}
		captchaDao.InsertCaptcha(dbClient, code, secretv2, getDateNow());
		File imageFile = new File(name);
		byte[] fileContent = FileUtils.readFileToByteArray(imageFile);
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		StringBuilder base64 = new StringBuilder();
		base64.append(PREFIX);
		base64.append(encodedString);
		imageFile.delete();

		return base64.toString();

	}

	private String getImageName() {
		StringBuilder nameString = new StringBuilder();
		nameString.append(FIRSTNAME);
		nameString.append(new RandomString(10).nextString());
		nameString.append(LASTNAME);

		return nameString.toString();
	}

	private String getDateNow() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date().getTime());
	}

}
