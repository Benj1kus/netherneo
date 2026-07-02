package com.benji.netherman.common.block.entity;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.PaintingSpawnerBlock;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.fml.common.Mod;

public class PaintingSpawnerBlockEntity extends BlockEntity {
    private int waitTicks = 0;

    public PaintingSpawnerBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.PAINTING_SPAWNER.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, PaintingSpawnerBlockEntity entity) {
        if (level.isClientSide()) return;

        entity.waitTicks++;
        if (entity.waitTicks < 10) return;


        Player player = level.getNearestPlayer(pos.getX(), pos.getY(), pos.getZ(), 200, false);
        if (player != null && player.isCreative()) {
            entity.waitTicks = 0;
            return;
        }

        if (state.getBlock() instanceof PaintingSpawnerBlock spawnerBlock) {
            Direction facing = state.getValue(PaintingSpawnerBlock.FACING);

            var registry = level.registryAccess().registryOrThrow(net.minecraft.core.registries.Registries.PAINTING_VARIANT);
            var holderOptional = registry.getHolder(spawnerBlock.getPaintingKey());

            if (holderOptional.isPresent()) {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                Painting painting = new Painting(level, pos, facing, holderOptional.get());

                if (painting.survives()) {
                    level.addFreshEntity(painting);
                } else {
                    level.setBlock(pos, state, 3);
                    entity.waitTicks = 0;
                }
            } else {
                level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }
}