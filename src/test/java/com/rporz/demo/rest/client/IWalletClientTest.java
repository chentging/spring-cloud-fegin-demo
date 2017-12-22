package com.rporz.demo.rest.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
//@ActiveProfiles("dev")
public class IWalletClientTest {
	@Autowired
	IWalletClient client;

	@Test
	public void test1() throws Exception {
		Object test = client.test(24771, 8525.19, "2525F8F3F72");
		System.out.println(test);
	}

}