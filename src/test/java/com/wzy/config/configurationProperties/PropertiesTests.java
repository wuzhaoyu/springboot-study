package com.wzy.config.configurationProperties;

import com.wzy.config.Parent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class PropertiesTests {

	@Autowired
	private Parent parent;
	@Test
	void contextLoads() {
		System.out.println(parent.toString());
	}

}
