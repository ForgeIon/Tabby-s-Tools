package net.mcreator.kurostools.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.World;
import net.minecraft.util.math.MathHelper;
import net.minecraft.entity.Entity;

import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class UpIdGenProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
			if (event.phase == TickEvent.Phase.END) {
				Entity entity = event.player;
				World world = entity.world;
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure UpIdGen!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		double UP_ID_Gen1 = 0;
		double UP_ID_Gen2 = 0;
		double UP_ID_Gen3 = 0;
		double UP_ID_Gen4 = 0;
		if ((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new KurosToolsModVariables.PlayerVariables())).UPID == 0) {
			UP_ID_Gen1 = (MathHelper.nextDouble(new Random(), 1, 1000));
			UP_ID_Gen2 = Math.round(MathHelper.nextDouble(new Random(), 1, 1000));
			UP_ID_Gen3 = (MathHelper.nextDouble(new Random(), 1250, 200000));
			UP_ID_Gen4 = (UP_ID_Gen1 + UP_ID_Gen2 + UP_ID_Gen3);
			{
				double _setval = UP_ID_Gen4;
				entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.UPID = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
		}
		if ((entity.getPersistentData().getString("UPID")).isEmpty()) {
			entity.getPersistentData().putString("UPID", ("" + (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
					.orElse(new KurosToolsModVariables.PlayerVariables())).UPID));
		}
	}
}
