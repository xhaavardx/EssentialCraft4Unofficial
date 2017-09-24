package essentialcraft.api;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ISpell {

	public EnumSpellType getSpellType(ItemStack stk);

	public int getUBMRURequired(ItemStack stk);

	public void onSpellUse(int currentUBMRU, int attunement, EntityPlayer player, ItemStack spell, ItemStack holder);

	public int getRequiredContainerTier(ItemStack stk);

	public int getAttunementRequired(ItemStack stk);

	public boolean requiresSpecificAttunement(ItemStack stk);

	public boolean canUseSpell(ItemStack spell, EntityPlayer user);

}
