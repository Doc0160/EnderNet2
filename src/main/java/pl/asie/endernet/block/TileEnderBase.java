package pl.asie.endernet.block;

import net.minecraft.nbt.NBTTagCompound;
import pl.asie.endernet.api.EnderNetRegistry;
import pl.asie.lib.tile.TileMachine;

public class TileEnderBase extends TileMachine {
	private int ea;
	
	public int getEnderNetAddress() { return ea; }

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("ea")) {
			ea = nbt.getInteger("ea");
			EnderNetRegistry.instance.set(ea, this);
		} else {
			ea = EnderNetRegistry.instance.register(this);
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("ea", ea);
	}
	
	@Override
	public void onBlockDestroy() {
		super.onBlockDestroy();
		EnderNetRegistry.instance.unregister(ea);
	}
}
