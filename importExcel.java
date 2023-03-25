package application;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class importExcel {

	private static final String NAME = "Competitions Participations.xlsx";

	public static ArrayList<Competition> getCompetitions() {
		//ArrayList<Sheet> sheet = new ArrayList<Sheet>();
		ArrayList<Competition> compsList = new ArrayList<Competition>();
		Competition comp = new Competition();
		ArrayList<Competition> competitions = new ArrayList<Competition>();
		try {
			FileInputStream file = new FileInputStream(new File(NAME));
			Workbook workbook = new XSSFWorkbook(file);
			DataFormatter dataFormatter = new DataFormatter();
			Iterator<Sheet> sheets = workbook.sheetIterator();
			
			
			while (sheets.hasNext()) {
				Sheet sh = sheets.next();
				Iterator<Row> iterator = sh.iterator();
				
				ArrayList<String> compList = new ArrayList<String>();
				// reading Competition
				for (int k = 0; k < 3; k++) {
					Row row = iterator.next();
					Iterator<Cell> cellIterator = row.iterator();
						cellIterator.next();
						Cell cell = cellIterator.next();
						String cellValue = dataFormatter.formatCellValue(cell);
						compList.add(cellValue);
				}
				Date date = new Date(compList.get(2));
				comp = new Competition(compList.get(0) , compList.get(1), date);
				comp.editType("Team Based");
				// Row InfoRow = iterator.next();

				// Check type
				iterator.next();
				Row row =	iterator.next(); 
				Iterator<Cell> cellIterator = row.iterator();
				for (int j = 0; j < 4 && cellIterator.hasNext(); j++) {
					cellIterator.next();
				}
				if (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					String cellValue = dataFormatter.formatCellValue(cell);
					if (cellValue.equals("team")) {
						comp.editType("Team Based");
						// if team based
						ArrayList<Team> teamList = new ArrayList<Team>();
						while (iterator.hasNext()) {
							ArrayList<String> studentList = new ArrayList<String>();
							row = iterator.next();
							cellIterator = row.iterator();
							cellIterator.next();
							for (int j = 0; j < 6; j++) {
								cell = cellIterator.next();
								cellValue = dataFormatter.formatCellValue(cell);
								studentList.add(cellValue);
								//System.out.print(cellValue + "\t");
								// if(cell.getCellType() == CellType.STRING) { // //}
								// System.out.print(cellValue);
							}
							Student student = new Student(studentList.get(0) , studentList.get(2) , studentList.get(1));
							//System.out.println(student.getID());
							if(checkTeam(studentList.get(4) , teamList) >= 0) {
								teamList.get(checkTeam(studentList.get(4) , teamList)).addMember(student);
								}
	
							else {
								String rank = studentList.get(5);
								if (rank.equals("-") )
									rank = "0";
								Team team = new Team(studentList.get(4) , Integer.parseInt(studentList.get(3)) , Integer.parseInt(rank));
								team.addMember(student);
								teamList.add(team);
								comp.addParticpant(team);
							}		
						}
					}  else {
						comp.editType("Individual based");
	
						// if individuals based
						while (iterator.hasNext()) {
							ArrayList<String> studentList = new ArrayList<String>();
							row = iterator.next();
							cellIterator = row.iterator();
							for (int j = 0; j < 5; j++) {
								cell = cellIterator.next();
								cellValue = dataFormatter.formatCellValue(cell);
								studentList.add(cellValue);
							}
							String rank = studentList.get(4);
							if (rank.equals("-") )
								rank = "0";
							Student student = new Student(Integer.parseInt(studentList.get(0)), studentList.get(1) , studentList.get(2) , studentList.get(3) , Integer.parseInt(rank));
							//Team tm = new Team();
							comp.addParticpant(student);
						}
	
					}
				}
						
				competitions.add(comp);
			}
			workbook.close();
			return competitions;
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	public static int checkTeam(String teamName , ArrayList<Team> teamList){
		for(int i = 0 ; i < teamList.size() ; i++) {
			if(teamList.get(i).getName().equals(teamName))
			return i;
		} 
		return -1;
	}

}