package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.dto.swagger.Swagger;
import org.apache.juneau.http.MediaType;
import org.apache.juneau.rest.BasicRestServlet;
import org.apache.juneau.rest.RestRequest;
import org.apache.juneau.rest.annotation.RestMethod;
import org.apache.ofbiz.base.util.Debug;

import static org.apache.juneau.dto.swagger.SwaggerBuilder.*;
import static org.apache.juneau.http.HttpMethod.GET;

public class RestUiServlet extends BasicRestServlet {
    private static final String MODULE = RestUiServlet.class.getName();

    @RestMethod(name=GET, path="/*")
    @Override
    public Swagger getOptions(RestRequest req) {
        Swagger originalSwagger = req.getSwagger();

        // TODO Custom Swagger
        try {
            Swagger swagger = swagger()
                    .swagger("2.0")
                    .info(
                            info("Swagger Petstore", "1.0.0")
                                    .description("This is a sample server Petstore server.")
                                    .termsOfService("http://swagger.io/terms/")
                                    .contact(
                                            contact().email("apiteam@swagger.io")
                                    )
                                    .license(
                                            license("Apache 2.0").url("http://www.apache.org/licenses/LICENSE-2.0.html")
                                    )
                    )
                    .consumes(originalSwagger.getConsumes())
                    .produces(originalSwagger.getProduces())
                    .definitions(originalSwagger.getDefinitions())
                    .tags(
                            tag("pet").description("Pet")
                    )
                    .path("/pet", "post",
                            operation()
                                    .tags("pet")
                                    .summary("Add a new pet to the store")
                                    .description("")
                                    .operationId("addPet")
                                    .consumes(MediaType.JSON, MediaType.XML)
                                    .produces(MediaType.JSON, MediaType.XML)
                                    .parameters(
                                            parameterInfo("body", "body")
                                                    .description("Pet object that needs to be added to the store")
                                                    .required(true)
                                                    .type("String")
                                                    .example("body 1")
                                    )
                                    .response("405", responseInfo("Invalid input"))
                    );

            return swagger;
        } catch (Exception e) {
            Debug.logError(e, MODULE);
            return null;
        }
    }
}
