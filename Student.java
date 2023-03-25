package application;
public class Student extends Particpant {
	private String ID = null;
	private String Major = null;
	private String Name = null;
	
	Student(){
	}
	
	Student(String ID, String Major,String Name){
		this.ID=ID;
		this.Major=Major;
		this.Name=Name;
	}
	
	Student(int Number , String ID , String Name , String Major ,int Rank ){
		super(Name , Number , Rank);
		this.Name = Name;
		this.ID = ID + "";
		this.Major = Major;
	}
	
	public String getID() {
		return this.ID;
	}
	public String getMajor() {
		return this.Major;
	}
	public String getName() {
		return this.Name;
	}
	public void setID(String id) {
		this.ID = id;
	}
	public void setMajor(String major) {
		this.Major = major;
	}
	public void setName(String name) {
		this.Name = name;
	}
}