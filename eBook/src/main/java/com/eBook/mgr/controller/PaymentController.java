package com.eBook.mgr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ebook")
public class PaymentController {

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public void payment(Model model) throws Exception{
		
	}
}
