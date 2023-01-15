package net.mcreator.kurostools.procedures;

import net.minecraft.entity.Entity;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.CommandSource;

import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class ModifyEntityDataProcedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("arguments") == null) {
			if (!dependencies.containsKey("arguments"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency arguments for procedure ModifyEntityDataProced!");
			return;
		}
		CommandContext<CommandSource> arguments = (CommandContext<CommandSource>) dependencies.get("arguments");
		{
			double _setval = (DoubleArgumentType.getDouble(arguments, "CustomUpID"));
			(new Object() {
				public Entity getEntity() {
					try {
						return EntityArgument.getEntity(arguments, "TargetedPlayer");
					} catch (CommandSyntaxException e) {
						e.printStackTrace();
						return null;
					}
				}
			}.getEntity()).getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.UPID = _setval;
				capability.syncPlayerVariables((new Object() {
					public Entity getEntity() {
						try {
							return EntityArgument.getEntity(arguments, "TargetedPlayer");
						} catch (CommandSyntaxException e) {
							e.printStackTrace();
							return null;
						}
					}
				}.getEntity()));
			});
		}
	}
}
