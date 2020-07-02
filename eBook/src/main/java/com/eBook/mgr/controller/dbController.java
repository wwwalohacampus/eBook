package com.eBook.mgr.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ebook")
public class dbController {
	
	private static final Logger log = LoggerFactory.getLogger(dbController.class);

	@Value("${upload.path}")
	private String uploadPath;
	
	@RequestMapping(value = "/dbSettings", method = RequestMethod.POST)
	public String dbSettings(@RequestParam("file") MultipartFile file) {
		
		return "redirect:/";
	}
}
