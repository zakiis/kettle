package com.zakiis.kettle.test;

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
	public void test() throws KettleException {
		String transformName = "user";
		kettleService.runTransfer(transformName);
	}
}
