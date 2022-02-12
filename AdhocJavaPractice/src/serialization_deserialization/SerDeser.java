package serialization_deserialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import clonemethod.Car;

public class SerDeser {
	
	public static void main(String[] args) {
		String path = "data.text";
		Car car = new Car();
		car.setColor("Green");
		car.setMake("Tata");
		car.setYear("2022");
		
		//Serialization
		try {
			FileOutputStream fos = new FileOutputStream(path);
			
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			oos.writeObject(car);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//De serialization
		try {
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			Car car1 = (Car)ois.readObject();
			System.out.println(car1);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

}
