package com.wzy;

import com.wzy.config.configurationProperties.Parent;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootStudyApplicationTests {

	@Autowired
	private Parent parent;

	@Autowired
	ApplicationContext ioc;

	@Test
	void test1(){
		boolean helloService = ioc.containsBean("helloService");
		System.out.println(helloService);

	}
	@Test
	void contextLoads() {
		System.out.println(parent.toString());
	}

}
