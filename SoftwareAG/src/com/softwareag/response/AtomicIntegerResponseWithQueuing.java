package com.softwareag.response;

import com.softwareag.resource.RequestJsonObjects;
import com.softwareag.resource.ThrotlingPojoResource;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.http.HttpHost;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;


public class AtomicIntegerResponseWithQueuing
{
  RestClient clientNode1 = RestClient.builder(new HttpHost[] { new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9205, "http") }).build();
  
  static Response responses;
  public static AtomicInteger source = new AtomicInteger(0);
  public static AtomicInteger source1 = new AtomicInteger(0);
  static Queue<String> queue = new LinkedList();
  static Queue<String> queue1 = new LinkedList();
  static List<ThrotlingPojoResource> list = new ArrayList();
  
  public static final int CONST_COUNT = 1;
  static int count;
  static int min;
  static TimeUnit periods;
  
  public AtomicIntegerResponseWithQueuing() {}
  
  public String get(String url, int counts)
    throws IOException
  {
    if (counts == 1) {
      d = new Date().getTime();
      for (ThrotlingPojoResource tp : list) {
        if (tp.getApi().equals("get"))
        {
          count = tp.getCount();
          min = tp.getMin();
          periods = tp.getPeriods();
        }
      }
    }
    String p = "";
    
    long MAX_DURATION = TimeUnit.MILLISECONDS.convert(min, periods);
    
    long duration = new Date().getTime() - d;
    
    if (duration < MAX_DURATION) {
      source.addAndGet(1);
      if (source.get() <= count) {
        if (queue.isEmpty()) {
          System.out.println("GET Response Code :: " + source.get());
        }
        else
        {
          String s = (String)queue.poll();
          System.out.println(s);
        }
        

        p = "sucess \n ";
      } else {
        queue.add("con : " + source.get());
        System.out.println("request Limit was exceeds,so wait for " + min + " " + periods);
        p = "fail-->request Limit was exceeds";
      }
    } else {
      d = new Date().getTime();
      source.set(0);
    }
    return p; }
  
  static int count1;
  static int min1;
  
  public String display(ThrotlingPojoResource o) { ThrotlingPojoResource tr1 = new ThrotlingPojoResource();
    tr1.setCount(o.getCount());
    tr1.setMin(o.getMin());
    tr1.setPeriods(o.getPeriods());
    tr1.setApi(o.getApi());
    list.add(o);
    for (ThrotlingPojoResource tp : list) {
      System.out.println(tp.getApi());
      System.out.println(tp.getCount());
      System.out.println(tp.getMin());
      System.out.println(tp.getPeriods());
    }
    return "sucessfully saved";
  }
  
  public String post(RequestJsonObjects re) throws IOException { if (re.getClick() == 1) {
      d1 = new Date().getTime();
      for (ThrotlingPojoResource tp : list) {
        if (tp.getApi().equals("post"))
        {
          count1 = tp.getCount();
          min1 = tp.getMin();
          periods1 = tp.getPeriods();
        }
      }
    }
    String p = "";
    long MAX_DURATION = TimeUnit.MILLISECONDS.convert(min1, periods1);
    
    long duration = new Date().getTime() - d1;
    
    if (duration < MAX_DURATION) {
      source1.addAndGet(1);
      if (source1.get() <= count1) {
        if (queue1.isEmpty()) {
          System.out.println("POST Response Code :: " + source1.get());

        }
        else
        {
          String s = (String)queue1.poll();
          System.out.println(s);
        }
        


        p = "sucess\n ";
      } else {
        queue1.add("con : " + source1.get());
        System.out.println("request Limit was exceeds,so wait for " + min1 + " " + periods1);
        p = "fail-->request Limit was exceeds";
      }
    } else {
      d1 = new Date().getTime();
      source1.set(0);
    }
    return p;
  }
  
  static TimeUnit periods1;
  static long d;
  static long d1;
}