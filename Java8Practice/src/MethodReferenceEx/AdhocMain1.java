package MethodReferenceEx;

public class AdhocMain1 {
	
	public static void main(String[] args) {
		//FunctionalInterfaceEx1 fi = ()->System.out.println("Sample Message");
		FunctionalInterfaceEx1 fi = DisplayMessage::print;
		
		fi.showMsg();
		
		EmployeeFuncIntr emp1 = (name,age,sal)->{
			System.out.println("Employee "+name+", who age is "+age+", draws a salary of $"+sal);
		};
		
		emp1.employee("Fazil", 32, 2000.00);
	}

}
