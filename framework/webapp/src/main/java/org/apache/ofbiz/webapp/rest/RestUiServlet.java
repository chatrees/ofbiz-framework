package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.annotation.BeanConfig;
import org.apache.juneau.dto.swagger.Swagger;
import org.apache.juneau.html.HtmlDocSerializer;
import org.apache.juneau.html.annotation.HtmlDocConfig;
import org.apache.juneau.rest.RestRequest;
import org.apache.juneau.rest.RestServlet;
import org.apache.juneau.rest.annotation.RestMethod;
import org.apache.juneau.rest.config.BasicUniversalRest;
import org.apache.ofbiz.webapp.rest.swagger.ui.SwaggerHtmlDocTemplate;
import org.apache.ofbiz.webapp.rest.swagger.ui.SwaggerUI;

import static org.apache.juneau.http.HttpMethod.GET;

/**
 * @see HtmlDocSerializer#HtmlDocSerializer(org.apache.juneau.PropertyStore, java.lang.String, java.lang.String)
 */
@HtmlDocConfig(
        template = SwaggerHtmlDocTemplate.class
)
@BeanConfig(
        // POJO swaps to apply to all serializers/parsers on this method.
        swaps={
                // Use the SwaggerUI swap when rendering Swagger beans.
                // This is a per-media-type swap that only applies to text/html requests.
                SwaggerUI.class
        }
)
public class RestUiServlet extends RestServlet implements BasicUniversalRest {
    private static final String MODULE = RestUiServlet.class.getName();

    @RestMethod(name=GET, path="/*")
    public Swagger getOptions(RestRequest req) {
        return req.getSwagger();
    }
}
