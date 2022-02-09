package com.ds.doublelinkedlist;

public class DSDoubleLinkedList {
	Node head;
	
	class Node{
		int data;
		Node prev;
		Node next;
	}
	
	public boolean isEmpty() {
		return head==null;
	}
	
	public void showDll() {
		Node temp = head;
		while(temp!=null) {
			System.out.println(temp.data);
			temp = temp.next;
		}
	}
	
	public void insertAtFirst(int data) {
		if(!isEmpty()) {
			Node newNode = new Node();
			newNode.data = data;
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		}
		else {
			Node newNode = new Node();
			newNode.data = data;
			head = newNode;
		}
	}
	
	public void insertAt(int data, int position) {
		if(!isEmpty()) {
			if(position==1) {
				insertAtFirst(data);
			}
			else {
				Node temp = head;
				
				for(int i=1;i<position-1;i++) {
					temp = temp.next;
				}
				
				if(temp!=null) {
					Node newNode = new Node();
					newNode.data = data;
					newNode.next = temp.next;
					newNode.prev = temp;
					temp.next = newNode;
				}
			}
		}
		else {
			Node newNode = new Node();
			newNode.data = data;
			head = newNode;
		}
	}
	
	public void insertAtLast(int data) {
		if(!isEmpty()) {
			Node temp = head;
			
			Node newNode = new Node();
			newNode.data = data;
			
			while(temp.next!=null) {
				temp = temp.next;
			}
			
			temp.next = newNode;
			newNode.prev = temp;
		}
		else
		{
			Node newNode = new Node();
			newNode.data = data;
			head = newNode;
		}
	}
	
	public void deleteAtFirst() {
		if(!isEmpty()) {
			Node temp = head.next;
			temp.prev = null;
			head = temp;
		}
	}
	
	public void deleteAt(int position) {
		if(!isEmpty()) {
			if(position==1) {
				deleteAtFirst();
			}
			else {
				Node temp = head;
				
				for(int i=1;i<position-1;i++) {
					if(temp!=null) {
						temp=temp.next;
					}
				}
				
				temp.next = temp.next.next;
				temp.next.prev = null;
			}
		}
	}

}
