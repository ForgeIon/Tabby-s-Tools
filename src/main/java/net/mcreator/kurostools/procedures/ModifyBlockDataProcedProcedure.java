package net.mcreator.kurostools.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.command.arguments.BlockPosArgument;
import net.minecraft.command.CommandSource;
import net.minecraft.block.BlockState;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class ModifyBlockDataProcedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure ModifyBlockDataProced!");
			return;
		}
		if (dependencies.get("arguments") == null) {
			if (!dependencies.containsKey("arguments"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency arguments for procedure ModifyBlockDataProced!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		CommandContext<CommandSource> arguments = (CommandContext<CommandSource>) dependencies.get("arguments");
		if (!world.isRemote()) {
			BlockPos _bp = new BlockPos(new Object() {
				public double getX() {
					try {
						return BlockPosArgument.getBlockPos(arguments, "TargetedBlock").getX();
					} catch (CommandSyntaxException e) {
						e.printStackTrace();
						return 0;
					}
				}
			}.getX(), new Object() {
				public double getY() {
					try {
						return BlockPosArgument.getBlockPos(arguments, "TargetedBlock").getY();
					} catch (CommandSyntaxException e) {
						e.printStackTrace();
						return 0;
					}
				}
			}.getY(), new Object() {
				public double getZ() {
					try {
						return BlockPosArgument.getBlockPos(arguments, "TargetedBlock").getZ();
					} catch (CommandSyntaxException e) {
						e.printStackTrace();
						return 0;
					}
				}
			}.getZ());
			TileEntity _tileEntity = world.getTileEntity(_bp);
			BlockState _bs = world.getBlockState(_bp);
			if (_tileEntity != null)
				_tileEntity.getTileData().putString("OwnerID", ("" + DoubleArgumentType.getDouble(arguments, "CustomUpID")));
			if (world instanceof World)
				((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
		}
	}
}
