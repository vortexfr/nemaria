package fr.nemaria.mods.future.items;

import fr.nemaria.mods.future.lib.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.ResourceLocation;

public class ItemEtFuturumRecord extends ItemRecord {

	public ItemEtFuturumRecord(String recordName) {
		super(recordName);
		setTextureName("music_disc_" + recordName);
		setUnlocalizedName("record");
		setCreativeTab(CreativeTabs.tabMisc);
	}

	@Override
	public ResourceLocation getRecordResource(String name) {
		return new ResourceLocation(Reference.MCAssetVer + ":music_disc." + recordName);
	}

}
