package net.mcreator.kurostools.procedures;

import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class TeleporterValueProcedure {

	public static double executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure TeleporterValue!");
			return 0;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure TeleporterValue!");
			return 0;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure TeleporterValue!");
			return 0;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure TeleporterValue!");
			return 0;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		double N = 0;
		if ((new Object() {
			public boolean getValue(IWorld world, BlockPos pos, String tag) {
				TileEntity tileEntity = world.getTileEntity(pos);
				if (tileEntity != null)
					return tileEntity.getTileData().getBoolean(tag);
				return false;
			}
		}.getValue(world, new BlockPos(x, y, z), "TPActive")) == true) {
			N = 15;
		} else {
			N = 0;
		}
		return N;
	}
}
