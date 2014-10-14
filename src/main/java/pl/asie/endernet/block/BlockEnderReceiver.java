package pl.asie.endernet.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import pl.asie.endernet.EnderNet;
import pl.asie.lib.block.BlockBase;

public class BlockEnderReceiver extends BlockBase {

	public BlockEnderReceiver() {
		super(Material.circuits, EnderNet.instance);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
