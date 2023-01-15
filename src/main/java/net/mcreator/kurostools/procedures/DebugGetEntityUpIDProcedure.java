package net.mcreator.kurostools.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class DebugGetEntityUpIDProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure DebugGetEntityUpID!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure DebugGetEntityUpID!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		if (entity instanceof PlayerEntity) {
			if (sourceentity instanceof PlayerEntity && !sourceentity.world.isRemote()) {
				((PlayerEntity) sourceentity)
						.sendStatusMessage(new StringTextComponent(("Hello, User! The Targeted Player's Custom Unique Player ID Var Is "
								+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).UPID
								+ ", Their UPiD NBT Tag Is " + entity.getPersistentData().getString("UPID"))), (false));
			}
		} else {
			if (sourceentity instanceof PlayerEntity && !sourceentity.world.isRemote()) {
				((PlayerEntity) sourceentity)
						.sendStatusMessage(new StringTextComponent(("Error Detected:Entity Not Player." + "Cannot Obtain UP-ID")), (false));
			}
		}
	}
}
