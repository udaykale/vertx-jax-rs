package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Application;

/**
 * Utilities to start and stop a JAX-RS application on Vertx
 */
public final class VertxJaxRS {

    private static final Logger LOG = LoggerFactory.getLogger(VertxJaxRS.class);

    private final Router baseRouter;
    private final Vertx vertx;
    private final int port;

    private HttpServer httpServer;

    private VertxJaxRS(Vertx vertx, Router baseRouter, int port) {
        this.vertx = vertx;
        this.baseRouter = baseRouter;
        this.port = port;
    }

    public static VertxJaxRS of(InjectManager injectManager, VertxJaxRSApplicationConf conf)
            throws InjectManagerException, VertxJaxRSParseException {

        // TODO: Check if minimum number of necessary providers and resolvers exist in Inject manager
        // TODO: Check if application resource and singleton classes are valid

        Vertx vertx = injectManager.getInstance(Vertx.class);
        Application application = injectManager.getInstance(Application.class);
        Router baseRouter = ApplicationClassParser.parse(application.getClass(), vertx, injectManager);

        return new VertxJaxRS(vertx, baseRouter, conf.getPort());
    }

    public static VertxJaxRS of(Application application, Vertx vertx, VertxJaxRSApplicationConf conf)
            throws InjectManagerException, VertxJaxRSParseException {

        if (vertx == null) throw new IllegalArgumentException("Vertx instance cannot be null");
        if (application == null) throw new IllegalArgumentException("Application instance cannot be null");

        return of(DefaultInjectManager.of(application, vertx), conf);
    }

    /**
     * Start the HTTP server
     */
    public synchronized void start() {
        if (httpServer == null) {
            LOG.info("Starting server at port {}", port);
            httpServer = vertx.createHttpServer().requestHandler(baseRouter::accept).listen(port, asyncResult -> {
                if (asyncResult.failed() || asyncResult.result() == null) {
                    LOG.error("Failed to start server", asyncResult.cause());
                } else {
                    LOG.info("Successfully started server on port {}", port);
                }
            });
        } else {
            LOG.error("Server is already running.");
        }
    }

    public synchronized void stop() {
        if (httpServer != null) {
            httpServer.close(asyncResult -> {
                if (asyncResult.failed() || asyncResult.result() == null) {
                    LOG.error("Failed to close server", asyncResult.cause());
                } else {
                    LOG.info("Successfully closed server on port {}", port);
                }
            });
        } else {
            LOG.info("Server is already closed.");
        }
    }
}
