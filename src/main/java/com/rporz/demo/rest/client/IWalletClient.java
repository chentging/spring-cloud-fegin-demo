package com.rporz.demo.rest.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="wallet")
public interface IWalletClient {
	/***
	 *  just s test patch method
	 * @param i
	 * @param d
	 * @param s
	 * @return
	 */
	@RequestMapping(value = "/walletbase/alterBalanceByUserId", method = RequestMethod.PATCH)
	Object test(@RequestParam("userid") int i, @RequestParam("balance") double d, @RequestParam("balanceCode") String s);
}
