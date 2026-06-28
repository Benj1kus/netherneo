package com.benji.netherman.common.block.entity;

import com.benji.netherman.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class VoidNetherMidCornerBlockEntity extends BlockEntity implements GeoBlockEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public VoidNetherMidCornerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOIDMIDCORNERNETHER.get(), pos, state);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {}


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}
