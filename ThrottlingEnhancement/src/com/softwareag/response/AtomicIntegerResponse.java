package com.softwareag.response;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.softwareag.performance.PerformanceCheck;

public class AtomicIntegerResponse extends Thread {
	
	
	static RestClient restClient = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	
	static org.elasticsearch.client.Response response; 
	
	PerformanceCheck p = new PerformanceCheck();
	
	public static AtomicInteger source = new AtomicInteger(0);
	static String urls;
	
	@SuppressWarnings("static-access")
	public  Object get(String url) throws IOException {
		this.urls=url;
		//System.out.println(urls);
		//System.exit(0);
		Thread t = null;
		Date d1=new Date();
		p.setD1(d1);
		for(int i=0;i<56;i++){
			t= new AtomicIntegerResponse();
			t.start();
			}
		Date d2=new Date();
//		System.out.println("starting time : "+d1);
//		System.out.println("ending time : "+d2);
//	System.out.println(response);
//		PerformanceCheck p = new PerformanceCheck();
//		p.setD1(d1);
		p.setD2(d2);
		try {
			sleep(1000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(response);
		p.setResponse(response);
	p.setT(t.currentThread());
	
	return p;
	
	}
	
	public void run() {
		source.incrementAndGet();
		//System.out.println(urls);
		//System.exit(0);
		try {
			if(source.get()<50){
			response = restClient.performRequest("GET",urls);
			
			//p.setResponse(response);
			//System.out.println(response);
			//System.out.println(p.getResponse());
			}
			else
			{
				//System.out.println("hit count : "+source.get()+" using atomic interger");
				//throw new OverLimitException();
				//System.exit(0);
				//p.setResponse(response);
				//System.out.println("after limitation");
				//System.out.println(response);
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("hit count : "+source.get()+" using atomic interger");
	}
	

}
