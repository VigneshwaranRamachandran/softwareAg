package com.softwareag.response;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import com.softwareag.performance.PerformanceCheck;

public class AtomicIntegerResponse extends Thread {
	
	
	static RestClient clientNode1 = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	static RestClient clientNode2 = RestClient.builder(
		       new HttpHost("localhost", 9201, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	
	static org.elasticsearch.client.Response responses; 
	
	PerformanceCheck p = new PerformanceCheck();
	
	public static AtomicInteger source = new AtomicInteger(0);
	static String urls;
	
	@SuppressWarnings("static-access")
	public  Object get(String url,int count) throws IOException {
		if(count==1){
			source.set(0);
		}
		this.urls=url;
		Thread t = null;
		Date d1=new Date();
		p.setD1(d1);
		for(int i=0;i<100;i++){
			t= new AtomicIntegerResponse();
			t.start();
			}
		Date d2=new Date();
		p.setD2(d2);
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		p.setResponse(responses);
	p.setT(t.currentThread());
	p.setaI(source);
	return p;
	
	}
	
	public void run() {
		
		if(source.get()<500){
			try {
				hitCount();
				responses = clientNode1.performRequest("GET",urls);
				hitCount();
				responses = clientNode2.performRequest("GET",urls);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		else
		{
			
		}
	}
	public void hitCount(){
		source.incrementAndGet();
	}
	


}
