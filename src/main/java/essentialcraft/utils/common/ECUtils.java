package essentialcraft.utils.common;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import com.google.common.collect.HashMultimap;
import com.mojang.authlib.GameProfile;

import DummyCore.Utils.Coord3D;
import DummyCore.Utils.DummyData;
import DummyCore.Utils.DummyPacketHandler;
import DummyCore.Utils.DummyPacketIMSG;
import DummyCore.Utils.MathUtils;
import DummyCore.Utils.MiscUtils;
import DummyCore.Utils.Notifier;
import DummyCore.Utils.ScheduledServerAction;
import baubles.api.BaublesApi;
import baubles.api.cap.IBaublesItemHandler;
import essentialcraft.api.ApiCore;
import essentialcraft.api.EnumStructureType;
import essentialcraft.api.IMRUHandler;
import essentialcraft.api.IMRUHandlerEntity;
import essentialcraft.api.IMRUHandlerItem;
import essentialcraft.api.IMRUResistHandler;
import essentialcraft.api.IMRUVisibilityHandler;
import essentialcraft.api.ISpell;
import essentialcraft.api.IWorldEvent;
import essentialcraft.api.MagicianTableRecipe;
import essentialcraft.api.MagicianTableRecipes;
import essentialcraft.api.RadiatingChamberRecipe;
import essentialcraft.api.RadiatingChamberRecipes;
import essentialcraft.api.ShapedFurnaceRecipe;
import essentialcraft.api.WorldEventRegistry;
import essentialcraft.common.entity.EntityMRUPresence;
import essentialcraft.common.inventory.InventoryMagicFilter;
import essentialcraft.common.item.ItemBaublesResistance;
import essentialcraft.common.item.ItemBoundGem;
import essentialcraft.common.item.ItemFilter;
import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.registry.PotionRegistry;
import essentialcraft.common.tile.TileMRUReactor;
import essentialcraft.common.tile.TileRayTower;
import essentialcraft.network.PacketNBT;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

public class ECUtils {
	public static final HashMultimap<EnumStructureType,Block> STRUCTURE_TO_BLOCKS_MAP = HashMultimap.<EnumStructureType, Block>create();
	public static final Hashtable<String, Float> MRU_RESISTANCES = new Hashtable<String, Float>();
	public static final Hashtable<String, Boolean> IGNORE_META = new Hashtable<String, Boolean>();
	public static final List<SpellEntry> SPELL_LIST = new ArrayList<SpellEntry>();
	public static final Hashtable<UUID,PlayerGenericData> PLAYER_DATA_MAP = new Hashtable<UUID, PlayerGenericData>();
	private static final List<ScheduledServerAction> ACTION_LIST = new ArrayList<ScheduledServerAction>();
	public static NBTTagCompound ec3WorldTag = new NBTTagCompound();

	public static void requestSync(EntityPlayer e)
	{
		NBTTagCompound tag = new NBTTagCompound();
		getData(e).writeToNBTTagCompound(tag);
		PacketNBT pkt = new PacketNBT(tag).setID(0);
		EssentialCraftCore.network.sendTo(pkt, (EntityPlayerMP)e);
	}

	public static PlayerGenericData getData(EntityPlayer e)
	{
		return playerDataExists(e) ? PLAYER_DATA_MAP.get(MiscUtils.getUUIDFromPlayer(e)) : createPlayerData(e);
	}

	/**
	 * Should only be used for CLIENT, but may be used for the SERVER
	 */
	public static PlayerGenericData getData(UUID uuid)
	{
		return playerDataExists(uuid) ? PLAYER_DATA_MAP.get(uuid) : null;
	}

	/**
	 * Should only be used for CLIENT, but may be used for the SERVER
	 */
	public static boolean playerDataExists(UUID uuid)
	{
		return PLAYER_DATA_MAP.containsKey(uuid);
	}

	public static boolean playerDataExists(EntityPlayer e)
	{
		return PLAYER_DATA_MAP.containsKey(MiscUtils.getUUIDFromPlayer(e));
	}

	public static boolean playerDataExists(String username) {
		try {
			return PLAYER_DATA_MAP.containsKey(UUID.fromString(username));
		}
		catch(IllegalArgumentException e) {
			return false;
		}
	}

	public static PlayerGenericData createPlayerData(EntityPlayer e)
	{
		if(e != null) {
			PlayerGenericData dat = new PlayerGenericData(e);
			PLAYER_DATA_MAP.put(MiscUtils.getUUIDFromPlayer(e), dat);
			return dat;
		}
		else
			return null;
	}

