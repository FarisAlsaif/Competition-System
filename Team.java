package application;
import java.util.ArrayList;
public class Team extends Particpant {
	ArrayList<Student> Students = new ArrayList<Student>();
	
	Team(){
	}
	
	Team(String Name , int Number , int Rank){
		super(Name, Number , Rank);
	}
	
	public void addMember(Student stu) {
		Students.add(stu);
	}
	public void deleteMember(Student stu) {
		Students.remove(stu);
	}
	public ArrayList<Student> getMember(){
		return Students;
	}
}