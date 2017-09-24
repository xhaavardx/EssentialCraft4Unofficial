package essentialcraft.common.world.dim;

import essentialcraft.common.mod.EssentialCraftCore;
import essentialcraft.common.registry.DimensionRegistry;
import essentialcraft.utils.cfg.Config;
import essentialcraft.utils.common.ECUtils;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderHoanna extends WorldProvider {

	@Override
	public DimensionType getDimensionType() {
		return DimensionRegistry.hoanna;
	}

	@Override
	protected void init()
	{
		this.biomeProvider = new BiomeProviderHoanna(this.world.getWorldInfo());
		this.doesWaterVaporize = false;
		this.nether = false;
		this.setDimension(Config.dimensionID);
	}

	@Override
	public void generateLightBrightnessTable()
	{
		float f = 0.0F;

		for (int i = 0; i <= 15; ++i)
		{
			float f1;
			if(!ECUtils.isEventActive("essentialcraft.event.darkness"))
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
		return new ChunkGeneratorHoanna(this.world, this.world.getSeed(), true, this.world.getWorldInfo().getGeneratorOptions());
	}
}
