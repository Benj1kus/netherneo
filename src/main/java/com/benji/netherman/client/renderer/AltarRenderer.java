package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.layer.GenericEmissiveLayer;
import com.benji.netherman.client.model.AltarModel;
import com.benji.netherman.common.block.entity.AltarBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class AltarRenderer extends GeoBlockRenderer<AltarBlockEntity> {
    public AltarRenderer(BlockEntityRendererProvider.Context context) {
        super(new AltarModel());
        ResourceLocation emissiveTexture = NetherExp.location("textures/block/altar_emissive.png");
        addRenderLayer(new GenericEmissiveLayer<>(this, emissiveTexture));
    }

    @Override
    public AABB getRenderBoundingBox(AltarBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(2);
    }
}
