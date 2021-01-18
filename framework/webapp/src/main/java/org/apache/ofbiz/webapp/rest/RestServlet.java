package org.apache.ofbiz.webapp.rest;

import org.apache.juneau.rest.RestContextBuilder;

import javax.servlet.ServletException;

public class RestServlet extends org.apache.juneau.rest.RestServlet {

    @Override
    public synchronized void setContext(org.apache.juneau.rest.RestContext context) throws ServletException {
        try {
            RestContextBuilder builder = org.apache.juneau.rest.RestContext.create(this);
            super.setContext(new RestContext(this, builder, context));
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
