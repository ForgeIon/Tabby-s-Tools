package net.mcreator.kurostools.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.state.Property;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;

import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class ConveyorBeltEntityUpProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure ConveyorBeltEntityUp!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure ConveyorBeltEntityUp!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure ConveyorBeltEntityUp!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure ConveyorBeltEntityUp!");
			return;
		}
		if (dependencies.get("blockstate") == null) {
			if (!dependencies.containsKey("blockstate"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency blockstate for procedure ConveyorBeltEntityUp!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure ConveyorBeltEntityUp!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		BlockState blockstate = (BlockState) dependencies.get("blockstate");
		Entity entity = (Entity) dependencies.get("entity");
		Direction Direct = Direction.NORTH;
		Direct = (new Object() {
			public Direction getDirection(BlockState _bs) {
				Property<?> _prop = _bs.getBlock().getStateContainer().getProperty("facing");
				if (_prop instanceof DirectionProperty)
					return _bs.get((DirectionProperty) _prop);
				_prop = _bs.getBlock().getStateContainer().getProperty("axis");
				return _prop instanceof EnumProperty && _prop.getAllowedValues().toArray()[0] instanceof Direction.Axis
						? Direction.getFacingFromAxisDirection(_bs.get((EnumProperty<Direction.Axis>) _prop), Direction.AxisDirection.POSITIVE)
						: Direction.NORTH;
			}
		}.getDirection(blockstate));
		if (((world instanceof World) ? ((World) world).isBlockPowered(new BlockPos(x, y, z)) : false) == false) {
			if (Direction.NORTH == Direct) {
				entity.setMotion((entity.getMotion().getX()), (entity.getMotion().getY()), (entity.getMotion().getZ() - 0.28));
			} else if (Direction.SOUTH == Direct) {
				entity.setMotion((entity.getMotion().getX()), (entity.getMotion().getY()), (entity.getMotion().getZ() + 0.28));
			} else if (Direction.WEST == Direct) {
				entity.setMotion((entity.getMotion().getX() - 0.28), (entity.getMotion().getY()), (entity.getMotion().getZ()));
			} else if (Direction.EAST == Direct) {
				entity.setMotion((entity.getMotion().getX() + 0.28), (entity.getMotion().getY()), (entity.getMotion().getZ()));
			}
		}
	}
}
