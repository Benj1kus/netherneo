package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelSpikeEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelSpikeModel extends GeoModel<AzazelSpikeEntity> {
    @Override
    public ResourceLocation getModelResource(AzazelSpikeEntity animatable) {
        return NetherExp.location("geo/azazel_spikes.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AzazelSpikeEntity animatable) {
        return NetherExp.location("textures/entity/azazel_spikes.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AzazelSpikeEntity animatable) {
        return NetherExp.location("animations/azazel_spikes.animation.json");
    }
}