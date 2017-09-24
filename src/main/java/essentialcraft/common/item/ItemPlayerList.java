package essentialcraft.common.item;

import java.util.List;

import DummyCore.Client.IModelRegisterer;
import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.MiscUtils;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class ItemPlayerList extends Item implements IModelRegisterer {

	public ItemPlayerList() {
		super();
		this.maxStackSize = 1;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World par2World, EntityPlayer par3EntityPlayer, EnumHand hand)
	{
		NBTTagCompound itemTag = MiscUtils.getStackTag(par3EntityPlayer.getHeldItem(hand));
		if(!itemTag.hasKey("usernames"))
			itemTag.setString("usernames", "||username:null");
		String str = itemTag.getString("usernames");
		DummyData[] dt = DataStorage.parseData(str);
		boolean canAddUsername = true;
		for(int i = 0; i < dt.length; ++i)
		{
			if(dt[i].fieldValue.equals(MiscUtils.getUUIDFromPlayer(par3EntityPlayer).toString()))
				canAddUsername = false;
		}
		if(canAddUsername)
		{
			str+="||username:"+MiscUtils.getUUIDFromPlayer(par3EntityPlayer).toString();
		}
		itemTag.setString("usernames", str);
		return super.onItemRightClick(par2World, par3EntityPlayer, hand);
	}

	@Override
	public void addInformation(ItemStack par1ItemStack, World par2EntityPlayer, List<String> par3List, ITooltipFlag par4)
	{
		if(par1ItemStack.getTagCompound() != null)
		{
			par3List.add("Allowed Players:");
			NBTTagCompound itemTag = MiscUtils.getStackTag(par1ItemStack);
			if(!itemTag.hasKey("usernames"))
				itemTag.setString("usernames", "||username:null");
			String str = itemTag.getString("usernames");
			DummyData[] dt = DataStorage.parseData(str);
			for(int i = 0; i < dt.length; ++i)
			{
				String name = dt[i].fieldValue;
				if(!name.equals("null"))
				{
					par3List.add(" -"+MiscUtils.getUsernameFromUUID(name));
				}
			}
		}
	}

	@Override
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation("minecraft:paper", "inventory"));
	}
}
