package net.mcreator.kurostools.procedures;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.World;
import net.minecraft.world.IWorld;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.Effects;
import net.minecraft.potion.EffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.block.Blocks;

import net.mcreator.kurostools.potion.StunEffectPotionEffect;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;
import java.util.HashMap;

public class SoulLockProcedure {
	@Mod.EventBusSubscriber
	private static class GlobalTrigger {
		@SubscribeEvent
		public static void onEntityDeath(LivingDeathEvent event) {
			if (event != null && event.getEntity() != null) {
				Entity entity = event.getEntity();
				Entity sourceentity = event.getSource().getTrueSource();
				double i = entity.getPosX();
				double j = entity.getPosY();
				double k = entity.getPosZ();
				World world = entity.world;
				Map<String, Object> dependencies = new HashMap<>();
				dependencies.put("x", i);
				dependencies.put("y", j);
				dependencies.put("z", k);
				dependencies.put("world", world);
				dependencies.put("entity", entity);
				dependencies.put("sourceentity", sourceentity);
				dependencies.put("event", event);
				executeProcedure(dependencies);
			}
		}
	}

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("world") == null) {
			if (!dependencies.containsKey("world"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency world for procedure SoulLock!");
			return;
		}
		if (dependencies.get("x") == null) {
			if (!dependencies.containsKey("x"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency x for procedure SoulLock!");
			return;
		}
		if (dependencies.get("y") == null) {
			if (!dependencies.containsKey("y"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency y for procedure SoulLock!");
			return;
		}
		if (dependencies.get("z") == null) {
			if (!dependencies.containsKey("z"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency z for procedure SoulLock!");
			return;
		}
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure SoulLock!");
			return;
		}
		IWorld world = (IWorld) dependencies.get("world");
		double x = dependencies.get("x") instanceof Integer ? (int) dependencies.get("x") : (double) dependencies.get("x");
		double y = dependencies.get("y") instanceof Integer ? (int) dependencies.get("y") : (double) dependencies.get("y");
		double z = dependencies.get("z") instanceof Integer ? (int) dependencies.get("z") : (double) dependencies.get("z");
		Entity entity = (Entity) dependencies.get("entity");
		if (entity.getPersistentData().getDouble("ArmorLock") == 4) {
			if (entity.getPersistentData().getDouble("SoulLock") != 1) {
				entity.getPersistentData().putDouble("SoulLock", 1);
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).setHealth((float) 2);
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.RESISTANCE, (int) 300, (int) 255, (true), (true)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.BLINDNESS, (int) 300, (int) 5, (true), (true)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(StunEffectPotionEffect.potion, (int) 300, (int) 1, (true), (true)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.SLOWNESS, (int) 300, (int) 255, (true), (true)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.WEAKNESS, (int) 300, (int) 255, (true), (true)));
				if (entity instanceof LivingEntity)
					((LivingEntity) entity).addPotionEffect(new EffectInstance(Effects.REGENERATION, (int) 300, (int) 255, (true), (true)));
				if (world instanceof World && !world.isRemote()) {
					((World) world).playSound(null, new BlockPos(x, y, z),
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")),
							SoundCategory.PLAYERS, (float) 1, (float) 1);
				} else {
					((World) world).playSound(x, y, z,
							(net.minecraft.util.SoundEvent) ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")),
							SoundCategory.PLAYERS, (float) 1, (float) 1, false);
				}
				if (entity instanceof PlayerEntity && !entity.world.isRemote()) {
					((PlayerEntity) entity).sendStatusMessage(new StringTextComponent("Warning, User has been soul-locked!"), (false));
				}
				if (dependencies.get("event") != null) {
					Object _obj = dependencies.get("event");
					if (_obj instanceof Event) {
						Event _evt = (Event) _obj;
						if (_evt.isCancelable())
							_evt.setCanceled(true);
					}
				}
			} else {
				if (entity instanceof LivingEntity) {
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).inventory.armorInventory.set((int) 0, new ItemStack(Blocks.AIR));
					else
						((LivingEntity) entity).setItemStackToSlot(EquipmentSlotType.FEET, new ItemStack(Blocks.AIR));
					if (entity instanceof ServerPlayerEntity)
						((ServerPlayerEntity) entity).inventory.markDirty();
				}
				if (entity instanceof LivingEntity) {
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).inventory.armorInventory.set((int) 1, new ItemStack(Blocks.AIR));
					else
						((LivingEntity) entity).setItemStackToSlot(EquipmentSlotType.LEGS, new ItemStack(Blocks.AIR));
					if (entity instanceof ServerPlayerEntity)
						((ServerPlayerEntity) entity).inventory.markDirty();
				}
				if (entity instanceof LivingEntity) {
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).inventory.armorInventory.set((int) 2, new ItemStack(Blocks.AIR));
					else
						((LivingEntity) entity).setItemStackToSlot(EquipmentSlotType.CHEST, new ItemStack(Blocks.AIR));
					if (entity instanceof ServerPlayerEntity)
						((ServerPlayerEntity) entity).inventory.markDirty();
				}
				if (entity instanceof LivingEntity) {
					if (entity instanceof PlayerEntity)
						((PlayerEntity) entity).inventory.armorInventory.set((int) 3, new ItemStack(Blocks.AIR));
					else
						((LivingEntity) entity).setItemStackToSlot(EquipmentSlotType.HEAD, new ItemStack(Blocks.AIR));
					if (entity instanceof ServerPlayerEntity)
						((ServerPlayerEntity) entity).inventory.markDirty();
				}
			}
		}
	}
}
