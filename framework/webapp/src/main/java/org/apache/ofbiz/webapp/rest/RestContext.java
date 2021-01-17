package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.http.exception.HttpException;
import org.apache.juneau.rest.RestCall;
import org.apache.juneau.rest.RestContextBuilder;

public class RestContext extends org.apache.juneau.rest.RestContext {

    private org.apache.juneau.rest.RestContext parent;
    private RestServlet restServlet;

    public RestContext(RestServlet restServlet, RestContextBuilder builder, org.apache.juneau.rest.RestContext parent) throws Exception {
        super(builder);
        this.parent = parent;
        this.restServlet = restServlet;
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
}
