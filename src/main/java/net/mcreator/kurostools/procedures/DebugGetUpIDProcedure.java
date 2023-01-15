package net.mcreator.kurostools.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class DebugGetUpIDProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure DebugGetUpID!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (!entity.isSneaking()) {
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("Hello, User! Your Custom Unique Player ID Is "
						+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
								.orElse(new KurosToolsModVariables.PlayerVariables())).UPID)),
						(false));
			}
		}
	}
}
