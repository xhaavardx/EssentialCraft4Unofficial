package essentialcraft.common.registry;

import essentialcraft.common.potion.PotionChaosInfluence;
import essentialcraft.common.potion.PotionFrozenMind;
import essentialcraft.common.potion.PotionMRUCorruption;
import essentialcraft.common.potion.PotionMindfoldParadox;
import essentialcraft.common.potion.PotionPurpleFlame;
import essentialcraft.common.potion.PotionRadiation;
import essentialcraft.common.potion.PotionShadeCorruption;
import essentialcraft.common.potion.PotionUnnormalLightness;
import essentialcraft.common.potion.PotionWindTouch;
import net.minecraft.potion.Potion;
import net.minecraftforge.registries.IForgeRegistry;

public class PotionRegistry {

	public static void registerPotions(IForgeRegistry<Potion> registry)
	{
		mruCorruption = new PotionMRUCorruption(0, true, 0xff00ff);
		chaosInfluence = new PotionChaosInfluence(0, true, 0xff0000);
		frozenMind = new PotionFrozenMind(0, true, 0x0000ff);
		windTouch = new PotionWindTouch(0, true, 0xccffcc);
		paranormalLightness = new PotionUnnormalLightness(0, true, 0xffffcc);
		radiation = new PotionRadiation(0, true, 0x660066);
		paradox = new PotionMindfoldParadox(0, true, 0xffffff);

		registry.register(mruCorruption);
		registry.register(chaosInfluence);
		registry.register(frozenMind);
		registry.register(windTouch);
		registry.register(paranormalLightness);
		registry.register(radiation);
		registry.register(paradox);
	}

	public static PotionMRUCorruption mruCorruption;
	public static PotionFrozenMind frozenMind;
	public static PotionChaosInfluence chaosInfluence;
	public static PotionWindTouch windTouch;
	public static PotionUnnormalLightness paranormalLightness;
	public static PotionRadiation radiation;
	public static PotionShadeCorruption shadeCorruption;
	public static PotionPurpleFlame purpleFlame;
	public static PotionMindfoldParadox paradox;
}
