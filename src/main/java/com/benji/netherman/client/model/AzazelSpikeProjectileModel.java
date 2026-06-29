package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelSpikesProjectileEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelSpikeProjectileModel extends GeoModel<AzazelSpikesProjectileEntity> {
    @Override
    public ResourceLocation getModelResource(AzazelSpikesProjectileEntity animatable) {
        return NetherExp.location( "geo/azazel_spikes_projectile.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AzazelSpikesProjectileEntity animatable) {
        return NetherExp.location( "textures/entity/azazel_spikes_projectile.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AzazelSpikesProjectileEntity animatable) {
        return NetherExp.location( "animations/empty.animation.json");
    }
}