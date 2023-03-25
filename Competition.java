package application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Competition {
	
	private Date date;
	private String link; 
	private String name;
	private String type;
	private ArrayList<Particpant> particpants = new ArrayList<>();
	
	Competition(){
		this.date = null;
		this.name = null;
		this.link = null;
		this.date = null;
	}
	
	Competition(String name, String link, Date date) {  // Type will be set later
		this.name = name;
		this.link = link;
		this.date = date;
	}
	// Getters
	public String getDate() {
		DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        String date = formatter.format(this.date);
		return date ;
	}
	
	public String getType() {
		return this.type;
	}

	public String getName(){
		return this.name; 
	}

	public String getLink(){
		return this.link; 
	}
	// 
	public ArrayList<Particpant> getParticpants() {
		return this.particpants;
	}
	// setters 
	public void editDate(Date date) {
		this.date = date;
	}
	public void editType(String type) {
		this.type = type;
	}
	public void  editLink(String link){
		this.link = link; 
	}
	public void editName(String name) {
		this.name = name; 
	}
	// Add and delete a participant 
	public void addParticpant(Particpant particpant) {
		particpants.add(particpant);
	}
	public void deleteParticpant(Particpant particpant) {
		particpants.remove(particpant); 
	}
	
	public void print(){
		System.out.println("Competetion info : ");
		System.out.println("The Name :" + this.getName());
		System.out.println("The Link :" + this.getLink());
		System.out.println("The Date :" + this.getDate().toString());
		System.out.println("The Type :" + this.getType());
		System.out.println("\n-------------------------------------\n");
		for(int i = 0 ; i < this.getParticpants().size() ; i++) {
			Team team = (Team)this.getParticpants().get(i);
			if(!this.getType().equals("Individual based")) {
				System.out.println("Team Name is :" + team.getName());
				System.out.println("Team Number is :" + team.getNumber());
				System.out.println("Team Rank is :" +  team.getRank());
				//System.out.println("-------------------------------------\n");
				
				ArrayList<Student> students = team.getMember();
				System.out.println("\nTeam members : ");
				for(int j = 0 ; j < students.size() ; j++) {
					System.out.println(students.get(j).getName() + " ; " + students.get(j).getID() + " ; " + students.get(j).getMajor());
				}
				System.out.println("\n-------------------------------------\n");
			} else {
				
			}
		}

	}
	
	@Override
	public String toString() {
		return this.getName();
	}
}

 