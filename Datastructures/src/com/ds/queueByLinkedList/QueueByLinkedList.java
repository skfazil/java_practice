package com.ds.queueByLinkedList;

public class QueueByLinkedList {
	Node front,rear;
	int currentSize;
	
	class Node{
		int data;
		Node next;
	}
	
	public QueueByLinkedList() {
		this.front=null;
		this.rear=null;
		this.currentSize=0;
	}
	
	public boolean isEmpty() {
		return currentSize==0;
	}
	
	public void enQueue(int data) {
		if(!isEmpty()) {
			currentSize++;
			Node newNode = new Node();
			newNode.data = data;
			rear.next = newNode;
			rear = newNode;
		}
		else {
			System.out.println("EWmpty");
			currentSize++;
			rear = new Node();
			rear.data = data;
			rear.next = null;
			front=rear;
		}
		System.out.println(data+" is enQueued");
	}
	
	public void deQueue() {
		if(!isEmpty()) {
			currentSize--;
			Node nextNode = front.next;
			System.out.println(front.data+" is deQueued");
			front.next=null;
			front=nextNode;
		}
		else {
			System.out.println("Queue is empty");
		}
	}

}
