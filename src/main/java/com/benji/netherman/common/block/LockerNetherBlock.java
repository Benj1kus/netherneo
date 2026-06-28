package com.benji.netherman.common.block;

import com.benji.netherman.common.network.TotemAnimationPayload;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashSet;
import java.util.Set;

public class LockerNetherBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<VoidMidBlock> CODEC = simpleCodec(VoidMidBlock::new);

    private static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public LockerNetherBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    
    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide()) {

            
            
            int randomStage = level.random.nextInt(3) + 1; 

            Item animationItem = ModItems.QUEST_ICON_1.get();
            if (randomStage == 2) animationItem = ModItems.QUEST_ICON_2.get();
            if (randomStage == 3) animationItem = ModItems.QUEST_ICON_3.get();

            
            level.playSound(null, pos, SoundEvents.BEACON_ACTIVATE, SoundSource.MASTER, 2.0F, 0.5F);

            
            if (player instanceof net.minecraft.server.level.ServerPlayer serverPlayer) {
                net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(
                        serverPlayer,
                        new TotemAnimationPayload(new ItemStack(animationItem))
                );
            }

            
            if (randomStage == 3) {
                player.getInventory().add(new ItemStack(ModItems.ALTAR_COMPASS_KEY.get()));
            }

            
            triggerChainReactionAndPillars((ServerLevel) level, pos);
        }
        return super.playerWillDestroy(level, pos, state, player); 
    }

    private void triggerChainReactionAndPillars(ServerLevel level, BlockPos startPos) {
        Set<BlockPos> lockersToDestroy = new HashSet<>();

        for (int x = -10; x <= 10; x++) {
            for (int y = -10; y <= 10; y++) {
                for (int z = -10; z <= 10; z++) {
                    BlockPos checkPos = startPos.offset(x, y, z);
                    if (level.getBlockState(checkPos).is(this)) {
                        lockersToDestroy.add(checkPos);
                    }
                }
            }
        }

        for (BlockPos lockerPos : lockersToDestroy) {
            level.setBlock(lockerPos, ModBlocks.BLACKSTONE_COLUMN.get().defaultBlockState(), 3);
            level.scheduleTick(lockerPos, ModBlocks.BLACKSTONE_COLUMN.get(), 2);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) { return RenderShape.MODEL; }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
