package com.softwareag.response;

import java.io.IOException;
import java.util.concurrent.atomic.LongAccumulator;

public class AccumulatorResponse extends Thread {
	
	//public static LongAccumulator source1= new LongAccumulator(Long::sum, 0);
	
	//public static DoubleAccumulator source1= new DoubleAccumulator(Double::sum,0);
	public static void get(String url) throws IOException {
	
Thread t; 
		
		//for(int i=0;i<5;i++){
			t= new AccumulatorResponse();
			t.start();
		//	}
		
			
	}
	public void run() {
		//source1.accumulate(1);
		System.out.println("hit count : +source1.get()+ using LongAccumulator");
	}
	
}
