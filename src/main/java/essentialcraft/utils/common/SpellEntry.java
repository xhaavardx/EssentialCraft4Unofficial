package essentialcraft.utils.common;

import essentialcraft.api.EnumSpellType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public abstract class SpellEntry {

	public String name;
	public String description;
	public String icon;
	public EnumSpellType type;
	public int required;
	public int attuneReq;
	public int reqTier;

	public SpellEntry() {
		super();
	}

	public SpellEntry setName(String i) {
		name = i;
		return this;
	}

	public SpellEntry setIconName(String i) {
		icon = i;
		return this;
	}

	public SpellEntry setDesc(String i) {
		description = i;
		return this;
	}

	public SpellEntry setType(EnumSpellType i) {
		type = i;
		return this;
	}

	public SpellEntry setUBMRUReq(int i) {
		required = i;
		return this;
	}

	public SpellEntry setAttuneReq(int i) {
		attuneReq = i;
		return this;
	}

	public SpellEntry setTierReq(int i) {
		reqTier = i;
		return this;
	}

	public SpellEntry register() {
		ECUtils.SPELL_LIST.add(this);
		return this;
	}

	public abstract void onSpellUse(int currentUBMRU, int attunement, EntityPlayer player, ItemStack spell, ItemStack holder);

	public abstract boolean canUseSpell(ItemStack spell, EntityPlayer user);
}
