package application;

public class Particpant {
	
	private String Name = null;
	private int Number = 0;
	private int Rank = 0;
	
	Particpant() {
	}
	
	Particpant(String Name , int Number , int Rank){
		this.Name = Name;
		this.Number = Number;
		this.Rank = Rank;
	}
	
	public String getName() {
		return this.Name;
	}
	
	public int getNumber() {
		return Number;
	}
	
	public int getRank() {
		return Rank;
	}
	
	public void setName(String Name) {
		this.Name = Name;
	}
	
	public void setNumber(int Number) {
		this.Number = Number;
	}
	
	public void setRank(int Rank) {
		this.Rank = Rank;
	}
}