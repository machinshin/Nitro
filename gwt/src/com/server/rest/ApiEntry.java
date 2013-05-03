package com.server.rest;

import com.server.model.*;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import org.fusesource.restygwt.client.Resource;
import org.fusesource.restygwt.client.RestServiceProxy;
import com.google.gwt.core.client.GWT;

public interface ApiEntry extends RestService {
/*
    @POST
    @Path("/api/yelp.json/{id}")
    void getYelpReviews(
        @PathParam("id") String id,
            MethodCallback<List<YelpReview>> callback );

    @POST
    @Path("/api/twitter.json/{id}")
    void getTweets(
        @PathParam("id") String id,
        MethodCallback<List<Tweets>> callback );
*/

    @POST
    @Path("/api/all.json/{id}")
    void getAll(
        @PathParam("id") String id,
        MethodCallback<NitroJsonWrapper> callback );

    public static final class Util {
        private static ApiEntry instance;
        public static final ApiEntry get() {
            if (instance == null ) {
                instance = GWT.create(ApiEntry.class);
                ((RestServiceProxy) instance).setResource( new Resource("/"));
            }
            return instance;
     }
    private Util() {
        //Don't instantiate Util class
     }

    };

 }
