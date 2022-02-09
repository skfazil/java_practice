package com.ds.linkedlist;

public class DSLinkedLst {
	Node head;
	int size = 0;

	class Node {
		int data;
		Node next;
	}

	public boolean isEmpty() {
		return head == null;
	}

	public void showLinkedList() {
		if (!isEmpty()) {
			Node temp = head;
			do {
				System.out.println(temp.data);
				temp = temp.next;
			} while (temp != null);
		} else
			System.out.println("LinkedList is empty");
	}

	public void insertAtFirst(int data) {
		if (!isEmpty()) {
			Node newNode = new Node();
			newNode.data = data;
			newNode.next = head;
			head = newNode;
		} else {
			Node newNode = new Node();
			newNode.data = data;
			newNode.next = null;
			head = newNode;
		}
		size++;
	}

	public void insertAtLast(int data) {
		if (!isEmpty()) {
			Node temp = head;
			while (temp.next != null) {
				temp = temp.next;
			}

			Node newNode = new Node();
			newNode.data = data;
			newNode.next = null;
			temp.next = newNode;
		} else
			System.out.println("Linked List is empty");
	}

	// This is the main core logic
	public void insertAt(int data, int position) {
		if (position < 1) {
			System.out.println("Cannot be inserted at this position");
		} else if (position == 1) {
			insertAtFirst(data);
		} else {
			Node newNode = new Node();
			newNode.data = data;
			newNode.next = null;

			Node temp = head;

			for (int i = 1; i < position - 1; i++) {
				if (temp != null) {
					temp = temp.next;
				}
			}

			newNode.next = temp.next;
			temp.next = newNode;

			size++;
		}
	}

	public void deleteAt(int position) {
		if (position < 1) {
			System.out.println("Cannot delete at this position");
		} else if (position == 1) {
			deleteAtFirst();
		} else {
			Node temp = head;

			for (int i = 1; i < position - 1; i++) {
				if (temp != null) {
					temp = temp.next;
				}
			}

			temp.next = temp.next.next;
		}
	}
	
	public void deleteAtLast() {
		if(!isEmpty()) {
			Node temp = head;
			if(temp.next!=null) {
				while(temp.next.next!=null) {
					temp = temp.next;
				}
				
				temp.next=null;
			}
		}
	}

	public void deleteAtFirst() {
		if (!isEmpty()) {
			Node temp = head;
			head = temp.next;
			temp.next = null;
			size--;
		} else
			System.out.println("LinkedLIst is empty");
	}

}
