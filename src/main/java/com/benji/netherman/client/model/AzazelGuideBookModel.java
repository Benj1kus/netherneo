package com.benji.netherman.client.model;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelGuideBookEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AzazelGuideBookModel extends GeoModel<AzazelGuideBookEntity> {
    @Override
    public ResourceLocation getModelResource(AzazelGuideBookEntity animatable) {
        return NetherExp.location("geo/azazel_guide_book.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AzazelGuideBookEntity animatable) {
        return NetherExp.location("textures/entity/azazel_guide_book_entity.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AzazelGuideBookEntity animatable) {
        return NetherExp.location("animations/azazel_guide_book.animation.json");
    }
}
