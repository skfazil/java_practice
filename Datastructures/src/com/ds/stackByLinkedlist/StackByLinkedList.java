package com.ds.stackByLinkedlist;

public class StackByLinkedList {
	Node head;
	
	class Node{
		int value;
		Node next;
	}
	
	public StackByLinkedList() {
		head = null;
	}
	
	public boolean isEmpty() {
		return head==null;
	}
	
	public void push(int value) {
		if(!isEmpty()) {
			Node newNode = new Node();
			newNode.value = value;
			newNode.next = head;
			head = newNode;
			System.out.println(value+" is pushed");
		}
		else {
			head = new Node();
			head.value = value;
			head.next = null;
			System.out.println(value+" is pushed");
		}
	}
	
	public void pop() {
		if(!isEmpty()) {
			System.out.println(head.value+" is popped from stack");
			head = head.next;
		}
		else
			System.out.println("Stack is empty");
	}
	
	public void peek() {
		if(!isEmpty()) {
			System.out.println(head.value);
		}
		else
			System.out.println("Stack is empty");
	}

}
