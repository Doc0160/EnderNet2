package pl.asie.endernet.endpoint;

import java.lang.reflect.Type;

import pl.asie.endernet.EnderNet;
import pl.asie.endernet.api.IEndpoint;

public class EndpointVersion implements IEndpoint {
	@Override
	public Type getInputType() {
		return null;
	}

	@Override
	public Type getOutputType() {
		return Integer.TYPE;
	}

	@Override
	public String getPath() {
		return "/api/version";
	}

	@Override
	public Object onRequest(Object data) {
		return EnderNet.API_VERSION;
	}

	@Override
	public int getPermissionLevel() { return 0; }
}
