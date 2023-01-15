package net.mcreator.kurostools.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;

import net.mcreator.kurostools.block.TeleporterBlock;
import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class TPR_BlockClickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure TPR_BlockClick!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure TPR_BlockClick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure TPR_BlockClick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure TPR_BlockClick!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure TPR_BlockClick!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure TPR_BlockClick!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		boolean CoordsSelected = false;
		if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == TeleporterBlock.block) {
			if (entity.isSneaking() == true) {
				itemstack.getOrCreateTag().putBoolean("SelectedBlock", (false));
				CoordsSelected = (false);
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Selection Cancelled."), (true));
				}
			} else {
				if (itemstack.getOrCreateTag().getBoolean("SelectedBlock") == true) {
					{
						double _setval = x;
						entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Coord4 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = y;
						entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Coord5 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = z;
						entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Coord6 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					itemstack.getOrCreateTag().putBoolean("SelectedBlock", (false));
					if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
						((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("Second Block Coords, "
								+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord4
								+ ", "
								+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord5
								+ ", " + (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord6
								+ ".")), (true));
					}
					CoordsSelected = (true);
				} else {
					itemstack.getOrCreateTag().putBoolean("SelectedBlock", (true));
					{
						double _setval = x;
						entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Coord1 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = y;
						entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Coord2 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					{
						double _setval = z;
						entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.Coord3 = _setval;
							capability.syncPlayerVariables(entity);
						});
					}
					if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
						((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("First Block Coords, "
								+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1
								+ ", "
								+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2
								+ ", " + (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3
								+ ".")), (true));
					}
				}
			}
		}
		if (CoordsSelected == true) {
			if ((world.getBlockState(new BlockPos(
					(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1,
					(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2,
					(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3)))
					.getBlock() == TeleporterBlock.block) {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("First Block Coords, "
							+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1
							+ ", "
							+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2
							+ ", "
							+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3
							+ ". Second Block Coords,"
							+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3
							+ ", "
							+ (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3
							+ ", " + (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3)),
							(true));
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("TPActive", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("XTarget",
								((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord4));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("YTarget",
								((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord5));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("ZTarget",
								((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord6));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord4,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord5,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord6);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putBoolean("TPActive", (true));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord4,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord5,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord6);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("XTarget",
								((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord1));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord4,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord5,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord6);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("YTarget",
								((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord2));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
				if (!world.isRemote()) {
					BlockPos _bp = new BlockPos(
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord4,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord5,
							(entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
									.orElse(new KurosToolsModVariables.PlayerVariables())).Coord6);
					TileEntity _tileEntity = world.getTileEntity(_bp);
					BlockState _bs = world.getBlockState(_bp);
					if (_tileEntity != null)
						_tileEntity.getTileData().putDouble("ZTarget",
								((entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
										.orElse(new KurosToolsModVariables.PlayerVariables())).Coord3));
					if (world instanceof World)
						((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
				}
			} else {
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Apologies, An Error Occurred."), (true));
				}
			}
			CoordsSelected = (false);
			itemstack.getOrCreateTag().putBoolean("SelectedBlock", (false));
		}
	}
}
