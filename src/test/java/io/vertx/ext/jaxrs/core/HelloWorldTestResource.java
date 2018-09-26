package io.vertx.ext.jaxrs.core;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldTestResource {

    @GET
    @Path("/world")
    public String gethelloWorld() {
        return "Hello World!!";
    }

    @PUT
    @Path("/world")
    public String puthelloWorld() {
        return "Hello World!!";
    }

    @POST
    @Path("/world")
    public String posthelloWorld() {
        return "Hello World!!";
    }
}
