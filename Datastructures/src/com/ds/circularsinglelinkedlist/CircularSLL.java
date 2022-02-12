package com.ds.circularsinglelinkedlist;

public class CircularSLL {
	Node head,tail;
	int size=0;
	
	class Node{
		int data;
		Node next;
	}
	
	public void show() {
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
	
	public void showCircularLoop() {
		if(!isEmpty()) {
			Node temp = head;
			while(temp!=null) {
				System.out.println(temp.data);
				temp=temp.next;
			}
		}
	}
	
	public void insertIfNull(int data) {
		Node newNode = new Node();
		newNode.data = data;
		head = tail = newNode;
		tail.next = head;
		size++;
	}
	
	public boolean isEmpty() {
		return size==0;
	}
	
	public void insertAtFirst(int data) {
		if(!isEmpty()) {
			Node newNode = new Node();
			newNode.data=data;
			
			newNode.next=head;
			head = newNode;
			tail.next = head;
			size++;
		}
		else
			insertIfNull(data);
	}
	
	public void insertAtLast(int data) {
		if(!isEmpty()) {
			Node newNode=new Node();
			newNode.data=data;
			
			newNode.next=tail.next;
			tail.next=newNode;
			tail=newNode;
			size++;
		}
		else
			insertIfNull(data);
	}
	
	public void insertAt(int data, int position) {
		if(!isEmpty()) {
			if(position<1)
				System.out.println("Cannot insert at this position");
			else if(position==1) {
				insertAtFirst(data);
			}
			else {
				Node newNode = new Node();
				newNode.data=data;
				
				Node temp=head;
				for(int i=1;i<position-1;i++) {
					if(temp!=null) {
						temp=temp.next;
					}
					
					if(temp!=tail) {
						newNode.next=temp.next;
						temp.next=newNode;
						size++;
					}
					else {
						insertAtLast(data);
					}
				}
			}
		}
		else
			insertAtFirst(data);
	}
	
	public void deleteAtFirst() {
		if(!isEmpty()) {
			Node temp=head;
			head=head.next;
			temp.next=null;
			size--;
		}
		else
			System.out.println("Empty list");
	}
	
	public void deleteAtLast() {
		if(!isEmpty()) {
			Node temp=head;
			while(temp!=null&&temp.next!=tail) {
				temp=temp.next;
			}
			
			Node last = temp.next;
			temp.next=last.next;
			last.next=null;
			tail=temp;
			size--;
		}
		else
			System.out.println("List empty");
	}
	
	public void deleteAt(int data, int position) {
		if(!isEmpty()) {
			if(position==1) {
				deleteAtFirst();
			}
			else {
				Node temp=head;
				for(int i=1;i<position-1;i++) {
					temp=temp.next;
				}
				
				if(temp!=tail) {
					temp.next = temp.next.next;
					temp.next.next=null;
				}
				else {
					deleteAtLast();
				}
			}
		}
	}

}
