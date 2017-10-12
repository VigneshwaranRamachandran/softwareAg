package com.softwareag.resource;

import java.util.concurrent.TimeUnit;

public class ThrotlingPojoResource { private int count;
  private int min;
  private TimeUnit periods;
  private String Api;
  
  public ThrotlingPojoResource() {}
  
  public int getCount() { return count; }
  
  public void setCount(int count) {
    this.count = count;
  }
  
  public int getMin() { return min; }
  
  public void setMin(int min) {
    this.min = min;
  }
  
  public TimeUnit getPeriods() {
    return periods;
  }
  
  public void setPeriods(TimeUnit periods) { this.periods = periods; }
  
  public String getApi() {
    return Api;
  }
  
  public void setApi(String api) { Api = api; }
}