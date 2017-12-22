package com.rporz.demo.ctrl;

import com.rporz.demo.rest.client.IWalletClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestCtrl {
	@Autowired
	private IWalletClient client;

	@RequestMapping("/test")
	public Object test() {
		return client.test(24771, 8525.19, "2525F8F3F72");
	}
}
