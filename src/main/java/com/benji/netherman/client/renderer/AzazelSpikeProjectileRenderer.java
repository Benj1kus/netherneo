package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.AzazelSpikeProjectileModel;
import com.benji.netherman.common.entity.AzazelSpikesProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class AzazelSpikeProjectileRenderer extends GeoEntityRenderer<AzazelSpikesProjectileEntity> {
    public AzazelSpikeProjectileRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new AzazelSpikeProjectileModel());
        this.shadowRadius = 0.0f;
        ResourceLocation emissiveTexture = NetherExp.location( "textures/entity/azazel_spikes_projectile_emissive.png");
        addRenderLayer(new GenericEmissiveLayer<>(this, emissiveTexture));
    }

    @Override
    protected int getBlockLightLevel(AzazelSpikesProjectileEntity entity, BlockPos pos) {
        return 15;
    }
}