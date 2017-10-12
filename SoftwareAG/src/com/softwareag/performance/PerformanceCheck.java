package com.softwareag.performance;

import java.util.Date;

public class PerformanceCheck
{
  private Date d1;
  private Date d2;
  private org.elasticsearch.client.Response response;
  private Thread t;
  private java.util.concurrent.atomic.AtomicInteger aI;
  
  public PerformanceCheck() {}
  
  public java.util.concurrent.atomic.AtomicInteger getaI() {
    return aI;
  }
  
  public void setaI(java.util.concurrent.atomic.AtomicInteger aI) { this.aI = aI; }
  
  public Date getD1() {
    return d1;
  }
  
  public void setD1(Date d1) { this.d1 = d1; }
  
  public Date getD2() {
    return d2;
  }
  
  public void setD2(Date d2) { this.d2 = d2; }
  
  public org.elasticsearch.client.Response getResponse() {
    return response;
  }
  
  public void setResponse(org.elasticsearch.client.Response response) { this.response = response; }
  
  public Thread getT() {
    return t;
  }
  
  public void setT(Thread t) { this.t = t; }
}