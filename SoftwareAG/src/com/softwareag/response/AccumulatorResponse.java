package com.softwareag.response;

import java.io.IOException;
import java.io.PrintStream;



public class AccumulatorResponse
  extends Thread
{
  public AccumulatorResponse() {}
  
  public static void get(String url)
    throws IOException
  {
    Thread t = new AccumulatorResponse();
    t.start();
  }
  


  public void run()
  {
    System.out.println("hit count : +source1.get()+ using LongAccumulator");
  }
}