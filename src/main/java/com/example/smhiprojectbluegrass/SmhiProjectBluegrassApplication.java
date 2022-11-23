package com.example.smhiprojectbluegrass;

import com.example.smhiprojectbluegrass.data.Data;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@SpringBootApplication
public class SmhiProjectBluegrassApplication {

	public static void main(String[] args) throws IOException, JSONException {
		SpringApplication.run(SmhiProjectBluegrassApplication.class, args);

		/*Data data = new Data();
		String parameter = "1";
		String station = "159880";
		String period = "latest-hour";

		String URL = "https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/1/station/159880/period/latest-hour/data.json";

		JSONObject jsonObject = data.readJsonFromUrl(URL);


		FileWriter file = new FileWriter("C:/output.json");
		file.write(String.valueOf(jsonObject));
		System.out.println(jsonObject);
		file.close();
*/














	}








}
