package com.eBook.mgr.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eBook.mgr.domain.Author;
import com.eBook.mgr.domain.Member;
import com.eBook.mgr.dto.AuthorListDto;
import com.eBook.mgr.service.AuthorService;
import com.eBook.mgr.service.MemberService;

@Controller
@RequestMapping("/user")
public class MemberController {
	
	
	private static final Logger log = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AuthorService authorService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerForm(Member member, String writerId, Model model) throws Exception {
		log.info("register Member.....");
		
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
		author.setId(request.getParameter("id"));
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
		System.out.println("리스트반환");
		List<AuthorListDto> list = memberService.list();
		
		for (AuthorListDto author : list) {
			System.out.println("author : " + author.getRealName());
		}
		
		model.addAttribute("memberList", list);
	}
	
	
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void readForm(Member member, String writerId, Model model) throws Exception {
		log.info("register Member.....");
		
		// writerId 로 조회
		AuthorListDto author =  authorService.read(writerId);
		model.addAttribute("AuthorListDto", author);
		model.addAttribute("writerId", writerId);
	}
	
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modify(AuthorListDto authorDto) throws Exception {
		System.out.println(authorDto);
		
		// update
		Member member = new Member();
		Author author = new Author();
		
		member.setId(authorDto.getId());
		member.setRealName(authorDto.getRealName());
		member.setCtzNumber(authorDto.getCtzNumber());
		member.setManager(authorDto.getManager());
		author.setAuthor(authorDto.getAuthor());
		author.setAccountNumber(authorDto.getAccountNumber());
		author.setVirtuousTax(authorDto.getVirtuousTax());
		author.setSettlementRatio(authorDto.getSettlementRatio());
		author.setWriterId(authorDto.getWriterId());
		
		memberService.modifyAuthorDto(member, author);
		
		return "redirect:/user/list";
	}
	
	
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(Model model, String delete_ids) throws Exception {
		
		
		String[] deleteIdsArr = delete_ids.split(",");
		String id = "";
		log.info("writerIIID : " + deleteIdsArr[0]);
		
		try {
			model.addAttribute("result", true);
			for (int i=0; i<deleteIdsArr.length; i++) {
				id = memberService.readWriterId(deleteIdsArr[i]);
				log.info("아이디값값?: " + id);
				
				memberService.removeAuthorDto(id, deleteIdsArr[i]);
			}
		} catch (Exception e) {
			model.addAttribute("result", false);
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return "redirect:/user/list";
	}
	
	
	@RequestMapping(value = "/status", method = RequestMethod.POST)
	public String status(Model model, String delete_ids) throws Exception{
		
		String[] deleteIdsArr = delete_ids.split(",");
		String id = memberService.readWriterId(deleteIdsArr[0]);
		String status = memberService.readStatus(id);
		
		log.info("writerIIID : " + deleteIdsArr[0]);
		log.info("id : " + id);
		log.info("status : " + status);
		
		Boolean i;
		if(status.equals("1")) {
			i = true;
		} else {
			i = false;
		}
		
		try {
			model.addAttribute("result", true);
			memberService.status(id, i);
		} catch (Exception e) {
			model.addAttribute("result", false);
			e.printStackTrace();
			// TODO: handle exception
		}
		
		return "redirect:/user/list";
	}
	
}
