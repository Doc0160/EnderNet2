package pl.asie.endernet.server;

import java.util.HashMap;

import com.google.gson.Gson;

import pl.asie.endernet.EnderNet;
import pl.asie.endernet.api.IEndpoint;
import fi.iki.elonen.NanoHTTPD;
import fi.iki.elonen.NanoHTTPD.*;

public class EnderServer extends NanoHTTPD {
	private static final Gson gson = new Gson();
	private final HashMap<String, IEndpoint> endpoints;
	
	public EnderServer() {
		super(EnderNet.HTTP_PORT);
		this.endpoints = new HashMap<String, IEndpoint>();
		EnderNet.log.info("HTTP server instance UP at port " + this.getListeningPort());
	}
	
	public IEndpoint getEndpoint(String uri) {
		return this.endpoints.get(uri);
	}
	
	public void registerEndpoint(IEndpoint endpoint) {
		String uri = endpoint.getPath();
		String className = endpoint.getClass().getCanonicalName();
		
		if(endpoints.containsKey(uri)) {
			EnderNet.log.warn("URI '" + uri + "' is being overwritten from "
					+ endpoints.get(uri).getClass().getCanonicalName()
					+ " to " + className + "! Please report this to..."
					+ " I don't know, GregTech Intergalactical or someone?"
					+ " Maybe they'll know what to do with it.");
		}
		endpoints.put(uri, endpoint);
	}
	
	@Override
	public Response serve(IHTTPSession session) {
		String uri = session.getUri();
		if(!endpoints.containsKey(uri)) {
			return new Response(Response.Status.NOT_FOUND, MIME_PLAINTEXT, "$$404_NOT_FOUND");
		} else {
			IEndpoint endpoint = endpoints.get(uri);
			Object output = null;
			try {
				if(endpoint.getInputType() != null) {
					String jsonData = session.getQueryParameterString();
					Object data = gson.fromJson(jsonData, endpoint.getInputType());
					output = endpoint.onRequest(data);
				} else {
					output = endpoint.onRequest(null);
				}
			} catch(Exception e) {
				e.printStackTrace();
				return new Response(Response.Status.INTERNAL_ERROR, MIME_PLAINTEXT, "$$500_SERVER_ERROR");
			}
			if(endpoint.getOutputType() != null && output != null) {
				return new Response(Response.Status.OK, "application/json",
						gson.toJson(output, endpoint.getOutputType()));
			} else {
				return new Response(Response.Status.OK, "text/plain", "");
			}
		}
	}
}
