package net.mcreator.kurostools.procedures;

import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class DrillUsagePowerlossProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure DrillUsagePowerloss!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack Itemstack = ItemStack.EMPTY;
		Itemstack = ((entity instanceof LivingEntity) ? ((LivingEntity) entity).getHeldItemMainhand() : ItemStack.EMPTY);
		if ((Itemstack).getOrCreateTag().getDouble("DrillEnergy") >= 1) {
			(Itemstack).getOrCreateTag().putDouble("DrillEnergy", ((Itemstack).getOrCreateTag().getDouble("DrillEnergy") - 1));
		}
	}
}
