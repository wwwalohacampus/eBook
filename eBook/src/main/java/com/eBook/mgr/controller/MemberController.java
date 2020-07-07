package com.eBook.mgr.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eBook.mgr.domain.Author;
import com.eBook.mgr.domain.Member;
import com.eBook.mgr.service.MemberService;

@Controller
@RequestMapping("/user")
public class MemberController {
	
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerForm(Member member, String writerId, Model model) throws Exception {
		log.info("register Member.....");
		
		System.out.println("작가 아아디: " + writerId);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest request, Model model, RedirectAttributes rttr) throws Exception {

//		System.out.println("값???????????" + request.getParameter("id"));
		
//		if(result.hasErrors()) {
//			return "user/register";
//		}
		
		Member member = new Member();
		Author author = new Author();
		
		member.setId(request.getParameter("id"));
		member.setPw(request.getParameter("pw"));
		member.setRealName(request.getParameter("realName"));
		member.setCtzNumber(request.getParameter("ctzNumber"));
		member.setManager(request.getParameter("manager"));
		member.setAuthNumber(Integer.parseInt(request.getParameter("authNumber")));
		author.setAuthor(request.getParameter("author"));
		author.setAccountNumber(request.getParameter("accountNumber"));
		author.setVirtuousTax(request.getParameter("virtuousTax"));
		author.setSettlementRatio(request.getParameter("settlementRatio"));
		
		System.out.println(member);
		System.out.println(author);
		
		String inputPassword = member.getPw();
		member.setPw(passwordEncoder.encode(inputPassword));
		System.out.println(inputPassword);
		System.out.println(passwordEncoder.encode(inputPassword));
		
		memberService.registerMember(member);
		memberService.registerAuthor(author);
		
		rttr.addFlashAttribute("userName", member.getRealName());
		
		return "redirect:/user/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception{
		model.addAttribute("memberList", memberService.list());
	}
	
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void readForm(Member member, String writerId, Model model) throws Exception {
		log.info("register Member.....");
		
		System.out.println("작가 아아디: " + writerId);
		model.addAttribute("writerId", writerId);
	}
	
	
}
