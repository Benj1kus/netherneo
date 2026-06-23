package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.block.entity.GrandDoorBlockEntity;
import com.benji.netherman.block.entity.VoidNetherCornerBlockEntity;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.VoidNetherCornerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class VoidNetherCornerRenderer extends GeoBlockRenderer<VoidNetherCornerBlockEntity> {
    public VoidNetherCornerRenderer(BlockEntityRendererProvider.Context context) {
        super(new VoidNetherCornerModel());
        
        addRenderLayer(new GenericEmissiveLayer<>(this, ResourceLocation.fromNamespaceAndPath(NetherExp.MODID, "textures/block/void_nether_emissive.png")));
    }

    @Override
    public AABB getRenderBoundingBox(VoidNetherCornerBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(6);
    }
}
