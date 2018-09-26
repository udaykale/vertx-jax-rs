package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.lang.reflect.Method;

class ResourceMethodParser {
    static <T> Router parse(Method method, T inst, Vertx vertx) {

        // TODO: @Consumes
        // TODO: @Produces
        // TODO: @Path
        // TODO: @Get
        // TODO: @Put
        // TODO: @Post
        // TODO: @Options
        // TODO: @Context
        // TODO: @QueryParam
        // TODO: @PathParam
        // TODO: @HeaderParam
        // TODO: @MatrixParam
        // TODO: @BeanParam
        // TODO: @FormParam

        Router router = Router.router(vertx);

        String path = method.getAnnotation(Path.class).value();
        HttpMethod httpMethod = httpMethod(method);

        router.route(httpMethod, path)
                .handler(routingContext -> {
                    try {
                        method.invoke(inst);
                        routingContext.response().end("done!!");
                    } catch (BadRequestException e) {
                        routingContext.response().setStatusCode(Response.Status.FORBIDDEN.getStatusCode())
                                .setStatusMessage(e.getMessage())
                                .end();
                    } catch (ForbiddenException e) {
                        routingContext.fail(400);
                    } catch (NotAcceptableException e) {
                        routingContext.fail(400);
                    } catch (NotAllowedException e) {
                        routingContext.fail(400);
                    } catch (NotAuthorizedException e) {
                        routingContext.fail(400);
                    } catch (NotFoundException e) {
                        routingContext.fail(400);
                    } catch (NotSupportedException e) {
                        routingContext.fail(400);
                    } catch (Exception e) {
                        routingContext.fail(e);
                    }
                });

        return router;
    }

    private static HttpMethod httpMethod(Method method) {
        if (method.isAnnotationPresent(GET.class)) return HttpMethod.GET;
        else if (method.isAnnotationPresent(PUT.class)) return HttpMethod.PUT;
        else if (method.isAnnotationPresent(POST.class)) return HttpMethod.POST;
        else if (method.isAnnotationPresent(DELETE.class)) return HttpMethod.DELETE;
        else if (method.isAnnotationPresent(OPTIONS.class)) return HttpMethod.OPTIONS;
        else if (method.isAnnotationPresent(PATCH.class)) return HttpMethod.PATCH;
        else if (method.isAnnotationPresent(HEAD.class)) return HttpMethod.HEAD;
        else throw new IllegalStateException(""); // TODO: Error handling
    }
}
