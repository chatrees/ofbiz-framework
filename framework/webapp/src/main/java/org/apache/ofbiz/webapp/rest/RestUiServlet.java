package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.dto.atom.Feed;
import org.apache.juneau.dto.atom.Person;
import org.apache.juneau.dto.swagger.Swagger;
import org.apache.juneau.html.HtmlSerializer;
import org.apache.juneau.http.MediaType;
import org.apache.juneau.json.JsonSerializer;
import org.apache.juneau.rest.BasicRestServlet;
import org.apache.juneau.rest.RestRequest;
import org.apache.juneau.rest.annotation.RestMethod;
import org.apache.ofbiz.base.util.Debug;

import static org.apache.juneau.dto.atom.AtomBuilder.link;
import static org.apache.juneau.dto.atom.AtomBuilder.*;
import static org.apache.juneau.dto.html5.HtmlBuilder.*;
import static org.apache.juneau.dto.swagger.SwaggerBuilder.*;
import static org.apache.juneau.http.HttpMethod.GET;

public class RestUiServlet extends BasicRestServlet {
    private static final String MODULE = RestUiServlet.class.getName();

    @RestMethod(name=GET, path="/*")
    @Override
    public Swagger getOptions(RestRequest req) {

        // TODO Custom Swagger
        try {
            //Produces
            /**
             * <table>
             * <tr>
             * <th>c1</th>
             * <th>c2</th>
             * </tr>
             * <tr>
             * <td>v1</td>
             * <td>v2</td>
             * </tr>
             * </table>
             */
            Object mytable =
                    table(
                            tr(
                                    th("c1"),
                                    th("c2")
                            ),
                            tr(
                                    td("v1"),
                                    td("v2")
                            )
                    );

            String html = HtmlSerializer.DEFAULT.serialize(mytable);

            Object mainJsp =
                    form().action("main.jsp").method("GET")
                            .children(
                                    input("text").name("first_name").value("apache"), br(),
                                    input("text").name("last_name").value("juneau"), br(),
                                    button("submit", "Submit"),
                                    button("reset", "Reset")
                            );

            /**
             * <form action='main.jsp' method='POST'>
             * Position (1-10000): <input name='pos' type='number'
             * value='1'/><br/>
             * Limit (1-10000): <input name='pos' type='number'
             * value='100'/><br/>
             * <button type='submit'>Submit</button>
             * <button type='reset'>Reset</button>
             * </form>
             */
            html = HtmlSerializer.DEFAULT.serialize(mainJsp);

            /**
             * Produces
             * {
             *    a:{action:'main.jsp',method:'GET'},
             *    c:[
             *    {a:{type:'text',name:'first_name',value:'apache'}},{},
             *    {a:{type:'text',name:'last_name',value:'juneau'}},{},
             *    {a:{type:'submit'},c:['Submit']},
             *    {a:{type:'reset'},c:['Reset']}
             *    ]
             * }
             */
            html =  JsonSerializer.create().simple().sq().build().serialize(mainJsp);

            Feed feed =
                    feed("tag:juneau.apache.org", "Juneau ATOM specification", "2018-12-15T08:52:05Z")
                            .title("Example apache Juneau feed")
                            .subtitle(text("html").text("Describes <em>stuff</em> about Juneau"))
                            .links(
                                    link("alternate", "text/html", "http://juneau.apache.org/").hreflang("en"),
                                    link("self", "application/atom+xml", "http://juneau.apache.org/feed.atom")
                            )
                            .rights("Copyright (c) 2016, Apache Foundation")
                            .authors(new Person("Juneau_Commiter"))
                            .updated("2018-12-15T08:52:05Z")
                            .entries(
                                    entry("tag:juneau.sample.com,2013:1.2345", "Juneau ATOM specification snapshot", "2016-01-02T03:04:05Z")
                                            .published("2016-01-02T03:04:05Z")
                                            .content(
                                                    content("xhtml")
                                                            .lang("en")
                                                            .base("http://www.apache.org/")
                                                            .text("<div><p><i>[Update: Juneau supports ATOM.]</i></p></div>")
                                            )
                            );

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
