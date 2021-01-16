package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.rest.RestCall;
import org.apache.juneau.rest.RestContext;
import org.apache.juneau.rest.RestContextBuilder;

public class ControlRestContext extends RestContext {
    /**
     * Constructor.
     *
     * @param builder The servlet configuration object.
     * @throws Exception If any initialization problems were encountered.
     */
    public ControlRestContext(RestContextBuilder builder) throws Exception {
        super(builder);
    }

    @Override
    protected void startCall(RestCall call) {
        super.startCall(call);
    }
}
