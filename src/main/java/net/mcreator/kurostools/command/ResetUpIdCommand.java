
package net.mcreator.kurostools.command;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegisterCommandsEvent;

import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.command.Commands;
import net.minecraft.command.CommandSource;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

@Mod.EventBusSubscriber
public class ResetUpIdCommand {
	@SubscribeEvent
	public static void registerCommands(RegisterCommandsEvent event) {
		event.getDispatcher().register(LiteralArgumentBuilder.<CommandSource>literal("reset_upid").requires(s -> s.hasPermissionLevel(2))
				.then(Commands.argument("ResetEntityTarget", EntityArgument.players())));
	}
}
