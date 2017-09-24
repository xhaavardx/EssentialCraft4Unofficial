package essentialcraft.common.block;

import java.util.ArrayList;
import java.util.List;

import DummyCore.Registries.BlockRegistry;
import essentialcraft.common.item.ItemBlockElementalCrystal;
import essentialcraft.common.item.ItemBlockFancy;
import essentialcraft.common.item.ItemBlockGeneric;
import essentialcraft.common.item.ItemBlockMeta;
import essentialcraft.common.item.ItemBlockMithrilineCrystal;
import essentialcraft.common.item.ItemBlockRDNS;
import essentialcraft.common.item.ItemsCore;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.registry.RecipesCore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class BlocksCore {

	public static void loadBlocks() {
		drops = new BlockDrops(Material.CLOTH).setUnlocalizedName("essentialcraft.drops");
		registerBlockSimple(new ItemBlockMeta(drops), "Drops");
		magicPlating = new BlockColored(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setUnlocalizedName("essentialcraft.magicPlatingBlock");
		registerBlockSimple(magicPlating, "magicPlating");
		fortifiedGlass = new BlockColored(Material.GLASS).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.fortifiedGlass");
		registerBlockSimple(fortifiedGlass, "fortifiedGlass");
		ecController = new BlockMRUCUECController(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecController");
		registerBlockSimple(ecController, "ecController");
		ecAcceptor = new BlockMRUCUECAcceptor(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecAcceptor");
		registerBlockSimple(ecAcceptor, "ecAcceptor");
		ecBalancer = new BlockMRUCUECBalancer(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecBalancer");
		registerBlockSimple(ecBalancer, "ecBalancer");
		ecEjector = new BlockMRUCUECEjector(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecEjector");
		registerBlockSimple(ecEjector, "ecEjector");
		ecHoldingChamber = new BlockMRUCUECHoldingChamber(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecHoldingChamber");
		registerBlockSimple(ecHoldingChamber, "ecHoldingChamber");
		ecStateChecker = new BlockMRUCUECStateChecker(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecStateChecker");
		registerBlockSimple(ecStateChecker, "ecStateChecker");
		ecRedstoneController = new BlockMRUCUECRedstoneController(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.ecRedstoneController");
		registerBlockSimple(ecRedstoneController, "ecRedstoneController");
		rayTower = new BlockRayTower().setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.rayTower");
		registerBlockSimple(rayTower, "rayTower");
		solarPrism = new BlockSolarPrism(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.solarPrism");
		registerBlockSimple(solarPrism, "solarPrism");
		sunRayAbsorber = new BlockSunRayAbsorber(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.sunRayAbsorber");
		registerBlockSimple(sunRayAbsorber, "sunRayAbsorber");
		coldStone = new BlockColdStone().setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.coldStone");
		registerBlockSimple(coldStone, "coldStone");
		String[] corruptionNames = {"chaos", "frozen", "magic", "shade"};
		coldDistillator = new BlockColdDistillator(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.coldDistillator");
		registerBlockSimple(coldDistillator, "coldDistillator");
		naturalFurnace = new BlockFlowerBurner(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.naturalFurnace");
		registerBlockSimple(naturalFurnace, "naturalFurnace");
		heatGenerator = new BlockHeatGenerator(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.heatGenerator");
		registerBlockSimple(heatGenerator, "heatGenerator");
		enderGenerator = new BlockEnderGenerator(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.enderGenerator");
		registerBlockSimple(enderGenerator, "enderGenerator");
		for(int i = 0; i < 4; ++i) {
			lightCorruption[i] = new BlockCorruption(Material.CIRCUITS).setBlockTextureName("essentialcraft:"+corruptionNames[i]).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.corruption."+corruptionNames[i]);
			registerBlockSimple(lightCorruption[i], corruptionNames[i]);
		}
		moonWell = new BlockMoonWell(Material.ROCK).setHardness(1.0F).setResistance(1.0F).setLightOpacity(0).setUnlocalizedName("essentialcraft.moonWell");
		registerBlockSimple(moonWell, "moonWell");
		magicianTable = registerBlockSimple(BlockMagicianTable.class, Material.ROCK, magicianTable, "magicianTable", 1, 1, 0);
		fortifiedStone = new BlockColored(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setUnlocalizedName("essentialcraft.fortifiedStone");
		registerBlockSimple(fortifiedStone, "fortifiedStone");
		magicalQuarry = registerBlockSimple(BlockMagicalQuarry.class, Material.ROCK, magicalQuarry, "magicalQuarry", 1, 1, 0);
		monsterClinger = registerBlockSimple(BlockMonsterHolder.class, Material.ROCK, monsterClinger, "monsterClinger", 1, 1, 0);
		potionSpreader = registerBlockSimple(BlockPotionSpreader.class, Material.ROCK, potionSpreader, "potionSpreader", 1, 1, 0);
		magicalEnchanter = registerBlockSimple(BlockMagicalEnchanter.class, Material.ROCK, magicalEnchanter, "magicalEnchanter", 1, 1, 0);
		monsterHarvester = registerBlockSimple(BlockMonsterHarvester.class, Material.ROCK, monsterHarvester, "monsterHarvester", 1, 1, 0);
		magicalRepairer = registerBlockSimple(BlockMagicalRepairer.class, Material.ROCK, magicalRepairer, "magicalRepairer", 1, 1, 0);
		matrixAbsorber = registerBlockSimple(BlockMatrixAbsorber.class, Material.ROCK, matrixAbsorber, "matrixAbsorber", 1, 1, 0);
		radiatingChamber = registerBlockSimple(BlockRadiatingChamber.class, Material.ROCK, radiatingChamber, "radiatingChamber", 1, 1, 0);
		magmaticSmeltery = registerBlockSimple(BlockMagmaticSmeltery.class, Material.ROCK, magmaticSmeltery, "magmaticSmeltery", 1, 1, 0);
		magicalJukebox = registerBlockSimple(BlockMagicalJukebox.class, Material.ROCK, magicalJukebox, "magicalJukebox", 1, 1, 0);
		elementalCrystal = new BlockElementalCrystal(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setUnlocalizedName("essentialcraft.elementalCrystal");
		registerBlockSimple(elementalCrystal, "elementalCrystal", ItemBlockElementalCrystal.class);
		crystalFormer = registerBlockSimple(BlockCrystalFormer.class, Material.ROCK, crystalFormer, "crystalFormer", 1, 1, 0);
		crystalController = registerBlockSimple(BlockCrystalController.class, Material.ROCK, crystalController, "crystalController", 1, 1, 0);
		crystalExtractor = registerBlockSimple(BlockCrystalExtractor.class, Material.ROCK, crystalExtractor, "crystalExtractor", 1, 1, 0);
		chargingChamber = registerBlockSimple(BlockChargingChamber.class, Material.ROCK ,chargingChamber, "chargingChamber", 1, 1, 0);
		voidStone = new BlockColored(Material.ROCK).setHardness(8.0F).setResistance(150.0F).setUnlocalizedName("essentialcraft.voidStone");
		registerBlockSimple(voidStone, "voidStone");
		voidGlass = new BlockColored(Material.GLASS).setHardness(8.0F).setResistance(150.0F).setUnlocalizedName("essentialcraft.voidGlass");
		registerBlockSimple(voidGlass, "voidGlass");

		concrete = registerBlockSimple(BlockEC.class, Material.ROCK, concrete, "concrete", 1, 1, 0);
		cacti = registerBlockSimple(BlockDreadCactus.class, Material.CACTUS, cacti, "cacti", 1, 1, 0);
		dreadDirt = registerBlockSimple(BlockEC.class, Material.GROUND, dreadDirt, "dreadDirt", 1, 1, 0);
		((BlockEC)dreadDirt).setSoundType(SoundType.GROUND);
		flowerGreen = registerBlockSimple(BlockHoannaFlower.class, Material.GRASS, flowerGreen, "flowerGreen", 1, 1, 0);
		fruit = registerBlockSimple(BlockMagicalFruit.class, Material.GRASS, fruit, "fruit", 1, 1, 0);
		root = registerBlockSimple(BlockEC.class, Material.WOOD,root, "root", 1, 1, 0);
		tallGrass = registerBlockSimple(BlockHoannaTallGrass.class, Material.GRASS, tallGrass, "tallGrass", 1, 1, 0);

		magicalTeleporter = registerBlockSimple(BlockMagicalTeleporter.class, Material.ROCK, magicalTeleporter, "magicalTeleporter", 1, 1, 0);
		magicalFurnace = registerBlockSimple(BlockMagicalFurnace.class, Material.ROCK, magicalFurnace, "magicalFurnace", 1, 1, 0);
		emberForge = registerBlockSimple(BlockEmberForge.class, Material.ROCK, emberForge, "emberForge", 1, 1, 0);
		levitator = registerBlockSimple(BlockMRULevitator.class, Material.ROCK, levitator, "levitator",1,100,0);
		spreader = registerBlockSimple(BlockMRUSpreader.class, Material.ROCK, spreader, "spreader",1 ,100 ,0);

		fence[0] = new BlockFenceEC(Material.ROCK).setHardness(8.0F).setResistance(150.0F).setUnlocalizedName("essentialcraft.voidFence");
		registerBlockSimple(fence[0], "voidFence");
		fence[1] = new BlockFenceEC(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setUnlocalizedName("essentialcraft.magicFence");
		registerBlockSimple(fence[1], "magicFence");
		fence[2] = new BlockFenceEC(Material.ROCK).setHardness(3.0F).setResistance(15.0F).setUnlocalizedName("essentialcraft.fFence");
		registerBlockSimple(fence[2], "fFence");

		torch = registerBlockSimple(BlockMagicLight.class, Material.CIRCUITS,torch, "torch", 0, 0, 1);
		torch.setLightLevel(1.0F);

		blockPale = registerBlockSimple(BlockEC.class, Material.ROCK, blockPale, "blockPale", 2, 100, 0);
		((BlockEC)blockPale).setSoundType(SoundType.METAL);
		platingPale = new BlockColored(Material.ROCK).setHardness(3.0F).setResistance(100.0F).setUnlocalizedName("essentialcraft.platingPale");
		registerBlockSimple(platingPale, "platingPale");
		mruCoilHardener = registerBlockSimple(BlockMRUCoilHardener.class, Material.ROCK, mruCoilHardener, "mruCoilHardener", 1, 100, 0);
		mruCoil = registerBlockSimple(BlockMRUCoil.class, Material.ROCK, mruCoil, "mruCoil", 1, 100, 0);
		corruptionCleaner = registerBlockSimple(BlockCorruptionCleaner.class, Material.ROCK, corruptionCleaner, "corruptionCleaner", 1, 100, 0);
		reactorSupport = new BlockReactorSupport().setUnlocalizedName("essentialcraft.reactorSupport").setHardness(1).setResistance(10).setLightOpacity(0);
		registerBlockSimple(reactorSupport, "reactorSupport");
		reactor = registerBlockSimple(BlockMRUReactor.class, Material.ROCK,reactor, "reactor", 1, 10, 0);
		air = new BlockEC(Material.ROCK).setHardness(-1).setResistance(-1).setLightLevel(0).setUnlocalizedName("air");
		registerBlockSimple(air, "air");
		darknessObelisk = registerBlockSimple(BlockDarknessObelisk.class, Material.ROCK, darknessObelisk, "darknessObelisk", 1, 1, 0);

		ultraHeatGen = registerBlockSimple(BlockUltraHeatGenerator.class, Material.ROCK, ultraHeatGen, "ultraHeatGen", 1, 1, 0);
		ultraFlowerBurner = registerBlockSimple(BlockUltraFlowerBurner.class, Material.ROCK, ultraFlowerBurner, "ultraFlowerBurner", 1, 1, 0);

		magicalMirror = registerBlockSimple(BlockMagicalMirror.class, Material.ROCK, magicalMirror, "magicalMirror", 1, 1, 0);
		magicalDisplay = registerBlockSimple(BlockMagicalDisplay.class, Material.ROCK, magicalDisplay, "magicalDisplay", 1, 1, 0);
		portal = registerBlockSimple(BlockHoannaPortal.class, Material.ROCK, portal, "portal",-1,-1,1);
		oreDrops = registerBlockSimple(BlockDropsOre.class, Material.ROCK, oreDrops, "oreDrops", 1,1,1);

		invertedBlock = new BlockColored(Material.ROCK).setHardness(4.0F).setResistance(100.0F).setUnlocalizedName("essentialcraft.mithrilinePlating");
		registerBlockSimple(invertedBlock, "invertedPlating");
		mithrilineCrystal = new BlockMithrilineCrystal().setUnlocalizedName("essentialcraft.mithrilineCrystal").setHardness(1).setResistance(1).setLightOpacity(1);
		registerBlockSimple(mithrilineCrystal, "mithrilineCrystal", ItemBlockMithrilineCrystal.class);
		mithrilineFurnace = registerBlockSimple(BlockMithrilineFurnace.class, Material.ROCK, mithrilineFurnace, "mithrilineFurnace",1,1,1);

		demonicPlating = new BlockColored(Material.ROCK).setHardness(3.0F).setResistance(100.0F).setUnlocalizedName("essentialcraft.demonicPlating");
		registerBlockSimple(demonicPlating, "demonicPlating");
		playerPentacle = registerBlockSimple(BlockPlayerPentacle.class, Material.ROCK, playerPentacle, "playerPentacle",0,0,0);
		windRune = registerBlockSimple(BlockWindRune.class, Material.ROCK, windRune, "windRune",3,10,0);
		rightClicker = registerBlockSimple(BlockRightClicker.class, Material.ROCK, rightClicker, "rightClicker", 1, 1, 15);
		redstoneTransmitter = registerBlockSimple(BlockRedstoneTransmitter.class, Material.ROCK,redstoneTransmitter, "redstoneTransmitter",0,0,0);
		magicalHopper = registerBlockSimple(BlockMagicalHopper.class, Material.ROCK, magicalHopper, "magicalHopper", 1, 1, 15);
		metadataManager = registerBlockSimple(BlockMetadataManager.class, Material.ROCK, metadataManager, "metadataManager",1, 1, 15);
		blockBreaker = registerBlockSimple(BlockBlockBreaker.class, Material.ROCK, blockBreaker, "blockBreaker", 1, 1, 15);
		compressed = new BlockCompressedDrops().setUnlocalizedName("essentialcraft.compressed").setHardness(0.4F).setResistance(1).setLightOpacity(15);
		registerBlockSimple(new ItemBlockMeta(compressed), "compressed");
		demonicPentacle = registerBlockSimple(BlockDemonicPentacle.class, Material.ROCK, demonicPentacle, "demonicPentacle",0,0,0);
		weaponMaker = registerBlockSimple(BlockWeaponMaker.class, Material.ROCK, weaponMaker, "weaponMaker", 1, 1, 15);
		furnaceMagic = new BlockFurnaceMagic().setUnlocalizedName("essentialcraft.furnaceMagic").setHardness(1).setResistance(1).setLightOpacity(15);
		registerBlockSimple(furnaceMagic, "furnaceMagic");
		holopad = registerBlockSimple(BlockHologramSpawner.class, Material.ROCK, holopad, "holopad", 1, 1, 15);
		chest = registerBlockSimple(BlockChestEC.class, Material.ROCK, chest, "chest", 1, 1, 0);
		mimInvStorage = registerBlockSimple(BlockMIMInventoryStorage.class, Material.ROCK,mimInvStorage, "mimInvStorage", 1, 1, 15);
		newMim = new BlockMIM(Material.ROCK).setResistance(1).setHardness(1).setLightOpacity(0).setUnlocalizedName("essentialcraft.newMim");
		registerBlockSimple(newMim, "newMim");
		mimScreen = registerBlockSimple(BlockMIMScreen.class, Material.ROCK, mimScreen, "mimScreen", 1, 1, 15);
		mimCrafter = registerBlockSimple(BlockMIMCraftingManager.class, Material.ROCK, mimCrafter, "mimCrafter", 1, 1, 15);
		mimEjector = registerBlockSimple(BlockMIMExporter.class, Material.ROCK, mimEjector, "mimEjector", 1, 1, 0);
		mimInjector = registerBlockSimple(BlockMIMImporter.class, Material.ROCK, mimInjector, "mimInjector", 1, 1, 0);
		device = new BlockRedstoneDeviceNotSided().setHardness(1).setResistance(1).setUnlocalizedName("essentialcraft.device");
		registerBlockSimple(device, "device", ItemBlockRDNS.class);
		advBreaker = registerBlockSimple(BlockAdvBlockBreaker.class, Material.ROCK,advBreaker, "advBreaker", 1, 1, 0);
		mimEjectorP = registerBlockSimple(BlockMIMExporterPersistant.class, Material.ROCK, mimEjectorP, "mimEjectorP", 1, 1, 0);
		mimInjectorP = registerBlockSimple(BlockMIMImporterPersistant.class, Material.ROCK, mimInjectorP, "mimInjectorP", 1, 1, 0);

		oreMithriline = new BlockMithrilineOre().setUnlocalizedName("essentialcraft.oreMithriline").setHardness(0.4F).setResistance(1).setLightOpacity(15);
		registerBlockSimple(oreMithriline, "oreMithriline");
		crystalLamp = new BlockCrystalLamp().setUnlocalizedName("essentialcraft.crystalLamp").setHardness(0.4F).setResistance(1).setLightOpacity(15);
		registerBlockSimple(new ItemBlockMeta(crystalLamp), "crystalLamp");
		mimic = new BlockMimic().setUnlocalizedName("essentialcraft.mimic").setResistance(15).setHardness(3).setLightOpacity(15);
		registerBlockSimple(mimic, "mimic");

		water = new BlockEC(Material.WATER).setHardness(-1).setResistance(-1).setLightLevel(0).setUnlocalizedName("water");
		registerBlockSimple(water, "water");
		lava = new BlockEC(Material.LAVA).setHardness(-1).setResistance(-1).setLightLevel(0).setUnlocalizedName("lava");
		registerBlockSimple(lava, "lava");
		fire = new BlockEC(Material.FIRE).setHardness(-1).setResistance(-1).setLightLevel(0).setUnlocalizedName("fire");
		registerBlockSimple(fire, "fire");

		chunkLoader = registerBlockSimple(BlockChunkLoader.class,Material.ROCK,chunkLoader,"chunkLoader",1,1,0);
		dimTransciever = registerBlockSimple(BlockDimensionalTransciever.class,Material.ROCK,dimTransciever,"dimTransciever",1,1,0);
	}

	public static void postInitLoad() {
		createFancyBlock(Material.ROCK, "mru", "mru", 1, 100, new ItemStack(ItemsCore.magicalSlag));
		createFancyBlock(Material.ROCK, "concrete", "concrete", 1, 5, new ItemStack(concrete));
		createFancyBlock(Material.ROCK, "fortifiedStone", "fortifiedStone", 1.5F, 8, new ItemStack(fortifiedStone));
		createFancyBlock(Material.GLASS, "coldStone", "coldStone", 0.7F, 1, new ItemStack(coldStone));
		createFancyBlock(Material.ROCK, "magicPlating", "magicPlating", 2F, 8, new ItemStack(magicPlating));
		createFancyBlock(Material.ROCK, "palePlating", "palePlating", 2F, 8,new ItemStack(platingPale));
		createFancyBlock(Material.ROCK, "voidStone", "voidStone", 3F, 28, new ItemStack(voidStone));
		createFancyBlock(Material.ROCK, "mithrilinePlating", "mithrilinePlating", 3F, 100, new ItemStack(invertedBlock));
		createFancyBlock(Material.ROCK, "demonicPlating", "demonicPlating", 3F, 100, new ItemStack(demonicPlating));

		Block mimicFancy = new BlockFancyMimic().setUnlocalizedName("essentialcraft.fancyBlock.mimic").setResistance(15).setHardness(3).setLightOpacity(15);
		registerFancyBlock(mimicFancy, "mimic", new ItemStack(mimic));
	}

	public static <T extends Block> T registerBlockSimple(Class<T> c, Material m, Block b, String name, float hardness, float resistance, int opacity) {
		try {
			b = c.getConstructor().newInstance().setUnlocalizedName("essentialcraft."+name).setResistance(resistance).setHardness(hardness).setLightOpacity(opacity);
			BlockRegistry.registerBlock(new ItemBlockGeneric(b), name, EssentialCraftCore.class);
			EssentialCraftCore.proxy.handleBlockRegister(b);
			return (T)b;
		}
		catch(Exception e) {
			try {
				if(c == BlockEC.class) {
					b = new BlockEC(m).setUnlocalizedName("essentialcraft."+name).setResistance(resistance).setHardness(hardness).setLightOpacity(opacity);
					BlockRegistry.registerBlock(new ItemBlockGeneric(b), name, EssentialCraftCore.class);
					EssentialCraftCore.proxy.handleBlockRegister(b);
					return (T)b;
				}
				else {
					c.getConstructor(Material.class).setAccessible(true);
					b = c.getConstructor(Material.class).newInstance(m).setUnlocalizedName("essentialcraft."+name).setResistance(resistance).setHardness(hardness).setLightOpacity(opacity);
					BlockRegistry.registerBlock(new ItemBlockGeneric(b), name, EssentialCraftCore.class);
					EssentialCraftCore.proxy.handleBlockRegister(b);
					return (T)b;
				}
			}
			catch (Exception e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}

	public static <T extends Block> T registerBlockSimple(Block b, String name) {
		BlockRegistry.registerBlock(new ItemBlockGeneric(b), name, EssentialCraftCore.class);
		EssentialCraftCore.proxy.handleBlockRegister(b);
		return (T)b;
	}

	public static <T extends Block> T registerBlockSimple(Block b, String name, Class<? extends ItemBlock> ib) {
		try {
			ib.getConstructor(Block.class).setAccessible(true);
			BlockRegistry.registerBlock(ib.getConstructor(Block.class).newInstance(b), name, EssentialCraftCore.class);
			EssentialCraftCore.proxy.handleBlockRegister(b);
			return (T)b;
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static ItemBlock registerBlockSimple(ItemBlock ib, String name) {
		BlockRegistry.registerBlock(ib, name, EssentialCraftCore.class);
		EssentialCraftCore.proxy.handleBlockRegister(ib.getBlock());
		return ib;
	}

	public static void createFancyBlock(Material m, String name, String texture, float hardness, float resistance, ItemStack createdFrom) {
		Block fancy = new BlockFancy(m).setUnlocalizedName("essentialcraft.fancyBlock."+name).setResistance(resistance).setHardness(hardness);
		BlockRegistry.registerBlock(new ItemBlockFancy(fancy), "fancyBlock."+name, EssentialCraftCore.class);
		EssentialCraftCore.proxy.handleBlockRegister(fancy);
		fancyBlocks.add(fancy);
		RecipesCore.fancyBlockRecipes.put(fancy, createdFrom);
	}

	public static Block registerFancyBlock(Block fancy, String name, ItemStack createdFrom) {
		BlockRegistry.registerBlock(new ItemBlockFancy(fancy), "fancyBlock."+name, EssentialCraftCore.class);
		EssentialCraftCore.proxy.handleBlockRegister(fancy);
		fancyBlocks.add(fancy);
		RecipesCore.fancyBlockRecipes.put(fancy, createdFrom);
		return fancy;
	}

	public static Block drops;
	public static Block magicPlating;
	public static Block fortifiedGlass;
	public static Block ecController;
	public static Block ecAcceptor;
	public static Block ecBalancer;
	public static Block ecEjector;
	public static Block ecHoldingChamber;
	public static Block ecStateChecker;
	public static Block ecRedstoneController;
	public static Block rayTower;
	public static Block moonWell;
	public static Block solarPrism;
	public static Block sunRayAbsorber;
	public static Block coldStone;
	public static Block coldDistillator;
	public static Block naturalFurnace;
	public static Block heatGenerator;
	public static Block enderGenerator;
	public static Block magicianTable;
	public static Block fortifiedStone;
	public static Block magicalQuarry;
	public static Block monsterClinger;
	public static Block potionSpreader;
	public static Block magicalEnchanter;
	public static Block monsterHarvester;
	public static Block magicalRepairer;
	public static Block matrixAbsorber;
	public static Block radiatingChamber;
	public static Block magmaticSmeltery;
	public static Block magicalJukebox;
	public static Block elementalCrystal;
	public static Block crystalFormer;
	public static Block crystalController;
	public static Block crystalExtractor;
	public static Block chargingChamber;
	public static Block voidStone;
	public static Block voidGlass;

	public static Block concrete;
	public static Block cacti;
	public static Block dreadDirt;
	public static Block flowerGreen;
	public static Block fruit;
	public static Block root;
	public static Block tallGrass;
	public static Block thorns;

	public static Block magicalTeleporter;
	public static Block magicalFurnace;
	public static Block emberForge;

	public static Block levitator;
	public static Block spreader;
	public static Block[] fence = new Block[3];
	public static Block torch;

	public static Block blockPale;

	public static Block platingPale;
	public static Block mruCoilHardener;
	public static Block mruCoil;
	public static Block corruptionCleaner;
	public static Block reactorSupport;
	public static Block reactor;

	public static Block air;

	//public static Block minEjector;
	//public static Block minInjector;
	//public static Block mim;
	public static Block darknessObelisk;
	public static Block ultraHeatGen;
	public static Block ultraFlowerBurner;
	public static Block magicalMirror;
	public static Block magicalDisplay;
	public static BlockHoannaPortal portal;
	public static Block oreDrops;
	public static Block invertedBlock;
	public static Block mithrilineCrystal;
	public static Block mithrilineFurnace;
	public static Block demonicPlating;
	public static Block playerPentacle;
	public static Block windRune;
	public static Block rightClicker;
	public static Block redstoneTransmitter;
	public static Block magicalHopper;
	public static Block metadataManager;
	public static Block blockBreaker;
	public static Block compressed;
	public static Block demonicPentacle;
	public static Block weaponMaker;
	public static Block furnaceMagic;

	public static Block holopad;
	public static Block chest;
	public static Block mimInvStorage;
	public static Block newMim;
	public static Block mimScreen;
	public static Block mimCrafter;
	public static Block mimEjector;
	public static Block mimInjector;
	public static Block device;
	public static Block advBreaker;
	public static Block mimEjectorP;
	public static Block mimInjectorP;

	public static Block oreMithriline;
	public static Block crystalLamp;
	public static Block mimic;
	public static Block chunkLoader;
	public static Block dimTransciever;

	public static Block water;
	public static Block lava;
	public static Block fire;

	public static List<Block> fancyBlocks = new ArrayList<Block>();

	public static Block[] lightCorruption = new Block[4];
}