	public static void changePlayerPositionOnClient(EntityPlayer e)
	{
		if(!e.getEntityWorld().isRemote)
		{
			DummyData aaa = new DummyData("x",e.posX);
			DummyData aab = new DummyData("y",e.posY);
			DummyData aac = new DummyData("z",e.posZ);
			DummyData aad = new DummyData("yaw",e.rotationYaw);
			DummyData aae = new DummyData("pitch",e.rotationPitch);
			DummyPacketIMSG pkt = new DummyPacketIMSG("||mod:essentialcraft.player.position"+aaa+""+aab+""+aac+""+aad+""+aae);
			DummyPacketHandler.sendToPlayer(pkt, (EntityPlayerMP) e);
		}
	}

	public static void playSoundToAllNearby(double x, double y, double z, String sound, float volume, float pitch, double radius, int dim)
	{
		DummyData aaa = new DummyData("x",x);
		DummyData aab = new DummyData("y",y);
		DummyData aac = new DummyData("z",z);
		DummyData aad = new DummyData("vol",volume);
		DummyData aae = new DummyData("pitch",pitch);
		DummyData aaf = new DummyData("sound",sound);
		DummyPacketIMSG pkt = new DummyPacketIMSG("||mod:essentialcraft.sound"+aaa+""+aab+""+aac+""+aad+""+aae+""+aaf);
		DummyPacketHandler.sendToAllAround(pkt, new TargetPoint(dim, x, y, z, radius));
	}

	public static void readOrCreatePlayerData(EntityPlayer e, NBTTagCompound tag)
	{
		if(e != null && tag != null) {
			if(!PLAYER_DATA_MAP.containsKey(e.getName()))
			{
				PlayerGenericData dat = new PlayerGenericData(e);
				PLAYER_DATA_MAP.put(MiscUtils.getUUIDFromPlayer(e), dat);
			}
			PLAYER_DATA_MAP.get(MiscUtils.getUUIDFromPlayer(e)).readFromNBTTagCompound(tag);
		}
	}

	public static void createNBTTag(ItemStack stack)
	{
		if(stack.isEmpty() || stack.hasTagCompound())
		{
			return;
		}
		NBTTagCompound itemTag = new NBTTagCompound();
		stack.setTagCompound(itemTag);
	}

	public static NBTTagCompound getStackTag(ItemStack stack)
	{
		createNBTTag(stack);
		return stack.getTagCompound();
	}

	public static NBTTagCompound getOrCreateNbtData(ItemStack itemStack)
	{
		NBTTagCompound ret = itemStack.getTagCompound();
		if(!itemStack.isEmpty() && ret == null)
		{
			ret = new NBTTagCompound();
			itemStack.setTagCompound(ret);
		}
		return ret;
	}

	public static void initMRUTag(ItemStack stack, int maxMRU)
	{
		if(maxMRU == 0)
		{
			try
			{
				Class<? extends Item> itemClz = stack.getItem().getClass();
				Field f = itemClz.getField("maxMRU");
				f.setInt(stack.getItem(), 5000);
				maxMRU = 5000;
			}catch(Exception e)
			{
				//Silent error tracking
			}
		}
		getStackTag(stack).setInteger("maxMRU", maxMRU);
		if(!getStackTag(stack).hasKey("mru"))
			getStackTag(stack).setInteger("mru", 0);
	}

	public static void registerBlockResistance(Block blk, int meta, float resistance)
	{
		DummyData dt = new DummyData(blk.getUnlocalizedName(), meta);
		IGNORE_META.put(blk.getUnlocalizedName(), meta == -1);
		MRU_RESISTANCES.put(dt.toString(), resistance);
	}

	public static boolean canPlayerSeeMRU(EntityPlayer player)
	{
		ItemStack currentItem = player.getHeldItemMainhand();
		ItemStack offhandItem = player.getHeldItemOffhand();

		ItemStack headwear = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);

