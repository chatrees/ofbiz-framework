package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.dto.swagger.Swagger;
import org.apache.juneau.dto.swagger.ui.SwaggerUI;
import org.apache.juneau.jsonschema.annotation.JsonSchemaConfig;
import org.apache.juneau.rest.RestContext;
import org.apache.juneau.rest.RestRequest;
import org.apache.juneau.rest.annotation.HtmlDoc;
import org.apache.juneau.rest.annotation.Rest;
import org.apache.juneau.rest.annotation.RestMethod;
import org.apache.juneau.rest.annotation.RestResource;
import org.apache.juneau.rest.util.UrlPathPattern;
import org.apache.juneau.rest.util.UrlPathPatternMatch;
import org.apache.ofbiz.base.util.Debug;

import java.util.HashMap;
import java.util.Map;

import static org.apache.juneau.http.HttpMethod.GET;
import static org.apache.juneau.http.HttpMethod.OPTIONS;

@Rest(

        // Allow OPTIONS requests to be simulated using ?method=OPTIONS query parameter.
        allowedMethodParams="OPTIONS"
)
public class RestServlet extends org.apache.juneau.rest.RestServlet {

    private static final String MODULE = RestServlet.class.getName();

    @RestMethod(method = GET, path = "*")
    public void onGet(RestContext restContext) {
        RestRequest restRequest = restContext.getRequest();
        String pathInfo = restRequest.getPathInfo();
        Debug.logInfo("GET: " + restContext, MODULE);

        String pattern = "/test/{param1}/{param2}";
        UrlPathPattern urlPathPattern = new UrlPathPattern(pattern);
        UrlPathPatternMatch urlPathPatternMatch = urlPathPattern.match(pathInfo);
        if (urlPathPatternMatch != null) {

        }

        Map<String, Object> output = new HashMap<>();

        // TODO set fields returned from calling an event
        output.put("text", "Hello world!");

        restContext.getResponse().setOutput(output);
    }

    /**
     * [OPTIONS /*] - Show resource options.
     *
     * @param req The HTTP request.
     * @return A bean containing the contents for the OPTIONS page.
     */
    @RestMethod(name=OPTIONS, path="/*",

            summary="Swagger documentation",
            description="Swagger documentation for this resource.",

            htmldoc=@HtmlDoc(
                    // Override the nav links for the swagger page.
                    navlinks={
                            "back: servlet:/",
                            "json: servlet:/?method=OPTIONS&Accept=text/json&plainText=true"
                    },
                    // Never show aside contents of page inherited from class.
                    aside="NONE"
            )
    )
    @JsonSchemaConfig(
            // Add descriptions to the following types when not specified:
            addDescriptionsTo="bean,collection,array,map,enum",
            // Add x-example to the following types:
            addExamplesTo="bean,collection,array,map",
            // Don't generate schema information on the Swagger bean itself or HTML beans.
            ignoreTypes="Swagger,org.apache.juneau.dto.html5.*",
            // Use $ref references for bean definitions to reduce duplication in Swagger.
            useBeanDefs="true"
    )
    @BeanConfig(
            // When parsing generated beans, ignore unknown properties that may only exist as getters and not setters.
            ignoreUnknownBeanProperties="true",
            // POJO swaps to apply to all serializers/parsers on this method.
            swaps={
                    // Use the SwaggerUI swap when rendering Swagger beans.
                    // This is a per-media-type swap that only applies to text/html requests.
                    SwaggerUI.class
            }
    )
    public Swagger getOptions(RestRequest restRequest) {
        return restRequest.getSwagger();
    }
}
