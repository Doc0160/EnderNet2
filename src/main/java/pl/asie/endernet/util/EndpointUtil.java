package pl.asie.endernet.util;

import java.io.BufferedReader;
import java.io.InputStream;

import net.minecraft.tileentity.TileEntity;
import pl.asie.endernet.EnderNet;
import pl.asie.endernet.api.EndpointAnswer;
import pl.asie.endernet.api.IEndpoint;
import pl.asie.endernet.api.EndpointAnswer.Status;

public class EndpointUtil {
	public static IEndpoint getEndpoint(String uri) {
		if(EnderNet.server != null)
			return EnderNet.server.getEndpoint(uri);
		else return null;
	}
	
	public static EndpointAnswer callEndpoint(String server, String uri, Object data) {
		IEndpoint endpoint = getEndpoint(uri);
		if(endpoint == null) {
			EnderNet.log.error("Endpoint " + uri + " not found!");
			return null;
		}
		
		String dataString = "";
		if(endpoint.getInputType() != null)
			EnderNet.gson.toJson(data, endpoint.getInputType());
		
		InputStream outputStream = HTTPUtil.sendPOSTMessage(server, uri, dataString);
		BufferedReader outputReader = MiscUtil.getBufferedReader(outputStream);
		String line = "";
		String output = "";
		try {
			while((line = outputReader.readLine()) != null)
				output += line;
		} catch(Exception e) { e.printStackTrace(); }
		
		if(output.startsWith("$$404_NOT_FOUND"))
			return new EndpointAnswer(null, Status.NOT_FOUND);
		if(output.startsWith("$$500_SERVER_ERROR"))
			return new EndpointAnswer(null, Status.SERVER_ERROR);
		
		if(endpoint.getOutputType() != null) {
			Object out = EnderNet.gson.fromJson(output, endpoint.getOutputType());
			return new EndpointAnswer(out, Status.ANSWERED);
		} else return new EndpointAnswer(null, Status.ANSWERED);
	}

	public static boolean isValidTarget(Object target) {
		if(target instanceof TileEntity && ((TileEntity)target).isInvalid())
			return false;
		
		return true;
	}
}
