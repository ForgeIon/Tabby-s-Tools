package net.mcreator.kurostools.procedures;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class CreditsRightclickedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure CreditsRightclicked!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
			((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("Main Dev: " + "Tabbycat/Kuro/Emily" + ", " + "Main Pixel Artists: "
					+ "T/K/E And AshleyPup" + ", " + "Main Sound Design " + "[None Found]")), (false));
		}
	}
}
