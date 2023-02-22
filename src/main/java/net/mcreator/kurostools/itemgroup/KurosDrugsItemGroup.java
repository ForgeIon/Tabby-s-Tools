
package net.mcreator.kurostools.itemgroup;

import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup;

import net.mcreator.kurostools.item.EcstasyItem;
import net.mcreator.kurostools.KurosToolsModElements;

@KurosToolsModElements.ModElement.Tag
public class KurosDrugsItemGroup extends KurosToolsModElements.ModElement {
	public KurosDrugsItemGroup(KurosToolsModElements instance) {
		super(instance, 162);
	}

	@Override
	public void initElements() {
		tab = new ItemGroup("tabkuros_drugs") {
			@OnlyIn(Dist.CLIENT)
			@Override
			public ItemStack createIcon() {
				return new ItemStack(EcstasyItem.block);
			}

			@OnlyIn(Dist.CLIENT)
			public boolean hasSearchBar() {
				return false;
			}
		};
	}

	public static ItemGroup tab;
}
