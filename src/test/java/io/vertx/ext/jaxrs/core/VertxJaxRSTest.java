package io.vertx.ext.jaxrs.core;

import io.vertx.core.Vertx;
import io.vertx.ext.jaxrs.InjectManagerException;
import io.vertx.ext.jaxrs.VertxJaxRS;
import io.vertx.ext.jaxrs.VertxJaxRSApplicationConf;
import io.vertx.ext.jaxrs.VertxJaxRSParseException;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class VertxJaxRSTest {

    @Test
    public void mainTest() throws InjectManagerException, VertxJaxRSParseException {

        Application application = new VertxApplication();
//        VertxJaxRSApplicationConf conf = VertxJaxRSApplicationConf.of(application, Vertx.vertx(), 8080);
//        VertxJaxRS.of(conf).start();
    }
}
