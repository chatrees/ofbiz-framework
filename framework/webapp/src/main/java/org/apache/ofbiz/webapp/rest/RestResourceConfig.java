package org.apache.ofbiz.webapp.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;

public class RestResourceConfig extends ResourceConfig {

    public RestResourceConfig() {
        final Resource.Builder resourceBuilder = Resource.builder();
        final Resource resource = resourceBuilder.build();
        registerResources(resource);
    }
}
