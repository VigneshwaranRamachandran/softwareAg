/* 
 * Vigneshwaran.Ramachandran
 */

package com.softwareag.controller;

import java.io.IOException;
import java.util.Collections;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.RestClient;

import com.softwareag.performance.PerformanceCheck;
import com.softwareag.resource.RequestJsonObjects;
import com.softwareag.response.AtomicIntegerResponse;
@Path("/index")
public class ElasticSearchRestController extends Thread{
	RestClient clientNode1 = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	RestClient clientNode2 = RestClient.builder(
		       new HttpHost("localhost", 9201, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	static org.elasticsearch.client.Response response; 
	 StringBuilder stBuilder;
	 
	 @GET
	 @Produces("text/plain")
	 public Response checking(){
		 return Response.status(200).entity("sucess").build();
	 }
	 
	 
	// for get value from the elasticsearch
	@GET
	@Path("/get")
	@Produces("text/plain")
	public Response getRetrive( @QueryParam("urls") String url,@QueryParam("count") int count) throws IOException {
		Object o	= new AtomicIntegerResponse().get(url,count);
		PerformanceCheck p = (PerformanceCheck)o;
		stBuilder=new StringBuilder("**Starting time of AtomicInteger : "+p.getD1()+"**");
   stBuilder.append("**Endind time of AtomicInteger : "+p.getD2()+"**");
      stBuilder.append("**Count expected :200/per hit & created count: "+p.getaI()+"**");
      //System.out.println(p.getResponse());
     // return Response.status(200).entity("sucess").build();
		 return Response.status(200).entity(EntityUtils.toString((HttpEntity) ((org.elasticsearch.client.Response) p.getResponse()).getEntity())+stBuilder.toString()).header("Access-Control-Allow-Origin", "*").build();  
	}
	// for push value into the elasticsearch
	@POST
	@Path("/post")
	@Consumes({"application/xml", "application/json", "text/plain"})
	@Produces({"application/xml", "application/json", "text/plain"})
	public Response postRetrive( RequestJsonObjects re) throws IOException {
		NStringEntity entity = new NStringEntity(re.jsonl, ContentType.APPLICATION_JSON);
				 response = clientNode1.performRequest("POST",re.url,Collections.<String, String>emptyMap(),entity);  
				response= clientNode2.performRequest("POST",re.url,Collections.<String, String>emptyMap(),entity);  
				return Response.status(200).entity(EntityUtils.toString((HttpEntity) response.getEntity())).header("Access-Control-Allow-Origin", "*").build();
	}
	
}
