package io.vertx.ext.jaxrs.core;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class VertxApplication extends Application {

    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();

        singletons.add(new HelloWorldTestResource());

        return singletons;
    }
}

