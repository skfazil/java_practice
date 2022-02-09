package com.ds.doublelinkedlist;

public class TestDLL {
	
	public static void main(String[] args) {
		DSDoubleLinkedList dll = new DSDoubleLinkedList();
		
		dll.insertAtFirst(400);
		dll.insertAtFirst(300);
		dll.insertAtFirst(200);
		dll.insertAtFirst(100);
		
		dll.showDll();
		
		dll.insertAt(700, 5);

		System.out.println();
		dll.showDll();
		
		dll.insertAtLast(800);
		
		System.out.println();
		dll.showDll();
		
		dll.deleteAtFirst();
		
		System.out.println();
		dll.showDll();
		
		dll.deleteAt(4);;
		
		System.out.println();
		dll.showDll();
	}

}
