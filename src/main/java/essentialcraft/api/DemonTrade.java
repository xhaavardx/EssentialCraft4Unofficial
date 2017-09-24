package essentialcraft.api;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class DemonTrade
{
	public static final List<DemonTrade> trades = new ArrayList<DemonTrade>();
	public static final List<EntityEntry> allMobs = new ArrayList<EntityEntry>();
	public ItemStack desiredItem = ItemStack.EMPTY;
	public EntityEntry entityType;

	public DemonTrade(ItemStack is) {
		desiredItem = is;
		trades.add(this);
	}

	public DemonTrade(EntityEntry e) {
		entityType = e;
		allMobs.add(e);
		trades.add(this);
	}

	public DemonTrade(ResourceLocation e) {
		this(ForgeRegistries.ENTITIES.getValue(e));
	}

	public static void removeTrade(DemonTrade tra) {
		if(tra.entityType != null)
			allMobs.remove(tra.entityType);
		trades.remove(tra);
	}

	public static void removeTrade(ItemStack is) {
		for(DemonTrade tra : trades) {
			if(tra.desiredItem.isItemEqual(is)) {
				removeTrade(tra);
				return;
			}
		}
	}

	public static void removeTrade(EntityEntry e) {
		for(DemonTrade tra : trades) {
			if(tra.entityType != null && tra.entityType.equals(e)) {
				removeTrade(tra);
				return;
			}
		}
	}

	public static void removeTrade(ResourceLocation e) {
		removeTrade(ForgeRegistries.ENTITIES.getValue(e));
	}
}
