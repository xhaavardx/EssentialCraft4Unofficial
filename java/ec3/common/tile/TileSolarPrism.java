package ec3.common.tile;

import DummyCore.Utils.DataStorage;
import DummyCore.Utils.DummyData;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Configuration;
import ec3.common.entity.EntitySolarBeam;

public class TileSolarPrism extends TileEntity implements ITickable {
	
	public static float solarBeamChance = 0.025F;
	public static boolean requiresUnabstructedSky = true;
	public static boolean requiresMidday = true;
	public static boolean ignoreRain = false;
	
	@Override
	public void update() {
		if(!worldObj.isRemote) {
			if(worldObj.rand.nextFloat() <= solarBeamChance && (worldObj.canBlockSeeSky(pos) || !requiresUnabstructedSky) && ((worldObj.getWorldTime() % 24000 >= 5000 && worldObj.getWorldTime() % 24000 <= 7000) || !requiresMidday) && (!worldObj.isRaining() || ignoreRain)) {
				int y = pos.getY()-1;
				BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(pos.down());
				while(y > 0 && worldObj.isAirBlock(p)) {
					--y;
					p.setY(y);
					if(!worldObj.isAirBlock(p)) {
						EntitySolarBeam beam = new EntitySolarBeam(worldObj,pos.getX(),y,pos.getZ());
						worldObj.spawnEntityInWorld(beam);
					}
				}
			}
			if(worldObj.isAirBlock(pos.east(2)) || worldObj.isAirBlock(pos.west(2)) || worldObj.isAirBlock(pos.north(2)) || worldObj.isAirBlock(pos.south(2))) {
				worldObj.getBlockState(pos).getBlock().dropBlockAsItem(worldObj, pos, worldObj.getBlockState(pos), 0);
				worldObj.setBlockToAir(pos);
			}
			if(!worldObj.isAirBlock(pos.east()) || !worldObj.isAirBlock(pos.west()) || !worldObj.isAirBlock(pos.north()) || !worldObj.isAirBlock(pos.south())) {
				worldObj.getBlockState(pos).getBlock().dropBlockAsItem(worldObj, pos, worldObj.getBlockState(pos), 0);
				worldObj.setBlockToAir(pos);
			}
			if(!worldObj.isAirBlock(pos.add(1, 0, 1)) || !worldObj.isAirBlock(pos.add(1, 0, -1)) || !worldObj.isAirBlock(pos.add(-1, 0, -1)) || !worldObj.isAirBlock(pos.add(-1, 0, 1))) {
				worldObj.getBlockState(pos).getBlock().dropBlockAsItem(worldObj, pos, worldObj.getBlockState(pos), 0);
				worldObj.setBlockToAir(pos);
			}
		}
	}
	
	public static void setupConfig(Configuration cfg) {
		try {
			cfg.load();
			String[] cfgArrayString = cfg.getStringList("SolarPrismSettings", "tileentities", new String[] {
					"Chance each tick to create a solar beam:0.025",
					"Requires unobstructed sky:true",
					"Requires midday:true",
					"Is rain ignored:false"
			}, "");
			String dataString = "";
			
			for(int i = 0; i < cfgArrayString.length; ++i)
				dataString += "||" + cfgArrayString[i];
			
			DummyData[] data = DataStorage.parseData(dataString);
			
			solarBeamChance = Float.parseFloat(data[0].fieldValue);
			requiresUnabstructedSky = Boolean.parseBoolean(data[1].fieldValue);
			requiresMidday = Boolean.parseBoolean(data[2].fieldValue);
			ignoreRain = Boolean.parseBoolean(data[3].fieldValue);
			
			cfg.save();
		}
		catch(Exception e) {
			return;
		}
	}
}
