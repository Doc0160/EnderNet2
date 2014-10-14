package pl.asie.endernet.endpoint;

import java.lang.reflect.Type;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import com.google.gson.reflect.TypeToken;

import pl.asie.endernet.api.IEndpoint;

public class EndpointAcceptsItems implements IEndpoint {
	@Override
	public Type getInputType() {
		return new TypeToken<ArrayList<String>>(){}.getType();
	}

	@Override
	public Type getOutputType() {
		return new TypeToken<ArrayList<Boolean>>(){}.getType();
	}

	@Override
	public int getPermissionLevel() {
		return 0;
	}

	@Override
	public String getPath() {
		return "/api/acceptsItems";
	}

	@Override
	public Object onRequest(Object data) {
		ArrayList<String> list = (ArrayList<String>)data;
		ArrayList<Boolean> out = new ArrayList<Boolean>();
		for(String s: list) {
			out.add(Block.getBlockFromName(s) != null
					|| Item.itemRegistry.containsKey(s));
		}
		return out;
	}
}
