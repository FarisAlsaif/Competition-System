package application;

import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class exportExcel {
	
	private ArrayList<Competition> competitions;
	
	exportExcel(ArrayList<Competition> competitions) {
		this.competitions = competitions;
	}

	public void export(){
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		
		try {
			
			FileOutputStream fileOut = new FileOutputStream("Competitions Participations.xlsx");
			
			for(int s = 0 ; s < competitions.size() ; s++) {
				Sheet sh = workbook.createSheet(competitions.get(s).getName());
			//For .xsl workbooks use new HSSFWorkbook();
			
			//CreationHelper creationHelper= workbook.getCreationHelper();

			
			CellStyle style = workbook.createCellStyle();  
            style.setBorderBottom(BorderStyle.THIN);  
            style.setBottomBorderColor(IndexedColors.BLACK.getIndex());  
            style.setBorderRight(BorderStyle.THIN);  
            style.setRightBorderColor(IndexedColors.BLACK.getIndex());  
            style.setBorderLeft(BorderStyle.THIN);  
            style.setLeftBorderColor(IndexedColors.BLACK.getIndex());  
            style.setBorderTop(BorderStyle.THIN);  
            style.setTopBorderColor(IndexedColors.BLACK.getIndex());  
			
			//Competition info
				Row row = sh.createRow(0);
				row.createCell(0).setCellValue("Competition Name");
				row.createCell(1).setCellValue(competitions.get(s).getName());
				row = sh.createRow(1);
				row.createCell(0).setCellValue("Competition Link");
				row.createCell(1).setCellValue(competitions.get(s).getLink());
				row = sh.createRow(2);
				row.createCell(0).setCellValue("Competition Date");
				row.createCell(1).setCellValue(competitions.get(s).getDate());
				row = sh.createRow(3);
				row.createCell(0).setCellValue(" ");
				row.createCell(1).setCellValue(" ");
				row.createCell(2).setCellValue(" ");
				row.createCell(3).setCellValue(" ");
				row.createCell(4).setCellValue(" ");
				row = sh.createRow(4);
				
				Cell c1 = row.createCell(0);
				Cell c2 = row.createCell(1);
				Cell c3 = row.createCell(2);
				Cell c4 = row.createCell(3);
				Cell c5 = row.createCell(4);
				Cell c6 = row.createCell(5);
				Cell c7 = row.createCell(6);
				
				row.createCell(0).setCellValue(" ");
				c1.setCellStyle(style);
				row.createCell(1).setCellValue("Student ID");
				c2.setCellStyle(style);
				row.createCell(2).setCellValue("Student Name");
				c3.setCellStyle(style);
				row.createCell(3).setCellValue("Major");
				c4.setCellStyle(style);
				int rowIndex = 4;
				int count = 0;
				
				if(competitions.get(s).getType().equals("Individual based")) {
					row.createCell(4).setCellValue("Rank");
					c5.setCellStyle(style);
				for(int i = 0 ; i < competitions.get(s).getParticpants().size() ; i++) {
					Student student = (Student) competitions.get(s).getParticpants().get(i);
					row = sh.createRow(rowIndex + i + 1);
					 c1 = row.createCell(0);
					 c2 = row.createCell(1);
					 c3 = row.createCell(2);
					 c4 = row.createCell(3);
					 c5 = row.createCell(4);
					c1.setCellValue(i + 1);
					c1.setCellStyle(style);
					c2.setCellValue(student.getID());
					c2.setCellStyle(style);
					c3.setCellValue(student.getName());
					c3.setCellStyle(style);
					c4.setCellValue(student.getMajor());
					c4.setCellStyle(style);
					c5.setCellValue(student.getRank());
					c5.setCellStyle(style);
				}
			}
				else { 
					
					row.createCell(4).setCellValue("team");
					c5.setCellStyle(style);
					row.createCell(5).setCellValue("Team Name");
					c6.setCellStyle(style);
					row.createCell(6).setCellValue("Rank");
					c7.setCellStyle(style);
					
					for(int i = 0 ; i < competitions.get(s).getParticpants().size() ; i++) {
					Team team  = (Team) competitions.get(s).getParticpants().get(i);
						for(int j = 0 ; j < team.getMember().size() ; j++) {
							rowIndex = rowIndex + 1;
							count = count + 1;
						 row = sh.createRow(rowIndex);
						 c1 = row.createCell(0);
						 c2 = row.createCell(1);
						 c3 = row.createCell(2);
						 c4 = row.createCell(3);
						 c5 = row.createCell(4);
						 c6 = row.createCell(5);
						 c7 = row.createCell(6);
						
						c1.setCellValue(count);
						c1.setCellStyle(style);
						c2.setCellValue(team.getMember().get(j).getID());
						c2.setCellStyle(style);
						c3.setCellValue(team.getMember().get(j).getName());
						c3.setCellStyle(style);
						c4.setCellValue(team.getMember().get(j).getMajor());
						c4.setCellStyle(style);
						c5.setCellValue(i + 1);
						c5.setCellStyle(style);
						c6.setCellValue(team.getName());
						c6.setCellStyle(style);
						c7.setCellValue(team.getRank());
						c7.setCellStyle(style);
						}
					}
					
				}
				for(int i=0;i < 7;i++) {
					sh.autoSizeColumn(i);
				}
				
				
				
				
				
			}
				workbook.write(fileOut);
				workbook.close();
				fileOut.close();
			} catch (Exception e) {
			System.out.println("Error");
			}
	}
}