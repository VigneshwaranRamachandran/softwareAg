package com.softwareag.response;

import com.softwareag.performance.PerformanceCheck;
import com.softwareag.resource.ThrotlingPojoResource;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;

public class AtomicIntegerResponse extends Thread
{
  static RestClient clientNode1 = RestClient.builder(new HttpHost[] {new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9205, "http") }).build();
  
  static RestClient clientNode2 = RestClient.builder(new HttpHost[] {new HttpHost("localhost", 9201, "http"), new HttpHost("localhost", 9205, "http") }).build();
  
  static Response responses;
  
  static int count = 1;
  static int min = 1;
  static String periods = "";
  
  PerformanceCheck p = new PerformanceCheck();
  
  public static AtomicInteger source = new AtomicInteger(0);
  static Queue<Integer> queue = new java.util.LinkedList();
  static int i = 1;
  static String urls;
  
  public AtomicIntegerResponse() {}
  
  public Object get(String url, int count) throws IOException, InterruptedException { if (count == 1) {
      source.set(0);
    }
    urls = url;
    Thread t = null;
    Date d1 = new Date();
    p.setD1(d1);
    for (int i = 0; i < 100; i++) {
      t = new AtomicIntegerResponse();
      t.start();
    }
    for (Object object : queue) {
      source.incrementAndGet();
      i = connectionMethod();
      System.out.println(object);
    }
    Date d2 = new Date();
    p.setD2(d2);
    try {
      sleep(2000L);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    p.setResponse(responses);
    p.setT(Thread.currentThread());
    p.setaI(source);
    return p;
  }
  
  private int connectionMethod() throws InterruptedException
  {
    if (source.get() < 50) {
      try {
        hitCount();
        responses = clientNode1.performRequest("GET", urls, new Header[0]);
        hitCount();
        responses = clientNode2.performRequest("GET", urls, new Header[0]);
      } catch (IOException e) {
        e.printStackTrace();
      }
      return 0;
    }
    System.out.println("after one release");
    sleep(500L);
    if (i == 0) {
      source.decrementAndGet();
      connectionMethod();
    }
    return 0;
  }
  
  public void run()
  {
    queue.add(Integer.valueOf(1));
  }
  


  public void hitCount() { source.incrementAndGet(); }
  
  public void display(ThrotlingPojoResource o) {
    count = o.getCount();
    min = o.getMin();
  }
}