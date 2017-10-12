package com.softwareag.response;

import java.io.IOException;

import java.util.Date;
import java.util.concurrent.atomic.LongAdder;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.softwareag.performance.PerformanceCheck;


public class AdderResponse extends Thread {
	
	static RestClient restClient = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	
	static org.elasticsearch.client.Response response; 

	//public static DoubleAdder d =new DoubleAdder();	 //Creates a new adder with initial sum of zero.
	
	public static LongAdder l =new LongAdder(); 	 //Creates a new adder with initial sum of zero.
	
	public static Object get(String url) throws IOException {
	
		Thread t; 
		Date d1 =new Date();
		for(int i=0;i<100000;i++){
			t= new AdderResponse();
			t.start();
			}
		Date d2=new Date();
		System.out.println("starting time : "+d1);
		System.out.println("ending time : "+d2);
		
		response = restClient.performRequest("GET",url);
		
		PerformanceCheck p = new PerformanceCheck();
		p.setD1(d1);
		p.setD2(d2);
		p.setResponse(response);
		
	return p;		
	}
	public void run() {
		//d.add(1);
		l.increment();
		//System.out.println("hit count : "+ d.intValue()+" using DoubleAdder");
		System.out.println("hit count : "+ l.intValue()+" using LongAdder");
	}
	
}
