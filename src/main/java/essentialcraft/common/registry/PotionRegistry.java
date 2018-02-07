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

	public static void registerPotions(IForgeRegistry<Potion> registry) {
		mruCorruption = new PotionMRUCorruption(true, 0xFF00FF);
		chaosInfluence = new PotionChaosInfluence(true, 0xFF0000);
		frozenMind = new PotionFrozenMind(true, 0x0000FF);
		windTouch = new PotionWindTouch(true, 0xCCFFCC);
		paranormalLightness = new PotionUnnormalLightness(true, 0xFFFFCC);
		radiation = new PotionRadiation(true, 0x660066);
		paradox = new PotionMindfoldParadox(true, 0xFFFFFF);

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
