
package net.mcreator.kurostools.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.world.World;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.item.UseAction;
import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.Food;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.BlockState;

import net.mcreator.kurostools.procedures.PortableOverworldPortalUsageProcedure;
import net.mcreator.kurostools.KurosToolsModElements;

import java.util.stream.Stream;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.AbstractMap;

@KurosToolsModElements.ModElement.Tag
public class PortableOverworldPortalItem extends KurosToolsModElements.ModElement {
	@ObjectHolder("kuros_tools:portable_overworld_portal")
	public static final Item block = null;

	public PortableOverworldPortalItem(KurosToolsModElements instance) {
		super(instance, 139);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ItemGroup.TOOLS).maxStackSize(1).isImmuneToFire().rarity(Rarity.EPIC)
					.food((new Food.Builder()).hunger(0).saturation(0f)

							.build()));
			setRegistryName("portable_overworld_portal");
		}

		@Override
		public UseAction getUseAction(ItemStack itemstack) {
			return UseAction.NONE;
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 60;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}

		@Override
		public void addInformation(ItemStack itemstack, World world, List<ITextComponent> list, ITooltipFlag flag) {
			super.addInformation(itemstack, world, list, flag);
			list.add(new StringTextComponent("Teleports you to the overworld."));
		}

		@Override
		public void onPlayerStoppedUsing(ItemStack itemstack, World world, LivingEntity entity, int time) {
			double x = entity.getPosX();
			double y = entity.getPosY();
			double z = entity.getPosZ();

			PortableOverworldPortalUsageProcedure.executeProcedure(Stream.of(new AbstractMap.SimpleEntry<>("entity", entity)).collect(HashMap::new,
					(_m, _e) -> _m.put(_e.getKey(), _e.getValue()), Map::putAll));
		}
	}
}
