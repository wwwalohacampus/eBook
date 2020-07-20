package com.eBook.mgr.controller;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.eBook.mgr.domain.Author;
import com.eBook.mgr.dto.AuthorListDto;
import com.eBook.mgr.dto.PaymentDto;
import com.eBook.mgr.service.AuthorService;
import com.eBook.mgr.service.MemberService;
import com.eBook.mgr.service.PaymentService;

@Controller
@RequestMapping("/ebook")
public class PaymentController {
	
	private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	PaymentService paymentService;
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	AuthorService authorService;

	@RequestMapping(value = "/payment")
	public void payment(Model model, String p_year_select, String p_month_select) throws Exception{
		
		log.info("===============================???"); 
		log.info("연... : " + p_year_select);
		log.info("월... : " + p_month_select);
		
		// setDate계산 ---------------------------------------------
		String setDate = ""; 

		if(p_month_select != null) {
			p_year_select = p_year_select.replace("년", "");
			p_month_select = p_month_select.replace("월", "");
			
			setDate = p_year_select + "-" + p_month_select;
		} else {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String monthString;
			
			if(month < 10) {
				monthString = "0" + Integer.toString(month);
			} else {
				monthString = Integer.toString(month);
			}
			
			setDate = Integer.toString(year) + "-" + monthString;
		}
		
		model.addAttribute("setDate", setDate);

		
		log.info("현재 셋데이터" + setDate);
		// --------------------------------------------------------
		
		List<PaymentDto> paymentDto = paymentService.listPayment(setDate);
		
		model.addAttribute("paymentList", paymentDto);
	}
	
