package net.mcreator.kurostools.procedures;

import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.item.OxySuitItem;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class OxygenHudTOFProcedure {

	public static boolean executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OxygenHudTOF!");
			return false;
		}
		Entity entity = (Entity) dependencies.get("entity");
		boolean TOF = false;
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.HEAD) : ItemStack.EMPTY)
				.getItem() == OxySuitItem.helmet) {
			TOF = (true);
		} else {
			TOF = (false);
		}
		return TOF;
	}
}
