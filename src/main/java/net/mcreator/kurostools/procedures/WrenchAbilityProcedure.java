package net.mcreator.kurostools.procedures;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.Direction;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.state.Property;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.DirectionProperty;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.Entity;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;

import net.mcreator.kurostools.block.TeleporterBlock;
import net.mcreator.kurostools.block.SeparatorBlock;
import net.mcreator.kurostools.block.MachineBlockBlock;
import net.mcreator.kurostools.block.ConveyorBeltStairUpBlock;
import net.mcreator.kurostools.block.ConveyorBeltStairBlock;
import net.mcreator.kurostools.block.ConveyorBeltBlock;
import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class WrenchAbilityProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure WrenchAbility!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure WrenchAbility!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure WrenchAbility!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure WrenchAbility!");
			return;
		}
		if (dependencies.get("direction") == null) {
			if (!dependencies.containsKey("direction"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency direction for procedure WrenchAbility!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure WrenchAbility!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure WrenchAbility!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Direction direction = (Direction) dependencies.get("direction");
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 2) {
			if ((new Object() {
				public String getValue(IWorld world, BlockPos pos, String tag) {
					TileEntity tileEntity = world.getTileEntity(pos);
					if (tileEntity != null)
						return tileEntity.getTileData().getString(tag);
					return "";
				}
			}.getValue(world, new BlockPos(x, y, z), "OwnerID")).isEmpty()) {
				if ((entity.getPersistentData().getString("UPID")).isEmpty()) {
					if (!world.isRemote()) {
						BlockPos _bp = new BlockPos(x, y, z);
						TileEntity _tileEntity = world.getTileEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_tileEntity != null)
							_tileEntity.getTileData().putString("OwnerID", (entity.getPersistentData().getString("UPID")));
						if (world instanceof World)
							((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
					}
				} else {
					if (!world.isRemote()) {
						BlockPos _bp = new BlockPos(x, y, z);
						TileEntity _tileEntity = world.getTileEntity(_bp);
						BlockState _bs = world.getBlockState(_bp);
						if (_tileEntity != null)
							_tileEntity.getTileData().putString("OwnerID",
									("" + (entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null)
											.orElse(new KurosToolsModVariables.PlayerVariables())).UPID));
						if (world instanceof World)
							((World) world).notifyBlockUpdate(_bp, _bs, _bs, 3);
					}
				}
			}
		} else if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 3) {
			if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == ConveyorBeltStairUpBlock.block
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == ConveyorBeltStairBlock.block
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == MachineBlockBlock.block
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == ConveyorBeltBlock.block
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == TeleporterBlock.block
					|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == SeparatorBlock.block) {
				if (world instanceof World) {
					Block.spawnDrops(world.getBlockState(new BlockPos(x, y, z)), (World) world, new BlockPos(x, y, z));
					world.destroyBlock(new BlockPos(x, y, z), false);
				}
			}
		} else if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 1) {
			try {
				BlockState _bs = world.getBlockState(new BlockPos(x, y, z));
				DirectionProperty _property = (DirectionProperty) _bs.getBlock().getStateContainer().getProperty("facing");
				if (_property != null) {
					world.setBlockState(new BlockPos(x, y, z), _bs.with(_property, (direction.getOpposite())), 3);
				} else {
					world.setBlockState(new BlockPos(x, y, z),
							_bs.with((EnumProperty<Direction.Axis>) _bs.getBlock().getStateContainer().getProperty("axis"),
									(direction.getOpposite()).getAxis()),
							3);
				}
			} catch (Exception e) {
			}
		} else if (itemstack.getOrCreateTag().getDouble("SelectedAbility") == 4) {
			if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == ConveyorBeltStairUpBlock.block) {
				{
					BlockPos _bp = new BlockPos(x, y, z);
					BlockState _bs = ConveyorBeltStairBlock.block.getDefaultState();
					BlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
						Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
						if (_property != null && _bs.get(_property) != null)
							try {
								_bs = _bs.with(_property, (Comparable) entry.getValue());
							} catch (Exception e) {
							}
					}
					TileEntity _te = world.getTileEntity(_bp);
					CompoundNBT _bnbt = null;
					if (_te != null) {
						_bnbt = _te.write(new CompoundNBT());
						_te.remove();
					}
					world.setBlockState(_bp, _bs, 3);
					if (_bnbt != null) {
						_te = world.getTileEntity(_bp);
						if (_te != null) {
							try {
								_te.read(_bso, _bnbt);
							} catch (Exception ignored) {
							}
						}
					}
				}
			} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == ConveyorBeltStairBlock.block) {
				{
					BlockPos _bp = new BlockPos(x, y, z);
					BlockState _bs = ConveyorBeltStairUpBlock.block.getDefaultState();
					BlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
						Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
						if (_property != null && _bs.get(_property) != null)
							try {
								_bs = _bs.with(_property, (Comparable) entry.getValue());
							} catch (Exception e) {
							}
					}
					TileEntity _te = world.getTileEntity(_bp);
					CompoundNBT _bnbt = null;
					if (_te != null) {
						_bnbt = _te.write(new CompoundNBT());
						_te.remove();
					}
					world.setBlockState(_bp, _bs, 3);
					if (_bnbt != null) {
						_te = world.getTileEntity(_bp);
						if (_te != null) {
							try {
								_te.read(_bso, _bnbt);
							} catch (Exception ignored) {
							}
						}
					}
				}
			} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == TeleporterBlock.block) {
				{
					BlockPos _bp = new BlockPos(x, y, z);
					BlockState _bs = TeleporterBlock.block.getDefaultState();
					BlockState _bso = world.getBlockState(_bp);
					for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
						Property _property = _bs.getBlock().getStateContainer().getProperty(entry.getKey().getName());
						if (_property != null && _bs.get(_property) != null)
							try {
								_bs = _bs.with(_property, (Comparable) entry.getValue());
							} catch (Exception e) {
							}
					}
					TileEntity _te = world.getTileEntity(_bp);
					CompoundNBT _bnbt = null;
					if (_te != null) {
						_bnbt = _te.write(new CompoundNBT());
						_te.remove();
					}
					world.setBlockState(_bp, _bs, 3);
					if (_bnbt != null) {
						_te = world.getTileEntity(_bp);
						if (_te != null) {
							try {
								_te.read(_bso, _bnbt);
							} catch (Exception ignored) {
							}
						}
					}
				}
			}
		}
	}
}
