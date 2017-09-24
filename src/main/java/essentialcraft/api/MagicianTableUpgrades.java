package essentialcraft.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author Modbder
 * @Description Use this to add new Upgrades to Magician Table. You should do this after initializing your Items.
 */
public class MagicianTableUpgrades {

	public static final List<ItemStack> UPGRADE_STACKS = new ArrayList<ItemStack>();

	public static final List<Float> UPGRADE_EFFICIENCIES = new ArrayList<Float>();

	public static final List<ResourceLocation> UPGRADE_TEXTURES = new ArrayList<ResourceLocation>();

	public static void addUpgrade(ItemStack is, float f,ResourceLocation rl)
	{
		UPGRADE_STACKS.add(is.copy());
		UPGRADE_EFFICIENCIES.add(f);
		UPGRADE_TEXTURES.add(rl);
	}

	public static boolean isItemUpgrade(ItemStack is)
	{
		for(ItemStack s : UPGRADE_STACKS)
			if(s.isItemEqual(is))
				return true;

		return false;
	}

	public static ItemStack createStackByUpgradeID(int uid)
	{
		if(UPGRADE_STACKS.size() > uid)
			return UPGRADE_STACKS.get(uid);
		return ItemStack.EMPTY;
	}

	public static int getUpgradeIDByItemStack(ItemStack is)
	{
		for(int i = 0; i < UPGRADE_STACKS.size(); ++i)
		{
			if(is.isItemEqual(UPGRADE_STACKS.get(i)))
				return i;
		}

		return -1;
	}
}