	@RequestMapping(value = "/finalPayment", method = RequestMethod.POST)
	public ModelAndView finalCreate(Model model, String p_year, String p_month, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		// setDate계산 ---------------------------------------------
		String setDate = ""; 

		if(p_month != null) {
			p_year = p_year.replace("년", "");
			p_month = p_month.replace("월", "");
			
			setDate = p_year + "-" + p_month;
		} else {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String monthString;
			
			if(month < 10) {
				monthString = "0" + Integer.toString(month);
			} else {
				monthString = Integer.toString(month);
			}
			
			setDate = Integer.toString(year) + "-" + monthString;
		}
		
		model.addAttribute("setDate", setDate);
		log.info("현재 셋데이터 : " + setDate);
		
		

		try {
			paymentService.removeList(setDate);
			
			PaymentDto paymentDto = new PaymentDto();
			String writerList[] = paymentService.writerIdList();
			
			log.info("길이가?? : " + writerList.length);
			log.info("첫번째 원소 : " + writerList[0]);
			for(int i=0; i<writerList.length; i++) {
				AuthorListDto authorListDto = new AuthorListDto();

				authorListDto = authorService.read(writerList[i]);
				System.out.println(writerList[i]);
				System.out.println(setDate);
				
				paymentDto = paymentService.allPayment(writerList[i], setDate);
				
				paymentDto.setAuthor(authorListDto.getAuthor());
				paymentDto.setRealName(authorListDto.getRealName());
				paymentDto.setCtzNumber(authorListDto.getCtzNumber());
				paymentDto.setAccountNumber(authorListDto.getAccountNumber());
				paymentDto.setSetDate(setDate + "-10");
				paymentDto.setWriterId(writerList[i]);
				paymentDto.setSettlementRatio(authorListDto.getSettlementRatio());
				paymentDto.setCarryAmount(authorListDto.getCarryAmount());
				paymentDto.setVirtuousTax(authorListDto.getVirtuousTax());
				
				// 여기서부터 정산계산
				String allPayment;						// 총매출
				String authorSettlement;				// 작가정산액
				String payment;							// 정산액합계
				
				//총매출계산
				allPayment = allpay(paymentDto);
				paymentDto.setAllPayment(allPayment);
				
				//작가정산액계산
				authorSettlement = Integer.toString((int)(Integer.parseInt(paymentDto.getAllPayment()) * Double.parseDouble(paymentDto.getSettlementRatio())));
				paymentDto.setAuthorSettlement(authorSettlement);
				
				//정산액합계계산
				payment = Integer.toString(Integer.parseInt(paymentDto.getAuthorSettlement()) + Integer.parseInt(paymentDto.getCarryAmount()));
				paymentDto.setPayment(payment);
				
				//소득세계산
				int a = Integer.parseInt(paymentDto.getPayment()) - Integer.parseInt(paymentDto.getVirtuousTax());
				a = (int) (a * 0.03);
				a = (int) (a * 0.1);
				a = a * 10;
				paymentDto.setIncomeTax(Integer.toString(a));
				
				//원천징수세계산
				a = (int) (a * 0.1);
				a = (int) (a * 0.1);
				a = a * 10;
				paymentDto.setWthldTax(Integer.toString(a));
				
				//작가지급액계산
				int tempAmount;
				tempAmount = Integer.parseInt(paymentDto.getPayment()) - Integer.parseInt(paymentDto.getVirtuousTax()) - Integer.parseInt(paymentDto.getWthldTax()) - Integer.parseInt(paymentDto.getIncomeTax());
				paymentDto.setAuthorPaid(Integer.toString(tempAmount));
				
				System.out.println(paymentDto);
				
				//선인세 이월정산금 다음달 존재여부 판단 업데이트
				if(tempAmount <= 0) {
					paymentService.updateCarryAmount(paymentDto.getPayment(), writerList[i]);
				} else {
					paymentService.updateCarryAmount("0", writerList[i]);
					paymentService.updateVirtuousTax(writerList[i]);
				}
				
				//정산행집어넣기
				paymentService.insertPayment(paymentDto);
			}
			
			log.info("정산되었습니다");
			mv.addObject("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			e.getStackTrace();
			mv.addObject("result", false);
		}
		
		return mv;
	}
	
	@RequestMapping(value = "/createPayment", method = RequestMethod.POST)
	public ModelAndView create(Model model, String p_year, String p_month, HttpServletRequest request) throws Exception{
		ModelAndView mv = new ModelAndView("jsonView");
		
		// setDate계산 ---------------------------------------------
		String setDate = ""; 

		if(p_month != null) {
			p_year = p_year.replace("년", "");
			p_month = p_month.replace("월", "");
			
			setDate = p_year + "-" + p_month;
		} else {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH) + 1;
			String monthString;
			
			if(month < 10) {
				monthString = "0" + Integer.toString(month);
			} else {
				monthString = Integer.toString(month);
			}
			
			setDate = Integer.toString(year) + "-" + monthString;
		}
		
		model.addAttribute("setDate", setDate);
		log.info("현재 셋데이터 : " + setDate);
		
		

		try {
			paymentService.removeList(setDate);
			
			PaymentDto paymentDto = new PaymentDto();
			String writerList[] = paymentService.writerIdList();
			
			log.info("길이가?? : " + writerList.length);
			log.info("첫번째 원소 : " + writerList[0]);
			for(int i=0; i<writerList.length; i++) {
				AuthorListDto authorListDto = new AuthorListDto();

				authorListDto = authorService.read(writerList[i]);
				System.out.println(writerList[i]);
				System.out.println(setDate);
				
				paymentDto = paymentService.allPayment(writerList[i], setDate);
				
				paymentDto.setAuthor(authorListDto.getAuthor());
				paymentDto.setRealName(authorListDto.getRealName());
				paymentDto.setCtzNumber(authorListDto.getCtzNumber());
				paymentDto.setAccountNumber(authorListDto.getAccountNumber());
				paymentDto.setSetDate(setDate + "-10");
				paymentDto.setWriterId(writerList[i]);
				paymentDto.setSettlementRatio(authorListDto.getSettlementRatio());
				paymentDto.setCarryAmount(authorListDto.getCarryAmount());
				paymentDto.setVirtuousTax(authorListDto.getVirtuousTax());
				
				// 여기서부터 정산계산
				String allPayment;						// 총매출
				String authorSettlement;				// 작가정산액
				String payment;							// 정산액합계
				
				//총매출계산
				allPayment = allpay(paymentDto);
				paymentDto.setAllPayment(allPayment);
				
				//작가정산액계산
				authorSettlement = Integer.toString((int)(Integer.parseInt(paymentDto.getAllPayment()) * Double.parseDouble(paymentDto.getSettlementRatio())));
				paymentDto.setAuthorSettlement(authorSettlement);
				
				//정산액합계계산
				payment = Integer.toString(Integer.parseInt(paymentDto.getAuthorSettlement()) + Integer.parseInt(paymentDto.getCarryAmount()));
				paymentDto.setPayment(payment);
				
				//소득세계산
				int a = Integer.parseInt(paymentDto.getPayment()) - Integer.parseInt(paymentDto.getVirtuousTax());
				a = (int) (a * 0.03);
				a = (int) (a * 0.1);
				a = a * 10;
				paymentDto.setIncomeTax(Integer.toString(a));
				
				//원천징수세계산
				a = (int) (a * 0.1);
				a = (int) (a * 0.1);
				a = a * 10;
				paymentDto.setWthldTax(Integer.toString(a));
				
				//작가지급액계산
				int tempAmount;
				tempAmount = Integer.parseInt(paymentDto.getPayment()) - Integer.parseInt(paymentDto.getVirtuousTax()) - Integer.parseInt(paymentDto.getWthldTax()) - Integer.parseInt(paymentDto.getIncomeTax());
				paymentDto.setAuthorPaid(Integer.toString(tempAmount));
				
				System.out.println(paymentDto);
				
				//정산행집어넣기
				paymentService.insertPayment(paymentDto);
			}
			
			log.info("정산되었습니다");
			mv.addObject("result", true);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			e.getStackTrace();
			mv.addObject("result", false);
		}
		
		return mv;
	}
	
	public String allpay(PaymentDto paymentDto) {
		String a;
		int sum;
		
		sum = Integer.parseInt(paymentDto.getPayBookcube()) +
				Integer.parseInt(paymentDto.getPayJoara()) +
				Integer.parseInt(paymentDto.getPayKakao()) +
				Integer.parseInt(paymentDto.getPayKyobo()) +
				Integer.parseInt(paymentDto.getPayMrblue()) +
				Integer.parseInt(paymentDto.getPayMunpia()) +
				Integer.parseInt(paymentDto.getPayNaver()) +
				Integer.parseInt(paymentDto.getPayRidibooks()) +
				Integer.parseInt(paymentDto.getPayRomance()) +
				Integer.parseInt(paymentDto.getPayTocsoda()) +
				Integer.parseInt(paymentDto.getPayWinstore()) +
				Integer.parseInt(paymentDto.getPayYes24()) +
				Integer.parseInt(paymentDto.getPayAladin());
		
		a = Integer.toString(sum);
		return a;
	}
}
