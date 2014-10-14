package pl.asie.endernet.util;

import java.io.IOException;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTSizeTracker;

public class PortableItemStack {
	private String itemName;
	private int damage, quantity;
	private byte[] nbtData;
	
	public PortableItemStack(ItemStack is) throws IOException, UnsupportedItemStackException {
		this.itemName = Item.itemRegistry.getNameForObject(is.getItem());
		this.damage = is.getItemDamage();
		this.quantity = is.stackSize;
		if(is.hasTagCompound())
			this.nbtData = CompressedStreamTools.compress(is.getTagCompound());
	}
	
	public ItemStack toItemStack() throws IOException {
		ItemStack is = new ItemStack((Item)Item.itemRegistry.getObject(itemName), quantity, damage);
		if(nbtData != null && nbtData.length > 0)
			is.setTagCompound(CompressedStreamTools.func_152457_a(nbtData, new NBTSizeTracker(2097152L)));
		return is;
	}
}
