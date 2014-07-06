package com.ovaflow.server.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import com.ovaflow.server.dao.AccountInterface;

@Path("/ovf")
public class MessageHandlingService {
	
	@GET
	@Path("/account")
	public Response getAccountInfo(@QueryParam("usr") String username,
			@QueryParam("pass")  String password) {
		AccountInterface ac = null;
		
		//ac.fetchAccountInfo(username, password);
		String output = "User:" + username + " Password: " + password;
		return Response.status(200).entity(output).build();
	}
	
	@GET
	@Path("/score")
	public Response getScoreInfo(@QueryParam("usr") String username,
			@QueryParam("score") String score) {
		AccountInterface ac = null;
		
		//ac.fetchAccountInfo(username, password);
		String output = username + " Score: " + score;
		return Response.status(200).entity(output).build();
	}
}