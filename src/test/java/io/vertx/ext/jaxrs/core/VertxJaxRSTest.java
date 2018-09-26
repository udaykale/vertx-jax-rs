package io.vertx.ext.jaxrs.core;

import io.vertx.core.Vertx;
import io.vertx.ext.jaxrs.InjectManagerException;
import io.vertx.ext.jaxrs.VertxJaxRS;
import io.vertx.ext.jaxrs.VertxJaxRSApplicationConf;
import io.vertx.ext.jaxrs.VertxJaxRSParseException;

import javax.ws.rs.core.Application;

public class VertxJaxRSTest {

    public static void main(String[] args) throws InjectManagerException, VertxJaxRSParseException {

        int port = 8080;
        Vertx vertx = Vertx.vertx();
        Application application = new VertxApplication();
        VertxJaxRSApplicationConf conf = VertxJaxRSApplicationConf.of(port);
        VertxJaxRS.of(application, vertx, conf).start();
    }
}
