package com.eBook.mgr.controller;

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
	public void registerForm(Member member, Model model) throws Exception {
		log.info("register Member.....");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@Validated Member member, BindingResult result, Model model, RedirectAttributes rttr) throws Exception {
		if(result.hasErrors()) {
			return "user/register";
		}
		
		System.out.println(member);
		
		String inputPassword = member.getPw();
		member.setPw(passwordEncoder.encode(inputPassword));
		System.out.println(inputPassword);
		System.out.println(passwordEncoder.encode(inputPassword));
		
		memberService.register(member);
		
		rttr.addFlashAttribute("userName", member.getRealName());
		
		return "redirect:/user/registerSuccess";
	}
	
	@RequestMapping(value = "/registerSuccess", method = RequestMethod.GET)
	public void registerSuccess(Model model) throws Exception{
		
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model) throws Exception{
		model.addAttribute("memberList", memberService.list());
	}
	
	
	
	
}
