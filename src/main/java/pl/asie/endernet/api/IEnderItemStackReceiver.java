package pl.asie.endernet.api;

import net.minecraft.item.ItemStack;

public interface IEnderItemStackReceiver {
	public boolean injectItemStack(ItemStack stack);
}
