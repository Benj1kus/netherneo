package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.VoidNetherMidCornerModel;
import com.benji.netherman.common.block.entity.VoidNetherMidCornerBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VoidNetherMidCornerRenderer extends GeoBlockRenderer<VoidNetherMidCornerBlockEntity> {
    public VoidNetherMidCornerRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoidNetherMidCornerModel());
        
        addRenderLayer(new GenericEmissiveLayer<>(this, NetherExp.location("textures/block/void_nether_emissive.png")));
    }

    @Override
    public AABB getRenderBoundingBox(VoidNetherMidCornerBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(6);
    }
}
