package com.ds.stackByLinkedlist;

public class MainTest {
	
	public static void main(String[] args) {
		StackByLinkedList sll = new StackByLinkedList();
		
		sll.pop();
		
		sll.push(100);
		sll.push(200);
		sll.push(300);
		sll.push(400);
		sll.push(500);
		sll.push(600);
		
		sll.pop();
		sll.pop();
	}

}
