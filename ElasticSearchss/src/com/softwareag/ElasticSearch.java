package com.softwareag;

import java.io.FileNotFoundException;
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


@Path("/index")
public class ElasticSearch {
	RestClient restClient = RestClient.builder(
		       new HttpHost("localhost", 9200, "http"),
		       new HttpHost("localhost", 9205, "http")).build();
	org.elasticsearch.client.Response response;   
	@GET
	@Produces("text/html")
	public Response getFirst() throws FileNotFoundException {
		return Response.status(200).entity("fis").build();
	}

	@GET
	@Path("/get")
	@Produces("text/html")
	public Response getRetrive( @QueryParam("urls") String url) throws IOException {
		System.out.println("inside get method");
		Object o1	= new AtomicIntegerResponse().get(url);
		Object o2 = new AdderResponse().get(url);
		          
		//response = restClient.performRequest("GET",url);
		return Response.status(200).entity(EntityUtils.toString((HttpEntity) ((org.elasticsearch.client.Response) o1).getEntity())).header("Access-Control-Allow-Origin", "*").build();
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
