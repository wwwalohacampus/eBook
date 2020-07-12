package com.eBook.mgr.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eBook.mgr.dto.PaymentDto;
import com.eBook.mgr.service.PaymentService;

@Controller
@RequestMapping("/ebook")
public class PaymentController {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	PaymentService paymentService;

	@RequestMapping(value = "/payment", method = RequestMethod.GET)
	public void payment(Model model) throws Exception{
		
		//setDate 계산
		String setDate = "2020-07";
		
		List<PaymentDto> paymentDto = paymentService.listPayment(setDate);
		
		model.addAttribute("paymentList", paymentDto);
	}
	
	@RequestMapping(value = "/createPayment", method = RequestMethod.POST)
	public ModelAndView create(Model model) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		
		try {
			log.info("정산되었습니다");
			mv.addObject("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			mv.addObject("result", false);
		}
		
		return mv;
	}
}
