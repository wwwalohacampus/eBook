package com.eBook.mgr.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/ebook")
public class dbController implements ServletContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(dbController.class);

	@Value("${upload.path}")
	private String uploadPath;
	
	private ServletContext servletContext;
	
	@RequestMapping(value = "/dbSettings", method = RequestMethod.GET)
	public void dbSettings() {
		
	}
	
	@RequestMapping(value = "/missing", method = RequestMethod.POST)
	public String missing(@RequestParam("file") MultipartFile file[], HttpServletRequest request) {
		System.out.println("------------------------------");
		for(int i=0; i<file.length; i++) {
			System.out.println("file : " + file[i].getOriginalFilename());
		
		}
		
		return "redirect:dbSettings";
	}
	
	private String uploadExcelFile(MultipartFile multipartFile)  {
		try {
			byte [] bytes = multipartFile.getBytes();
			Path path = Paths.get(uploadPath + '/' + multipartFile.getOriginalFilename());
			System.out.println("path : " + path.toString());
			Files.write(path, bytes);
			return multipartFile.getOriginalFilename();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
}
