package io.vertx.ext.jaxrs.core;

import javax.ws.rs.Path;

@Path("/hello")
public class HelloWorldTestResource {

    @Path("world")
    public String helloWorld() {
        return "Hello World!!";
    }
}
