package com.eBook.mgr.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.eBook.mgr.domain.platform.Aladin;
import com.eBook.mgr.domain.platform.Bookcube;
import com.eBook.mgr.domain.platform.Joara;
import com.eBook.mgr.domain.platform.Kakao;
import com.eBook.mgr.domain.platform.Kyobo;
import com.eBook.mgr.domain.platform.Mrblue;
import com.eBook.mgr.domain.platform.Munpia;
import com.eBook.mgr.domain.platform.Naver;
import com.eBook.mgr.domain.platform.Ridibooks;
import com.eBook.mgr.domain.platform.Romance;
import com.eBook.mgr.domain.platform.Tocsoda;
import com.eBook.mgr.domain.platform.Winstore;
import com.eBook.mgr.domain.platform.Yes24;
import com.eBook.mgr.service.EBookService;

@Controller
@RequestMapping("/ebook")
public class EBookController {
	
	private static final Logger log = LoggerFactory.getLogger(EBookController.class);

	@Autowired
	EBookService eBookService;	
	
	/*
	안되는것 2020-07-05 : 카멜케이스 변환하여 Front에 표시 안됨 kakao한정
	  
	 */
	
	/*
	 엑셀 플랫폼 속성 통일하기
	 --- 5월기준 ---
	 로망띠끄 (할인 열 추가됨)
	 예스24 (bookID 열 추가됨)
	 */
	
	
//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list1(Model model, @RequestParam String platformType, Date setDate) throws Exception{
//		return "/ebook/list";
//	}
	
