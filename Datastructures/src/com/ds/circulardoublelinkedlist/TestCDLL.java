package com.ds.circulardoublelinkedlist;

public class TestCDLL {
	
	public static void main(String[] args) {
		CircularDLL dll = new CircularDLL();
		
		dll.insertAtFirst(400);
		dll.insertAtFirst(300);
		dll.insertAtFirst(200);
		dll.insertAtFirst(100);
		
		dll.insertAt(500, 5);
		dll.insertAt(600, 5);
		
		System.out.println();
		dll.showAll();
		
		dll.deleteAt(5);
		
		System.out.println();
		dll.showAll();
		
		/*
		 * dll.insertAtLast(600);
		 * 
		 * System.out.println(); dll.showAllReverse();
		 * 
		 * dll.insertAt(500, 5);
		 * 
		 * System.out.println(); dll.showAll();
		 * 
		 * dll.deleteAtFirst();
		 * 
		 * System.out.println(); dll.showAll();
		 * 
		 * dll.deleteAtLast();
		 * 
		 * System.out.println(); dll.showAll();
		 */
	}

}
