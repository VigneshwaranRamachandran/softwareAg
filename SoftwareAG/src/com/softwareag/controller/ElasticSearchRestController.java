package com.softwareag.controller;

import com.softwareag.resource.RequestJsonObjects;
import com.softwareag.resource.ThrotlingPojoResource;
import com.softwareag.response.AtomicIntegerResponseWithQueuing;
import java.io.IOException;
import java.io.PrintStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;










@Path("/index")
public class ElasticSearchRestController
  extends Thread
{
  StringBuilder stBuilder;
  
  public ElasticSearchRestController() {}
  
  @GET
  @Produces({"text/plain"})
  public Response checking()
  {
    return Response.status(200).entity("sucess").build();
  }
  
  @GET
  @Path("/get")
  @Produces({"text/plain"})
  public Response getRetrive(@QueryParam("urls") String url, @QueryParam("count") int count) throws IOException, InterruptedException {
    String o = new AtomicIntegerResponseWithQueuing().get(url, count);
    return Response.status(200).entity(o).build();
  }
  
  @POST
  @Path("/post")
  @Consumes({"application/xml", "application/json", "text/plain"})
  @Produces({"application/xml", "application/json", "text/plain"})
  public Response postRetrive(RequestJsonObjects re) throws IOException {
    System.out.println(re.getClick());
    System.out.println(re.getJsonl());
    System.out.println(re.getUrl());
    String o = new AtomicIntegerResponseWithQueuing().post(re);
    return Response.status(200).entity(o).header("Access-Control-Allow-Origin", "*").build();
  }
  
  @POST
  @Path("/throtling")
  @Consumes({"application/xml", "application/json", "text/plain"})
  @Produces({"application/xml", "application/json", "text/plain"})
  public Response limitRetrive(ThrotlingPojoResource tr) throws IOException { String res = new AtomicIntegerResponseWithQueuing().display(tr);
    return Response.status(200).entity(res).header("Access-Control-Allow-Origin", "*").build();
  }
}