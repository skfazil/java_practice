package com.ds.circulardoublelinkedlist;

public class CircularDLL {
	Node head,tail;
	int size=0;

	class Node{
		int data;
		Node prev;
		Node next;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public void showAllInfinite() {
		if(!isEmpty()) {
			Node temp = head;
			while(temp!=null) {
				System.out.println(temp.data);
				temp=temp.next;
			}
		}
	}
	
	public void showAll() {
		if(!isEmpty()) {
			Node temp = head;
			while(temp!=null) {
				System.out.println(temp.data);
				if(temp==tail)
					break;
				temp=temp.next;
			}
		}
	}
	
	public void showAllReverse() {
		if(!isEmpty()) {
			Node temp = tail;
			while(temp!=null) {
				System.out.println(temp.data);
				if(temp==head)
					break;
				temp=temp.prev;
			}
		}
	}
	
	public void insertAtNull(int data) {
		Node newNode = new Node();
		newNode.data=data;
		head=tail=newNode;
		size++;
	}
	
	public void insertAtFirst(int data) {
		if(!isEmpty()) {
			Node newNode = new Node();
			newNode.data=data;
			
			Node temp=head;
			newNode.prev=temp.prev;
			newNode.next=temp;
			temp.prev=newNode;
			head=newNode;
			tail.next=head;
			size++;
		}
		else
			insertAtNull(data);
	}
	
	public void insertAtLast(int data) {
		if(!isEmpty()) {
			Node newNode = new Node();
			newNode.data=data;
			
			Node temp=tail;
			
			newNode.next=temp.next;
			newNode.prev=temp;
			temp.next=newNode;
			tail=newNode;
			size++;
		}
		else
			insertAtNull(data);
	}
	
	//This is the main logic to remember
	public void insertAt(int data,int position) {
		if(!isEmpty()) {
			if(position<1) {
				System.out.println("Cannot insert here");
			}
			else if(position==1) {
				insertAtFirst(data);
			}
			else {
				Node newNode=new Node();
				newNode.data=data;
				
				Node temp=head;
				
				//Especially this condition
				for(int i=1;i<position-1;i++) {
					temp=temp.next;
				}
				
				if(temp!=tail) {
					newNode.next=temp.next;
					newNode.prev=temp;
					temp.next.prev=newNode;
					temp.next=newNode;
					size++;
				}
				else {
					insertAtLast(data);
				}
				
			}
		}
	}
	
	public void deleteAtFirst() {
		if(!isEmpty()) {
			Node temp=head;
			temp.prev=temp.next;
			temp.next=temp.prev;
			head=temp.next;
			temp.prev=temp.next=null;
			tail.next=head;
			size++;
		}
		else
			System.out.println("Empty");
	}
	
	public void deleteAtLast() {
		if(!isEmpty()) {
			Node temp=tail;
			temp.prev.next=temp.next;
			temp.next.prev=temp.prev;
			tail=temp.prev;
			temp.next=temp.prev=null;
			size--;
		}
	}
	
	public void deleteAt(int position) {
		if(!isEmpty()) {
			Node temp=head;
			
			for(int i=1;i<position-1;i++) {
				temp=temp.next;
			}
			if(temp!=tail) {
				temp.next=temp.next.next;
				temp.next.next.prev=temp;
				temp.next.next=temp.next.prev=null;
				size--;
			}
			else
				deleteAtLast();
			
		}
	}
}
