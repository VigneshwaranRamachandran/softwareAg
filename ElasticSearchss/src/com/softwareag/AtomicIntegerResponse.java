package com.softwareag;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

public class AtomicIntegerResponse extends Thread {
	static RestClient restClient = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	static org.elasticsearch.client.Response response; 
	public static AtomicInteger source = new AtomicInteger(0);
	
	public  Object get(String url) throws IOException {
		
		Thread t; 
		Date d1 =new Date();
		for(int i=0;i<1000;i++){
			t= new AtomicIntegerResponse();
			t.start();
			}
		Date d2 =new Date();
		System.out.println("start time : "+d1);
		System.out.println("end time : "+d2);
		
		response = restClient.performRequest("GET",url);
	return response;	
	}
	public void run() {
		source.incrementAndGet();
		System.out.println("hit count : "+source.get()+" using atomic interger");
	}
	

}
