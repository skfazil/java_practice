package com.ds.queueByArray;

public class MyTest {
	
	public static void main(String[] args) {
		QueueByArray q  = new QueueByArray(4);
		
		q.enQueue(100);
		
		q.deQueue();
		q.deQueue();
		q.deQueue();
		
		q.enQueue(200);
		q.enQueue(100);
		
		q.deQueue();
		q.deQueue();
	}

}
