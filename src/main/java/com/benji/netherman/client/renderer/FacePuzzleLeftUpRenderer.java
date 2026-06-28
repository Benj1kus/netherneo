package com.benji.netherman.client.renderer;

import com.benji.netherman.client.model.FacePuzzleLeftUpModel;
import com.benji.netherman.common.block.entity.FacePuzzleBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class FacePuzzleLeftUpRenderer extends GeoBlockRenderer<FacePuzzleBlockEntity> {
    public FacePuzzleLeftUpRenderer(BlockEntityRendererProvider.Context context) {
        super(new FacePuzzleLeftUpModel());
    }
}
