package com.ds.stackByArray;

public class StackByArray {
	
	int top;
	int size;
	int[] arr;
	
	public StackByArray(int size) {
		this.size=size;
		this.arr = new int[size];
		this.top = -1;
	}
	
	public void push(int val) {
		if(!isFull()) {
			top++;
			arr[top]=val;
			System.out.println(val + " pushed to stack");
		}
		else
			System.out.println("Stack is full");
	}
	
	public void pop() {
		if(!isEmpty()) {
			System.out.println(arr[top]+" is popped from stack");
			top--;
		}
		else
			System.out.println("Stack is empty");
	}
	
	public void peek() {
		if(!isEmpty()) {
			System.out.println(arr[top]);
		}
		else
			System.out.println("Stack is empty");
	}
	
	public boolean isFull() {
		return (size-1==top);
	}
	
	public boolean isEmpty() {
		return top==-1;
	}

}
