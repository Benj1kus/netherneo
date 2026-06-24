package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.TotemusPuzzleEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class TotemusPuzzleModel extends GeoModel<TotemusPuzzleEntity> {

    @Override
    public ResourceLocation getModelResource(TotemusPuzzleEntity animatable) {
        return  NetherExp.location("geo/totemus_puzzle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(TotemusPuzzleEntity animatable) {
        return  NetherExp.location("textures/entity/totemus_puzzle_red.png");
    }

    @Override
    public ResourceLocation getAnimationResource(TotemusPuzzleEntity animatable) {
        return  NetherExp.location("animations/totemus_puzzle.animation.json");
    }
}
