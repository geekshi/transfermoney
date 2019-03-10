package com.example.transfermoney;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.transfermoney.common.Constants;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		init();
	}
	
	public static void init() {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = null;
		String url = Constants.HOST + Constants.INIT;
		httpGet = new HttpGet(url);
		CloseableHttpResponse resp = null;
		try {
			resp = client.execute(httpGet);
		} catch (Exception e) {
			logger.error("Unknown error: ", e);
		} finally{
			try {
				if(resp != null){
					resp.close();
				}
			} catch (Exception e) {
				logger.error("Close resp error: ", e);
			}
			try{
				client.close();
			} catch (Exception e) {
				logger.error("Close client error: ", e);
			}
		}
	}
}
