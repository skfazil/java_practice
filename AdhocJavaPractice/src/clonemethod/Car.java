package clonemethod;

import java.io.Serializable;

public class Car implements Cloneable,Serializable{
	
	private String make;
	private String color;
	private String year;
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "Car [make=" + make + ", color=" + color + ", year=" + year + "]";
	}
	
	//Class needs to implement Cloneable else below exception is thrown.
	@Override
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	

}
