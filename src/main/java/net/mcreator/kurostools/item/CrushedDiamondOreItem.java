
package net.mcreator.kurostools.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.Rarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.kurostools.KurosToolsModElements;

@KurosToolsModElements.ModElement.Tag
public class CrushedDiamondOreItem extends KurosToolsModElements.ModElement {
	@ObjectHolder("kuros_tools:crushed_diamond_ore")
	public static final Item block = null;

	public CrushedDiamondOreItem(KurosToolsModElements instance) {
		super(instance, 28);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}

	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(ItemGroup.MATERIALS).maxStackSize(64).rarity(Rarity.UNCOMMON));
			setRegistryName("crushed_diamond_ore");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}
