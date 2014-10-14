package pl.asie.endernet.api;

import java.lang.reflect.Type;

/**
 * Implement this interface for endpoints used by servers.
 * Please use a unique prefix for endpoints added by specific mods.
 * @author asie
 */
public interface IEndpoint {
	/**
	 * Get the type of the input to this endpoint.
	 */
	public Type getInputType();
	/**
	 * Get the class type of the output from this endpoint.
	 */
	public Type getOutputType();
	/**
	 * Get the permission level required for this endpoint.
	 * Example permission levels:
	 * 0 - anyone
	 * 10 - read-only user
	 * 20 - read-write user (trusted user)
	 * 30 - administrator
	 * @return Permission level
	 */
	public int getPermissionLevel();
	
	/**
	 * @return A path, following the convention "/[mod]/[...]"
	 */
	public String getPath();
	
	/**
	 * Called whenever a packet is received at the specified endpoint
	 * on the server side.
	 * @param data The object of a type specified by getEndpointInputType()
	 * @return An object of a type specified by getEndpointOutputType(), or null.
	 */
	public Object onRequest(Object data);
}

