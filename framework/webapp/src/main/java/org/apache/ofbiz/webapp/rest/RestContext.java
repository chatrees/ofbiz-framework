package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.http.exception.HttpException;
import org.apache.juneau.http.exception.NotFound;
import org.apache.juneau.rest.*;
import org.apache.juneau.rest.util.UrlPathPattern;
import org.apache.juneau.rest.util.UrlPathPatternMatch;
import org.apache.ofbiz.base.util.Debug;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestContext extends org.apache.juneau.rest.RestContext {

    private static final String MODULE = RestContext.class.getName();

    private org.apache.juneau.rest.RestContext parent;
    private RestServlet restServlet;

    public RestContext(RestServlet restServlet, RestContextBuilder builder, org.apache.juneau.rest.RestContext parent) throws Exception {
        super(builder);
        this.parent = parent;
        this.restServlet = restServlet;
    }

    /**
     * see org.apache.juneau.rest.RestContext#findMethod(org.apache.juneau.rest.RestCall)
     */
    protected RestMethodContext findMethod(RestCall call) {
        // TODO read from rest.xml
        List<RestMethodContext> methods = new ArrayList<>();

        String pattern = "/test/{param1}/{param2}";
        UrlPathPattern urlPathPattern = new UrlPathPattern(pattern);
        UrlPathPatternMatch urlPathPatternMatch = urlPathPattern.match(call.getUrlPathInfo());
        if (urlPathPatternMatch != null) {
            call.urlPathPatternMatch(urlPathPatternMatch);
        }
        return null;
    }

    /**
     * Similar to org.apache.juneau.rest.RestMethodContext#invoke(org.apache.juneau.rest.RestCall)
     */
    protected void invoke(RestCall call, RestMethodContext restMethodContext) {
        Debug.logInfo("Invoking rest call: " + call.getPathInfo(), MODULE);

        UrlPathPatternMatch pm = call.getUrlPathPatternMatch();
        if (pm == null)
            throw new NotFound();

        RestRequest req = call.getRestRequest();
        RestResponse res = call.getRestResponse();

        RequestPath rp = req.getPathMatch();
        for (Map.Entry<String,String> e : pm.getVars().entrySet())
            rp.put(e.getKey(), e.getValue());

        Map<String, Object> output = new HashMap<>();

        // TODO set fields returned from calling an event
        output.put("text", "Hello world!");

        call.getRestResponse().setOutput(output);
    }

    @Override
    protected void preCall(RestCall call) throws HttpException {
        try {
            this.restServlet.onPreCall(call.getRestRequest(), call.getRestResponse());
        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        }
    }

    @Override
    protected void startCall(RestCall call) {
        try {
            this.restServlet.onStartCall(call.getRestRequest(), call.getRestResponse());
        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        }
    }

    @Override
    protected void postCall(RestCall call) throws HttpException {
        try {
            this.restServlet.onPostCall(call.getRestRequest(), call.getRestResponse());
        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        }
    }

    @Override
    protected void destroy() {
        try {
            this.restServlet.onDestroy(this);
        } catch (Exception e) {
            throw new HttpException(e.getMessage());
        }
    }

    @Override
    public org.apache.juneau.rest.RestContext postInit() throws ServletException {
        try {
            this.restServlet.onPostInit(this);
            return this;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public org.apache.juneau.rest.RestContext postInitChildFirst() throws ServletException {
        try {
            this.restServlet.onPostInitChildFirst(this);
            return this;
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Called by {@link org.apache.juneau.rest.RestContext#execute(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    @Override
    public void handleNotFound(RestCall call) throws Exception {
        try {
            RestMethodContext restMethodContext = this.findMethod(call);
            this.invoke(call, restMethodContext);
        } catch (NotFound e) {
            if (call.getStatus() == 0)
                call.status(404);
            super.handleNotFound(call);
        }
    }
}
