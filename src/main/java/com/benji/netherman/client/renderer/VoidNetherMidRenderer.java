package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.VoidNetherMidModel;
import com.benji.netherman.common.block.entity.VoidNetherMidBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VoidNetherMidRenderer extends GeoBlockRenderer<VoidNetherMidBlockEntity> {
    public VoidNetherMidRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoidNetherMidModel());
        
        addRenderLayer(new GenericEmissiveLayer<>(this, NetherExp.location("textures/block/void_nether_emissive.png")));
    }

    @Override
    public AABB getRenderBoundingBox(VoidNetherMidBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(6);
    }
}
