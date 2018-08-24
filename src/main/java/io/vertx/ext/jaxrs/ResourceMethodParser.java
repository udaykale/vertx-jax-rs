package io.vertx.ext.jaxrs;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

class ResourceMethodParser {

    static List<Router> parse(Method method, ResourceClassInfo resourceClassInfo,
                              Vertx vertx, InjectManager injectManager) {

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
        return Collections.emptyList();
    }
}