		return ApiCore.MRU_VISIBLE_LIST.contains(currentItem.getItem()) ||
				ApiCore.MRU_VISIBLE_LIST.contains(headwear.getItem()) ||
				ApiCore.MRU_VISIBLE_LIST.contains(offhandItem.getItem()) ||
				currentItem.getItem() instanceof IMRUVisibilityHandler && ((IMRUVisibilityHandler)currentItem.getItem()).canSeeMRU(currentItem) ||
				headwear.getItem() instanceof IMRUVisibilityHandler && ((IMRUVisibilityHandler)headwear.getItem()).canSeeMRU(headwear) ||
				offhandItem.getItem() instanceof IMRUVisibilityHandler && ((IMRUVisibilityHandler)offhandItem.getItem()).canSeeMRU(offhandItem);
	}

	public static boolean canSpellWork(ItemStack spell, ISpell spell_2, int ubmru, int attune,EntityPlayer player)
	{
		NBTTagCompound tag = MiscUtils.getStackTag(spell);
		if(tag.hasKey("cooldown"))
		{
			if(tag.getInteger("cooldown") <= 0)
			{
				if(ubmru >= spell_2.getUBMRURequired(spell))
				{
					if(spell_2.getAttunementRequired(spell) == -1 || spell_2.getAttunementRequired(spell) == attune)
					{
						return true;
					}
				}
			}
		}else
		{
			if(ubmru >= spell_2.getUBMRURequired(spell))
			{
				if(spell_2.getAttunementRequired(spell) == -1 || spell_2.getAttunementRequired(spell) == attune)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean tryToDecreaseMRUInStorage(EntityPlayer player, int amount) {
		if(player.capabilities.isCreativeMode) {
			return true;
		}
		IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
		for(int i = 0; i < handler.getSlots(); ++i) {
			ItemStack stk = handler.getStackInSlot(i);
			if(stk.getItem() instanceof IMRUHandlerItem && ((IMRUHandlerItem)stk.getItem()).isStorage(stk)) {
				if(((IMRUHandlerItem)stk.getItem()).increaseMRU(stk, amount)) {
					return true;
				}
			}
		}
		InventoryPlayer inv = player.inventory;
		for(int i = 0; i < inv.mainInventory.size(); ++i) {
			ItemStack stk = inv.mainInventory.get(i);
			if(stk.getItem() instanceof IMRUHandlerItem && ((IMRUHandlerItem)stk.getItem()).isStorage(stk)) {
				if(((IMRUHandlerItem)stk.getItem()).increaseMRU(stk, amount)) {
					return true;
				}
			}
		}

		return false;
	}


	public static void saveMRUState(IMRUHandler tile, NBTTagCompound saveTag)
	{
		saveTag.setFloat("mru", tile.getMRU());
		saveTag.setFloat("maxMRU", tile.getMaxMRU());
		saveTag.setFloat("balance", tile.getBalance());
	}

	public static void loadMRUState(IMRUHandler tile, NBTTagCompound loadTag)
	{
		tile.setBalance(loadTag.getFloat("balance"));
		tile.setMRU((int) loadTag.getFloat("mru"));
		tile.setMaxMRU(loadTag.getFloat("maxMRU"));
	}

	public static void createMRUCUAt(World w, Coord3D c, int MRU, float balance, boolean flag, boolean canAlwaysStay)
	{
		EntityMRUPresence mru = new EntityMRUPresence(w);
		mru.setPositionAndRotation(c.x, c.y, c.z, 0, 0);
		if(!w.isRemote)
		{
			mru.setMRU(MRU);
			mru.setBalance(balance);
			mru.setFlag(flag);
			mru.setAlwaysStay(canAlwaysStay);
			w.spawnEntity(mru);
		}
	}

	public static void mruIn(TileEntity tile, int slotNum) {
		if(tile instanceof IInventory && tile instanceof IMRUHandler && !tile.getWorld().isRemote) {
			IInventory inv = (IInventory) tile;
			IMRUHandler mrut = (IMRUHandler) tile;

			if(inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null) {
				ItemStack s = inv.getStackInSlot(slotNum);
				int[] o = ItemBoundGem.getCoords(s);
				if(MathUtils.getDifference(tile.getPos().getX(), o[0]) <= 16 && MathUtils.getDifference(tile.getPos().getY(), o[1]) <= 16 && MathUtils.getDifference(tile.getPos().getZ(), o[2]) <= 16) {
					BlockPos o1 = new BlockPos(o[0],o[1],o[2]);
					if(tile.getWorld().getTileEntity(o1) != null && tile.getWorld().getTileEntity(o1) instanceof IMRUHandler) {
						IMRUHandler t = (IMRUHandler) tile.getWorld().getTileEntity(o1);
						if(t != tile && t != null) {
							if(mrut.getMRU() < mrut.getMaxMRU()) {
								int mru = t.getMRU();
								if(mru > mrut.getMaxMRU() - mrut.getMRU()) {
									if(t.setMRU(mru-(mrut.getMaxMRU() - mrut.getMRU()))) {
										mrut.setMRU(mrut.getMaxMRU());
									}
								}
								else {
									if(t.setMRU(0)) {
										mrut.setMRU(mrut.getMRU()+mru);
									}
								}
								mrut.setBalance((mrut.getBalance()+t.getBalance())/2);
							}
						}
					}
				}
			}
			if(mrut.getMRU() < 0)
				mrut.setMRU(0);
		}
	}

	public static boolean increaseCorruptionAt(World w, float x, float y, float z, int amount)
	{
		Coord3D c = new Coord3D(x,y,z);
		try {
			EntityMRUPresence mru = (EntityMRUPresence)getClosestMRUCU(w, c, 32);
			if(mru != null) {
				if(!w.isRemote) {
					mru.setMRU(mru.getMRU()+amount);
				}
			}
			else {
				createMRUCUAt(w, c, amount, 1.0F+MathUtils.randomFloat(w.rand), true, false);
			}
			List<EntityPlayer> players = w.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(x, y, z, x+1, y+1, z+1).expand(6D, 3D, 6D));
			for(EntityPlayer player : players) {
				if(!w.isRemote)
					calculateAndAddMRUCorruptionPE(player);
			}
		}
		catch(Exception e) {
			return false;
		}
		return false;
	}

	public static void calculateAndAddMRUCorruptionPE(EntityPlayer player)
	{
		if(player.isCreative() || player.isSpectator()) {
			return;
		}
		boolean hasEffect = player.getActivePotionEffect(PotionRegistry.mruCorruption) != null;
		if(hasEffect) {
			int currentDuration = player.getActivePotionEffect(PotionRegistry.mruCorruption).getDuration();
			int newDuration = currentDuration+2;
			int newModifier = currentDuration/2000;
			player.removePotionEffect(PotionRegistry.mruCorruption);
			player.addPotionEffect(new PotionEffect(PotionRegistry.mruCorruption,newDuration,newModifier,true,true));
		}
		else {
			player.addPotionEffect(new PotionEffect(PotionRegistry.mruCorruption,200,0,true,true));
		}
	}

	public static void calculateAndAddPE(EntityPlayer player, Potion potion, int index, int index2)
	{
		boolean hasEffect = player.getActivePotionEffect(potion) != null;
		if(hasEffect) {
			int currentDuration = player.getActivePotionEffect(potion).getDuration();
			int newDuration = currentDuration+index2;
			int newModifier = currentDuration/index;
			player.removePotionEffect(potion);
			player.addPotionEffect(new PotionEffect(potion,newDuration,newModifier,true,true));
		}
		else {
			player.addPotionEffect(new PotionEffect(potion,index2,0,true,true));
		}
	}

	public static IMRUHandlerEntity getClosestMRUCU(World w, DummyCore.Utils.Coord3D c, int radius)
	{
		return ApiCore.getClosestMRUCU(w, c, radius);
	}

	public static void spawnMRUParticles(TileEntity tile, int slotNum) {
		IInventory inv = (IInventory) tile;
		if(tile.getWorld().isRemote) {
			if(inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null) {
				ItemStack s = inv.getStackInSlot(slotNum);
				int[] o = ItemBoundGem.getCoords(s);
				if(MathUtils.getDifference(tile.getPos().getX(), o[0]) <= 16 && MathUtils.getDifference(tile.getPos().getY(), o[1]) <= 16 && MathUtils.getDifference(tile.getPos().getZ(), o[2]) <= 16) {
					BlockPos o1 = new BlockPos(o[0],o[1],o[2]);
					if(tile.getWorld().getTileEntity(o1) != null && tile.getWorld().getTileEntity(o1) instanceof IMRUHandler) {
						float balance = ((IMRUHandler)tile.getWorld().getTileEntity(o1)).getBalance();
						float colorRRender = 0.0F;
						float colorGRender = 1.0F;
						float colorBRender = 1.0F;

						float colorRNormal = 0.0F;
						float colorGNormal = 1.0F;
						float colorBNormal = 1.0F;

						float colorRChaos = 1.0F;
						float colorGChaos = 0.0F;
						float colorBChaos = 0.0F;

						float colorRFrozen = 0.0F;
						float colorGFrozen = 0.0F;
						float colorBFrozen = 1.0F;
						if(balance!=1.0F) {
							if(balance<1.0F) {
								float diff = balance;
								if(diff < 0.01F)
									diff = 0.0F;
								colorRRender = colorRNormal*diff + colorRFrozen*(1.0F-diff);
								colorGRender = colorGNormal*diff + colorGFrozen*(1.0F-diff);
								colorBRender = colorBNormal*diff + colorBFrozen*(1.0F-diff);
							}
							if(balance>1.0F) {
								float diff = 2.0F-balance;
								if(diff < 0.01F)
									diff = 0.0F;
								colorRRender = colorRNormal*diff + colorRChaos*(1.0F-diff);
								colorGRender = colorGNormal*diff + colorGChaos*(1.0F-diff);
								colorBRender = colorBNormal*diff + colorBChaos*(1.0F-diff);
							}
						}
						if(tile instanceof TileRayTower) {
							if(tile.getWorld().getTileEntity(o1) instanceof TileRayTower)
								EssentialCraftCore.proxy.MRUFX((float) (o[0]+0.5D), (float) (o[1]+1.85D), (float) (o[2]+0.5D), tile.getPos().getX()-o[0], tile.getPos().getY()-o[1]+0.25D, tile.getPos().getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else if(tile.getWorld().getTileEntity(o1) instanceof TileMRUReactor)
								EssentialCraftCore.proxy.MRUFX((float) (o[0]+0.5D), (float) (o[1]+1.1D), (float) (o[2]+0.5D), tile.getPos().getX()-o[0], tile.getPos().getY()-o[1]+0.8D, tile.getPos().getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else
								EssentialCraftCore.proxy.MRUFX((float) (o[0]+0.5D), (float) (o[1]+0.5D), (float) (o[2]+0.5D), tile.getPos().getX()-o[0], tile.getPos().getY()-o[1]+1.5D, tile.getPos().getZ()-o[2],colorRRender,colorGRender,colorBRender);
						}
						else {
							if(tile.getWorld().getTileEntity(o1) instanceof TileRayTower)
								EssentialCraftCore.proxy.MRUFX((float) (o[0]+0.5D), (float) (o[1]+1.85D), (float) (o[2]+0.5D), tile.getPos().getX()-o[0], tile.getPos().getY()-o[1]-1.5D, tile.getPos().getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else if(tile.getWorld().getTileEntity(o1) instanceof TileMRUReactor)
								EssentialCraftCore.proxy.MRUFX((float) (o[0]+0.5D), (float) (o[1]+1.1D), (float) (o[2]+0.5D), tile.getPos().getX()-o[0], tile.getPos().getY()-o[1]-0.6D, tile.getPos().getZ()-o[2],colorRRender,colorGRender,colorBRender);
							else
								EssentialCraftCore.proxy.MRUFX((float) (o[0]+0.5D), (float) (o[1]+0.5D), (float) (o[2]+0.5D), tile.getPos().getX()-o[0], tile.getPos().getY()-o[1], tile.getPos().getZ()-o[2],colorRRender,colorGRender,colorBRender);
						}
					}
				}
			}
		}
	}

	public static float getGenResistance(int index, EntityPlayer p)
	{
		float resistance = 0F;
		for(ItemStack armorStk : p.inventory.armorInventory) {
			Item itm = armorStk.getItem();
			if(ApiCore.ITEM_RESISTANCE_MAP.containsKey(itm)) {
				List<Float> lst = ApiCore.ITEM_RESISTANCE_MAP.get(itm);
				resistance += lst.get(index);
			}
			if(itm instanceof IMRUResistHandler) {
				List<Float> lst = ((IMRUResistHandler)itm).getMRUResistances(armorStk);
				resistance += lst.get(index);
			}
		}
		resistance /= 4;
		IBaublesItemHandler baublesInventory = BaublesApi.getBaublesHandler(p);
		if(baublesInventory != null) {
			for(int i = 0; i < baublesInventory.getSlots(); ++i) {
				ItemStack stk = baublesInventory.getStackInSlot(i);
				if(stk.getItem() instanceof ItemBaublesResistance && MiscUtils.getStackTag(stk).hasKey("type")) {
					NBTTagCompound bTag = MiscUtils.getStackTag(stk);
					List<Float> fltLst = new ArrayList<Float>();
					fltLst.add(bTag.getFloat("mrucr"));
					fltLst.add(bTag.getFloat("mrurr"));
					fltLst.add(bTag.getFloat("car"));
					resistance += fltLst.get(index);
				}
				if(ApiCore.ITEM_RESISTANCE_MAP.containsKey(stk.getItem())) {
					List<Float> lst = ApiCore.ITEM_RESISTANCE_MAP.get(stk.getItem());
					resistance += lst.get(index);
				}
				if(stk.getItem() instanceof IMRUResistHandler) {
					List<Float> lst = ((IMRUResistHandler)stk.getItem()).getMRUResistances(stk);
					resistance += lst.get(index);
				}
			}
		}

		float retFlt = 1.0F - resistance;
		if(retFlt < 0)
			retFlt = 0;
		return retFlt;
	}

	public static void newWorldEvent(World w)
	{
		if(WorldEventRegistry.currentEvent == null)
		{
			IWorldEvent event = WorldEventRegistry.selectRandomEffect(w);
			if(event != null && WorldEventRegistry.currentEvent == null)
			{
				WorldEventRegistry.currentEvent = event;
				WorldEventRegistry.currentEventDuration = event.getEventDuration(w);
				event.onEventBeginning(w);
			}
		}
	}

	public static void endEvent(World w)
	{
		if(WorldEventRegistry.currentEvent != null)
		{
			if(WorldEventRegistry.currentEventDuration-20 <= 0)
			{
				WorldEventRegistry.currentEvent.onEventEnd(w);
				WorldEventRegistry.currentEvent = null;
				WorldEventRegistry.currentEventDuration = -1;
			}else
			{
				WorldEventRegistry.currentEventDuration -= 20;
			}
		}
	}

	public static void requestCurrentEventSyncForPlayer(EntityPlayerMP player)
	{
		PacketNBT syncPacket = new PacketNBT(ec3WorldTag).setID(2);
		EssentialCraftCore.network.sendTo(syncPacket, player);
	}

	public static void requestCurrentEventSync()
	{
		PacketNBT syncPacket = new PacketNBT(ec3WorldTag).setID(2);
		EssentialCraftCore.network.sendToAll(syncPacket);
	}

	public static int getActiveEventDuration()
	{
		return ec3WorldTag.getInteger("currentEventDuration");
	}

	public static String getActiveEvent()
	{
		return ec3WorldTag.getString("currentEvent");
	}

	public static boolean hasActiveEvent()
	{
		return !ec3WorldTag.hasNoTags() && ec3WorldTag.getString("currentEvent") != null && !ec3WorldTag.getString("currentEvent").isEmpty();
	}

	public static boolean isEventActive(String id)
	{
		return ec3WorldTag.getString("currentEvent").equalsIgnoreCase(id);
	}

	public static void sendChatMessageToAllPlayersInDim(int dimID,String msg)
	{
		for(int i = 0; i < FMLCommonHandler.instance().getMinecraftServerInstance().getCurrentPlayerCount(); ++i)
		{
			EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayerByUsername(FMLCommonHandler.instance().getMinecraftServerInstance().getOnlinePlayerNames()[i]);
			if(player.dimension == dimID)
			{
				player.sendMessage(new TextComponentString(msg));
			}
		}
	}

	/**
	 * 0 - ShapedRecipe
	 * 1 - ShapelessRecipe
	 * 2 - ShapedOreRecipe
	 * 3 - ShapelessOreRecipe
	 * 4 - ShapedFurnaceRecipe
	 * 5 - RadiatingChamberRecipe
	 * 6 - MagicianTableRecipe
	 * 7 - ?StructureRecipe
	 * @param searched The IS you want to find
	 * @param recipeType ID
	 * @return The actual recipe or null if none found
	 */
	public static IRecipe findRecipeByIS(ItemStack searched, int recipeType)
	{
		if(recipeType == 0 || recipeType == 1)
		{
			for(IRecipe recipe : CraftingManager.REGISTRY)
			{
				if(recipe instanceof ShapedRecipes)
				{
					ShapedRecipes mRecipe = (ShapedRecipes) recipe;
					ItemStack output = mRecipe.getRecipeOutput();
					if(ItemStack.areItemStackTagsEqual(output, searched) && output.isItemEqual(searched))
						return new ShapedRecipes(mRecipe.getGroup(),mRecipe.recipeWidth,mRecipe.recipeHeight,mRecipe.recipeItems,mRecipe.getRecipeOutput());
				}
				if(recipe instanceof ShapelessRecipes)
				{
					ShapelessRecipes mRecipe = (ShapelessRecipes) recipe;
					ItemStack output = mRecipe.getRecipeOutput();
					if(output.isItemEqual(searched))
						return new ShapelessRecipes(mRecipe.getGroup(),mRecipe.getRecipeOutput(),mRecipe.recipeItems);
				}
			}
		}
		if(recipeType == 2 || recipeType == 3)
		{
			for(IRecipe recipe : CraftingManager.REGISTRY) {
				if(recipe instanceof ShapedOreRecipe)
				{
					ShapedOreRecipe mRecipe = (ShapedOreRecipe) recipe;
					ItemStack output = mRecipe.getRecipeOutput();
					if(ItemStack.areItemStackTagsEqual(output, searched) && output.isItemEqual(searched))
						return copyShapedOreRecipe(mRecipe);
				}
				if(recipe instanceof ShapelessOreRecipe)
				{
					ShapelessOreRecipe mRecipe = (ShapelessOreRecipe) recipe;
					ItemStack output = mRecipe.getRecipeOutput();
					if(output.isItemEqual(searched))
						return copyShapelessOreRecipe(mRecipe);
				}
			}
		}
		if(recipeType == 4) {
			for(Entry<ItemStack,ItemStack> entry : FurnaceRecipes.instance().getSmeltingList().entrySet()) {
				ItemStack key = entry.getKey();
				ItemStack value = entry.getValue();
				if(value.isItemEqual(searched)) {
					return new ShapedFurnaceRecipe(key,value);
				}
			}

		}
		if(recipeType == 5) {
			return new RadiatingChamberRecipe(RadiatingChamberRecipes.getRecipeByResult(searched));
		}
		if(recipeType == 6) {
			return new MagicianTableRecipe(MagicianTableRecipes.getRecipeByResult(searched));
		}
		return null;
	}

	public static ShapelessOreRecipe copyShapelessOreRecipe(ShapelessOreRecipe recipe) {
		ShapelessOreRecipe ret = new ShapelessOreRecipe(recipe.getGroup().isEmpty() ? null : new ResourceLocation(recipe.getGroup()),recipe.getIngredients(),recipe.getRecipeOutput());
		return ret;
	}

	public static ShapedOreRecipe copyShapedOreRecipe(ShapedOreRecipe recipe) {
		ShapedOreRecipe ret = new ShapedOreRecipe(recipe.getGroup().isEmpty() ? null : new ResourceLocation(recipe.getGroup()), recipe.getRecipeOutput(),new Object[]{"ooo","ooo","ooo",'o',Items.STICK});
		try {
			Class<ShapedOreRecipe> sorClazz = ShapedOreRecipe.class;
			Field inputFld = sorClazz.getDeclaredField("input");
			inputFld.setAccessible(true);
			Field widthFld = sorClazz.getDeclaredField("width");
			widthFld.setAccessible(true);
			Field heightFd = sorClazz.getDeclaredField("height");
			heightFd.setAccessible(true);
			inputFld.set(ret, inputFld.get(recipe));
			widthFld.set(ret, widthFld.get(recipe));
			heightFd.set(ret, heightFd.get(recipe));
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		return ret;
	}


	public static boolean oreDictionaryCompare(ItemStack stk, ItemStack stk1) {
		if(OreDictionary.getOreIDs(stk) == null && OreDictionary.getOreIDs(stk1) == null || OreDictionary.getOreIDs(stk).length == 0 && OreDictionary.getOreIDs(stk1).length == 0)
			return true;

		if(OreDictionary.getOreIDs(stk) == null || OreDictionary.getOreIDs(stk1) == null || OreDictionary.getOreIDs(stk).length == 0 || OreDictionary.getOreIDs(stk1).length == 0)
			return false;

		int[] ids = OreDictionary.getOreIDs(stk);
		int[] ids1 = OreDictionary.getOreIDs(stk1);

		for (int id : ids) {
			for (int element : ids1) {
				if(id == element)
					return true;
			}
		}

		return false;
	}

	public static boolean canFilterAcceptItem(IInventory filterInventory, ItemStack is, ItemStack filter) {
		if(filter.getItemDamage() == 0 || filter.getItemDamage() == 2) {
			for(int i = 0; i < filterInventory.getSizeInventory(); ++i) {
				ItemStack f = filterInventory.getStackInSlot(i);
				if(f.getItem() instanceof ItemFilter) {
					if(canFilterAcceptItem(new InventoryMagicFilter(f),is,f))
						return true;
				}
				else if(filter.getItemDamage() == 2) {
					if(!f.isItemEqual(is) || !ItemStack.areItemStackTagsEqual(f, is))
						return true;
					else
						return false;
				}
				else if(f.isItemEqual(is) && ItemStack.areItemStackTagsEqual(f, is))
					return true;
			}
		}else
		{
			boolean ignoreMeta = MiscUtils.getStackTag(filter).getBoolean("ignoreMeta");
			boolean ignoreNBT = MiscUtils.getStackTag(filter).getBoolean("ignoreNBT");
			boolean ignoreOreDict = MiscUtils.getStackTag(filter).getBoolean("ignoreOreDict");
			for(int i = 0; i < filterInventory.getSizeInventory(); ++i)
			{
				ItemStack f = filterInventory.getStackInSlot(i);
				if(f.getItem() instanceof ItemFilter)
				{
					if(canFilterAcceptItem(new InventoryMagicFilter(f),is,f))
						return true;
				}else
				{
					if(filter.getItemDamage() == 1)
					{
						if(oreDictionaryCompare(is,f) || ignoreOreDict)
						{
							if(ItemStack.areItemStackTagsEqual(f, is) || ignoreNBT)
							{
								return true;
							}
						}else
						{
							if(ItemStack.areItemStacksEqual(is, f) || is.getItem() == f.getItem() && ignoreMeta)
							{
								if(ItemStack.areItemStackTagsEqual(f, is) || ignoreNBT)
								{
									return true;
								}
							}
						}
					}else
					{
						if(!oreDictionaryCompare(is,f) || ignoreOreDict)
						{
							if(!ItemStack.areItemStackTagsEqual(f, is) || ignoreNBT)
							{
								return true;
							}else
								return false;
						}else
						{
							if(!ItemStack.areItemStacksEqual(is, f) || is.getItem() == f.getItem() && ignoreMeta)
							{
								if(!ItemStack.areItemStackTagsEqual(f, is) || ignoreNBT)
								{
									return true;
								}else
									return false;
							}else
								return false;
						}
					}
				}
			}
		}
		return filter.getItemDamage() > 1;
	}

	public static void spawnItemFX(TileEntity source, TileEntity destination)
	{
		double sX = source.getPos().getX() + 0.5D;
		double sY = source.getPos().getY() + 0.5D;
		double sZ = source.getPos().getZ() + 0.5D;
		double mX = destination.getPos().getX()+0.5D - sX;
		double mY = destination.getPos().getY()+0.5D - sY;
		double mZ = destination.getPos().getZ()+0.5D - sZ;
		String dataString = new String();
		dataString += "||mod:EC3.Particle.Item";
		dataString += "||x:"+sX+"||y:"+sY+"||z:"+sZ;
		dataString += "||mX:"+mX+"||mY:"+mY+"||mZ:"+mZ;
		DummyPacketIMSG pkt = new DummyPacketIMSG(dataString);
		DummyPacketHandler.sendToAll(pkt);
	}

	public static void spawnItemFX(double sX, double sY, double sZ, double dX, double dY, double dZ)
	{
		double mX = dX - sX;
		double mY = dY - sY;
		double mZ = dZ - sZ;
		String dataString = new String();
		dataString += "||mod:EC3.Particle.Item";
		dataString += "||x:"+sX+"||y:"+sY+"||z:"+sZ;
		dataString += "||mX:"+mX+"||mY:"+mY+"||mZ:"+mZ;
		DummyPacketIMSG pkt = new DummyPacketIMSG(dataString);
		DummyPacketHandler.sendToAll(pkt);
	}

	protected static void actionsTick() {
		if(!ACTION_LIST.isEmpty())
			for(int i = 0; i < ACTION_LIST.size(); ++i) {
				ScheduledServerAction ssa = ACTION_LIST.get(i);
				--ssa.actionTime;
				if(ssa.actionTime <= 0) {
					ssa.execute();
					ACTION_LIST.remove(i);
				}
			}
	}

	public static void addScheduledAction(ScheduledServerAction ssa) {
		if(FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
			Notifier.notifyCustomMod("EssentialCraft", "[WARNING][SEVERE]Trying to add a scheduled server action not on server side, aborting!");

		ACTION_LIST.add(ssa);
	}

	public static void requestScheduledTileSync(TileEntity tile, EntityPlayer requester) {
		Side s = FMLCommonHandler.instance().getEffectiveSide();
		if(s == Side.CLIENT) {
			if(tile.getWorld() == null || tile.getWorld().provider == null)
				return;

			NBTTagCompound clientData = new NBTTagCompound();
			clientData.setString("playername", MiscUtils.getUUIDFromPlayer(requester).toString());
			clientData.setInteger("x", tile.getPos().getX());
			clientData.setInteger("y", tile.getPos().getY());
			clientData.setInteger("z", tile.getPos().getZ());
			clientData.setInteger("dim", tile.getWorld().provider.getDimension());
			PacketNBT packet = new PacketNBT(clientData).setID(7);
			EssentialCraftCore.network.sendToServer(packet);
		}
		else {
			addScheduledAction(new ServerToClientSyncAction(requester, tile));
		}
	}

	public static void balanceIn(TileEntity tile, int slotNum) {
		if(tile instanceof IInventory && tile instanceof IMRUHandler) {
			IInventory inv = (IInventory) tile;
			IMRUHandler mrut = (IMRUHandler) tile;
			if(inv.getStackInSlot(slotNum).getItem() instanceof ItemBoundGem && inv.getStackInSlot(slotNum).getTagCompound() != null) {
				ItemStack s = inv.getStackInSlot(slotNum);
				int[] o = ItemBoundGem.getCoords(s);
				if(MathUtils.getDifference(tile.getPos().getX(), o[0]) <= 16 && MathUtils.getDifference(tile.getPos().getY(), o[1]) <= 16 && MathUtils.getDifference(tile.getPos().getZ(), o[2]) <= 16) {
					BlockPos o1 = new BlockPos(o[0],o[1],o[2]);
					if(tile.getWorld().getTileEntity(o1) != null && tile.getWorld().getTileEntity(o1) instanceof IMRUHandler) {
						mrut.setBalance(((IMRUHandler) tile.getWorld().getTileEntity(o1)).getBalance());
					}
				}
			}
		}
	}

	public static void manage(TileEntity tile, int slotNum) {
		mruIn(tile, 0);
		spawnMRUParticles(tile, 0);
		balanceIn(tile, 0);
	}

	public static GameProfile EC3FakePlayerProfile = new GameProfile(UUID.fromString("5cd89d0b-e9ba-0000-89f4-b5dbb05963da"), "[EC3]");
}
