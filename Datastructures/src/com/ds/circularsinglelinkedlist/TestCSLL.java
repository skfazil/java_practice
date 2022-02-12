package com.ds.circularsinglelinkedlist;

public class TestCSLL {
	
	public static void main(String[] args) {
		CircularSLL cl = new CircularSLL();
		
		cl.insertAtFirst(400);
		cl.insertAtFirst(300);
		cl.insertAtFirst(200);
		cl.insertAtFirst(100);
		
		System.out.println();
		cl.show();
		
		cl.insertAtLast(500);
		
		System.out.println();
		cl.show();
		
		cl.deleteAtLast();
		
		System.out.println();
		cl.show();
	}
	

}
