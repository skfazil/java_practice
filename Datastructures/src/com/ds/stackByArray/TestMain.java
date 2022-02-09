package com.ds.stackByArray;

public class TestMain {

	public static void main(String[] args) {
		StackByArray sa = new StackByArray(6);
		
		sa.push(100);
		sa.push(200);
		sa.push(300);
		sa.push(400);
		sa.push(500);
		sa.push(600);
		
		sa.peek();
		
		sa.push(700);
		
		sa.pop();
		sa.pop();
		sa.pop();
		sa.pop();
		sa.pop();
		sa.pop();
		sa.pop();
	}
}
