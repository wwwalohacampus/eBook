package com.eBook.mgr.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.eBook.mgr.domain.DbSetting;
import com.eBook.mgr.service.DbSettingService;

@Controller
@RequestMapping("/ebook")
public class dbController implements ServletContextAware{
	
	private static final Logger log = LoggerFactory.getLogger(dbController.class);

	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	DbSettingService dbSettingService;
	
	private ServletContext servletContext;
	
	@RequestMapping(value = "/dbSettings", method = RequestMethod.GET)
	public void dbSettings() {
		
	}
	
	@RequestMapping(value = "/missing", method = RequestMethod.POST)
	public String missing(@RequestParam("file") MultipartFile file[], HttpServletRequest request) {
		System.out.println("------------------------------");
		for(int i=0; i<file.length; i++) {
			System.out.println("file : " + file[i].getOriginalFilename());
			String fileName = uploadExcelFile(file[i]);
			if(fileName != null) {
				System.out.println("File Name : " + fileName);
				String excelPath = uploadPath + '/' + fileName ;
				System.out.println("Excel Path : " + excelPath);
			} else {
				System.out.println("파일이 없음.");
			}
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
	
	@RequestMapping(value = "/dbUpload", method = RequestMethod.POST)
	public String dbUpload(@RequestParam("file") MultipartFile file[], HttpServletRequest request) throws Exception {
		System.out.println("------------------------------");
		System.out.println("file : " + file[13].getOriginalFilename());
		String fileName = uploadExcelFile(file[13]);
		System.out.println("File Name : " + fileName);
		String excelPath = uploadPath + '/' + fileName ;
		System.out.println("Excel Path : " + excelPath);
		
		int rowindex=0;
		int columnindex=0;
		
		XSSFWorkbook workbook = new XSSFWorkbook(excelPath);
		//시트 수 (첫번째에만 존재하므로 0을 준다) 
		//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다 
		XSSFSheet sheet = workbook.getSheetAt(0);
		
		//행의 수 
		int rows=sheet.getPhysicalNumberOfRows();
		
		//이하 DB세팅 값 삽입---------------------------------------------------------
		DbSetting dbSetting = new DbSetting();
		
		for(rowindex=1;rowindex<rows;rowindex++){
			//행을읽는다 
			XSSFRow row=sheet.getRow(rowindex); if(row !=null){
				//셀의 수 
				int cells=row.getPhysicalNumberOfCells();
	
				for(columnindex=0;columnindex<=cells;columnindex++){
	
					//셀값을 읽는다 
					XSSFCell cell=row.getCell(columnindex);
					String value=""; 
	
					//셀이 빈값일경우를 위한 널체크
					if(cell==null){
						continue;
					}else{ 
						//타입별로 내용 읽기
						switch (cell.getCellType()){
							case XSSFCell.CELL_TYPE_FORMULA:
								value=cell.getCellFormula();
								break; 
							case XSSFCell.CELL_TYPE_NUMERIC:
								if ( HSSFDateUtil.isCellDateFormatted(cell) ){
									SimpleDateFormat fommatter = new SimpleDateFormat("yyyy-MM-dd");
									value = fommatter.format(cell.getDateCellValue())+"";
								}else{
									int numeric = (int) cell.getNumericCellValue();
									value = numeric+"";
								}
								break;
							case XSSFCell.CELL_TYPE_STRING:
								value=cell.getStringCellValue()+"";
								break;
							case XSSFCell.CELL_TYPE_BLANK:
								value="";
								break;
							case XSSFCell.CELL_TYPE_ERROR:
								value="";
								break;
						} 
					}
	
					System.out.println("value : " + value);
					
					if (columnindex==0) {
						dbSetting.setProductName(value);
					} else if (columnindex==1) {
						dbSetting.setAuthor(value);
					} else if (columnindex==2) {
						dbSetting.setBrand(value);
					} else if (columnindex==3) {
						dbSetting.setNetPrice(value);
					} else if (columnindex==4) {
						dbSetting.setManager(value);
					}
					
				}
				System.out.println("값은? " + dbSetting);
				dbSettingService.registerDbSetting(dbSetting);
			}
		}
		
		return "redirect:dbSettings";
	}
}
