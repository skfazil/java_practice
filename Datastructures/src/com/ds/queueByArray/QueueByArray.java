package com.ds.queueByArray;

public class QueueByArray {
	
	int rear;
	int front;
	int size;
	int[] arr;
	int currentSize = 0;

	public QueueByArray(int size) {
		this.rear=-1;
		this.front=0;
		this.size=size;
		this.arr = new int[size];
	}
	
	public boolean isEmpty() {
		return currentSize==0;
	}
	
	public boolean isFull() {
		return currentSize==size;
	}
	
	public void enQueue(int data) {
		if(!isFull()) {
			currentSize++;
			//THis is to circle back to starting position
			if(rear==size-1) {
				rear=-1;
			}
			rear++;
			arr[rear]=data;
			System.out.println(data+" is enQueued");
		}
		else
			System.out.println("Queue is Full");
	}
	
	public void deQueue() {
		if(!isEmpty()) {
			currentSize--;			
			System.out.println(arr[front]+" is deQueued");
			front++;
			//Resetting the front back to index 0.
			if(front==size)
				front=0;
		}
		else
			System.out.println("Queue is empty");
	}
}
