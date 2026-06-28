package com.benji.netherman.common.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GeoBlockItem extends BlockItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public final ResourceLocation modelLocation;
    public final ResourceLocation textureLocation;
    public final ResourceLocation animationLocation;
    public final ResourceLocation emissiveTextureLocation;

    public GeoBlockItem(Block block, Properties properties, ResourceLocation modelLocation, ResourceLocation textureLocation, ResourceLocation animationLocation, ResourceLocation emissiveTextureLocation) {
        super(block, properties);
        this.modelLocation = modelLocation;
        this.textureLocation = textureLocation;
        this.animationLocation = animationLocation;
        this.emissiveTextureLocation = emissiveTextureLocation;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
