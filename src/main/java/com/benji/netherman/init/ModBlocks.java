package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.block.*;
import com.benji.netherman.common.item.AzazelTrophyItem;
import com.benji.netherman.common.item.GeoBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NetherExp.MODID);

    public static final DeferredSoundType SAMSONIT_SOUNDS = new DeferredSoundType(
            1.0F, 1.0F,
            ModSounds.SAMSONIT_BREAK,
            ModSounds.SAMSONIT_STEP,
            ModSounds.SAMSONIT_PLACE,
            ModSounds.SAMSONIT_HIT,
            ModSounds.SAMSONIT_STEP
    );

    public static final DeferredSoundType SAMSONIT_BRICKS_SOUNDS = new DeferredSoundType(
            1.0F, 1.0F,
            ModSounds.SAMSONIT_BREAK,
            ModSounds.SAMSONIT_BRICKS_STEP,
            ModSounds.SAMSONIT_BRICKS_PLACE,
            ModSounds.SAMSONIT_HIT,
            ModSounds.SAMSONIT_BRICKS_STEP
    );

    public static final DeferredBlock<Block> ANGEL_SPAWNER = BLOCKS.register("angel_spawner",
            () -> new PaintingSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.ANGEL_PAINTING));

    public static final DeferredBlock<Block> FACE_SPAWNER = BLOCKS.register("face_spawner",
            () -> new PaintingSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.FACE_PAINTING));

    public static final DeferredBlock<Block> VILLAGE_SPAWNER = BLOCKS.register("village_spawner",
            () -> new PaintingSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.VILLAGE_PAINTING));

    public static final DeferredBlock<Block> KING_SPAWNER = BLOCKS.register("king_spawner",
            () -> new PaintingSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.KING_PAINTING));

    public static final DeferredBlock<Block> THRONE_SPAWNER = BLOCKS.register("throne_spawner",
            () -> new PaintingSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRUCTURE_VOID).noCollission().noLootTable(), ModPaintings.THRONE_PAINTING));


    public static final DeferredBlock<Block> A_PUZZLE = register("a_puzzle", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> Z_PUZZLE = register("z_puzzle", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> E_PUZZLE = register("e_puzzle", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> L_PUZZLE = register("l_puzzle", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT = register("samsonit", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SAMSONIT = register("cobbled_samsonit", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_BRICKS = register("samsonit_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> POLISHED_SAMSONIT = register("polished_samsonit", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_TILES = register("samsonit_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> CHISELED_SAMSONIT = register("chiseled_samsonit", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final  DeferredBlock<Block> LABYRINTH_TELEPORT = register("labyrinth_teleport", () -> new LabyrinthTeleportBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(20.0F)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SAMSONIT_SLAB = register("cobbled_samsonit_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> POLISHED_SAMSONIT_SLAB = register("polished_samsonit_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_BRICKS_SLAB = register("samsonit_bricks_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SAMSONIT_STAIRS = register("cobbled_samsonit_stairs", () -> new StairBlock(COBBLED_SAMSONIT.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> POLISHED_SAMSONIT_STAIRS = register("polished_samsonit_stairs", () -> new StairBlock(POLISHED_SAMSONIT.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_BRICKS_STAIRS = register("samsonit_bricks_stairs", () -> new StairBlock(SAMSONIT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> COBBLED_SAMSONIT_WALL = register("cobbled_samsonit_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> POLISHED_SAMSONIT_WALL = register("polished_samsonit_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_BRICKS_WALL = register("samsonit_bricks_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> NETHER_SPAWNER = register("nether_spawner", () -> new NetherSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> BLACKSTONE_COLUMN = register("blackstone_column", () -> new BlackstoneColumnBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .strength(2.0F)
                    .requiresCorrectToolForDrops()), new Item.Properties());

    public static final DeferredBlock<Block> POINTED_BLACKSTONE = register("pointed_blackstone", () -> new PointedBlackstoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.0F)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> LOCKER_NETHER = register("locker_nether", () -> new LockerNetherBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .strength(2.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_EYE = register("samsonit_eye", () -> new SamsonitEyeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_BELL = register("samsonit_bell", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONIT_KEY = register("samsonit_key", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)), new Item.Properties());

    public static final DeferredBlock<Block> BLACKSTONE_PLANT = register("blackstone_plant", () -> new BlackstonePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)
                    .lightLevel(state -> 10)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> BLACKSTONE_AXON = register("blackstone_axon", () -> new BlackstoneAxonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)
                    .lightLevel(state -> 10)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> POTENT_MAGMA = register("potent_magma", () -> new PotentMagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .strength(2.0F)
                    .lightLevel(state -> 10)
                    .requiresCorrectToolForDrops()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDMID = register("void_mid", () -> new VoidMidBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDCORNER = register("void_corner", () -> new VoidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDMIDCORNER = register("void_midcorner", () -> new VoidMidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDMID_CAVE = register("void_cave_mid", () -> new VoidCaveMidBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 10)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDCORNER_CAVE = register("void_cave_corner", () -> new VoidCaveCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 10)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDMIDCORNER_CAVE = register("void_cave_midcorner", () -> new VoidCaveMidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 10)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDMIDNETHER = register("voidnether_mid", () -> new VoidNetherMidBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDCORNERNETHER = register("voidnether_corner", () -> new VoidNetherCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> VOIDMIDCORNERNETHER = register("voidnether_midcorner", () -> new VoidNetherMidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> ENTRANCE = register("entrance", () -> new EntranceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> CRIMSON_WEB = registerSpecialItem("crimson_web", () -> new CrimsonWebBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK)
                    .instabreak()
                    .noOcclusion()),
            (web) -> () -> new GeoBlockItem(
                    web.get(),
                    new Item.Properties(),
                    NetherExp.location("geo/crimson_web.geo.json"),
                    NetherExp.location("textures/block/crimson_web.png"),
                    NetherExp.location("animations/crimson_web.animation.json"),
                    NetherExp.location("textures/block/blackstone_column_emissive.png")
            ));

    public static final DeferredBlock<Block> TRAPHIVE = registerSpecialItem("traphive", () -> new TraphiveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK)
                    .instabreak()
                    .noOcclusion()),
            (hive) -> () -> new GeoBlockItem(
                    hive.get(),
                    new Item.Properties(),
                    NetherExp.location("geo/traphive.geo.json"),
                    NetherExp.location("textures/block/traphive.png"),
                    NetherExp.location("animations/traphive.animation.json"),
                    NetherExp.location("textures/block/blackstone_column_emissive.png")
            ));

    public static final DeferredBlock<Block> STATUE_STAND = registerSpecialItem("statue_stand", () -> new StatueStandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS)
                    .strength(5.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()),
            (stand) -> () -> new DoubleHighBlockItem(stand.get(), new Item.Properties()));

    public static final DeferredBlock<Block> TOTEMUS = register("totemus", () -> new TotemusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS)
                    .strength(5.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> EYE = registerSpecialItem("eye_block", () -> new EyeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HONEY_BLOCK)
                    .strength(5.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()),
            (eye) -> () -> new GeoBlockItem(
                    eye.get(),
                    new Item.Properties(),
                    NetherExp.location("geo/eye_block.geo.json"),
                    NetherExp.location("textures/block/eye_block.png"),
                    NetherExp.location("animations/eye_block.animation.json"),
                    NetherExp.location("textures/block/eye_block_emissive.png")
            ));

    public static final DeferredBlock<Block> ALTAR = registerSpecialItem("altar", () -> new AltarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
                    .lightLevel(state -> state.getValue(AltarBlock.LIT) ? 15 : 0)
                    .strength(10.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()),
            (altar) -> () -> new GeoBlockItem(
                    altar.get(),
                    new Item.Properties(),
                    NetherExp.location("geo/altar.geo.json"),
                    NetherExp.location("textures/block/altar.png"),
                    NetherExp.location("animations/altar.animation.json"),
                    NetherExp.location("textures/block/altar_emissive.png")
            ));

    public static final DeferredBlock<Block> MOSAIC_CHURCH = register("mosaic_church", () -> new MosaicChurchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .lightLevel(state -> 10)
                    .instabreak()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> GRAND_DOOR = registerSpecialItem("grand_door", () -> new GrandDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()),
            (door) -> () -> new GeoBlockItem(
                    door.get(),
                    new Item.Properties(),
                    NetherExp.location("geo/grand_door.geo.json"),
                    NetherExp.location("textures/block/grand_door.png"),
                    NetherExp.location("animations/grand_door.animation.json"),
                    NetherExp.location("textures/block/grand_door_emissive.png")
            ));

    public static final DeferredBlock<Block> GRAND_DOOR_PART = registerNoItem("grand_door_part", () -> new GrandDoorPartBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()
                    .noLootTable()));

    public static final DeferredBlock<Block> CRIMSON_HONEY_BLOCK = register("crimson_honey_block", () -> new CrimsonHoneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HONEY_BLOCK)
                    .instabreak()
                    .lightLevel(state -> 5)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> AZAZEL_TROPHY = registerSpecialItem("azazel_trophy", () -> new AzazelTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
            .noOcclusion()), (trophy) -> () -> new AzazelTrophyItem(trophy.get(), new Item.Properties()));

    public static final DeferredBlock<Block> AZAZEL_TROPHY_STAGE2 = registerSpecialItem("azazel_trophy_stage2", () -> new AzazelTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
            .noOcclusion()), (trophy) -> () -> new AzazelTrophyItem(trophy.get(), new Item.Properties()));

    public static final DeferredBlock<Block> AZAZEL_TROPHY_STAGE3 = registerSpecialItem("azazel_trophy_stage3", () -> new AzazelTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
            .noOcclusion()), (trophy) -> () -> new AzazelTrophyItem(trophy.get(), new Item.Properties()));

    public static final DeferredBlock<Block> GHASTLY_NEST = register("ghastly_nest", () -> new GhastlyNestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE)
                    .strength(2.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> FACE_PUZZLE_RIGHT_DOWN = register("face_puzzle_right_down", () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .strength(2.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion(),
            2, ModBlockEntities.FACE_PUZZLE_RIGHT_DOWN), new Item.Properties());

    public static final DeferredBlock<Block> FACE_PUZZLE_LEFT_UP = register("face_puzzle_left_up", () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .strength(2.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion(),
            2, ModBlockEntities.FACE_PUZZLE_LEFT_UP), new Item.Properties());

    public static final DeferredBlock<Block> FACE_PUZZLE_RIGHT_UP = register("face_puzzle_right_up", () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .strength(2.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion(),
            3, ModBlockEntities.FACE_PUZZLE_RIGHT_UP), new Item.Properties());

    public static final DeferredBlock<Block> FACE_PUZZLE_LEFT_DOWN = register("face_puzzle_left_down", () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
            .strength(2.0F)
            .requiresCorrectToolForDrops()
            .noOcclusion(),
            3, ModBlockEntities.FACE_PUZZLE_LEFT_DOWN), new Item.Properties());

    public static final DeferredBlock<Block> SAMSONITE_BELL = register(
            "samsonite_bell",
            () -> new SamsoniteBellBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.BELL)
                            .strength(5.0F)
                            .requiresCorrectToolForDrops()
                            .noOcclusion()
            ),
            new Item.Properties()
    );

    public static final DeferredBlock<Block> LABYRINTH_BELLSPAWN = register("labyrinth_bellspawn",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(20.0F)
                    .noOcclusion()), new Item.Properties());

    public static final DeferredBlock<Block> MAZE_DOOR = registerSpecialItem("maze_door", () -> new MazeDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()),
            (door) -> () -> new GeoBlockItem(
                    door.get(),
                    new Item.Properties(),
                    NetherExp.location("geo/maze_door.geo.json"),
                    NetherExp.location("textures/block/maze_door.png"),
                    NetherExp.location("animations/maze_door.animation.json"),
                    NetherExp.location("textures/block/blackstone_column_emissive.png")
            ));

    public static final DeferredBlock<Block> TOTEMUS_HOLE = register("totemus_hole", () -> new TotemusHoleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F)
                    .noOcclusion()), new Item.Properties());

    private static <T extends Block> DeferredBlock<T> register(String id, Supplier<T> block, Item.Properties pIProp) {
        DeferredBlock<T> toReturn = BLOCKS.register(id.toLowerCase(), block);
        makeBlockItem(toReturn, pIProp);
        return toReturn;
    }
    private static <T extends Block> DeferredBlock<T> registerSpecialItem(String id, Supplier<T> block, Function<DeferredBlock<T>, Supplier<? extends BlockItem>> sup) {
        DeferredBlock<T> toReturn = BLOCKS.register(id.toLowerCase(), block);
        makeSpecialBlockItem(toReturn, sup.apply(toReturn));
        return toReturn;
    }
    private static <T extends Block> DeferredBlock<T> registerNoItem(String id, Supplier<T> block) {
        return BLOCKS.register(id.toLowerCase(), block);
    }
    private static <T extends Block> void makeBlockItem(DeferredBlock<T> block, Item.Properties pIProp) {
        ModItems.registerBlockItem(block, pIProp);
    }
    private static <T extends Block> void makeSpecialBlockItem(DeferredBlock<T> block, Supplier<? extends BlockItem> sup) {
        ModItems.registerSpecialBlockItem(block, sup);
    }

    public static void init(IEventBus bus) {
        BLOCKS.register(bus);
    }

    public static Collection<DeferredHolder<Block, ? extends Block>> getBlocks() {
        return BLOCKS.getEntries();
    }
}
