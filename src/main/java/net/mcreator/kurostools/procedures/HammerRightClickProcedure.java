package net.mcreator.kurostools.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;
import net.minecraft.block.BlockState;

import net.mcreator.kurostools.item.CrushedIronOreItem;
import net.mcreator.kurostools.item.CrushedGoldOreItem;
import net.mcreator.kurostools.item.CrushedEmeraldOreItem;
import net.mcreator.kurostools.item.CrushedDiamondOreItem;
import net.mcreator.kurostools.item.CoalFragmentsItem;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Random;
import java.util.Map;

public class HammerRightClickProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure HammerRightClick!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure HammerRightClick!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure HammerRightClick!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure HammerRightClick!");
			return;
		}
		if (dependencies.get("blockstate") == null) {
			if (!dependencies.containsKey("blockstate"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency blockstate for procedure HammerRightClick!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure HammerRightClick!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure HammerRightClick!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		BlockState blockstate = (BlockState) dependencies.get("blockstate");
		Entity entity = (Entity) dependencies.get("entity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		ItemStack tool = ItemStack.EMPTY;
		if (blockstate.getMaterial() == net.minecraft.block.material.Material.ROCK
				&& !((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.DIAMOND_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GOLD_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.LAPIS_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.COAL_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.EMERALD_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.NETHER_QUARTZ_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.NETHER_GOLD_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.REDSTONE_ORE
						|| (world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.REDSTONE_ORE)) {
			{
				BlockPos _bp = new BlockPos(x, y, z);
				BlockState _bs = Blocks.GRAVEL.getDefaultState();
				world.setBlockState(_bp, _bs, 3);
			}
			new Object() {
				private int ticks = 0;
				private float waitTicks;
				private IWorld world;

				public void start(IWorld world, int waitTicks) {
					this.waitTicks = waitTicks;
					MinecraftForge.EVENT_BUS.register(this);
					this.world = world;
				}

				@SubscribeEvent
				public void tick(TickEvent.ServerTickEvent event) {
					if (event.phase == TickEvent.Phase.END) {
						this.ticks += 1;
						if (this.ticks >= this.waitTicks)
							run();
					}
				}

				private void run() {
					if (world instanceof World)
						((World) world).notifyNeighborsOfStateChange(new BlockPos(x, y, z),
								((World) world).getBlockState(new BlockPos(x, y, z)).getBlock());
					MinecraftForge.EVENT_BUS.unregister(this);
				}
			}.start(world, (int) 5);
			{
				ItemStack _ist = itemstack;
				if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
					_ist.shrink(1);
					_ist.setDamage(0);
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1, false);
			}
		} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.GOLD_ORE) {
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(CrushedGoldOreItem.block);
				_setstack.setCount((int) 4);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
			{
				ItemStack _ist = itemstack;
				if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
					_ist.shrink(1);
					_ist.setDamage(0);
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1, false);
			}
		} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.IRON_ORE) {
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(CrushedIronOreItem.block);
				_setstack.setCount((int) 4);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
			{
				ItemStack _ist = itemstack;
				if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
					_ist.shrink(1);
					_ist.setDamage(0);
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1, false);
			}
		} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.DIAMOND_ORE) {
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(CrushedDiamondOreItem.block);
				_setstack.setCount((int) 4);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
			{
				ItemStack _ist = itemstack;
				if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
					_ist.shrink(1);
					_ist.setDamage(0);
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1, false);
			}
		} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.EMERALD_ORE) {
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(CrushedEmeraldOreItem.block);
				_setstack.setCount((int) 4);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
			{
				ItemStack _ist = itemstack;
				if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
					_ist.shrink(1);
					_ist.setDamage(0);
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1, false);
			}
		} else if ((world.getBlockState(new BlockPos(x, y, z))).getBlock() == Blocks.COAL_ORE) {
			world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
			if (entity instanceof PlayerEntity) {
				ItemStack _setstack = new ItemStack(CoalFragmentsItem.block);
				_setstack.setCount((int) 4);
				ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) entity), _setstack);
			}
			{
				ItemStack _ist = itemstack;
				if (_ist.attemptDamageItem((int) 1, new Random(), null)) {
					_ist.shrink(1);
					_ist.setDamage(0);
				}
			}
			if (world instanceof World && !world.isRemote()) {
				((World) world).playSound(null, new BlockPos(x, y, z),
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1);
			} else {
				((World) world).playSound(x, y, z,
						(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.break")),
						SoundCategory.MASTER, (float) 1, (float) 1, false);
			}
		}
	}
}
