package com.ds.linkedlist;

public class TestLL {
	
	public static void main(String[] args) {
		DSLinkedLst ll = new DSLinkedLst();
		
		ll.insertAtFirst(400);
		ll.insertAtFirst(300);
		ll.insertAtFirst(200);
		ll.insertAtFirst(100);
		
		ll.showLinkedList();
		
		System.out.println();
		ll.insertAt(700, 3);
		
		System.out.println();
		ll.showLinkedList();
		
		ll.deleteAtLast();
		
		System.out.println();
		ll.showLinkedList();
	}

}
