package net.mcreator.kurostools.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.EnchantmentHelper;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class MechanicalArmorLockProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure MechanicalArmorLock!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure MechanicalArmorLock!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (itemstack.getOrCreateTag().getBoolean("ItemLock") == false) {
			entity.getPersistentData().putDouble("ArmorLock", (entity.getPersistentData().getDouble("ArmorLock") + 1));
			itemstack.getOrCreateTag().putBoolean("ItemLock", (true));
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("ArmorLock Set To +1"), (true));
			}
		}
		if (entity.getPersistentData().getDouble("ArmorLock") == 4) {
			if (!(EnchantmentHelper.getEnchantmentLevel(Enchantments.BINDING_CURSE, itemstack) != 0)) {
				(itemstack).addEnchantment(Enchantments.BINDING_CURSE, (int) 10);
			}
		}
	}
}
