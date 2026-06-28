package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NetherExp.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GhastlyNestBlockEntity>> GHASTLY_NEST = BLOCK_ENTITIES.register("ghastly_nest",
            () -> BlockEntityType.Builder.of(GhastlyNestBlockEntity::new, ModBlocks.GHASTLY_NEST.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherMidCornerBlockEntity>> VOIDMIDCORNERNETHER = BLOCK_ENTITIES.register("voidnether_midcorner",
            () -> BlockEntityType.Builder.of(VoidNetherMidCornerBlockEntity::new, ModBlocks.VOIDMIDCORNERNETHER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherCornerBlockEntity>> VOIDCORNERNETHER = BLOCK_ENTITIES.register("voidnether_corner",
            () -> BlockEntityType.Builder.of(VoidNetherCornerBlockEntity::new, ModBlocks.VOIDCORNERNETHER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherMidBlockEntity>> VOIDMIDNETHER = BLOCK_ENTITIES.register("voidnether_mid",
            () -> BlockEntityType.Builder.of(VoidNetherMidBlockEntity::new, ModBlocks.VOIDMIDNETHER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EyeBlockEntity>> EYE = BLOCK_ENTITIES.register("eye_block",
            () -> BlockEntityType.Builder.of(EyeBlockEntity::new, ModBlocks.EYE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LabyrinthTeleportBlockEntity>> LABYRINTH_TELEPORT = BLOCK_ENTITIES.register("labyrinth_teleport",
            () -> BlockEntityType.Builder.of(LabyrinthTeleportBlockEntity::new, ModBlocks.LABYRINTH_TELEPORT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR = BLOCK_ENTITIES.register("altar",
            () -> BlockEntityType.Builder.of(AltarBlockEntity::new, ModBlocks.ALTAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemusBlockEntity>> TOTEMUS = BLOCK_ENTITIES.register("totemus",
            () -> BlockEntityType.Builder.of(TotemusBlockEntity::new, ModBlocks.TOTEMUS.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TraphiveBlockEntity>> TRAPHIVE = BLOCK_ENTITIES.register("traphive",
            () -> BlockEntityType.Builder.of(TraphiveBlockEntity::new, ModBlocks.TRAPHIVE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrandDoorBlockEntity>> GRAND_DOOR = BLOCK_ENTITIES.register("grand_door",
            () -> BlockEntityType.Builder.of(GrandDoorBlockEntity::new, ModBlocks.GRAND_DOOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NetherSpawnerBlockEntity>> NETHER_SPAWNER = BLOCK_ENTITIES.register("nether_spawner",
            () -> BlockEntityType.Builder.of(NetherSpawnerBlockEntity::new, ModBlocks.NETHER_SPAWNER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PointedBlackstoneBlockEntity>> POINTED_BLACKSTONE = BLOCK_ENTITIES.register("pointed_blackstone",
            () -> BlockEntityType.Builder.of(PointedBlackstoneBlockEntity::new, ModBlocks.POINTED_BLACKSTONE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_RIGHT_DOWN = BLOCK_ENTITIES.register("face_puzzle_right_down",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(ModBlockEntities.FACE_PUZZLE_RIGHT_DOWN.get(), pos, state), ModBlocks.FACE_PUZZLE_RIGHT_DOWN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_LEFT_UP = BLOCK_ENTITIES.register("face_puzzle_left_up",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(ModBlockEntities.FACE_PUZZLE_LEFT_UP.get(), pos, state), ModBlocks.FACE_PUZZLE_LEFT_UP.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_RIGHT_UP = BLOCK_ENTITIES.register("face_puzzle_right_up",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(ModBlockEntities.FACE_PUZZLE_RIGHT_UP.get(), pos, state), ModBlocks.FACE_PUZZLE_RIGHT_UP.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_LEFT_DOWN = BLOCK_ENTITIES.register("face_puzzle_left_down",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(ModBlockEntities.FACE_PUZZLE_LEFT_DOWN.get(), pos, state), ModBlocks.FACE_PUZZLE_LEFT_DOWN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MazeDoorBlockEntity>> MAZE_DOOR = BLOCK_ENTITIES.register("maze_door",
            () -> BlockEntityType.Builder.of(MazeDoorBlockEntity::new, ModBlocks.MAZE_DOOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemusHoleBlockEntity>> TOTEMUS_HOLE = BLOCK_ENTITIES.register("totemus_hole",
            () -> BlockEntityType.Builder.of(TotemusHoleBlockEntity::new, ModBlocks.TOTEMUS_HOLE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SamsoniteBellBlockEntity>> SAMSONITE_BELL = BLOCK_ENTITIES.register("samsonite_bell",
            () -> BlockEntityType.Builder.of(SamsoniteBellBlockEntity::new, ModBlocks.SAMSONITE_BELL.get()).build(null));

    public static void init(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
