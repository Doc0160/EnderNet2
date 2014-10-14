package pl.asie.endernet.endpoint;

import java.io.IOException;
import java.lang.reflect.Type;

import net.minecraft.item.ItemStack;
import pl.asie.endernet.api.EnderNetRegistry;
import pl.asie.endernet.api.IEnderItemStackReceiver;
import pl.asie.endernet.api.IEndpoint;
import pl.asie.endernet.util.EndpointUtil;
import pl.asie.endernet.util.PortableItemStack;

public class EndpointSendItem implements IEndpoint {
	public class Packet {
		private int address;
		private PortableItemStack is;
		
		public Packet(int address, ItemStack stack) throws Exception {
			this.address = address;
			this.is = new PortableItemStack(stack);
		}
		
		public int getAddress() { return address; }
		public ItemStack getItemStack() throws IOException {
			return is.toItemStack();
		}
	}
	
	@Override
	public Type getInputType() {
		return Packet.class;
	}

	@Override
	public Type getOutputType() {
		return Boolean.class;
	}

	@Override
	public int getPermissionLevel() {
		return 20;
	}

	@Override
	public String getPath() {
		return "/endernet/sendItem";
	}

	@Override
	public Object onRequest(Object data) {
		Packet p = (Packet)data;
		Object target = EnderNetRegistry.instance.get(p.getAddress());
		try {
			if(target instanceof IEnderItemStackReceiver && EndpointUtil.isValidTarget(target))
				return ((IEnderItemStackReceiver)target).injectItemStack(p.getItemStack());
			else
				return false;
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
