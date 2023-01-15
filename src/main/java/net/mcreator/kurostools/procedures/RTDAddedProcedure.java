package net.mcreator.kurostools.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Blocks;

import net.mcreator.kurostools.block.TeamCollisionBlockType1Block;
import net.mcreator.kurostools.block.RedTeamDoorBlock;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class RTDAddedProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure RTDAdded!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure RTDAdded!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure RTDAdded!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure RTDAdded!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(new BlockPos(x, y + 1, z), Blocks.AIR.getDefaultState(), 3);
		world.setBlockState(new BlockPos(x, y + 1, z), RedTeamDoorBlock.block.getDefaultState(), 3);
		world.setBlockState(new BlockPos(x, y, z), TeamCollisionBlockType1Block.block.getDefaultState(), 3);
	}
}
