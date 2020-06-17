package com.eBook.mgr.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eBook.mgr.domain.platform.Bookcube;
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
	
	
	/*
	안되는것 2020-06-21 : 카멜케이스 변환하여 Front에 표시 안됨 kakao한정
						td에 input태그 숨기기
						list에 layout적용이안됨
	  
	 */
	
	
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list1(Model model, @RequestParam String platformType, Date setDate) throws Exception{
//		return "/ebook/list";
//	}
	
	// 플랫폼 별로 리스트 보여주기
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(Model model, @RequestParam String platformType, String setDate) throws Exception{
		log.info("플랫폼타입???" + platformType);
		
		model.addAttribute("platformType", platformType);
		
		setDate = "2020-01-12";
		
		switch (platformType) {
		case "p_bookcube":
			List<Bookcube> bookList = eBookService.listBookcube(setDate);
			model.addAttribute("listBookcube", bookList);
			log.info("찍힘?");
			log.info(Integer.toString(bookList.size()));
			break;
//		case "p_epub":
//			model.addAttribute("listBookcube", eBookService.listEpub(setDate));
//			break;
		case "p_joara":
			log.info("p_joara 진입");
			model.addAttribute("listJoara", eBookService.listJoara(setDate));
			break;
		case "p_kakao":
			log.info("p_kakao 진입");
			model.addAttribute("listKakao", eBookService.listKakao(setDate));
			break;
		case "p_kyobo":
			log.info("p_kyobo 진입");
			model.addAttribute("listKyobo", eBookService.listkyobo(setDate));
			break;
		case "p_mrblue":
			log.info("p_mrblue 진입");
			model.addAttribute("listMrblue", eBookService.listMrblue(setDate));
			break;
		case "p_munpia":
			log.info("p_munpia 진입");
			model.addAttribute("listMunpia", eBookService.listMunpia(setDate));
			break;
		case "p_naver":
			log.info("p_naver 진입");
			model.addAttribute("listNaver", eBookService.listNaver(setDate));
			break;
		case "p_ridibooks":
			log.info("p_ridibooks 진입");
			model.addAttribute("listRidibooks", eBookService.listRidibooks(setDate));
			break;
		case "p_romance":
			log.info("p_romance 진입");
			model.addAttribute("listRomance", eBookService.listRomance(setDate));
			break;
		case "p_tocsoda":
			log.info("p_tocsoda 진입");
			model.addAttribute("listTocsoda", eBookService.listTocsoda(setDate));
			break;
		case "p_winstore":
			log.info("p_winstore 진입");
			model.addAttribute("listWinstore", eBookService.listWinstore(setDate));
			break;
		case "p_yes24":
			log.info("p_yes24 진입");
			model.addAttribute("listYes24", eBookService.listYes24(setDate));
			break;
		case "p_aladin":
			log.info("p_aladin 진입");
			model.addAttribute("listAladin", eBookService.listAladin(setDate));
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
