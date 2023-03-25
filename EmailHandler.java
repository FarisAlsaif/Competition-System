package application;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;

public class EmailHandler {
	
	
	public static void main(String[] args) throws URISyntaxException, IOException {
		// TODO Auto-generated method stub
		/*
		 * File inputFile = new File("Email Body template.txt"); Scanner input = new
		 * Scanner(inputFile); String templete = input.nextLine();
		 */
		
	}

	
	public void generateEmail(ArrayList<Student> students , Team team , Competition comp) throws URISyntaxException, IOException{
		File inputFile = new File("Email Body template.txt");
		Scanner input = new Scanner(inputFile);
		String templete = "";
		String Subject = "Congratulation on achieving [their ranking] place in [the competition name]";
		Subject = Subject.replace("[their ranking]" , ""+team.getRank());
		Subject = Subject.replace("[the competition name]" , comp.getName());
		Subject = Subject.replace(" ", "%20");
		
		while(input.hasNext())
			templete += "\n" +input.nextLine();
		templete = templete.replace("[Student name/Team name]" , team.getName()); 
		templete = templete.replace("[Competition name]" , comp.getName());
		templete = templete.replace("\n" , "%0D%0A");
		templete = templete.replace(" " , "%20");

		
		String emails = "";
		for(int i = 0 ; i < students.size() ; i++) {
			emails += "S" + students.get(i).getID() + "@kfupm.edu.sa," ;
		}
		Desktop desktop;
		if (Desktop.isDesktopSupported() 
		    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
		  URI mailto = new URI("mailto:"+emails+"?subject="+Subject+"&body="+templete);
		  desktop.mail(mailto);
		} else {
		  throw new RuntimeException("Error Check Email Class)");
		}
		
	}
	
	public void generateEmail(Student std , Competition comp) throws URISyntaxException, IOException{
		File inputFile = new File("Email Body template.txt");
		Scanner input = new Scanner(inputFile);
		String templete = "";
		String Subject = "Congratulation on achieving [their ranking] place in [the competition name]";
		Subject = Subject.replace("[their ranking]" , "" + std.getRank());
		Subject = Subject.replace("[the competition name]" , comp.getName());
		Subject = Subject.replace(" ", "%20");
		
		while(input.hasNext())
			templete += "\n" +input.nextLine();
		templete = templete.replace("[Student name/Team name]" , std.getName()); 
		templete = templete.replace("[Competition name]" , comp.getName());
		templete = templete.replace("\n" , "%0D%0A");
		templete = templete.replace(" " , "%20");

		
		String emails = "S" + std.getID() + "@kfupm.edu.sa," ;
		Desktop desktop;
		if (Desktop.isDesktopSupported() 
		    && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
		  URI mailto = new URI("mailto:"+emails+"?subject="+Subject+"&body="+templete);
		  desktop.mail(mailto);
		} else {
		  throw new RuntimeException("Error Check Email Class)");
		}
		
	}

}
