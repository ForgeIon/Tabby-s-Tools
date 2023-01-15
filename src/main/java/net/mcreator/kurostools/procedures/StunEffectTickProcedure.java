package net.mcreator.kurostools.procedures;

import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class StunEffectTickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure StunEffectTick!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		entity.setMotion(0, (-1), 0);
	}
}
