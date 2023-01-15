package net.mcreator.kurostools.procedures;

import net.minecraft.item.ItemStack;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class TeleporterRemoteGlowProcedure {

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure TeleporterRemoteGlow!");
			return false;
		}
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		boolean TOF = false;
		if (itemstack.getOrCreateTag().getBoolean("SelectedBlock") == true) {
			TOF = (true);
		} else {
			TOF = (false);
		}
		return TOF;
	}
}
