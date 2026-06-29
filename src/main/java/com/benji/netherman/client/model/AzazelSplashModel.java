package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelSplashEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelSplashModel extends GeoModel<AzazelSplashEntity> {
    @Override
    public ResourceLocation getModelResource(AzazelSplashEntity animatable) {
        return NetherExp.location( "geo/splash.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AzazelSplashEntity animatable) {
        return NetherExp.location( "textures/entity/splash.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AzazelSplashEntity animatable) {
        // У нас нет анимации, можно передать пустышку или null
        return NetherExp.location( "animations/empty.animation.json");
    }
}