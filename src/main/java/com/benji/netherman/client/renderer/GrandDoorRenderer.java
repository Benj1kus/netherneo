package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.GrandDoorModel;
import com.benji.netherman.block.entity.GrandDoorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class GrandDoorRenderer extends GeoBlockRenderer<GrandDoorBlockEntity> {
    public GrandDoorRenderer(BlockEntityRendererProvider.Context context) {
        super(new GrandDoorModel());

        
        addRenderLayer(new GenericEmissiveLayer<>(this, ResourceLocation.fromNamespaceAndPath(NetherExp.MODID, "textures/block/grand_door_emissive.png")));
    }

    @Override
    public AABB getRenderBoundingBox(GrandDoorBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(3, 12, 3);
    }
}