	// 플랫폼 별로 리스트 보여주기
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/list")
	public void list(Model model, @RequestParam String platformType, String p_year, String p_month) throws Exception{
		log.info("연도...: " + p_year);
		log.info("월...: " + p_month);
		model.addAttribute("platformType", platformType);
		
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

		
		log.info("현재 셋데이터" + setDate);
		// --------------------------------------------------------
		switch (platformType) {
		case "p_bookcube":
			List<Bookcube> bookList = eBookService.listBookcube(setDate);
			model.addAttribute("listBookcube", bookList);
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
	
	/*
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public void listPost(Model model, @RequestParam String platformType, String setDate) throws Exception{
		log.info("플랫폼타입???" + platformType);
		
		model.addAttribute("platformType", platformType);
		
		log.info("현재 셋데이터" + setDate);
		
		setDate = "2020-01-12";
		
		switch (platformType) {
		case "p_bookcube":
			List<Bookcube> bookList = eBookService.listBookcube(setDate);
			model.addAttribute("listBookcube", bookList);
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
	*/
	
	// 체크된 리스트 삭제하기
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String remove(Model model, String delete_ids, String p_type) throws Exception{
		log.info("플랫폼타입검사 : " + p_type);
		String[] deleteIdsArr = delete_ids.split(",");
		
		try {
			model.addAttribute("result", true);
			for (int i=0; i<deleteIdsArr.length; i++)
			{ 
				System.out.println("해당 튜플삭제.....................     = " + deleteIdsArr[i]); 
				switch (p_type) {
				case "p_bookcube":
					eBookService.removeBookcube(deleteIdsArr[i]);
					break;
				case "p_joara":
					eBookService.removeJoara(deleteIdsArr[i]);
					break;
				case "p_kakao":
					eBookService.removeKakao(deleteIdsArr[i]);
					break;
				case "p_kyobo":
					eBookService.removeKyobo(deleteIdsArr[i]);
					break;
				case "p_mrblue":
					eBookService.removeMrblue(deleteIdsArr[i]);
					break;
				case "p_munpia":
					eBookService.removeMunpia(deleteIdsArr[i]);
					break;
				case "p_naver":
					eBookService.removeNaver(deleteIdsArr[i]);
					break;
				case "p_ridibooks":
					eBookService.removeRidibooks(deleteIdsArr[i]);
					break;
				case "p_romance":
					eBookService.removeRomance(deleteIdsArr[i]);
					break;
				case "p_tocsoda":
					eBookService.removeTocsoda(deleteIdsArr[i]);
					break;
				case "p_winstore":
					eBookService.removeWinstore(deleteIdsArr[i]);
					break;
				case "p_yes24":
					eBookService.removeYes24(deleteIdsArr[i]);
					break;
				case "p_aladin":
					eBookService.removeAladin(deleteIdsArr[i]);
					break;

				default:
					break;
				}
			}
			
			// mv.addObject("result", true);
		} catch (Exception e) {
			model.addAttribute("result", false);
			//mv.addObject("result", false);
			e.printStackTrace();
		}
		
		// return mv;
		return "redirect:/ebook/list?platformType=" + p_type;
	
	}
	
	
	// 적용하기 누르면 리스트에있는 목록 수정시키기
	@RequestMapping(value = "/apply", method = RequestMethod.POST)
	public String apply(Model model, HttpServletRequest request, String p_type) throws Exception{
		
		int i;
		
		//log.info("찍힘? " + a.length + " 값은?" + a[1]);
		
		log.info("플랫폼타입검사 : " + p_type);
		
		try {
			
			switch (p_type) {
			case "p_bookcube":
				log.info("-------------------------------------------------------------");
				eBookService.allRemoveBookcube();
				Bookcube bookcube = new Bookcube();
				
				String[] productNameBookcube = request.getParameterValues("productName");
				String[] authorBookcube = request.getParameterValues("author");
				String[] brandBookcube = request.getParameterValues("brand");
				String[] salesCategoryBookcube = request.getParameterValues("salesCategory");
				String[] salesTypeBookcube = request.getParameterValues("salesType");
				String[] episodeBookcube = request.getParameterValues("episode");
				String[] netPriceBookcube = request.getParameterValues("netPrice");
				String[] salePriceBookcube = request.getParameterValues("salePrice");
				String[] discountBookcube = request.getParameterValues("discount");
				String[] feeBookcube = request.getParameterValues("fee");
				String[] netTargetPriceBookcube = request.getParameterValues("netTargetPrice");
				String[] deductionReservesEventBookcube = request.getParameterValues("deductionReservesEvent");
				String[] paymentBookcube = request.getParameterValues("payment");
				String[] bookIdBookcube = request.getParameterValues("bookId");
				String[] isbnBookcube = request.getParameterValues("isbn");
				String[] eIsbnBookcube = request.getParameterValues("eIsbn");
				String[] eIsbnEpubBookcube = request.getParameterValues("eIsbnEpub");
				String[] writerIdBookcube = request.getParameterValues("writerId");
				String[] setDateBookcube = request.getParameterValues("setDate");
				
				for(i=0; i<productNameBookcube.length; i++) {
					bookcube.setProductName(productNameBookcube[i]);
					bookcube.setAuthor(authorBookcube[i]);			
					bookcube.setBrand(brandBookcube[i]);			
					bookcube.setSalesCategory(salesCategoryBookcube[i]);			
					bookcube.setSalesType(salesTypeBookcube[i]);			
					bookcube.setEpisode(episodeBookcube[i]);			
					bookcube.setNetPrice(netPriceBookcube[i]);			
					bookcube.setSalePrice(salePriceBookcube[i]);			
					bookcube.setDiscount(discountBookcube[i]);			
					bookcube.setFee(feeBookcube[i]);			
					bookcube.setNetTargetPrice(netTargetPriceBookcube[i]);			
					bookcube.setDeductionReservesEvent(deductionReservesEventBookcube[i]);			
					bookcube.setPayment(paymentBookcube[i]);			
					bookcube.setBookId(bookIdBookcube[i]);			
					bookcube.setIsbn(isbnBookcube[i]);			
					bookcube.setEIsbn(eIsbnBookcube[i]);			
					bookcube.setEIsbnEpub(eIsbnEpubBookcube[i]);			
					bookcube.setWriterId(writerIdBookcube[i]);	
					bookcube.setSetDate(setDateBookcube[i]);	
					
					eBookService.registerBookcube(bookcube);
				}
				break;
				
			case "p_joara":
				eBookService.allRemoveJoara();
				Joara joara = new Joara();
				
				String[] brandJoara = request.getParameterValues("brand");
				String[] productNameJoara = request.getParameterValues("productName");
				String[] productCodeJoara = request.getParameterValues("productCode");
				String[] authorJoara = request.getParameterValues("author");
				String[] unitJoara = request.getParameterValues("unit");
				String[] saleAmountJoara = request.getParameterValues("saleAmount");
				String[] ratioJoara = request.getParameterValues("ratio");
				String[] paymentJoara = request.getParameterValues("payment");
				String[] paymentDateJoara = request.getParameterValues("paymentDate");
				String[] writerIdJoara = request.getParameterValues("writerId");
				String[] setDateJoara = request.getParameterValues("setDate");
				
				for(i=0; i<brandJoara.length; i++) {
					joara.setBrand(brandJoara[i]);
					joara.setProductName(productNameJoara[i]);
					joara.setProductCode(productCodeJoara[i]);
					joara.setAuthor(authorJoara[i]);
					joara.setUnit(unitJoara[i]);
					joara.setSaleAmount(saleAmountJoara[i]);
					joara.setRatio(ratioJoara[i]);
					joara.setPayment(paymentJoara[i]);
					joara.setPaymentDate(paymentDateJoara[i]);
					joara.setWriterId(writerIdJoara[i]);
					joara.setSetDate(setDateJoara[i]);
					
					eBookService.registerJoara(joara);
				}
				break;
				
			case "p_kakao":
				eBookService.allRemoveKakao();
				Kakao kakao = new Kakao();
				
				String[] brandKakao = request.getParameterValues("brand");
				String[] specialGeneral2Kakao = request.getParameterValues("specialGeneral2");
				String[] entrepreneurKakao = request.getParameterValues("entrepreneur");
				String[] entrepreneurIdKakao = request.getParameterValues("entrepreneurId");
				String[] publisherKakao = request.getParameterValues("publisher");
				String[] contractUidKakao = request.getParameterValues("contractUid");
				String[] contractCategoryKakao = request.getParameterValues("contractCategory");
				String[] contractKakao = request.getParameterValues("contract");
				String[] newContractKakao = request.getParameterValues("newContract");
				String[] categoryKakao = request.getParameterValues("category");
				String[] seriesIdKakao = request.getParameterValues("seriesId");
				String[] productNameKakao = request.getParameterValues("productName");
				String[] productCodeKakao = request.getParameterValues("productCode");
				String[] lableKakao = request.getParameterValues("lable");
				String[] authorKakao = request.getParameterValues("author");
				String[] isIsbnKakao = request.getParameterValues("isIsbn");
				String[] cpSettlementRateAndroidKakao = request.getParameterValues("cpSettlementRateAndroid");
				String[] cpSettlementRateIosKakao = request.getParameterValues("cpSettlementRateIos");
				String[] useTicketTypeKakao = request.getParameterValues("useTicketType");
				String[] useTicketAmountKakao = request.getParameterValues("useTicketAmount");
				String[] cacheSumKakao = request.getParameterValues("cacheSum");
				String[] cpp1Kakao = request.getParameterValues("cpp1");
				String[] cpl1Kakao = request.getParameterValues("cpl1");
				String[] cp1_1Kakao = request.getParameterValues("cp1_1");
				String[] cp2_1Kakao = request.getParameterValues("cp2_1");
				String[] cp3_1Kakao = request.getParameterValues("cp3_1");
				String[] cp7_1Kakao = request.getParameterValues("cp7_1");
				String[] cp8_1Kakao = request.getParameterValues("cp8_1");
				String[] cp9_1Kakao = request.getParameterValues("cp9_1");
				String[] eventCache1Kakao = request.getParameterValues("eventCache1");
				String[] originalSumKakao = request.getParameterValues("originalSum");
				String[] cpp2Kakao = request.getParameterValues("cpp2");
				String[] cpl2Kakao = request.getParameterValues("cpl2");
				String[] cp1_2Kakao = request.getParameterValues("cp1_2");
				String[] cp2_2Kakao = request.getParameterValues("cp2_2");
				String[] cp3_2Kakao = request.getParameterValues("cp3_2");
				String[] cp7_2Kakao = request.getParameterValues("cp7_2");
				String[] cp8_2Kakao = request.getParameterValues("cp8_2");
				String[] cp9_2Kakao = request.getParameterValues("cp9_2");
				String[] eventCache2Kakao = request.getParameterValues("eventCache2");
				String[] valueSupplyKakao = request.getParameterValues("valueSupply");
				String[] taxAmountKakao = request.getParameterValues("taxAmount");
				String[] paymentKakao = request.getParameterValues("payment");
				String[] writerIdKakao = request.getParameterValues("writerId");
				String[] setDateKakao = request.getParameterValues("setDate");
				
				for(i=0; i<brandKakao.length; i++) {
					kakao.setBrand(brandKakao[i]);
					kakao.setSpecialGeneral2(specialGeneral2Kakao[i]);
					kakao.setEntrepreneur(entrepreneurKakao[i]);
					kakao.setEntrepreneurId(entrepreneurIdKakao[i]);
					kakao.setPublisher(publisherKakao[i]);
					kakao.setContractUid(contractUidKakao[i]);
					kakao.setContractCategory(contractCategoryKakao[i]);
					kakao.setContract(contractKakao[i]);
					kakao.setNewContract(newContractKakao[i]);
					kakao.setCategory(categoryKakao[i]);
					kakao.setSeriesId(seriesIdKakao[i]);
					kakao.setProductName(productNameKakao[i]);
					kakao.setProductCode(productCodeKakao[i]);
					kakao.setLable(lableKakao[i]);
					kakao.setAuthor(authorKakao[i]);
					kakao.setIsIsbn(isIsbnKakao[i]);
					kakao.setCpSettlementRateAndroid(cpSettlementRateAndroidKakao[i]);
					kakao.setCpSettlementRateIos(cpSettlementRateIosKakao[i]);
					kakao.setUseTicketType(useTicketTypeKakao[i]);
					kakao.setUseTicketAmount(useTicketAmountKakao[i]);
					kakao.setCacheSum(cacheSumKakao[i]);
					kakao.setCpp1(cpp1Kakao[i]);
					kakao.setCpl1(cpl1Kakao[i]);
					kakao.setCp1_1(cp1_1Kakao[i]);
					kakao.setCp2_1(cp2_1Kakao[i]);
					kakao.setCp3_1(cp3_1Kakao[i]);
					kakao.setCp7_1(cp7_1Kakao[i]);
					kakao.setCp8_1(cp8_1Kakao[i]);
					kakao.setCp9_1(cp9_1Kakao[i]);
					kakao.setEventCache1(eventCache1Kakao[i]);
					kakao.setOriginalSum(originalSumKakao[i]);
					kakao.setCpp2(cpp2Kakao[i]);
					kakao.setCpl2(cpl2Kakao[i]);
					kakao.setCp1_2(cp1_2Kakao[i]);
					kakao.setCp2_2(cp2_2Kakao[i]);
					kakao.setCp3_2(cp3_2Kakao[i]);
					kakao.setCp7_2(cp7_2Kakao[i]);
					kakao.setCp8_2(cp8_2Kakao[i]);
					kakao.setCp9_2(cp9_2Kakao[i]);
					kakao.setEventCache2(eventCache2Kakao[i]);
					kakao.setValueSupply(valueSupplyKakao[i]);
					kakao.setTaxAmount(taxAmountKakao[i]);
					kakao.setPayment(paymentKakao[i]);
					kakao.setWriterId(writerIdKakao[i]);
					kakao.setSetDate(setDateKakao[i]);
					
					eBookService.registerKakao(kakao);
				}
				break;
				
			case "p_kyobo":
				eBookService.allRemoveKyobo();
				Kyobo kyobo = new Kyobo();
				
				String[] brandKyobo = request.getParameterValues("brand");
				String[] salesCategoryKyobo = request.getParameterValues("salesCategory");
				String[] salesTypeKyobo = request.getParameterValues("salesType");
				String[] productKyobo = request.getParameterValues("product");
				String[] mainSectionKyobo = request.getParameterValues("mainSection");
				String[] bookBarcodeKyobo = request.getParameterValues("bookBarcode");
				String[] isbnKyobo = request.getParameterValues("isbn");
				String[] epubIsbnKyobo = request.getParameterValues("epubIsbn");
				String[] pdfIsbnKyobo = request.getParameterValues("pdfIsbn");
				String[] productNameKyobo = request.getParameterValues("productName");
				String[] episodeKyobo = request.getParameterValues("episode");
				String[] authorKyobo = request.getParameterValues("author");
				String[] netPriceKyobo = request.getParameterValues("netPrice");
				String[] salePriceKyobo = request.getParameterValues("salePrice");
				String[] saleRateKyobo = request.getParameterValues("saleRate");
				String[] downloadKyobo = request.getParameterValues("download");
				String[] netTargetPriceKyobo = request.getParameterValues("netTargetPrice");
				String[] netTargetPriceAmountKyobo = request.getParameterValues("netTargetPriceAmount");
				String[] paymentKyobo = request.getParameterValues("payment");
				String[] accountingRatesKyobo = request.getParameterValues("accountingRates");
				String[] bookNumberKyobo = request.getParameterValues("bookNumber");
				String[] writerIdKyobo = request.getParameterValues("writerId");
				String[] setDateKyobo = request.getParameterValues("setDate");
				
				for(i=0; i<brandKyobo.length; i++) {
					kyobo.setBrand(brandKyobo[i]);
					kyobo.setSalesCategory(salesCategoryKyobo[i]);
					kyobo.setSalesType(salesTypeKyobo[i]);
					kyobo.setProduct(productKyobo[i]);
					kyobo.setMainSection(mainSectionKyobo[i]);
					kyobo.setBookBarcode(bookBarcodeKyobo[i]);
					kyobo.setIsbn(isbnKyobo[i]);
					kyobo.setEpubIsbn(epubIsbnKyobo[i]);
					kyobo.setPdfIsbn(pdfIsbnKyobo[i]);
					kyobo.setProductName(productNameKyobo[i]);
					kyobo.setEpisode(episodeKyobo[i]);
					kyobo.setAuthor(authorKyobo[i]);
					kyobo.setNetPrice(netPriceKyobo[i]);
					kyobo.setSalePrice(salePriceKyobo[i]);
					kyobo.setSaleRate(saleRateKyobo[i]);
					kyobo.setDownload(downloadKyobo[i]);
					kyobo.setNetTargetPrice(netTargetPriceKyobo[i]);
					kyobo.setNetTargetPriceAmount(netTargetPriceAmountKyobo[i]);
					kyobo.setPayment(paymentKyobo[i]);
					kyobo.setAccountingRates(accountingRatesKyobo[i]);
					kyobo.setBookNumber(bookNumberKyobo[i]);
					kyobo.setWriterId(writerIdKyobo[i]);
					kyobo.setSetDate(setDateKyobo[i]);
					
					eBookService.registerKyobo(kyobo);
				}
				break;
				
			case "p_mrblue":
				eBookService.allRemoveMrblue();
				Mrblue mrblue = new Mrblue();
				
				String[] brandMrblue = request.getParameterValues("brand");
				String[] dateMrblue = request.getParameterValues("date");
				String[] productNameMrblue = request.getParameterValues("productName");
				String[] authorMrblue = request.getParameterValues("author");
				String[] codeMrblue = request.getParameterValues("code");
				String[] fixedNumMrblue = request.getParameterValues("fixedNum");
				String[] fixedSalesMrblue = request.getParameterValues("fixedSales");
				String[] fixedPaymentMrblue = request.getParameterValues("fixedPayment");
				String[] aAppOnerentalNumMrblue = request.getParameterValues("aAppOnerentalNum");
				String[] aAppOnerentalUseMrblue = request.getParameterValues("aAppOnerentalUse");
				String[] aAppOnerentalPaymentMrblue = request.getParameterValues("aAppOnerentalPayment");
				String[] aAppOnemngrNumMrblue = request.getParameterValues("aAppOnemngrNum");
				String[] aAppOnemngrUseMrblue = request.getParameterValues("aAppOnemngrUse");
				String[] aAppOnemngrPaymentMrblue = request.getParameterValues("aAppOnemngrPayment");
				String[] aAppAllrentalNumMrblue = request.getParameterValues("aAppAllrentalNum");
				String[] aAppAllrentalUseMrblue = request.getParameterValues("aAppAllrentalUse");
				String[] aAppAllrentalPaymentMrblue = request.getParameterValues("aAppAllrentalPayment");
				String[] aAppAllmngrNumMrblue = request.getParameterValues("aAppAllmngrNum");
				String[] aAppAllmngrUseMrblue = request.getParameterValues("aAppAllmngrUse");
				String[] aAppAllmngrPaymentMrblue = request.getParameterValues("aAppAllmngrPayment");
				String[] iAppOnerentalNumMrblue = request.getParameterValues("iAppOnerentalNum");
				String[] iAppOnerentalUseMrblue = request.getParameterValues("iAppOnerentalUse");
				String[] iAppOnerentalPaymentMrblue = request.getParameterValues("iAppOnerentalPayment");
				String[] iAppOnemngrNumMrblue = request.getParameterValues("iAppOnemngrNum");
				String[] iAppOnemngrUseMrblue = request.getParameterValues("iAppOnemngrUse");
				String[] iAppOnemngrPaymentMrblue = request.getParameterValues("iAppOnemngrPayment");
				String[] iAppAllrentalNumMrblue = request.getParameterValues("iAppAllrentalNum");
				String[] iAppAllrentalUseMrblue = request.getParameterValues("iAppAllrentalUse");
				String[] iAppAllrentalPaymentMrblue = request.getParameterValues("iAppAllrentalPayment");
				String[] iAppAllmngrNumMrblue = request.getParameterValues("iAppAllmngrNum");
				String[] iAppAllmngrUseMrblue = request.getParameterValues("iAppAllmngrUse");
				String[] iAppAllmngrPaymentMrblue = request.getParameterValues("iAppAllmngrPayment");
				String[] amountNumMrblue = request.getParameterValues("amountNum");
				String[] amountUseMrblue = request.getParameterValues("amountUse");
				String[] amountPaymentMrblue = request.getParameterValues("amountPayment");
				String[] amountSettlementMrblue = request.getParameterValues("amountSettlement");
				String[] salesMrblue = request.getParameterValues("sales");
				String[] paymentMrblue = request.getParameterValues("payment");
				String[] writerIdMrblue = request.getParameterValues("writerId");
				String[] setDateMrblue = request.getParameterValues("setDate");
				
				for(i=0; i<brandMrblue.length; i++) {
					mrblue.setBrand(brandMrblue[i]);
					mrblue.setDate(dateMrblue[i]);
					mrblue.setProductName(productNameMrblue[i]);
					mrblue.setAuthor(authorMrblue[i]);
					mrblue.setCode(codeMrblue[i]);
					mrblue.setFixedNum(fixedNumMrblue[i]);
					mrblue.setFixedSales(fixedSalesMrblue[i]);
					mrblue.setFixedPayment(fixedPaymentMrblue[i]);
					mrblue.setAAppOnerentalNum(aAppOnerentalNumMrblue[i]);
					mrblue.setAAppOnerentalUse(aAppOnerentalUseMrblue[i]);
					mrblue.setAAppOnerentalPayment(aAppOnerentalPaymentMrblue[i]);
					mrblue.setAAppOnemngrNum(aAppOnemngrNumMrblue[i]);
					mrblue.setAAppOnemngrUse(aAppOnemngrUseMrblue[i]);
					mrblue.setAAppOnemngrPayment(aAppOnemngrPaymentMrblue[i]);
					mrblue.setAAppAllrentalNum(aAppAllrentalNumMrblue[i]);
					mrblue.setAAppAllrentalUse(aAppAllrentalUseMrblue[i]);
					mrblue.setAAppAllrentalPayment(aAppAllrentalPaymentMrblue[i]);
					mrblue.setAAppAllmngrNum(aAppAllmngrNumMrblue[i]);
					mrblue.setAAppAllmngrUse(aAppAllmngrUseMrblue[i]);
					mrblue.setAAppAllmngrPayment(aAppAllmngrPaymentMrblue[i]);
					mrblue.setIAppOnerentalNum(iAppOnerentalNumMrblue[i]);
					mrblue.setIAppOnerentalUse(iAppOnerentalUseMrblue[i]);
					mrblue.setIAppOnerentalPayment(iAppOnerentalPaymentMrblue[i]);
					mrblue.setIAppOnemngrNum(iAppOnemngrNumMrblue[i]);
					mrblue.setIAppOnemngrUse(iAppOnemngrUseMrblue[i]);
					mrblue.setIAppOnemngrPayment(iAppOnemngrPaymentMrblue[i]);
					mrblue.setIAppAllrentalNum(iAppAllrentalNumMrblue[i]);
					mrblue.setIAppAllrentalUse(iAppAllrentalUseMrblue[i]);
					mrblue.setIAppAllrentalPayment(iAppAllrentalPaymentMrblue[i]);
					mrblue.setIAppAllmngrNum(iAppAllmngrNumMrblue[i]);
					mrblue.setIAppAllmngrUse(iAppAllmngrUseMrblue[i]);
					mrblue.setIAppAllmngrPayment(iAppAllmngrPaymentMrblue[i]);
					mrblue.setAmountNum(amountNumMrblue[i]);
					mrblue.setAmountUse(amountUseMrblue[i]);
					mrblue.setAmountPayment(amountPaymentMrblue[i]);
					mrblue.setAmountSettlement(amountSettlementMrblue[i]);
					mrblue.setSales(salesMrblue[i]);
					mrblue.setPayment(paymentMrblue[i]);
					mrblue.setWriterId(writerIdMrblue[i]);
					mrblue.setSetDate(setDateMrblue[i]);
					
					eBookService.registerMrblue(mrblue);
				}
				break;
				
			case "p_munpia":
				eBookService.allRemoveMunpia();
				Munpia munpia = new Munpia();
				
				String[] authorMunpia = request.getParameterValues("author");
				String[] brandMunpia = request.getParameterValues("brand");
				String[] dateMunpia = request.getParameterValues("date");
				String[] classificationMunpia = request.getParameterValues("classification");
				String[] productNameMunpia = request.getParameterValues("productName");
				String[] purchaseMunpia = request.getParameterValues("purchase");
				String[] purchaseCancelMunpia = request.getParameterValues("purchaseCancel");
				String[] rentalMunpia = request.getParameterValues("rental");
				String[] rentalCencelMunpia = request.getParameterValues("rentalCencel");
				String[] totalSalesMunpia = request.getParameterValues("totalSales");
				String[] totalSalesCencelMunpia = request.getParameterValues("totalSalesCencel");
				String[] realSaleMunpia = request.getParameterValues("realSale");
				String[] subPaymentMunpia = request.getParameterValues("subPayment");
				String[] iosMunpia = request.getParameterValues("ios");
				String[] paymentMunpia = request.getParameterValues("payment");
				String[] writerIdMunpia = request.getParameterValues("writerId");
				String[] setDateMunpia = request.getParameterValues("setDate");
				
				for(i=0; i<authorMunpia.length; i++) {
					munpia.setAuthor(authorMunpia[i]);
					munpia.setBrand(brandMunpia[i]);
					munpia.setDate(dateMunpia[i]);
					munpia.setClassification(classificationMunpia[i]);
					munpia.setProductName(productNameMunpia[i]);
					munpia.setPurchase(purchaseMunpia[i]);
					munpia.setPurchaseCancel(purchaseCancelMunpia[i]);
					munpia.setRental(rentalMunpia[i]);
					munpia.setRentalCencel(rentalCencelMunpia[i]);
					munpia.setTotalSales(totalSalesMunpia[i]);
					munpia.setTotalSalesCencel(totalSalesCencelMunpia[i]);
					munpia.setRealSale(realSaleMunpia[i]);
					munpia.setSubPayment(subPaymentMunpia[i]);
					munpia.setIos(iosMunpia[i]);
					munpia.setPayment(paymentMunpia[i]);
					munpia.setWriterId(writerIdMunpia[i]);
					munpia.setSetDate(setDateMunpia[i]);
					
					eBookService.registerMunpia(munpia);
				}
				break;
				
			case "p_naver":
				eBookService.allRemoveNaver();
				Naver naver = new Naver();
				
				String[] productNameNaver = request.getParameterValues("productName");
				String[] productNoNaver = request.getParameterValues("productNo");
				String[] providerCodeNaver = request.getParameterValues("providerCode");
				String[] cpNameNaver = request.getParameterValues("cpName");
				String[] authorNaver = request.getParameterValues("author");
				String[] brandNaver = request.getParameterValues("brand");
				String[] ageLimitNaver = request.getParameterValues("ageLimit");
				String[] serviceDayNaver = request.getParameterValues("serviceDay");
				String[] rentalNumberNaver = request.getParameterValues("rentalNumber");
				String[] rentalNonpyNaver = request.getParameterValues("rentalNonpy");
				String[] rentalFreeNaver = request.getParameterValues("rentalFree");
				String[] rentalPaidCookieNaver = request.getParameterValues("rentalPaidCookie");
				String[] rentalFreeCookieNaver = request.getParameterValues("rentalFreeCookie");
				String[] rentalPaidICookieNaver = request.getParameterValues("rentalPaidICookie");
				String[] rentalFreeICookieNaver = request.getParameterValues("rentalFreeICookie");
				String[] ownNumberNaver = request.getParameterValues("ownNumber");
				String[] ownNonpyNaver = request.getParameterValues("ownNonpy");
				String[] ownFreeNaver = request.getParameterValues("ownFree");
				String[] ownPaidCookieNaver = request.getParameterValues("ownPaidCookie");
				String[] ownFreeCookieNaver = request.getParameterValues("ownFreeCookie");
				String[] ownPaidICookieNaver = request.getParameterValues("ownPaidICookie");
				String[] ownFreeICookieNaver = request.getParameterValues("ownFreeICookie");
				String[] cookieRentalNumberNaver = request.getParameterValues("cookieRentalNumber");
				String[] cookieRentalPaidNaver = request.getParameterValues("cookieRentalPaid");
				String[] cookieRentalFreeNaver = request.getParameterValues("cookieRentalFree");
				String[] cookieOwnNumberNaver = request.getParameterValues("cookieOwnNumber");
				String[] cookieOwnPaidNaver = request.getParameterValues("cookieOwnPaid");
				String[] cookieOwnFreeNaver = request.getParameterValues("cookieOwnFree");
				String[] iCookieRentalNumberNaver = request.getParameterValues("iCookieRentalNumber");
				String[] iCookieRentalPaidNaver = request.getParameterValues("iCookieRentalPaid");
				String[] iCookieRentalFreeNaver = request.getParameterValues("iCookieRentalFree");
				String[] iCookieOwnNumberNaver = request.getParameterValues("iCookieOwnNumber");
				String[] iCookieOwnPaidNaver = request.getParameterValues("iCookieOwnPaid");
				String[] iCookieOwnFreeNaver = request.getParameterValues("iCookieOwnFree");
				String[] iCookieFeeNaver = request.getParameterValues("iCookieFee");
				String[] sumNaver = request.getParameterValues("sum");
				String[] paymentNaver = request.getParameterValues("payment");
				String[] writerIdNaver = request.getParameterValues("writerId");
				String[] setDateNaver = request.getParameterValues("setDate");
				
				for(i=0; i<productNameNaver.length; i++) {
					naver.setProductName(productNameNaver[i]);
					naver.setProductNo(productNoNaver[i]);
					naver.setProviderCode(providerCodeNaver[i]);
					naver.setCpName(cpNameNaver[i]);
					naver.setAuthor(authorNaver[i]);
					naver.setBrand(brandNaver[i]);
					naver.setAgeLimit(ageLimitNaver[i]);
					naver.setServiceDay(serviceDayNaver[i]);
					naver.setRentalNumber(rentalNumberNaver[i]);
					naver.setRentalNonpy(rentalNonpyNaver[i]);
					naver.setRentalFree(rentalFreeNaver[i]);
					naver.setRentalPaidCookie(rentalPaidCookieNaver[i]);
					naver.setRentalFreeCookie(rentalFreeCookieNaver[i]);
					naver.setRentalPaidICookie(rentalPaidICookieNaver[i]);
					naver.setRentalFreeICookie(rentalFreeICookieNaver[i]);
					naver.setOwnNumber(ownNumberNaver[i]);
					naver.setOwnNonpy(ownNonpyNaver[i]);
					naver.setOwnFree(ownFreeNaver[i]);
					naver.setOwnPaidCookie(ownPaidCookieNaver[i]);
					naver.setOwnFreeCookie(ownFreeCookieNaver[i]);
					naver.setOwnPaidICookie(ownPaidICookieNaver[i]);
					naver.setOwnFreeICookie(ownFreeICookieNaver[i]);
					naver.setCookieRentalNumber(cookieRentalNumberNaver[i]);
					naver.setCookieRentalPaid(cookieRentalPaidNaver[i]);
					naver.setCookieRentalFree(cookieRentalFreeNaver[i]);
					naver.setCookieOwnNumber(cookieOwnNumberNaver[i]);
					naver.setCookieOwnPaid(cookieOwnPaidNaver[i]);
					naver.setCookieOwnFree(cookieOwnFreeNaver[i]);
					naver.setICookieRentalNumber(iCookieRentalNumberNaver[i]);
					naver.setICookieRentalPaid(iCookieRentalPaidNaver[i]);
					naver.setICookieRentalFree(iCookieRentalFreeNaver[i]);
					naver.setICookieOwnNumber(iCookieOwnNumberNaver[i]);
					naver.setICookieOwnPaid(iCookieOwnPaidNaver[i]);
					naver.setICookieOwnFree(iCookieOwnFreeNaver[i]);
					naver.setICookieFee(iCookieFeeNaver[i]);
					naver.setSum(sumNaver[i]);
					naver.setPayment(paymentNaver[i]);
					naver.setWriterId(writerIdNaver[i]);
					naver.setSetDate(setDateNaver[i]);
					
					eBookService.registerNaver(naver);
				}
				break;
				
			case "p_ridibooks":
				eBookService.allRemoveRidibooks();
				Ridibooks ridibooks = new Ridibooks();
				
				String[] seriesIdRidibooks = request.getParameterValues("seriesId");
				String[] bookIdRidibooks = request.getParameterValues("bookId");
				String[] productNameRidibooks = request.getParameterValues("productName");
				String[] episodeRidibooks = request.getParameterValues("episode");
				String[] authorRidibooks = request.getParameterValues("author");
				String[] translatorsRidibooks = request.getParameterValues("translators");
				String[] pictureRidibooks = request.getParameterValues("picture");
				String[] brandRidibooks = request.getParameterValues("brand");
				String[] seriesNameRidibooks = request.getParameterValues("seriesName");
				String[] ebooksPriceRidibooks = request.getParameterValues("ebooksPrice");
				String[] singleSaleSalesRidibooks = request.getParameterValues("singleSaleSales");
				String[] singleSaleTicketRidibooks = request.getParameterValues("singleSaleTicket");
				String[] singleFreeTicketRidibooks = request.getParameterValues("singleFreeTicket");
				String[] rentalAmountRidibooks = request.getParameterValues("rentalAmount");
				String[] rentalTicketRidibooks = request.getParameterValues("rentalTicket");
				String[] freeDownAmountRidibooks = request.getParameterValues("freeDownAmount");
				String[] freeDownTicketRidibooks = request.getParameterValues("freeDownTicket");
				String[] setSaleAmountRidibooks = request.getParameterValues("setSaleAmount");
				String[] setSaleTicketRidibooks = request.getParameterValues("setSaleTicket");
				String[] setRentalAmountRidibooks = request.getParameterValues("setRentalAmount");
				String[] setRentalTicketRidibooks = request.getParameterValues("setRentalTicket");
				String[] cancelAmountRidibooks = request.getParameterValues("cancelAmount");
				String[] cancelTicketRidibooks = request.getParameterValues("cancelTicket");
				String[] cancelSingleSaleSalesRidibooks = request.getParameterValues("cancelSingleSaleSales");
				String[] cancelSingleSaleTicketRidibooks = request.getParameterValues("cancelSingleSaleTicket");
				String[] cancelRentalAmountRidibooks = request.getParameterValues("cancelRentalAmount");
				String[] cancelRentalTicketRidibooks = request.getParameterValues("cancelRentalTicket");
				String[] cancelFreeDownAmountRidibooks = request.getParameterValues("cancelFreeDownAmount");
				String[] cancelFreeDownTicketRidibooks = request.getParameterValues("cancelFreeDownTicket");
				String[] cancelSetSaleAmountRidibooks = request.getParameterValues("cancelSetSaleAmount");
				String[] cancelSetSaleTicketRidibooks = request.getParameterValues("cancelSetSaleTicket");
				String[] cancelSetRentalAmountRidibooks = request.getParameterValues("cancelSetRentalAmount");
				String[] cancelSetRentalTicketRidibooks = request.getParameterValues("cancelSetRentalTicket");
				String[] paymentRidibooks = request.getParameterValues("payment");
				String[] paperbookIsbn10Ridibooks = request.getParameterValues("paperbookIsbn10");
				String[] paperbookIsbn13Ridibooks = request.getParameterValues("paperbookIsbn13");
				String[] ebookIsbn10Ridibooks = request.getParameterValues("ebookIsbn10");
				String[] ebookIsbn13Ridibooks = request.getParameterValues("ebookIsbn13");
				String[] cpManageIdRidibooks = request.getParameterValues("cpManageId");
				String[] category1Ridibooks = request.getParameterValues("category1");
				String[] category2Ridibooks = request.getParameterValues("category2");
				String[] writerIdRidibooks = request.getParameterValues("writerId");
				String[] setDateRidibooks = request.getParameterValues("setDate");
				
				for(i=0; i<seriesIdRidibooks.length; i++) {
					ridibooks.setSeriesId(seriesIdRidibooks[i]);
					ridibooks.setBookId(bookIdRidibooks[i]);
					ridibooks.setProductName(productNameRidibooks[i]);
					ridibooks.setEpisode(episodeRidibooks[i]);
					ridibooks.setAuthor(authorRidibooks[i]);
					ridibooks.setTranslators(translatorsRidibooks[i]);
					ridibooks.setPicture(pictureRidibooks[i]);
					ridibooks.setBrand(brandRidibooks[i]);
					ridibooks.setSeriesName(seriesNameRidibooks[i]);
					ridibooks.setEbooksPrice(ebooksPriceRidibooks[i]);
					ridibooks.setSingleSaleSales(singleSaleSalesRidibooks[i]);
					ridibooks.setSingleSaleTicket(singleSaleTicketRidibooks[i]);
					ridibooks.setSingleFreeTicket(singleFreeTicketRidibooks[i]);
					ridibooks.setRentalAmount(rentalAmountRidibooks[i]);
					ridibooks.setRentalTicket(rentalTicketRidibooks[i]);
					ridibooks.setFreeDownAmount(freeDownAmountRidibooks[i]);
					ridibooks.setFreeDownTicket(freeDownTicketRidibooks[i]);
					ridibooks.setSetSaleAmount(setSaleAmountRidibooks[i]);
					ridibooks.setSetSaleTicket(setSaleTicketRidibooks[i]);
					ridibooks.setSetRentalAmount(setRentalAmountRidibooks[i]);
					ridibooks.setSetRentalTicket(setRentalTicketRidibooks[i]);
					ridibooks.setCancelAmount(cancelAmountRidibooks[i]);
					ridibooks.setCancelTicket(cancelTicketRidibooks[i]);
					ridibooks.setCancelSingleSaleSales(cancelSingleSaleSalesRidibooks[i]);
					ridibooks.setCancelSingleSaleTicket(cancelSingleSaleTicketRidibooks[i]);
					ridibooks.setCancelRentalAmount(cancelRentalAmountRidibooks[i]);
					ridibooks.setCancelRentalTicket(cancelRentalTicketRidibooks[i]);
					ridibooks.setCancelFreeDownAmount(cancelFreeDownAmountRidibooks[i]);
					ridibooks.setCancelFreeDownTicket(cancelFreeDownTicketRidibooks[i]);
					ridibooks.setCancelSetSaleAmount(cancelSetSaleAmountRidibooks[i]);
					ridibooks.setCancelSetSaleTicket(cancelSetSaleTicketRidibooks[i]);
					ridibooks.setCancelSetRentalAmount(cancelSetRentalAmountRidibooks[i]);
					ridibooks.setCancelSetRentalTicket(cancelSetRentalTicketRidibooks[i]);
					ridibooks.setPayment(paymentRidibooks[i]);
					ridibooks.setPaperbookIsbn10(paperbookIsbn10Ridibooks[i]);
					ridibooks.setPaperbookIsbn13(paperbookIsbn13Ridibooks[i]);
					ridibooks.setEbookIsbn10(ebookIsbn10Ridibooks[i]);
					ridibooks.setEbookIsbn13(ebookIsbn13Ridibooks[i]);
					ridibooks.setCpManageId(cpManageIdRidibooks[i]);
					ridibooks.setCategory1(category1Ridibooks[i]);
					ridibooks.setCategory2(category2Ridibooks[i]);
					ridibooks.setWriterId(writerIdRidibooks[i]);
					ridibooks.setSetDate(setDateRidibooks[i]);
					
					eBookService.registerRidibooks(ridibooks);
				}
				break;
				
			case "p_romance":
				eBookService.allRemoveRomance();
				Romance romance = new Romance();
				
				String[] bookCodeRomance = request.getParameterValues("bookCode");
				String[] productNameRomance = request.getParameterValues("productName");
				String[] brandRomance = request.getParameterValues("brand");
				String[] authorRomance = request.getParameterValues("author");
				String[] saleRateRomance = request.getParameterValues("saleRate");
				String[] internetSalesRomance = request.getParameterValues("internetSales");
				String[] paymentRomance = request.getParameterValues("payment");
				String[] regDateRomance = request.getParameterValues("regDate");
				String[] dealerRomance = request.getParameterValues("dealer");
				String[] isbnRomance = request.getParameterValues("isbn");
				String[] writerIdRomance = request.getParameterValues("writerId");
				String[] setDateRomance = request.getParameterValues("setDate");
				
				for(i=0; i<bookCodeRomance.length; i++) {
					romance.setBookCode(bookCodeRomance[i]);
					romance.setProductName(productNameRomance[i]);
					romance.setBrand(brandRomance[i]);
					romance.setAuthor(authorRomance[i]);
					romance.setSaleRate(saleRateRomance[i]);
					romance.setInternetSales(internetSalesRomance[i]);
					romance.setPayment(paymentRomance[i]);
					romance.setRegDate(regDateRomance[i]);
					romance.setDealer(dealerRomance[i]);
					romance.setIsbn(isbnRomance[i]);
					romance.setWriterId(writerIdRomance[i]);
					romance.setSetDate(setDateRomance[i]);
					
					eBookService.registerRomance(romance);
				}
				break;
				
			case "p_tocsoda":
				eBookService.allRemoveTocsoda();
				Tocsoda tocsoda = new Tocsoda();
				
				String[] productBacordTocsoda = request.getParameterValues("productBacord");
				String[] productNameTocsoda = request.getParameterValues("productName");
				String[] authorTocsoda = request.getParameterValues("author");
				String[] brandTocsoda = request.getParameterValues("brand");
				String[] pcTocsoda = request.getParameterValues("pc");
				String[] androidTocsoda = request.getParameterValues("android");
				String[] iosTocsoda = request.getParameterValues("ios");
				String[] iosIapTocsoda = request.getParameterValues("iosIap");
				String[] sumTocsoda = request.getParameterValues("sum");
				String[] paymentTocsoda = request.getParameterValues("payment");
				String[] writerIdTocsoda = request.getParameterValues("writerId");
				String[] setDateTocsoda = request.getParameterValues("setDate");
				
				for(i=0; i<productBacordTocsoda.length; i++) {
					tocsoda.setProductBacord(productBacordTocsoda[i]);
					tocsoda.setProductName(productNameTocsoda[i]);
					tocsoda.setAuthor(authorTocsoda[i]);
					tocsoda.setBrand(brandTocsoda[i]);
					tocsoda.setPc(pcTocsoda[i]);
					tocsoda.setAndroid(androidTocsoda[i]);
					tocsoda.setIos(iosTocsoda[i]);
					tocsoda.setIosIap(iosIapTocsoda[i]);
					tocsoda.setSum(sumTocsoda[i]);
					tocsoda.setPayment(paymentTocsoda[i]);
					tocsoda.setWriterId(writerIdTocsoda[i]);
					tocsoda.setSetDate(setDateTocsoda[i]);
					
					eBookService.registerTocsoda(tocsoda);
				}
				break;
				
			case "p_winstore":
				eBookService.allRemoveWinstore();
				Winstore winstore = new Winstore();
				
				String[] partnerNameWinstore = request.getParameterValues("partnerName");
				String[] partnerIdWinstore = request.getParameterValues("partnerId");
				String[] entrepreneurWinstore = request.getParameterValues("entrepreneur");
				String[] channelProductIdWinstore = request.getParameterValues("channelProductId");
				String[] channelProductNameWinstore = request.getParameterValues("channelProductName");
				String[] productNameWinstore = request.getParameterValues("productName");
				String[] episodeWinstore = request.getParameterValues("episode");
				String[] productIdWinstore = request.getParameterValues("productId");
				String[] secondSettlementStatusWinstore = request.getParameterValues("secondSettlementStatus");
				String[] categoryWinstore = request.getParameterValues("category");
				String[] brandWinstore = request.getParameterValues("brand");
				String[] authorWinstore = request.getParameterValues("author");
				String[] pictureAuthorWinstore = request.getParameterValues("pictureAuthor");
				String[] typeWinstore = request.getParameterValues("type");
				String[] saleRateWinstore = request.getParameterValues("saleRate");
				String[] cencelRateWinstore = request.getParameterValues("cencelRate");
				String[] settlementTargetAmountWinstore = request.getParameterValues("settlementTargetAmount");
				String[] salesRateWinstore = request.getParameterValues("salesRate");
				String[] salesAmountWinstore = request.getParameterValues("salesAmount");
				String[] cencelsRateWinstore = request.getParameterValues("cencelsRate");
				String[] cencelsAmountWinstore = request.getParameterValues("cencelsAmount");
				String[] sumWinstore = request.getParameterValues("sum");
				String[] customerPaymentWinstore = request.getParameterValues("customerPayment");
				String[] customerPaymentReceiptWinstore = request.getParameterValues("customerPaymentReceipt");
				String[] customerPaymentEtcWinstore = request.getParameterValues("customerPaymentEtc");
				String[] customerPaymentServiceWinstore = request.getParameterValues("customerPaymentService");
				String[] customerPaymentServiceEtcWinstore = request.getParameterValues("customerPaymentServiceEtc");
				String[] serviceDeductionWinstore = request.getParameterValues("serviceDeduction");
				String[] paymentWinstore = request.getParameterValues("payment");
				String[] paymentTypeWinstore = request.getParameterValues("paymentType");
				String[] writerIdWinstore = request.getParameterValues("writerId");
				String[] setDateWinstore = request.getParameterValues("setDate");
				
				for(i=0; i<partnerNameWinstore.length; i++) {
					winstore.setPartnerName(partnerNameWinstore[i]);
					winstore.setPartnerId(partnerIdWinstore[i]);
					winstore.setEntrepreneur(entrepreneurWinstore[i]);
					winstore.setChannelProductId(channelProductIdWinstore[i]);
					winstore.setChannelProductName(channelProductNameWinstore[i]);
					winstore.setProductName(productNameWinstore[i]);
					winstore.setEpisode(episodeWinstore[i]);
					winstore.setProductId(productIdWinstore[i]);
					winstore.setSecondSettlementStatus(secondSettlementStatusWinstore[i]);
					winstore.setCategory(categoryWinstore[i]);
					winstore.setBrand(brandWinstore[i]);
					winstore.setAuthor(authorWinstore[i]);
					winstore.setPictureAuthor(pictureAuthorWinstore[i]);
					winstore.setType(typeWinstore[i]);
					winstore.setSaleRate(saleRateWinstore[i]);
					winstore.setCencelRate(cencelRateWinstore[i]);
					winstore.setSettlementTargetAmount(settlementTargetAmountWinstore[i]);
					winstore.setSalesRate(salesRateWinstore[i]);
					winstore.setSalesAmount(salesAmountWinstore[i]);
					winstore.setCencelsRate(cencelsRateWinstore[i]);
					winstore.setCencelsAmount(cencelsAmountWinstore[i]);
					winstore.setSum(sumWinstore[i]);
					winstore.setCustomerPayment(customerPaymentWinstore[i]);
					winstore.setCustomerPaymentReceipt(customerPaymentReceiptWinstore[i]);
					winstore.setCustomerPaymentEtc(customerPaymentEtcWinstore[i]);
					winstore.setCustomerPaymentService(customerPaymentServiceWinstore[i]);
					winstore.setCustomerPaymentServiceEtc(customerPaymentServiceEtcWinstore[i]);
					winstore.setServiceDeduction(serviceDeductionWinstore[i]);
					winstore.setPayment(paymentWinstore[i]);
					winstore.setPaymentType(paymentTypeWinstore[i]);
					winstore.setWriterId(writerIdWinstore[i]);
					winstore.setSetDate(setDateWinstore[i]);
					
					eBookService.registerWinstore(winstore);
				}
				break;
				
			case "p_yes24":
				eBookService.allRemoveYes24();
				Yes24 yes24 = new Yes24();
				
				String[] noYes24 = request.getParameterValues("no");
				String[] productNameYes24 = request.getParameterValues("productName");
				String[] brandYes24 = request.getParameterValues("brand");
				String[] brandSalePriceYes24 = request.getParameterValues("brandSalePrice");
				String[] salesYes24 = request.getParameterValues("sales");
				String[] salesSalePriceYes24 = request.getParameterValues("salesSalePrice");
				String[] salesRefundPriceYes24 = request.getParameterValues("salesRefundPrice");
				String[] costRateYes24 = request.getParameterValues("costRate");
				String[] paymentYes24 = request.getParameterValues("payment");
				String[] eventTypeYes24 = request.getParameterValues("eventType");
				String[] saleTypeYes24 = request.getParameterValues("saleType");
				String[] eventSalePriceYes24 = request.getParameterValues("eventSalePrice");
				String[] authorYes24 = request.getParameterValues("author");
				String[] epublIdYes24 = request.getParameterValues("epublId");
				String[] setCodeYes24 = request.getParameterValues("setCode");
				String[] paperbookIsbnYes24 = request.getParameterValues("paperbookIsbn");
				String[] ebookIsbnYes24 = request.getParameterValues("ebookIsbn");
				String[] saleDateYes24 = request.getParameterValues("saleDate");
				String[] refundDateYes24 = request.getParameterValues("refundDate");
				String[] writerIdYes24 = request.getParameterValues("writerId");
				String[] setDateYes24 = request.getParameterValues("setDate");
				
				for(i=0; i<noYes24.length; i++) {
					yes24.setNo(noYes24[i]);
					yes24.setProductName(productNameYes24[i]);
					yes24.setBrand(brandYes24[i]);
					yes24.setBrandSalePrice(brandSalePriceYes24[i]);
					yes24.setSales(salesYes24[i]);
					yes24.setSalesSalePrice(salesSalePriceYes24[i]);
					yes24.setSalesRefundPrice(salesRefundPriceYes24[i]);
					yes24.setCostRate(costRateYes24[i]);
					yes24.setPayment(paymentYes24[i]);
					yes24.setEventType(eventTypeYes24[i]);
					yes24.setSaleType(saleTypeYes24[i]);
					yes24.setEventSalePrice(eventSalePriceYes24[i]);
					yes24.setAuthor(authorYes24[i]);
					yes24.setEpublId(epublIdYes24[i]);
					yes24.setSetCode(setCodeYes24[i]);
					yes24.setPaperbookIsbn(paperbookIsbnYes24[i]);
					yes24.setEbookIsbn(ebookIsbnYes24[i]);
					yes24.setSaleDate(saleDateYes24[i]);
					yes24.setRefundDate(refundDateYes24[i]);
					yes24.setWriterId(writerIdYes24[i]);
					yes24.setSetDate(setDateYes24[i]);
					
					eBookService.registerYes24(yes24);
				}
				break;
				
			case "p_aladin":
				eBookService.allRemoveAladin();
				Aladin aladin = new Aladin();
				
				String[] salesCancelDateAladin = request.getParameterValues("salesCancelDate");
				String[] itemIdAladin = request.getParameterValues("itemId");
				String[] productNameAladin = request.getParameterValues("productName");
				String[] salesTypeAladin = request.getParameterValues("salesType");
				String[] netPriceAladin = request.getParameterValues("netPrice");
				String[] salePriceAladin = request.getParameterValues("salePrice");
				String[] paymentAladin = request.getParameterValues("payment");
				String[] cancelDateAladin = request.getParameterValues("cancelDate");
				String[] authorAladin = request.getParameterValues("author");
				String[] brandAladin = request.getParameterValues("brand");
				String[] publisherAladin = request.getParameterValues("publisher");
				String[] isbnAladin = request.getParameterValues("isbn");
				String[] cidAladin = request.getParameterValues("cid");
				String[] writerIdAladin = request.getParameterValues("writerId");
				String[] setDateAladin = request.getParameterValues("setDate");
				
				for(i=0; i<salesCancelDateAladin.length; i++) {
					aladin.setSalesCancelDate(salesCancelDateAladin[i]);
					aladin.setItemId(itemIdAladin[i]);
					aladin.setProductName(productNameAladin[i]);
					aladin.setSalesType(salesTypeAladin[i]);
					aladin.setNetPrice(netPriceAladin[i]);
					aladin.setSalePrice(salePriceAladin[i]);
					aladin.setPayment(paymentAladin[i]);
					aladin.setCancelDate(cancelDateAladin[i]);
					aladin.setAuthor(authorAladin[i]);
					aladin.setBrand(brandAladin[i]);
					aladin.setPublisher(publisherAladin[i]);
					aladin.setIsbn(isbnAladin[i]);
					aladin.setCid(cidAladin[i]);
					aladin.setWriterId(writerIdAladin[i]);
					aladin.setSetDate(setDateAladin[i]);
					
					eBookService.registerAladin(aladin);
				}
				break;

				
				
				
			default:
				break;
			}
			
			
			
			model.addAttribute("result", true);
			
			// mv.addObject("result", true);
		} catch (Exception e) {
			model.addAttribute("result", false);
			//mv.addObject("result", false);
			e.printStackTrace();
		}
//		
//		// return mv;
		return "redirect:/ebook/list?platformType=" + p_type;
	
	}
	
	
	
	
}












