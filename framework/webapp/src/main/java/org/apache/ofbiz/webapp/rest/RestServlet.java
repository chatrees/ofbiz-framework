package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.dto.swagger.Info;
import org.apache.juneau.dto.swagger.Swagger;
import org.apache.juneau.dto.swagger.SwaggerBuilder;
import org.apache.juneau.rest.RestContext;
import org.apache.juneau.rest.RestRequest;
import org.apache.juneau.rest.annotation.RestMethod;
import org.apache.juneau.rest.util.UrlPathPattern;
import org.apache.juneau.rest.util.UrlPathPatternMatch;
import org.apache.ofbiz.base.util.Debug;

import java.util.HashMap;
import java.util.Map;

import static org.apache.juneau.http.HttpMethod.GET;

public class RestServlet extends org.apache.juneau.rest.BasicRestServlet {

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


    @Override
    public Swagger getOptions(RestRequest req) {
        // TODO custom Swagger
        Info info = new Info();
        info.setTitle("Test 1");
        info.setVersion("1.0");
        // FIXME NullPointerException
        Swagger swagger = SwaggerBuilder.swagger()
                .basePath("/basePath");
        return req.getSwagger();
    }
}
