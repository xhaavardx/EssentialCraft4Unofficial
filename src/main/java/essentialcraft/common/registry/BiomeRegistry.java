package essentialcraft.common.registry;

import essentialcraft.common.world.biome.BiomeChaosCorruption;
import essentialcraft.common.world.biome.BiomeFrozenCorruption;
import essentialcraft.common.world.biome.BiomeHoannaDesert;
import essentialcraft.common.world.biome.BiomeHoannaDreadlands;
import essentialcraft.common.world.biome.BiomeMagicCorruption;
import essentialcraft.common.world.biome.BiomeShadowCorruption;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class BiomeRegistry {

	public BiomeRegistry() {
		//MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}

	@SubscribeEvent
	public void manageBiomeGen(WorldTypeEvent.InitBiomeGens event) {}

	public static void register(IForgeRegistry<Biome> registry) {
		chaosCorruption = new BiomeChaosCorruption(new BiomeProperties("Chaos Corrupted Land")).setRegistryName("essentialcraft:chaoscorruption");
		frozenCorruption = new BiomeFrozenCorruption(new BiomeProperties("Frozen Corrupted Land")).setRegistryName("essentialcraft:frozencorruption");
		shadowCorruption = new BiomeShadowCorruption(new BiomeProperties("Shadow Corrupted Land")).setRegistryName("essentialcraft:shadowcorruption");
		magicCorruption = new BiomeMagicCorruption(new BiomeProperties("Magic Corrupted Land")).setRegistryName("essentialcraft:magiccorruption");
		dreadlands = new BiomeHoannaDreadlands(new BiomeProperties("Hoanna Dreadlands").setRainDisabled().setTemperature(2F).setRainfall(0F).setBaseHeight(0.125F).setHeightVariation(0.126F))
				.setGrassColor(0x889688).setWaterColor(0x889688).setLeavesColor(0x889688).setRegistryName("essentialcraft:dreadlands");
		desert = new BiomeHoannaDesert(new BiomeProperties("Hoanna Desert").setRainDisabled().setTemperature(2F).setRainfall(0F).setBaseHeight(0.125F).setHeightVariation(0.05F))
				.setGrassColor(0xFA9418).setLeavesColor(0xFA9418).setWaterColor(0xFA9418).setRegistryName("essentialcraft:desert");

		registry.register(chaosCorruption);
		registry.register(frozenCorruption);
		registry.register(shadowCorruption);
		registry.register(magicCorruption);
		registry.register(dreadlands);
		registry.register(desert);

		BiomeDictionary.addTypes(chaosCorruption, Type.MAGICAL, Type.WASTELAND, Type.PLAINS);
		BiomeDictionary.addTypes(frozenCorruption, Type.MAGICAL, Type.WASTELAND, Type.PLAINS);
		BiomeDictionary.addTypes(shadowCorruption, Type.MAGICAL, Type.WASTELAND, Type.PLAINS, Type.RARE);
		BiomeDictionary.addTypes(magicCorruption, Type.MAGICAL, Type.WASTELAND, Type.PLAINS);
		BiomeDictionary.addTypes(dreadlands, Type.HOT, Type.DRY, Type.DEAD);
		BiomeDictionary.addTypes(desert, Type.HOT, Type.DRY);
	}

	public static Biome chaosCorruption;
	public static Biome frozenCorruption;
	public static Biome shadowCorruption;
	public static Biome magicCorruption;
	public static Biome dreadlands;
	public static Biome desert;
}
