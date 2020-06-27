package com.eBook.mgr.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import com.eBook.mgr.domain.platform.Bookcube;
import com.eBook.mgr.domain.platform.Product;
import com.eBook.mgr.service.EBookService;

@Controller
@RequestMapping("/ebook")
public class ExcelController implements ServletContextAware {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Autowired
	EBookService eBookService;
	
	private ServletContext servletContext;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "ebook/index";
	}
	
	
	@RequestMapping(value = "/process", method = RequestMethod.POST)
	public String process(@RequestParam("file") MultipartFile file) throws Exception {
		System.out.println("file : " + file.getOriginalFilename());
		String fileName = uploadExcelFile(file);
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
		
		// 이상 공통적인 엑셀 파싱 코드 ---------------------------------------------------------
		
		
		
		
		
		
		// 이하 플랫폼별 분기처리 로직 ---------------------------------------------------------
		
		Bookcube bookcube = new Bookcube();
		
		for(rowindex=3;rowindex<rows;rowindex++){
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
						bookcube.setProductName(value);
					} else if (columnindex==1) {
						bookcube.setAuthor(value);
					} else if (columnindex==2) {
						bookcube.setBrand(value);
					} else if (columnindex==3) {
						bookcube.setSalesCategory(value);
					} else if (columnindex==4) {
						bookcube.setSalesType(value);
					} else if (columnindex==5) {
						bookcube.setEpisode(value);
					} else if (columnindex==6) {
						bookcube.setNetPrice(value);
					} else if (columnindex==7) {
						bookcube.setSalePrice(value);
					} else if (columnindex==8) {
						bookcube.setDiscount(value);
					} else if (columnindex==9) {
						bookcube.setFee(value);
					} else if (columnindex==10) {
						bookcube.setNetTargetPrice(value);
					} else if (columnindex==11) {
						bookcube.setPayment(value);
					} else if (columnindex==12) {
						bookcube.setBookId(value);
					} else if (columnindex==13) {
						bookcube.setIsbn(value);
					} else if (columnindex==14) {
						bookcube.setEIsbn(value);
					} else if (columnindex==15) {
						bookcube.setEIsbnEpub(value);
					} 
					
					
				}
				System.out.println("값은? " + bookcube);
				//eBookService.registerBookcube(bookcube);
			}
		}
		
		// 플랫폼별 분기처리 끝 -----------------------------------------------------------------------
		
		return "redirect:/";
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
	
	
	@RequestMapping(value = "/download", method = RequestMethod.POST)
	public String download(String p_type, HttpServletResponse response) throws Exception {
		
		HSSFWorkbook objWorkBook = new HSSFWorkbook();

		//워크시트 생성
		HSSFSheet objSheet = objWorkBook.createSheet();

		//시트 이름
		objWorkBook.setSheetName(0 , "sheet");

		//행생성
		HSSFRow objRow = objSheet.createRow((short)0);

		//셀 생성
		HSSFCell objCell = null;


		//스타일 설정

		//스타일 객체 생성 
		HSSFCellStyle styleHd = objWorkBook.createCellStyle();    //제목 스타일
		HSSFCellStyle styleSub = objWorkBook.createCellStyle();   //상단 스타일
		HSSFCellStyle styleCon = objWorkBook.createCellStyle();   //내용 스타일
		HSSFCellStyle styleCon2 = objWorkBook.createCellStyle();   //내용 스타일2
		HSSFCellStyle styleCon3 = objWorkBook.createCellStyle();   //내용 스타일3
		HSSFCellStyle styleCon4 = objWorkBook.createCellStyle();   //내용 스타일4
		HSSFCellStyle styleBody = objWorkBook.createCellStyle();   //왼쪽 스타일
		HSSFCellStyle stylesum = objWorkBook.createCellStyle();   //소계 스타일
		HSSFCellStyle styleBottom = objWorkBook.createCellStyle();   //하단 스타일


		//제목 폰트
		HSSFFont font = objWorkBook.createFont();
		font.setFontHeightInPoints((short)15);

		//제목 스타일에 폰트 적용, 정렬
		styleHd.setFont(font);


		//상단 폰트
		HSSFFont font2 = objWorkBook.createFont();

		//상단 스타일 설정
		styleSub.setBottomBorderColor(HSSFColor.BLACK.index);
		styleSub.setLeftBorderColor(HSSFColor.BLACK.index);
		styleSub.setRightBorderColor(HSSFColor.BLACK.index);
		styleSub.setTopBorderColor(HSSFColor.BLACK.index);

		//셀에 색 넣기
		styleSub.setFillForegroundColor(HSSFColor.GREY_40_PERCENT.index);

		//글자 속성
		styleSub.setFont(font2);
		styleSub.setWrapText(true); 


		//왼쪽 스타일 설정
		styleBody.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
		styleBody.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
		styleBody.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
		styleBody.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
		styleBody.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);

		//내용 스타일
		styleCon.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
		styleCon.setLeftBorderColor(HSSFColor.GREY_50_PERCENT.index);
		styleCon.setRightBorderColor(HSSFColor.GREY_50_PERCENT.index);
		styleCon.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);


		//5,"담보구분"
		String[] sub_title = {"담보번호","PNU","원본주소","정제주소","가격","전세가","전용면적","토지면적","건물연면적","비고"};
				
		
		//1행
		objRow = objSheet.createRow(0);
		objRow.setHeight ((short) 0x300);

		//제목
		objCell = objRow.createCell(0);
		objCell.setCellValue("가격산정");
		objCell.setCellStyle(styleHd);

		//상단
		objRow = objSheet.createRow(3);
		objRow.setHeight ((short) 0x200);

		for(int i=0; i<sub_title.length; i++){
		
			objCell = objRow.createCell(i);
			objCell.setCellValue(sub_title[i]);
			objCell.setCellStyle(styleSub);
			
		}

		//----------------------------------------------------------------------------------------

		//길이 설정

	//	for(int i=0; i<sub_title.length; i++){
	//		objSheet.autoSizeColumn(i);
	//		objSheet.setColumnWidth(i, (objSheet.getColumnWidth(i))+512);
	//	}

		//--------------------------------------------------------------------------------------
		try {
			response.setContentType("Application/Msexcel");
			response.setHeader("Content-Disposition", "ATTachment; Filename="+URLEncoder.encode("가격산정","UTF-8")+".xls");
	
			OutputStream fileOut  = response.getOutputStream(); 
			objWorkBook.write(fileOut);
			fileOut.close();
	
			response.getOutputStream().flush();
			response.getOutputStream().close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "redirect:list?platformType=" + p_type;
	}
}
