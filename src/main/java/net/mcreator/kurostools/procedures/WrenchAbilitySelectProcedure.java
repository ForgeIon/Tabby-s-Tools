package net.mcreator.kurostools.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class WrenchAbilitySelectProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure WrenchAbilitySelect!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure WrenchAbilitySelect!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (entity.isSneaking() == true) {
			if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 1) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Wrench Set To Claim"), (true));
				}
				itemstack.getOrCreateTag().putDouble("SelectedAbility", 2);
				(itemstack).setDisplayName(new StringTextComponent("Wrench:Claim"));
			} else if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 2) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Wrench Set To Pick Up"), (true));
				}
				itemstack.getOrCreateTag().putDouble("SelectedAbility", 3);
				(itemstack).setDisplayName(new StringTextComponent("Wrench:Pick Up"));
			} else if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 3) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Wrench Set To Special [Block Dependant]"), (true));
				}
				itemstack.getOrCreateTag().putDouble("SelectedAbility", 4);
				(itemstack).setDisplayName(new StringTextComponent("Wrench:Special"));
			} else if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 4) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Wrench Set To Rotate"), (true));
				}
				itemstack.getOrCreateTag().putDouble("SelectedAbility", 1);
				(itemstack).setDisplayName(new StringTextComponent("Wrench:Rotate"));
			} else {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Wrench Set To Rotate"), (true));
				}
				itemstack.getOrCreateTag().putDouble("SelectedAbility", 1);
				(itemstack).setDisplayName(new StringTextComponent("Wrench:Rotate"));
			}
		}
	}
}
