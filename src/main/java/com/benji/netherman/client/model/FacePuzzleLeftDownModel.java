package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FacePuzzleLeftDownModel extends GeoModel<FacePuzzleBlockEntity> {
    @Override
    public ResourceLocation getModelResource(FacePuzzleBlockEntity animatable) {
        return  NetherExp.location("geo/face_puzzle_left_down.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FacePuzzleBlockEntity animatable) {
        return  NetherExp.location("textures/block/face_puzzle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FacePuzzleBlockEntity animatable) {
        return  NetherExp.location("animations/left_down.animation.json");
    }
}
