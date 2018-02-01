package essentialcraft.integration.crafttweaker;

import java.util.ArrayList;
import java.util.List;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import essentialcraft.api.DemonTrade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.essentialcraft.DemonTrade")
public class DemonTrading {

	@ZenMethod
	public static void addRecipe(IItemStack input) {
		if(input == null) {
			CraftTweakerAPI.logError("Cannot turn "+input+" into a Demon Trade");
			return;
		}

		CraftTweakerAPI.apply(new AddRecipeAction(CraftTweakerMC.getItemStack(input)));
	}

	@ZenMethod
	public static void addRecipe(String input) {
		if(input == null) {
			CraftTweakerAPI.logError("Cannot turn "+input+" into a Demon Trade");
			return;
		}

		boolean flag = true;
		for(EntityEntry e : DemonTrade.allMobs) {
			if(ForgeRegistries.ENTITIES.getValue(new ResourceLocation(input)).equals(e)) {
				flag = false;
				break;
			}
		}
		if(flag)
			CraftTweakerAPI.apply(new AddEntityRecipeAction(input));
		else
			CraftTweakerAPI.logWarning("Recipe already exists!");
	}

	@ZenMethod
	public static void removeRecipe(IItemStack input) {
		if(input == null) {
			CraftTweakerAPI.logError("Input cannot be null");
			return;
		}
		ArrayList<DemonTrade> toRemove = new ArrayList<DemonTrade>();
		DemonTrade.trades.stream().
		filter(entry->input.matches(CraftTweakerMC.getIItemStack(entry.desiredItem))).
		forEach(entry->toRemove.add(entry));

		if(toRemove.isEmpty())
			CraftTweakerAPI.logWarning("No recipe for "+input.toString());
		else
			CraftTweakerAPI.apply(new RemoveRecipeAction(toRemove));
	}

	@ZenMethod
	public static void removeRecipe(String input) {
		if(input == null) {
			CraftTweakerAPI.logError("Input cannot be null");
			return;
		}

		DemonTrade toRemove = null;
		toRemove = DemonTrade.trades.stream().
				filter(entry->entry.entityType != null && new ResourceLocation(input).equals(entry.entityType.getRegistryName())).findFirst().get();

		if(toRemove == null)
			CraftTweakerAPI.logWarning("No recipe for "+input);
		else
			CraftTweakerAPI.apply(new RemoveEntityRecipeAction(new ResourceLocation(input)));
	}

	private static class AddRecipeAction implements IAction {
		DemonTrade rec;
		ItemStack input;

		public AddRecipeAction(ItemStack input) {
			this.input = input;
		}

		@Override
		public void apply() {
			rec = new DemonTrade(input);
		}

		@Override
		public String describe() {
			return "Adding Demon Trade for "+input.getDisplayName();
		}
	}

	private static class AddEntityRecipeAction implements IAction {
		DemonTrade rec;
		String input;

		public AddEntityRecipeAction(String input) {
			this.input = input;
		}

		@Override
		public void apply() {
			rec = new DemonTrade(new ResourceLocation(input));
		}

		@Override
		public String describe() {
			return "Adding Demon Trade for "+input;
		}
	}

	private static class RemoveRecipeAction implements IAction {
		List<DemonTrade> rec;

		public RemoveRecipeAction(List<DemonTrade> rec) {
			this.rec = rec;
		}

		@Override
		public void apply() {
			for(DemonTrade entry : rec) {
				DemonTrade.removeTrade(entry);
			}
		}

		@Override
		public String describe() {
			return "Removing "+rec.size()+" Demon Trade(s)";
		}
	}

	private static class RemoveEntityRecipeAction implements IAction {
		ResourceLocation input;

		public RemoveEntityRecipeAction(ResourceLocation input) {
			this.input = input;
		}

		@Override
		public void apply() {
			DemonTrade.removeTrade(input);
		}

		@Override
		public String describe() {
			return "Removing Demon Trade for "+input;
		}
	}
}
