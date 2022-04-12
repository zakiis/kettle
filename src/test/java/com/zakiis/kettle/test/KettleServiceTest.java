package com.zakiis.kettle.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.pentaho.di.core.exception.KettleException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zakiis.kettle.service.KettleService;

@SpringBootTest
public class KettleServiceTest {

	@Autowired
	private KettleService kettleService;
	
	@Test
	public void testArchive() throws KettleException {
		String directory = "trans";
		String transformName = "archive";
		Map<String, String> params = new HashMap<String, String>();
		params.put("host", "192.168.137.105");
		params.put("port", "3306");
		params.put("name", "zakiis");
		params.put("username", "root");
		params.put("password", "123456");
		params.put("sql", "select * from user");
		params.put("outputFile", "D:\\data\\kettle\\user-20220411");
		boolean result = kettleService.runTransform(directory, transformName, params);
		assertEquals(result, true);
	}
	
	@Test
	public void testPurge() throws KettleException {
		String directory = "trans";
		String transformName = "purge";
		Map<String, String> params = new HashMap<String, String>();
		params.put("host", "192.168.137.105");
		params.put("port", "3306");
		params.put("name", "zakiis");
		params.put("username", "root");
		params.put("password", "123456");
		params.put("sql", "delete from user where id < 10");
		boolean result = kettleService.runTransform(directory, transformName, params);
		assertEquals(result, true);
	}
	
	@Test
	public void testRestore() throws KettleException, InterruptedException {
		String directory = "trans";
		String transformName = "restore";
		Map<String, String> params = new HashMap<String, String>();
		params.put("host", "192.168.137.105");
		params.put("port", "3306");
		params.put("name", "zakiis");
		params.put("username", "root");
		params.put("password", "123456");
		
		
		Thread thread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				params.put("inputFile", "D:\\data\\kettle\\address-20220411.csv");
				params.put("table", "address");
				try {
					kettleService.runTransform(directory, transformName, params);
				} catch (KettleException e) {
					e.printStackTrace();
				}
			}
		});
		thread.start();
		thread.join();
		
		params.put("inputFile", "D:\\data\\kettle\\user-20220411.csv");
		params.put("table", "user");
		boolean result = kettleService.runTransform(directory, transformName, params);
		assertEquals(result, true);
	}
}
