package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.rest.RestCall;
import org.apache.juneau.rest.RestContextBuilder;

public class RestContext extends org.apache.juneau.rest.RestContext {

    private org.apache.juneau.rest.RestContext parent;

    public RestContext(RestContextBuilder builder, org.apache.juneau.rest.RestContext parent) throws Exception {
        super(builder);
        this.parent = parent;
    }

    @Override
    protected void startCall(RestCall call) {
        super.startCall(call);
    }
}
