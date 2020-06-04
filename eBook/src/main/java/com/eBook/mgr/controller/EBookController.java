package com.eBook.mgr.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eBook.mgr.service.EBookService;

@Controller
@RequestMapping("/ebook")
public class EBookController {
	
	private static final Logger log = LoggerFactory.getLogger(EBookController.class);

	@Autowired
	EBookService eBookService;
	
	/*
	로직구상:
	 뷰단에서 hidden 처리된 platformType을 가져와 분기하여 플랫폼별로 리스트를 보여줌.
	 파일 삭제, 적용하기 버튼을 눌러도 platformType값은 고정되어있어 서로 다른 값을 침해하는 일은 발생되지 않을것으로 판단됨.
	 
	 */
	
	
	// 플랫폼 별로 리스트 보여주기
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model, String platformType, Date setDate) throws Exception{
		log.info("플랫폼타입???" + platformType);
		
		switch (platformType) {
		case "p_bookcube":
			model.addAttribute("listBookcube", eBookService.listBookcube(setDate));
			break;
		case "p_epub":
			model.addAttribute("listBookcube", eBookService.listEpub(setDate));
			break;
		case "p_joara":
			model.addAttribute("listBookcube", eBookService.listJoara(setDate));
			break;
		case "p_kakao":
			model.addAttribute("listBookcube", eBookService.listKakao(setDate));
			break;
		case "p_kyobo":
			model.addAttribute("listBookcube", eBookService.listkyobo(setDate));
			break;
		case "p_mrblue":
			model.addAttribute("listBookcube", eBookService.listMrblue(setDate));
			break;
		case "p_munpia":
			model.addAttribute("listBookcube", eBookService.listMunpia(setDate));
			break;
		case "p_naver":
			model.addAttribute("listBookcube", eBookService.listNaver(setDate));
			break;
		case "p_ridibooks":
			model.addAttribute("listBookcube", eBookService.listRidibooks(setDate));
			break;
		case "p_romance":
			model.addAttribute("listBookcube", eBookService.listRomance(setDate));
			break;
		case "p_tocsoda":
			model.addAttribute("listBookcube", eBookService.listTocsoda(setDate));
			break;
		case "p_winstore":
			model.addAttribute("listBookcube", eBookService.listWinstore(setDate));
			break;

		default:
			break;
		}
	}
	
	// 체크된 리스트 삭제하기
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public ModelAndView remove(Model model, String writeId) throws Exception{
		ModelAndView mv = new ModelAndView();
	
		try {
			
			mv.addObject("result", true);
		} catch (Exception e) {
			mv.addObject("result", false);
		}
		
		return mv;
	
	}
	
	
	// 적용하기 누르면 리스트에있는 목록 수정시키기
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public ModelAndView apply(Model model, String writeId) throws Exception{
		ModelAndView mv = new ModelAndView();
	
		try {
			
			mv.addObject("result", true);
		} catch (Exception e) {
			mv.addObject("result", false);
		}
		
		return mv;
	
	}
	
}
