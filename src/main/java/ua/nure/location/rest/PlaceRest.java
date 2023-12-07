package ua.nure.location.rest;

import jakarta.ws.rs.core.UriBuilder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import ua.nure.location.rest.providers.CommonJsonProvider;
import ua.nure.location.rest.providers.PlaceJsonProvider;
import ua.nure.location.rest.providers.PlaceXmlProvider;
import ua.nure.location.rest.providers.exception.CommonExceptionMapper;
import ua.nure.location.rest.providers.exception.ValidationExceptionMapper;

import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PlaceRest {
    public static void main(String[] args) throws IOException {
        System.out.println("Place Application");

        ResourceConfig resourceConfig = new ResourceConfig(getClasses());
        URI baseUri = getBaseURI(Constants.BASE_URI, Constants.PORT);

        var container = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
        System.out.println("Application started.\nTry accessing " + baseUri + " in the browser.");
        System.out.println("WADL accessing: " + baseUri + "/application.wadl in the browser.\n");

        System.in.read(); // Keep the application running

        container.shutdown();

    }

    public static URI getBaseURI(String basePath, int port) {
        UriBuilder builder = UriBuilder.fromUri(basePath).port(port);

        return builder.build();
    }

    public static Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(PlaceRestService.class);
        classes.add(CommonExceptionMapper.class);
        classes.add(ValidationExceptionMapper.class);
        classes.add(CommonJsonProvider.class);
        classes.add(PlaceJsonProvider.class);
        classes.add(PlaceXmlProvider.class);
        classes.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
        return Collections.unmodifiableSet(classes);
    }
}
