package com.benji.netherman.client.renderer;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.model.MazeDoorModel;
import com.benji.netherman.common.block.entity.MazeDoorBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoBlockRenderer;

public class MazeDoorRenderer extends GeoBlockRenderer<MazeDoorBlockEntity> {

    public MazeDoorRenderer(BlockEntityRendererProvider.Context context) {
        super(new MazeDoorModel());
    }
    @Override
    public ResourceLocation getTextureLocation(MazeDoorBlockEntity animatable) {
        if (animatable.requiresKey) {
            return  NetherExp.location("textures/block/maze_door_key.png");
        }
        return  NetherExp.location("textures/block/maze_door.png");
    }
    @Override
    public net.minecraft.world.phys.AABB getRenderBoundingBox(MazeDoorBlockEntity animatable) {
        return new net.minecraft.world.phys.AABB(animatable.getBlockPos()).inflate(6.0D);
    }
}
