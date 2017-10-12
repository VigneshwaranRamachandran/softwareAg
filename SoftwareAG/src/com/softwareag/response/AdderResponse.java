package com.softwareag.response;

import com.softwareag.performance.PerformanceCheck;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.atomic.LongAdder;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;


public class AdderResponse
  extends Thread
{
  static RestClient restClient = RestClient.builder(new HttpHost[] {new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9205, "http") }).build();
  

  static Response response;
  

  public static LongAdder l = new LongAdder();
  
  public AdderResponse() {}
  
  public static Object get(String url) throws IOException {
    Date d1 = new Date();
    for (int i = 0; i < 100000; i++) {
      Thread t = new AdderResponse();
      t.start();
    }
    Date d2 = new Date();
    System.out.println("starting time : " + d1);
    System.out.println("ending time : " + d2);
    
    response = restClient.performRequest("GET", url, new Header[0]);
    
    PerformanceCheck p = new PerformanceCheck();
    p.setD1(d1);
    p.setD2(d2);
    p.setResponse(response);
    
    return p;
  }
  
  public void run() {
    l.increment();
    
    System.out.println("hit count : " + l.intValue() + " using LongAdder");
  }
}