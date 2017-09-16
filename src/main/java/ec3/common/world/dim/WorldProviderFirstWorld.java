package ec3.common.world.dim;

import ec3.common.mod.EssentialCraftCore;
import ec3.common.registry.DimensionRegistry;
import ec3.utils.cfg.Config;
import ec3.utils.common.ECUtils;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderFirstWorld extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.firstWorld;
	}

	@Override
	protected void createBiomeProvider()
	{
		this.biomeProvider = new WorldChunkManager_FirstWorld(this.world.getWorldInfo());
		this.doesWaterVaporize = false;
		this.hasNoSky = false;
		this.setDimension(Config.dimensionID);
	}

	@Override
	public void generateLightBrightnessTable()
	{
		float f = 0.0F;

		for (int i = 0; i <= 15; ++i)
		{
			float f1;
			if(!ECUtils.isEventActive("ec3.event.darkness"))
				f1 = 1.0F - i / 15.0F;
			else
				f1 = 1.9F - i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getSkyRenderer()
	{
		return (IRenderHandler)EssentialCraftCore.proxy.getRenderer(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IRenderHandler getCloudRenderer()
	{
		return (IRenderHandler)EssentialCraftCore.proxy.getRenderer(1);
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return true;
	}

	@Override
	public IChunkGenerator createChunkGenerator()
	{
		return new ChunkProviderFirstWorld(this.world, this.world.getSeed(), true, this.world.getWorldInfo().getGeneratorOptions());
	}
}
