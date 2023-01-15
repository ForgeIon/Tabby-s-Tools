package net.mcreator.kurostools.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.living.LivingEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.block.Blocks;

import net.mcreator.kurostools.item.OxySuitItem;
import net.mcreator.kurostools.enchantment.CompressingOxygenEnchantment;
import net.mcreator.kurostools.KurosToolsModVariables;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Random;
import java.util.Map;
import java.util.HashMap;

public class OxySuitEqProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityTick(LivingEvent.LivingUpdateEvent event) {
			Entity entity = event.getEntityLiving();
			World world = entity.world;
			double i = entity.getPosX();
			double j = entity.getPosY();
			double k = entity.getPosZ();
			Map<String, Object> dependencies = new HashMap<>();
			dependencies.put("x", i);
			dependencies.put("y", j);
			dependencies.put("z", k);
			dependencies.put("world", world);
			dependencies.put("entity", entity);
			dependencies.put("event", event);
			executeProcedure(dependencies);
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure OxySuitEq!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure OxySuitEq!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure OxySuitEq!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure OxySuitEq!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure OxySuitEq!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		boolean TOF = false;
		double YCoord = 0;
		double Y_Coord = 0;
		if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.HEAD) : ItemStack.EMPTY)
				.getItem() == OxySuitItem.helmet) {
			if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
					.getItem() == OxySuitItem.body) {
				if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
						.getOrCreateTag().getDouble("OxysuitMax") <= 1) {
					((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
							.getOrCreateTag().putDouble("OxysuitMax", 100);
					if ((world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.WATER) {
						((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
								.getOrCreateTag().putDouble("OxysuitOxy",
										(((entity instanceof LivingEntity)
												? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
												: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitMax")));
					} else {
						((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
								.getOrCreateTag().putDouble("OxysuitOxy", 0);
					}
				} else if ((world.getBlockState(new BlockPos(x, y + 1, z))).getBlock() == Blocks.WATER) {
					if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
							.getOrCreateTag().getDouble("OxysuitOxy") >= 1) {
						entity.setAir((int) 300);
						if (MathHelper.nextDouble(new Random(), 1, 50) <= 5) {
							((entity instanceof LivingEntity)
									? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
									: ItemStack.EMPTY).getOrCreateTag()
									.putDouble("OxysuitOxy",
											(((entity instanceof LivingEntity)
													? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
													: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitOxy") - 1));
						}
						if ((EnchantmentHelper.getEnchantmentLevel(CompressingOxygenEnchantment.enchantment,
								((entity instanceof LivingEntity)
										? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.HEAD)
										: ItemStack.EMPTY)) != 0) == false) {
							for (int index0 = 0; index0 < (int) (25); index0++) {
								if ((world.getBlockState(new BlockPos(x, Y_Coord, z))).getBlock() == Blocks.WATER) {
									Y_Coord = (y + 1);
									continue;
								} else {
									if (Y_Coord >= y + 25) {
										TOF = (true);
										break;
									} else {
										TOF = (false);
										break;
									}
								}
							}
							if (TOF) {
								if (MathHelper.nextDouble(new Random(), 1, 50) <= 6) {
									((entity instanceof LivingEntity)
											? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
											: ItemStack.EMPTY).getOrCreateTag()
											.putDouble("OxysuitOxy",
													(((entity instanceof LivingEntity)
															? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
															: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitOxy") - 3));
								}
							}
						}
					}
				} else if (((entity instanceof LivingEntity)
						? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
						: ItemStack.EMPTY)
						.getOrCreateTag()
						.getDouble("OxysuitOxy") < ((entity instanceof LivingEntity)
								? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
								: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitMax")) {
					((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
							.getOrCreateTag().putDouble("OxysuitOxy",
									(((entity instanceof LivingEntity)
											? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
											: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitOxy") + 0.5));
					if (((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
							.getOrCreateTag()
							.getDouble("OxysuitOxy") > ((entity instanceof LivingEntity)
									? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
									: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitMax")) {
						((entity instanceof LivingEntity) ? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST) : ItemStack.EMPTY)
								.getOrCreateTag().putDouble("OxysuitOxy",
										(((entity instanceof LivingEntity)
												? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
												: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitMax")));
					}
				}
				{
					double _setval = (((entity instanceof LivingEntity)
							? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
							: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitOxy"));
					entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.Oxygen = _setval;
						capability.syncPlayerVariables(entity);
					});
				}
			}
			{
				boolean _setval = TOF;
				entity.getCapability(KurosToolsModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
					capability.Pressure = _setval;
					capability.syncPlayerVariables(entity);
				});
			}
			if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
				((PlayerEntity) entity).sendStatusMessage(new StringTextComponent(("OxySuit: Oxygen " + (((entity instanceof LivingEntity)
						? ((LivingEntity) entity).getItemStackFromSlot(EquipmentSlotType.CHEST)
						: ItemStack.EMPTY).getOrCreateTag().getDouble("OxysuitOxy")) + "%")), (true));
			}
		}
	}
}
