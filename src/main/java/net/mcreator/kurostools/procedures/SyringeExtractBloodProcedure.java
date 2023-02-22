package net.mcreator.kurostools.procedures;

import net.minecraftforge.items.ItemHandlerHelper;

import net.minecraft.util.text.StringTextComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.passive.horse.SkeletonHorseEntity;
import net.minecraft.entity.monster.WitherSkeletonEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.CreatureAttribute;

import net.mcreator.kurostools.item.VillagerBloodItem;
import net.mcreator.kurostools.item.VialItem;
import net.mcreator.kurostools.item.AnimalBloodItem;
import net.mcreator.kurostools.KurosToolsMod;

import java.util.Map;

public class SyringeExtractBloodProcedure {

	public static void executeProcedure(Map<String, Object> dependencies) {
		if (dependencies.get("entity") == null) {
			if (!dependencies.containsKey("entity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency entity for procedure SyringeExtractBlood!");
			return;
		}
		if (dependencies.get("sourceentity") == null) {
			if (!dependencies.containsKey("sourceentity"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency sourceentity for procedure SyringeExtractBlood!");
			return;
		}
		if (dependencies.get("itemstack") == null) {
			if (!dependencies.containsKey("itemstack"))
				KurosToolsMod.LOGGER.warn("Failed to load dependency itemstack for procedure SyringeExtractBlood!");
			return;
		}
		Entity entity = (Entity) dependencies.get("entity");
		Entity sourceentity = (Entity) dependencies.get("sourceentity");
		ItemStack itemstack = (ItemStack) dependencies.get("itemstack");
		boolean Hasblood = false;
		if ((entity instanceof SkeletonEntity || entity instanceof SkeletonHorseEntity || entity instanceof WitherSkeletonEntity
				|| entity instanceof WitherEntity) == false) {
			Hasblood = (true);
		}
		if (Hasblood == true) {
			if ((sourceentity instanceof PlayerEntity)
					? ((PlayerEntity) sourceentity).inventory.hasItemStack(new ItemStack(VialItem.block))
					: false) {
				if (sourceentity instanceof PlayerEntity) {
					ItemStack _stktoremove = new ItemStack(VialItem.block);
					((PlayerEntity) sourceentity).inventory.func_234564_a_(p -> _stktoremove.getItem() == p.getItem(), (int) 1,
							((PlayerEntity) sourceentity).container.func_234641_j_());
				}
				if ((entity instanceof LivingEntity ? (((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.WATER) : false) == true) {
					if (sourceentity instanceof PlayerEntity && !sourceentity.world.isRemote()) {
						((PlayerEntity) sourceentity).sendStatusMessage(
								new StringTextComponent("\u00A74Warning, Modification Currently does not have this entity configured."), (false));
					}
				} else if ((entity instanceof LivingEntity
						? (((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.ILLAGER)
						: false) == true) {
					if (sourceentity instanceof PlayerEntity) {
						ItemStack _setstack = new ItemStack(VillagerBloodItem.block);
						_setstack.setCount((int) 1);
						ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
					}
				} else if (entity instanceof VillagerEntity == true) {
					if (sourceentity instanceof PlayerEntity) {
						ItemStack _setstack = new ItemStack(VillagerBloodItem.block);
						_setstack.setCount((int) 1);
						ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
					}
				} else if ((entity instanceof LivingEntity
						? (((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.ARTHROPOD)
						: false) == true) {
					if (sourceentity instanceof PlayerEntity && !sourceentity.world.isRemote()) {
						((PlayerEntity) sourceentity).sendStatusMessage(
								new StringTextComponent("\u00A74Warning, Modification Currently does not have this entity configured."), (false));
					}
				} else if ((entity instanceof LivingEntity
						? (((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.UNDEAD)
						: false) == true) {
					if (sourceentity instanceof PlayerEntity && !sourceentity.world.isRemote()) {
						((PlayerEntity) sourceentity).sendStatusMessage(
								new StringTextComponent("\u00A74Warning, Modification Currently does not have this entity configured."), (false));
					}
				} else if ((entity instanceof LivingEntity
						? (((LivingEntity) entity).getCreatureAttribute() == CreatureAttribute.UNDEFINED)
						: false) == true) {
					if (sourceentity instanceof PlayerEntity) {
						ItemStack _setstack = new ItemStack(AnimalBloodItem.block);
						_setstack.setCount((int) 1);
						ItemHandlerHelper.giveItemToPlayer(((PlayerEntity) sourceentity), _setstack);
					}
				}
				if (sourceentity instanceof PlayerEntity)
					((PlayerEntity) sourceentity).getCooldownTracker().setCooldown(itemstack.getItem(), (int) 10);
			}
		}
	}
}
