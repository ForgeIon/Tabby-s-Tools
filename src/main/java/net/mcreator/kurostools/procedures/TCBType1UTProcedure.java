package net.mcreator.kurostools.procedures;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;

import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import net.mcreator.kurostools.item.RedTeamArmorItem;
import net.mcreator.kurostools.block.TeamCollisionBlockType1Block;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.function.Function;
import java.util.Map;
import java.util.Comparator;

public class TCBType1UTProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure TCBType1UT!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure TCBType1UT!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure TCBType1UT!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure TCBType1UT!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity EntRepl = null;
		if ((((Entity) world
				.getEntitiesWithinAABB(PlayerEntity.class,
						new AxisAlignedBB(x - (2 / 2d), (y + 1) - (2 / 2d), z - (2 / 2d), x + (2 / 2d), (y + 1) + (2 / 2d), z + (2 / 2d)), null)
				.stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
					}
				}.compareDistOf(x, (y + 1), z)).findFirst().orElse(null)) != null) == true) {
			EntRepl = (Entity) world
					.getEntitiesWithinAABB(PlayerEntity.class,
							new AxisAlignedBB(x - (2 / 2d), (y + 1) - (2 / 2d), z - (2 / 2d), x + (2 / 2d), (y + 1) + (2 / 2d), z + (2 / 2d)), null)
					.stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparing((Function<Entity, Double>) (_entcnd -> _entcnd.getDistanceSq(_x, _y, _z)));
						}
					}.compareDistOf(x, (y + 1), z)).findFirst().orElse(null);
			if (!(((EntRepl instanceof LivingEntity) ? ((LivingEntity) EntRepl).getItemStackFromSlot(EquipmentSlotType.FEET) : ItemStack.EMPTY)
					.getItem() == RedTeamArmorItem.boots)) {
				world.setBlockState(new BlockPos(x, y, z), Blocks.AIR.getDefaultState(), 3);
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
						world.setBlockState(new BlockPos(x, y, z), TeamCollisionBlockType1Block.block.getDefaultState(), 3);
						MinecraftForge.EVENT_BUS.unregister(this);
					}
				}.start(world, (int) 50);
				if (EntRepl instanceof PlayerEntity && !EntRepl.world.isRemote()) {
					((PlayerEntity) EntRepl).sendStatusMessage(new StringTextComponent("Removed"), (false));
				}
			}
		}
	}
}
