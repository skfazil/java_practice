package MethodReferenceEx;

public class DisplayMessage {
	
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public static void print(String s) {
		System.out.println(s);
	}
	
	public static void print() {
		System.out.println("Printing Default value here");
	}

	@Override
	public String toString() {
		return "DisplayMessage [msg=" + msg + "]";
	}
	
	

}
