package com.softwareag.controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.RestClient;
import com.softwareag.resource.RequestJsonObjects;
import com.softwareag.response.AtomicIntegerResponse;
import com.softwareag.performance.PerformanceCheck;
@Path("/index")
public class ElasticSearchRestController extends Thread{
	RestClient restClient = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	org.elasticsearch.client.Response response;   
	@GET
	@Produces("text/html")
	public Response getFirst() {
		
		
		//java.net.URI location = new java.net.URI("/index.html");
		//return Response.temporaryRedirect(location);
		return Response.status(200).entity("sucess").build();
	}

	@GET
	@Path("/get")
	@Produces("text/plain")
	public Response getRetrive( @QueryParam("urls") String url) throws IOException {
		
		Object o	= new AtomicIntegerResponse().get(url);
		PerformanceCheck p = (PerformanceCheck)o;
		//Object o1 = AdderResponse.get(url);
		try {
			sleep(2000);
	
		         
		         System.out.println("Starting time of AtomicInteger : "+p.getD1());
		         System.out.println("Endind time of AtomicInteger : "+p.getD2());
		         System.out.println("response thread name : "+p.getT());
		         System.out.println("response : "+p.getResponse());
		       //  PerformanceCheck p1 = (PerformanceCheck)o1;
		        // System.out.println("Starting time of LongAdder : "+p1.getD1());
		       //  System.out.println("Ending time of LongAdder : "+p1.getD2());
		}     catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		 return Response.status(200).entity(EntityUtils.toString((HttpEntity) ((org.elasticsearch.client.Response) p.getResponse()).getEntity())).header("Access-Control-Allow-Origin", "*").build();  
		         
		       //  return Response.status(200).entity("sucess").header("Access-Control-Allow-Origin", "*").build();
	}
	@POST
	@Path("/post")
	@Consumes({"application/xml", "application/json", "text/plain"})
	@Produces({"application/xml", "application/json", "text/plain"})
	public Response postRetrive( RequestJsonObjects re) throws IOException {
//	String[] words=url.split("\\&");
		NStringEntity entity = new NStringEntity(re.jsonl, ContentType.APPLICATION_JSON);
				 response = restClient.performRequest("POST",re.url,Collections.<String, String>emptyMap(),entity);                      
				return Response.status(200).entity(EntityUtils.toString((HttpEntity) response.getEntity())).header("Access-Control-Allow-Origin", "*").build();
		
		
//		for(String word:words)
//		{
//			System.out.println(word);
//		}
//		System.out.println(re.jsonl);
//		System.out.println(re.url);
//		
//		return Response.status(200).entity("words[0]").header("Access-Control-Allow-Origin", "*").build();
	}
	
}
