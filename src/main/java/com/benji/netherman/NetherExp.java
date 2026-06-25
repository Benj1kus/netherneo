package com.benji.netherman;

import com.benji.netherman.block.*;
import com.benji.netherman.block.entity.*;
import com.benji.netherman.client.ManipulationOverlay;
import com.benji.netherman.client.renderer.*;
import com.benji.netherman.client.renderer.entity.GhastlyRenderer;
import com.benji.netherman.client.renderer.entity.GildedGolemRenderer;
import com.benji.netherman.client.renderer.entity.GuardianRenderer;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.effect.ManipulationEffect;
import com.benji.netherman.effect.ZoneEffect;
import com.benji.netherman.item.AzazelGuideBookItem;
import com.benji.netherman.item.AzazelTrophyItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.*;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import com.benji.netherman.network.TotemAnimationPayload;
import com.benji.netherman.network.ClientPayloadHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import com.benji.netherman.entity.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.common.NeoForge;
import com.benji.netherman.entity.BelieverEntity;
import com.benji.netherman.item.GeoBlockItem;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.benji.netherman.network.ModMessages;
import net.minecraft.core.registries.Registries;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.common.EventBusSubscriber;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredRegister;
import com.benji.netherman.client.renderer.entity.WelcomerRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;

@Mod(NetherExp.MODID)
public class NetherExp {
    public static final String MODID = "netherman";
    private static final Logger LOGGER = LogUtils.getLogger();

    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, MODID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);


    
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

    

    
    public static final DeferredBlock<Block> A_PUZZLE = BLOCKS.register("a_puzzle",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredBlock<Block> Z_PUZZLE = BLOCKS.register("z_puzzle",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredBlock<Block> E_PUZZLE = BLOCKS.register("e_puzzle",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredBlock<Block> L_PUZZLE = BLOCKS.register("l_puzzle",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> A_PUZZLE_ITEM = ITEMS.register("a_puzzle",
            () -> new BlockItem(A_PUZZLE.get(), new Item.Properties()));

    public static final DeferredItem<Item> Z_PUZZLE_ITEM = ITEMS.register("z_puzzle",
            () -> new BlockItem(Z_PUZZLE.get(), new Item.Properties()));

    public static final DeferredItem<Item> E_PUZZLE_ITEM = ITEMS.register("e_puzzle",
            () -> new BlockItem(E_PUZZLE.get(), new Item.Properties()));

    public static final DeferredItem<Item> L_PUZZLE_ITEM = ITEMS.register("l_puzzle",
            () -> new BlockItem(L_PUZZLE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> TOTEMUS_HOLE = BLOCKS.register("totemus_hole",
            () -> new TotemusHoleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE_BRICKS)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F)
                    .noOcclusion()));

    public static final DeferredItem<Item> TOTEMUS_HOLE_ITEM = ITEMS.register("totemus_hole",
            () -> new BlockItem(TOTEMUS_HOLE.get(), new Item.Properties()));

    //PUZZLE:
    public static final DeferredBlock<Block> FACE_PUZZLE_RIGHT_DOWN = BLOCKS.register("face_puzzle_right_down",
            () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(), 2, NetherExp.FACE_PUZZLE_RIGHT_DOWN_BE));

    public static final DeferredBlock<Block> FACE_PUZZLE_LEFT_UP = BLOCKS.register("face_puzzle_left_up",
            () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(), 2, NetherExp.FACE_PUZZLE_LEFT_UP_BE));

    public static final DeferredBlock<Block> FACE_PUZZLE_RIGHT_UP = BLOCKS.register("face_puzzle_right_up",
            () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(), 3, NetherExp.FACE_PUZZLE_RIGHT_UP_BE));

    public static final DeferredBlock<Block> FACE_PUZZLE_LEFT_DOWN = BLOCKS.register("face_puzzle_left_down",
            () -> new FacePuzzleBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE).strength(2.0F).requiresCorrectToolForDrops().noOcclusion(), 3, NetherExp.FACE_PUZZLE_LEFT_DOWN_BE));

    public static final  DeferredItem<Item> FACE_PUZZLE_RIGHT_DOWN_ITEM = ITEMS.register("face_puzzle_right_down", () -> new BlockItem(FACE_PUZZLE_RIGHT_DOWN.get(), new Item.Properties()));
    public static final  DeferredItem<Item> FACE_PUZZLE_LEFT_UP_ITEM = ITEMS.register("face_puzzle_left_up", () -> new BlockItem(FACE_PUZZLE_LEFT_UP.get(), new Item.Properties()));
    public static final  DeferredItem<Item> FACE_PUZZLE_RIGHT_UP_ITEM = ITEMS.register("face_puzzle_right_up", () -> new BlockItem(FACE_PUZZLE_RIGHT_UP.get(), new Item.Properties()));
    public static final  DeferredItem<Item> FACE_PUZZLE_LEFT_DOWN_ITEM = ITEMS.register("face_puzzle_left_down", () -> new BlockItem(FACE_PUZZLE_LEFT_DOWN.get(), new Item.Properties()));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_RIGHT_DOWN_BE = BLOCK_ENTITIES.register("face_puzzle_right_down",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(NetherExp.FACE_PUZZLE_RIGHT_DOWN_BE.get(), pos, state), FACE_PUZZLE_RIGHT_DOWN.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_LEFT_UP_BE = BLOCK_ENTITIES.register("face_puzzle_left_up",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(NetherExp.FACE_PUZZLE_LEFT_UP_BE.get(), pos, state), FACE_PUZZLE_LEFT_UP.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_RIGHT_UP_BE = BLOCK_ENTITIES.register("face_puzzle_right_up",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(NetherExp.FACE_PUZZLE_RIGHT_UP_BE.get(), pos, state), FACE_PUZZLE_RIGHT_UP.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FacePuzzleBlockEntity>> FACE_PUZZLE_LEFT_DOWN_BE = BLOCK_ENTITIES.register("face_puzzle_left_down",
            () -> BlockEntityType.Builder.of((pos, state) -> new FacePuzzleBlockEntity(NetherExp.FACE_PUZZLE_LEFT_DOWN_BE.get(), pos, state), FACE_PUZZLE_LEFT_DOWN.get()).build(null));

    public static final DeferredBlock<Block> SAMSONITE_BELL = BLOCKS.register("samsonite_bell",
            () -> new SamsoniteBellBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BELL)));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SamsoniteBellBlockEntity>> SAMSONITE_BELL_BE = BLOCK_ENTITIES.register("samsonite_bell",
            () -> BlockEntityType.Builder.of(SamsoniteBellBlockEntity::new, SAMSONITE_BELL.get()).build(null));

    public static final DeferredBlock<Block> LABYRINTH_BELLSPAWN = BLOCKS.register("labyrinth_bellspawn",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(20.0F)
                    .noOcclusion()));

    public static final DeferredItem<Item> LABYRINTH_BELLSPAWN_ITEM = ITEMS.register("labyrinth_bellspawn",
            () -> new BlockItem(LABYRINTH_BELLSPAWN.get(), new Item.Properties()));

    public static final DeferredBlock<Block> SAMSONIT = BLOCKS.register("samsonit",
            () -> new SamsonitBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_ITEM = ITEMS.register("samsonit",
            () -> new BlockItem(SAMSONIT.get(), new Item.Properties()));

    public static final DeferredBlock<Block> COBBLED_SAMSONIT = BLOCKS.register("cobbled_samsonit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> COBBLED_SAMSONIT_ITEM = ITEMS.register("cobbled_samsonit",
            () -> new BlockItem(COBBLED_SAMSONIT.get(), new Item.Properties()));


    public static final DeferredBlock<Block> SAMSONIT_BRICKS = BLOCKS.register("samsonit_bricks",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_BRICKS_ITEM = ITEMS.register("samsonit_bricks",
            () -> new BlockItem(SAMSONIT_BRICKS.get(), new Item.Properties()));

    public static final DeferredBlock<Block> POLISHED_SAMSONIT = BLOCKS.register("polished_samsonit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> POLISHED_SAMSONIT_ITEM = ITEMS.register("polished_samsonit",
            () -> new BlockItem(POLISHED_SAMSONIT.get(), new Item.Properties()));

    public static final DeferredBlock<Block> SAMSONIT_TILES = BLOCKS.register("samsonit_tiles",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_TILES_ITEM = ITEMS.register("samsonit_tiles",
            () -> new BlockItem(SAMSONIT_TILES.get(), new Item.Properties()));

    public static final DeferredBlock<Block> CHISELED_SAMSONIT = BLOCKS.register("chiseled_samsonit",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> CHISELED_SAMSONIT_ITEM = ITEMS.register("chiseled_samsonit",
            () -> new BlockItem(CHISELED_SAMSONIT.get(), new Item.Properties()));

    public static final  DeferredBlock<Block> LABYRINTH_TELEPORT = BLOCKS.register("labyrinth_teleport",
            () -> new LabyrinthTeleportBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(20.0F)
                    .noOcclusion()));

    public static final DeferredItem<Item> LABYRINTH_TELEPORT_ITEM = ITEMS.register("labyrinth_teleport",
            () -> new BlockItem(LABYRINTH_TELEPORT.get(), new Item.Properties()));



    public static final DeferredBlock<Block> COBBLED_SAMSONIT_SLAB = BLOCKS.register("cobbled_samsonit_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> COBBLED_SAMSONIT_SLAB_ITEM = ITEMS.register("cobbled_samsonit_slab",
            () -> new BlockItem(COBBLED_SAMSONIT_SLAB.get(), new Item.Properties()));

    public static final DeferredBlock<Block> POLISHED_SAMSONIT_SLAB = BLOCKS.register("polished_samsonit_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> POLISHED_SAMSONIT_SLAB_ITEM = ITEMS.register("polished_samsonit_slab",
            () -> new BlockItem(POLISHED_SAMSONIT_SLAB.get(), new Item.Properties()));


    public static final DeferredBlock<Block> SAMSONIT_BRICKS_SLAB = BLOCKS.register("samsonit_bricks_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_BRICKS_SLAB_ITEM = ITEMS.register("samsonit_bricks_slab",
            () -> new BlockItem(SAMSONIT_BRICKS_SLAB.get(), new Item.Properties()));

    

    public static final DeferredBlock<Block> COBBLED_SAMSONIT_STAIRS = BLOCKS.register("cobbled_samsonit_stairs",
            () -> new StairBlock(COBBLED_SAMSONIT.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> COBBLED_SAMSONIT_STAIRS_ITEM = ITEMS.register("cobbled_samsonit_stairs",
            () -> new BlockItem(COBBLED_SAMSONIT_STAIRS.get(), new Item.Properties()));

    public static final DeferredBlock<Block> POLISHED_SAMSONIT_STAIRS = BLOCKS.register("polished_samsonit_stairs",
            () -> new StairBlock(POLISHED_SAMSONIT.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> POLISHED_SAMSONIT_STAIRS_ITEM = ITEMS.register("polished_samsonit_stairs",
            () -> new BlockItem(POLISHED_SAMSONIT_STAIRS.get(), new Item.Properties()));


    public static final DeferredBlock<Block> SAMSONIT_BRICKS_STAIRS = BLOCKS.register("samsonit_bricks_stairs",
            () -> new StairBlock(SAMSONIT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_BRICKS_STAIRS_ITEM = ITEMS.register("samsonit_bricks_stairs",
            () -> new BlockItem(SAMSONIT_BRICKS_STAIRS.get(), new Item.Properties()));


    public static final DeferredBlock<Block> COBBLED_SAMSONIT_WALL = BLOCKS.register("cobbled_samsonit_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> COBBLED_SAMSONIT_WALL_ITEM = ITEMS.register("cobbled_samsonit_wall",
            () -> new BlockItem(COBBLED_SAMSONIT_WALL.get(), new Item.Properties()));

    public static final DeferredBlock<Block> POLISHED_SAMSONIT_WALL = BLOCKS.register("polished_samsonit_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> POLISHED_SAMSONIT_WALL_ITEM = ITEMS.register("polished_samsonit_wall",
            () -> new BlockItem(POLISHED_SAMSONIT_WALL.get(), new Item.Properties()));


    public static final DeferredBlock<Block> SAMSONIT_BRICKS_WALL = BLOCKS.register("samsonit_bricks_wall",
            () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_BRICKS_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_BRICKS_WALL_ITEM = ITEMS.register("samsonit_bricks_wall",
            () -> new BlockItem(SAMSONIT_BRICKS_WALL.get(), new Item.Properties()));

    
    public static final DeferredBlock<Block> NETHER_SPAWNER = BLOCKS.register("nether_spawner",
            () -> new NetherSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK) 
                    .lightLevel(state -> 15)
                    .requiresCorrectToolForDrops()
                    .strength(2.0F)
                    .noOcclusion())); 

    
    public static final DeferredItem<Item> NETHER_SPAWNER_ITEM = ITEMS.register("nether_spawner",
            () -> new BlockItem(NETHER_SPAWNER.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BLACKSTONE_COLUMN = BLOCKS.register("blackstone_column",
            () -> new BlackstoneColumnBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .strength(2.0F)
                    .requiresCorrectToolForDrops()));

    public static final DeferredItem<Item> BLACKSTONE_COLUMN_ITEM = ITEMS.register("blackstone_column",
            () -> new BlockItem(BLACKSTONE_COLUMN.get(), new Item.Properties()));


    
    public static final DeferredBlock<Block> POINTED_BLACKSTONE = BLOCKS.register("pointed_blackstone",
            () -> new PointedBlackstoneBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POINTED_DRIPSTONE)
                    .requiresCorrectToolForDrops()
                    .strength(1.0F)
                    .noOcclusion()));

    public static final DeferredItem<Item> POINTED_BLACKSTONE_ITEM = ITEMS.register("pointed_blackstone",
            () -> new BlockItem(POINTED_BLACKSTONE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> LOCKER_NETHER = BLOCKS.register("locker_nether",
            () -> new LockerNetherBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHERITE_BLOCK)
                    .strength(2.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> LOCKER_NETHER_ITEM = ITEMS.register("locker_nether",
            () -> new BlockItem(LOCKER_NETHER.get(), new Item.Properties()));

    public static final DeferredBlock<Block> SAMSONIT_EYE = BLOCKS.register("samsonit_eye",
            () -> new SamsonitEyeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_EYE_ITEM = ITEMS.register("samsonit_eye",
            () -> new BlockItem(SAMSONIT_EYE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> SAMSONIT_BELL = BLOCKS.register("samsonit_bell",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_BELL_ITEM = ITEMS.register("samsonit_bell",
            () -> new BlockItem(SAMSONIT_BELL.get(), new Item.Properties()));

    public static final DeferredItem<Item>  ALTAR_COMPASS_KEY = ITEMS.register("altar_compass_key",
            () -> new com.benji.netherman.item.AltarCompassKeyItem(new Item.Properties().stacksTo(1)));

    public static final DeferredBlock<Block> SAMSONIT_KEY = BLOCKS.register("samsonit_key",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.TUFF)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .sound(SAMSONIT_SOUNDS)));

    public static final DeferredItem<Item> SAMSONIT_KEY_ITEM = ITEMS.register("samsonit_key",
            () -> new BlockItem(SAMSONIT_KEY.get(), new Item.Properties()));

    

    public static final DeferredBlock<Block> BLACKSTONE_PLANT = BLOCKS.register("blackstone_plant",
            () -> new BlackstonePlantBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)
                    .lightLevel(state -> 10)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredHolder<StructureType<?>, StructureType<com.benji.netherman.worldgen.structure.MegaJigsawStructure>> MEGA_JIGSAW_STRUCTURE =
            STRUCTURE_TYPES.register("mega_jigsaw", () -> () -> com.benji.netherman.worldgen.structure.MegaJigsawStructure.CODEC);

    public static final DeferredBlock<Block> POTENT_MAGMA = BLOCKS.register("potent_magma",
            () -> new PotentMagmaBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                    .strength(2.0F)
                    .lightLevel(state -> 10)
                    .requiresCorrectToolForDrops()));

    public static final DeferredItem<Item> POTENT_MAGMA_ITEM = ITEMS.register("potent_magma",
            () -> new BlockItem(POTENT_MAGMA.get(), new Item.Properties()));


    public static final DeferredBlock<Block> VOIDMID = BLOCKS.register("void_mid",
            () -> new VoidMidBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDMID_ITEM = ITEMS.register("void_mid",
            () -> new BlockItem(VOIDMID.get(), new Item.Properties()));

    public static final DeferredBlock<Block> VOIDCORNER = BLOCKS.register("void_corner",
            () -> new VoidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDCORNER_ITEM = ITEMS.register("void_corner",
            () -> new BlockItem(VOIDCORNER.get(), new Item.Properties()));


    public static final DeferredBlock<Block> VOIDMIDCORNER = BLOCKS.register("void_midcorner",
            () -> new VoidMidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDMIDCORNER_ITEM = ITEMS.register("void_midcorner",
            () -> new BlockItem(VOIDMIDCORNER.get(), new Item.Properties()));


    

    public static final DeferredBlock<Block> VOIDMID_CAVE = BLOCKS.register("void_cave_mid",
            () -> new VoidCaveMidBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 10)
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDMID_CAVE_ITEM = ITEMS.register("void_cave_mid",
            () -> new BlockItem(VOIDMID_CAVE.get(), new Item.Properties()));

    public static final DeferredBlock<Block> VOIDCORNER_CAVE = BLOCKS.register("void_cave_corner",
            () -> new VoidCaveCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 10)
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDCORNER_CAVE_ITEM = ITEMS.register("void_cave_corner",
            () -> new BlockItem(VOIDCORNER_CAVE.get(), new Item.Properties()));


    public static final DeferredBlock<Block> VOIDMIDCORNER_CAVE = BLOCKS.register("void_cave_midcorner",
            () -> new VoidCaveMidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .lightLevel(state -> 10)
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDMIDCORNER_CAVE_ITEM = ITEMS.register("void_cave_midcorner",
            () -> new BlockItem(VOIDMIDCORNER_CAVE.get(), new Item.Properties()));


    public static final DeferredItem<Item> QUEST_ICON_1 = ITEMS.register("locker_quest1", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUEST_ICON_2 = ITEMS.register("locker_quest2", () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> QUEST_ICON_3 = ITEMS.register("locker_quest3", () -> new Item(new Item.Properties()));

    public static final DeferredBlock<Block> VOIDMIDNETHER = BLOCKS.register("voidnether_mid",
            () -> new VoidNetherMidBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDMIDNETHER_ITEM = ITEMS.register("voidnether_mid",
            () -> new BlockItem(VOIDMIDNETHER.get(), new Item.Properties()));

    public static final DeferredBlock<Block> VOIDCORNERNETHER = BLOCKS.register("voidnether_corner",
            () -> new VoidNetherCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDCORNERNETHER_ITEM = ITEMS.register("voidnether_corner",
            () -> new BlockItem(VOIDCORNERNETHER.get(), new Item.Properties()));


    public static final DeferredBlock<Block> VOIDMIDCORNERNETHER = BLOCKS.register("voidnether_midcorner",
            () -> new VoidNetherMidCornerBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> VOIDMIDCORNERNETHER_ITEM = ITEMS.register("voidnether_midcorner",
            () -> new BlockItem(VOIDMIDCORNERNETHER.get(), new Item.Properties()));
    

    public static final DeferredItem<Item> BLACKSTONE_PLANT_ITEM = ITEMS.register("blackstone_plant",
            () -> new BlockItem(BLACKSTONE_PLANT.get(), new Item.Properties()));

    public static final DeferredBlock<Block> BLACKSTONE_AXON = BLOCKS.register("blackstone_axon",
            () -> new BlackstoneAxonBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)
                    .lightLevel(state -> 10)
                    .instabreak()
                    .noCollission()
                    .noOcclusion()));

    public static final DeferredItem<Item> BLACKSTONE_AXON_ITEM = ITEMS.register("blackstone_axon",
            () -> new BlockItem(BLACKSTONE_AXON.get(), new Item.Properties()));

    
    public static final DeferredBlock<Block> ENTRANCE = BLOCKS.register("entrance",
            () -> new EntranceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK)
                    .strength(-1.0F, 3600000.0F)
                    .noOcclusion())); 

    
    public static final DeferredItem<Item> ENTRANCE_ITEM = ITEMS.register("entrance",
            () -> new BlockItem(ENTRANCE.get(), new Item.Properties()));


    public static final DeferredBlock<Block> MAZE_DOOR = BLOCKS.register("maze_door",
            () -> new MazeDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> MAZE_DOOR_ITEM = ITEMS.register("maze_door",
            () -> new GeoBlockItem(
                    MAZE_DOOR.get(),
                    new Item.Properties(),
                     ResourceLocation.fromNamespaceAndPath(MODID, "geo/maze_door.geo.json"),
                     ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/maze_door.png"),
                     ResourceLocation.fromNamespaceAndPath(MODID, "animations/maze_door.animation.json"),
                     ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/blackstone_column_emissive.png")
            ));


    public static final DeferredBlock<Block> CRIMSON_WEB = BLOCKS.register("crimson_web",
            () -> new CrimsonWebBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK)
                    .instabreak()
                    .noOcclusion())); 


    public static final DeferredItem<Item> CRIMSON_WEB_ITEM = ITEMS.register("crimson_web",
            () -> new GeoBlockItem(
                    CRIMSON_WEB.get(),
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "geo/crimson_web.geo.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/crimson_web.png"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "animations/crimson_web.animation.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/blackstone_column_emissive.png")
            ));


    public static final DeferredBlock<Block> TRAPHIVE = BLOCKS.register("traphive",
            () -> new TraphiveBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_WART_BLOCK)
                    .instabreak()
                    .noOcclusion())); 


    public static final DeferredItem<Item> TRAPHIVE_ITEM = ITEMS.register("traphive",
            () -> new GeoBlockItem(
                    TRAPHIVE.get(),
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "geo/traphive.geo.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/traphive.png"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "animations/traphive.animation.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/blackstone_column_emissive.png")
            ));

    public static final DeferredBlock<Block> STATUE_STAND = BLOCKS.register("statue_stand",
            () -> new StatueStandBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS)
                    .strength(5.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> STATUE_STAND_ITEM = ITEMS.register("statue_stand",
            () -> new BlockItem(STATUE_STAND.get(), new Item.Properties()));

    public static final DeferredBlock<Block> TOTEMUS = BLOCKS.register("totemus",
            () -> new TotemusBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS)
                    .strength(6.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));


    public static final DeferredItem<Item> TOTEMUS_ITEM = ITEMS.register("totemus",
            () -> new BlockItem(TOTEMUS.get(), new Item.Properties()));


    public static final DeferredBlock<Block> EYE = BLOCKS.register("eye_block",
            () -> new EyeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HONEY_BLOCK)
                    .strength(5.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));


    public static final DeferredItem<Item> EYE_ITEM = ITEMS.register("eye_block",
            () -> new GeoBlockItem(
                    EYE.get(),
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "geo/eye_block.geo.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/eye_block.png"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "animations/eye_block.animation.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/eye_block_emissive.png")
            ));


    public static final DeferredBlock<Block> ALTAR = BLOCKS.register("altar",
            () -> new AltarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.DEEPSLATE)
                    .lightLevel(state -> state.getValue(AltarBlock.LIT) ? 15 : 0)
                    .strength(10.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    public static final DeferredItem<Item> ALTAR_ITEM = ITEMS.register("altar",
            () -> new GeoBlockItem(
                    ALTAR.get(),
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "geo/altar.geo.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/altar.png"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "animations/altar.animation.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/altar_emissive.png")
            ));


    public static final DeferredBlock<Block> MOSAIC_CHURCH = BLOCKS.register("mosaic_church",
            () -> new MosaicChurchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)
                    .lightLevel(state -> 10)
                    .instabreak()
                    .noOcclusion())); 


    public static final DeferredItem<Item> MOSAIC_CHURCH_ITEM = ITEMS.register("mosaic_church",
            () -> new BlockItem(MOSAIC_CHURCH.get(), new Item.Properties()));

    
    public static final DeferredItem<Item> MANIPULATOR_STICK = ITEMS.register("manipulator_stick",
            () -> new com.benji.netherman.item.ManipulatorStickItem());

    public static final DeferredItem<Item> CHANCE_TOTEM = ITEMS.register("chance_totem",
            () -> new com.benji.netherman.item.ChanceTotemItem(new Item.Properties()));

    
    public static final DeferredItem<Item> NOTE = ITEMS.register("note",
            () -> new com.benji.netherman.item.NoteItem(new Item.Properties().stacksTo(1)));

    public static final DeferredBlock<Block> GRAND_DOOR = BLOCKS.register("grand_door",
            () -> new GrandDoorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)
                    .strength(20.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion()));

    
    public static final DeferredItem<Item> GRAND_DOOR_ITEM = ITEMS.register("grand_door",
            () -> new GeoBlockItem(
                    GRAND_DOOR.get(),
                    new Item.Properties(),
                    ResourceLocation.fromNamespaceAndPath(MODID, "geo/grand_door.geo.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/grand_door.png"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "animations/grand_door.animation.json"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "textures/block/grand_door_emissive.png")
            ));

    
    public static final DeferredBlock<Block> GRAND_DOOR_PART = BLOCKS.register("grand_door_part",
            () -> new GrandDoorPartBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.POLISHED_BLACKSTONE_BRICKS)
                    .strength(-1.0F, 3600000.0F)
                    .noOcclusion()
                    .noLootTable()));

    
    public static final DeferredBlock<Block> CRIMSON_HONEY_BLOCK = BLOCKS.register("crimson_honey_block",
            () -> new CrimsonHoneyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HONEY_BLOCK)
                    .instabreak()
                    .lightLevel(state -> 5)
                    .noOcclusion())); 

    public static final DeferredItem<Item> CRIMSON_HONEY_BLOCK_ITEM = ITEMS.register("crimson_honey_block",
            () -> new BlockItem(CRIMSON_HONEY_BLOCK.get(), new Item.Properties()));

    
    public static final DeferredItem<Item> CRIMSON_ARROW_ITEM = ITEMS.register("crimson_arrow",
            () -> new com.benji.netherman.item.CrimsonArrowItem(new Item.Properties()));

    
    public static final DeferredHolder<EntityType<?>, EntityType<com.benji.netherman.entity.CrimsonArrowEntity>> CRIMSON_ARROW_ENTITY = ENTITIES.register("crimson_arrow",
            () -> EntityType.Builder.<com.benji.netherman.entity.CrimsonArrowEntity>of(com.benji.netherman.entity.CrimsonArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build("crimson_arrow"));

    public static final DeferredBlock<Block> AZAZEL_TROPHY = BLOCKS.register("azazel_trophy",
            () -> new AzazelTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK)
                    .noOcclusion()));

    public static final DeferredItem<Item> AZAZEL_TROPHY_ITEM = ITEMS.register("azazel_trophy",
            () -> new AzazelTrophyItem(AZAZEL_TROPHY.get(), new Item.Properties()));


    public static final DeferredBlock<Block> AZAZEL_TROPHY_STAGE2 = BLOCKS.register("azazel_trophy_stage2",
            () -> new AzazelTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noOcclusion()));

    public static final DeferredBlock<Block> AZAZEL_TROPHY_STAGE3 = BLOCKS.register("azazel_trophy_stage3",
            () -> new AzazelTrophyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GOLD_BLOCK).noOcclusion()));

    public static final DeferredItem<Item> AZAZEL_TROPHY_STAGE2_ITEM = ITEMS.register("azazel_trophy_stage2",
            () -> new AzazelTrophyItem(AZAZEL_TROPHY_STAGE2.get(), new Item.Properties()));

    public static final DeferredItem<Item> AZAZEL_TROPHY_STAGE3_ITEM = ITEMS.register("azazel_trophy_stage3",
            () -> new AzazelTrophyItem(AZAZEL_TROPHY_STAGE3.get(), new Item.Properties()));


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NETHERMAN_TAB = CREATIVE_MODE_TABS.register("netherman_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(AZAZEL_TROPHY_ITEM.get())) 
                    .title(Component.translatable("creativetab.netherman_tab")) 
                    .displayItems((parameters, output) -> {

                        ITEMS.getEntries().forEach(holder -> output.accept(holder.get()));

                    })
                    .build()
    );

    public static final DeferredBlock<Block> GHASTLY_NEST = BLOCKS.register("ghastly_nest",
            () -> new GhastlyNestBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE)
                    .strength(2.0F)
                    .requiresCorrectToolForDrops()
                    .noOcclusion())); 

    
    public static final DeferredItem<Item> GHASTLY_NEST_ITEM = ITEMS.register("ghastly_nest",
            () -> new BlockItem(GHASTLY_NEST.get(), new Item.Properties()));

    public static final DeferredItem<Item> CRIMSON_HONEY_BOTTLE = ITEMS.register("crimson_honey_bottle",
            () -> new com.benji.netherman.item.CrimsonHoneyBottleItem(new Item.Properties()
                    .stacksTo(16) 
                    .craftRemainder(net.minecraft.world.item.Items.GLASS_BOTTLE)
                    .food(new net.minecraft.world.food.FoodProperties.Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build())));


    public static final DeferredItem<Item> MAZE_KEY = ITEMS.register("maze_key",
            () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GhastlyNestBlockEntity>> GHASTLY_NEST_BE = BLOCK_ENTITIES.register("ghastly_nest",
            () -> BlockEntityType.Builder.of(GhastlyNestBlockEntity::new, GHASTLY_NEST.get()).build(null));

    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherMidCornerBlockEntity>> VOIDMIDCORNERNETHER_BE = BLOCK_ENTITIES.register("voidnether_midcorner",
            () -> BlockEntityType.Builder.of(VoidNetherMidCornerBlockEntity::new, VOIDMIDCORNERNETHER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherCornerBlockEntity>> VOIDCORNERNETHER_BE = BLOCK_ENTITIES.register("voidnether_corner",
            () -> BlockEntityType.Builder.of(VoidNetherCornerBlockEntity::new, VOIDCORNERNETHER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<MazeDoorBlockEntity>> MAZE_DOOR_BE = BLOCK_ENTITIES.register("maze_door",
            () -> BlockEntityType.Builder.of(MazeDoorBlockEntity::new, MAZE_DOOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemusHoleBlockEntity>> TOTEMUS_HOLE_BE = BLOCK_ENTITIES.register("totemus_hole",
            () -> BlockEntityType.Builder.of(TotemusHoleBlockEntity::new, TOTEMUS_HOLE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidNetherMidBlockEntity>> VOIDMIDNETHER_BE = BLOCK_ENTITIES.register("voidnether_mid",
            () -> BlockEntityType.Builder.of(VoidNetherMidBlockEntity::new, VOIDMIDNETHER.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<EyeBlockEntity>> EYE_BE = BLOCK_ENTITIES.register("eye_block",
            () -> BlockEntityType.Builder.of(EyeBlockEntity::new, EYE.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LabyrinthTeleportBlockEntity>> LABYRINTH_TELEPORT_BE = BLOCK_ENTITIES.register("labyrinth_teleport",
            () -> BlockEntityType.Builder.of(LabyrinthTeleportBlockEntity::new, LABYRINTH_TELEPORT.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarBlockEntity>> ALTAR_BE = BLOCK_ENTITIES.register("altar",
            () -> BlockEntityType.Builder.of(AltarBlockEntity::new, ALTAR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TotemusBlockEntity>> TOTEMUS_BE = BLOCK_ENTITIES.register("totemus",
            () -> BlockEntityType.Builder.of(TotemusBlockEntity::new, TOTEMUS.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<TraphiveBlockEntity>> TRAPHIVE_BE = BLOCK_ENTITIES.register("traphive",
            () -> BlockEntityType.Builder.of(TraphiveBlockEntity::new, TRAPHIVE.get()).build(null));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<GrandDoorBlockEntity>> GRAND_DOOR_BE = BLOCK_ENTITIES.register("grand_door",
            () -> BlockEntityType.Builder.of(GrandDoorBlockEntity::new, GRAND_DOOR.get()).build(null));

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NetherSpawnerBlockEntity>> NETHER_SPAWNER_BE = BLOCK_ENTITIES.register("nether_spawner",
            () -> BlockEntityType.Builder.of(NetherSpawnerBlockEntity::new, NETHER_SPAWNER.get()).build(null));


    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<PointedBlackstoneBlockEntity>> POINTED_BLACKSTONE_BE = BLOCK_ENTITIES.register("pointed_blackstone",
            () -> BlockEntityType.Builder.of(PointedBlackstoneBlockEntity::new, POINTED_BLACKSTONE.get()).build(null));

    
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, MODID);

    public static final DeferredRegister<net.minecraft.world.item.crafting.RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, MODID);

    

    public static final DeferredHolder<MobEffect, ManipulationEffect> MANIPULATION_EFFECT = EFFECTS.register("manipulation", ManipulationEffect::new);
    public static final DeferredHolder<MobEffect, ZoneEffect> FEAR_EFFECT = EFFECTS.register("fear", () -> new ZoneEffect(0x000000));
    public static final DeferredHolder<MobEffect, ZoneEffect> EXCITEMENT_EFFECT = EFFECTS.register("excitement", () -> new ZoneEffect(0xFF0000));
    public static final DeferredHolder<MobEffect, ZoneEffect> FAITH_EFFECT = EFFECTS.register("faith", () -> new ZoneEffect(0x800080));
    public static final DeferredHolder<MobEffect, ZoneEffect> ANXIETY_EFFECT = EFFECTS.register("anxiety", () -> new ZoneEffect(0x8B0000));
    public static final DeferredHolder<MobEffect, ZoneEffect> ALERTNESS_EFFECT = EFFECTS.register("alertness", () -> new ZoneEffect(0x8B0000));


    public static final DeferredHolder<net.minecraft.world.item.crafting.RecipeSerializer<?>, net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer<?>> CRIMSON_ARROW_CRAFTING =
            RECIPE_SERIALIZERS.register("crimson_arrow_coating", () -> new net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer<>(com.benji.netherman.item.crafting.CrimsonArrowRecipe::new));


    public static final DeferredHolder<EntityType<?>, EntityType<AzazelEntity>> AZAZEL = ENTITIES.register("azazel",
            () -> EntityType.Builder.of(AzazelEntity::new, MobCategory.MONSTER)
                    .sized(3.0F, 4.5F) 
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "azazel").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<TotemusPuzzleEntity>> TOTEMUS_PUZZLE = ENTITIES.register("totemus_puzzle",
            () -> EntityType.Builder.of(TotemusPuzzleEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 2.25F)
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "totemus_puzzle").toString()));


    public static final DeferredHolder<EntityType<?>, EntityType<GildedGolemEntity>> GILDED_GOLEM = ENTITIES.register("gilded_golem",
            () -> EntityType.Builder.of(GildedGolemEntity::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F) 
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "gilded_golem").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<StatueBossunitEntity>> STATUE_BOSSUNIT = ENTITIES.register("statue_bossunit",
            () -> EntityType.Builder.of(StatueBossunitEntity::new, MobCategory.MONSTER)
                    .sized(0.625F, 2.125F) 
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "statue_bossunit").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<LaserEntity>> LASER = ENTITIES.register("laser",
            () -> EntityType.Builder.of(LaserEntity::new, MobCategory.MISC)
                    .sized(3.0F, 18.75F) 
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "laser").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<StatueEntity>> STATUE = ENTITIES.register("statue_entity",
            () -> EntityType.Builder.of(StatueEntity::new, MobCategory.MONSTER)
                    .sized(0.625F, 2.125F) 
                    .fireImmune()
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "statue_entity").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BellGuardianEntity>> BELL_GUARDIAN = ENTITIES.register("bell_guardian",
            () -> EntityType.Builder.of(BellGuardianEntity::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.8F)
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "bell_guardian").toString()));


    public static final DeferredHolder<EntityType<?>, EntityType<TraderEntity>> TRADER = ENTITIES.register("trader",
            () -> EntityType.Builder.of(TraderEntity::new, MobCategory.CREATURE)
                    .sized(1.125F, 1.5F)
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "trader").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<DoctorEntity>> DOCTOR = ENTITIES.register("doctor",
            () -> EntityType.Builder.of(DoctorEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F) 
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "doctor").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BlacksmithEntity>> BLACKSMITH = ENTITIES.register("blacksmith",
            () -> EntityType.Builder.of(BlacksmithEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F) 
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "blacksmith").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BelieverEntity>> BELIEVER = ENTITIES.register("believer",
            () -> EntityType.Builder.of(BelieverEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F) 
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "believer").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BelieverVillagerEntity>> BELIEVER_VILLAGER = ENTITIES.register("believer_villager",
            () -> EntityType.Builder.of(BelieverVillagerEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F) 
                    .build(ResourceLocation.fromNamespaceAndPath(MODID, "believer_villager").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<VillagerPrisonerEntity>> VILLAGER_PRISONER = ENTITIES.register("villager_prisoner",
            () -> EntityType.Builder.of(VillagerPrisonerEntity::new, MobCategory.CREATURE) 
                    .sized(0.6F, 1.95F) 
                    .build("villager_prisoner"));

    public static final DeferredHolder<EntityType<?>, EntityType<PiglinPrisonerEntity>> PIGLIN_PRISONER = ENTITIES.register("piglin_prisoner",
            () -> EntityType.Builder.of(PiglinPrisonerEntity::new, MobCategory.CREATURE) 
                    .sized(0.6F, 1.95F) 
                    .build("piglin_prisoner"));

    
    public static final DeferredHolder<EntityType<?>, EntityType<ManipulatorEntity>> MANIPULATOR = ENTITIES.register("manipulator",
            () -> EntityType.Builder.of(ManipulatorEntity::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.9375F, 2.125F) 
                    .build("manipulator"));

    public static final DeferredHolder<EntityType<?>, EntityType<WelcomerEntity>> WELCOMER = ENTITIES.register("welcomer",
            () -> EntityType.Builder.of(WelcomerEntity::new, MobCategory.MONSTER)
                    
                    .sized(0.625f, 2.25f)
                    .build("welcomer"));

    public static final DeferredItem<Item> AZAZEL_GUIDE_BOOK_ITEM = ITEMS.register("azazel_guide_book",
            () -> new AzazelGuideBookItem(new Item.Properties().stacksTo(1))); 

    public static final DeferredHolder<EntityType<?>, EntityType<AzazelGuideBookEntity>> AZAZEL_GUIDE_BOOK_ENTITY = ENTITIES.register("azazel_guide_book",
            () -> EntityType.Builder.of(AzazelGuideBookEntity::new, MobCategory.MISC)
                    .sized(1.0F, 1.0F)
                    .fireImmune()
                    .build("azazel_guide_book"));

    
    public static final DeferredHolder<EntityType<?>, EntityType<GuardianEntity>> GUARDIAN = ENTITIES.register("guardian",
            () -> EntityType.Builder.of(GuardianEntity::new, MobCategory.MONSTER)
                    .sized(1.0f, 5.125f)
                    .fireImmune()
                    .build("guardian"));

    public static final DeferredHolder<EntityType<?>, EntityType<GhastlyEntity>> GHASTLY = ENTITIES.register("ghastly",
            () -> EntityType.Builder.of(GhastlyEntity::new, MobCategory.CREATURE) 
                    .sized(0.625f, 0.8125f)
                    .fireImmune()
                    .build("ghastly"));

    

    public static final DeferredItem<Item> GILDED_GOLEM_SPAWN_EGG = ITEMS.register("gilded_golem_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    GILDED_GOLEM, 
                    0xFFFFFF,     
                    0xFFFFFF,     
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> GHASTLY_SPAWN_EGG = ITEMS.register("ghastly_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    GHASTLY, 
                    0xFFFFFF,     
                    0xFFFFFF,     
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> GUARDIAN_SPAWN_EGG = ITEMS.register("guardian_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    GUARDIAN,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> BELIEVER_SPAWN_EGG = ITEMS.register("believer_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    BELIEVER,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> BELIEVER_VILLAGER_SPAWN_EGG = ITEMS.register("believer_villager_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    BELIEVER_VILLAGER,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> BLACKSMITH_SPAWN_EGG = ITEMS.register("blacksmith_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    BLACKSMITH,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> DOCTOR_SPAWN_EGG = ITEMS.register("doctor_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    DOCTOR,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> MANIPULATOR_SPAWN_EGG = ITEMS.register("manipulator_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    MANIPULATOR,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> STATUE_BOSSUNIT_SPAWN_EGG = ITEMS.register("statue_bossunit_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    STATUE_BOSSUNIT,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> STATUE_SPAWN_EGG = ITEMS.register("statue_entity_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    STATUE,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> TRADER_SPAWN_EGG = ITEMS.register("trader_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    TRADER,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public static final DeferredItem<Item> AZAZEL_SPAWN_EGG = ITEMS.register("azazel_spawn_egg",
            () -> new DeferredSpawnEggItem(
                    AZAZEL,
                    0xFFFFFF,
                    0xFFFFFF,
                    new Item.Properties()
            ));

    public NetherExp(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, AzazelConfig.SPEC);

        
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        ENTITIES.register(modEventBus);
        EFFECTS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        RECIPE_SERIALIZERS.register(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);
        STRUCTURE_TYPES.register(modEventBus);

        
        modEventBus.addListener(ModMessages::register);
        modEventBus.addListener(ModEvents::registerAttributes);
        modEventBus.addListener(this::registerPayloads);
        
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(ClientModEvents::registerRenderers);
            modEventBus.addListener(ClientModEvents::registerGuiOverlays);
            modEventBus.addListener(ClientModEvents::onClientSetup);
        }
        modEventBus.addListener(this::addCreative);

        
    }

    
    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(MODID);
    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            
            
        });
    }


    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS || event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(NETHER_SPAWNER_ITEM);
            event.accept(GRAND_DOOR_ITEM);
            event.accept(BLACKSTONE_COLUMN_ITEM);
            event.accept(ENTRANCE_ITEM);
            event.accept(CRIMSON_WEB_ITEM);
            event.accept(TRAPHIVE_ITEM);
            event.accept(VOIDMID_ITEM);
            event.accept(VOIDCORNER_ITEM);
            event.accept(AZAZEL_TROPHY_ITEM);
            event.accept(VOIDMIDCORNER_ITEM);
            event.accept(VOIDMIDNETHER_ITEM);
            event.accept(VOIDCORNERNETHER_ITEM);
            event.accept(MOSAIC_CHURCH_ITEM);
            event.accept(STATUE_STAND_ITEM);
            event.accept(LABYRINTH_BELLSPAWN_ITEM);
            event.accept(VOIDMID_CAVE_ITEM);
            event.accept(VOIDCORNER_CAVE_ITEM);
            event.accept(VOIDMIDCORNER_CAVE_ITEM);
            event.accept(SAMSONIT_EYE_ITEM);
            event.accept(SAMSONIT_BELL_ITEM);
            event.accept(LOCKER_NETHER_ITEM);
            event.accept(SAMSONIT_KEY_ITEM);
            event.accept(TOTEMUS_HOLE_ITEM);
            event.accept(MAZE_DOOR_ITEM);
            event.accept(TOTEMUS_ITEM);
            event.accept(EYE_ITEM);
            event.accept(A_PUZZLE_ITEM);
            event.accept(Z_PUZZLE_ITEM);
            event.accept(E_PUZZLE_ITEM);
            event.accept(L_PUZZLE_ITEM);
            event.accept(LABYRINTH_TELEPORT_ITEM);
            event.accept(ALTAR_ITEM);
            event.accept(VOIDMIDCORNERNETHER_ITEM);
            event.accept(SAMSONIT_ITEM);
            event.accept(SAMSONIT_BRICKS_ITEM);
            event.accept(SAMSONIT_TILES_ITEM);
            event.accept(POLISHED_SAMSONIT_ITEM);
            event.accept(COBBLED_SAMSONIT_ITEM);
            event.accept(CHISELED_SAMSONIT_ITEM);
            event.accept(COBBLED_SAMSONIT_SLAB_ITEM);
            event.accept(COBBLED_SAMSONIT_STAIRS_ITEM);
            event.accept(COBBLED_SAMSONIT_WALL_ITEM);
            event.accept(POLISHED_SAMSONIT_SLAB_ITEM);
            event.accept(POLISHED_SAMSONIT_STAIRS_ITEM);
            event.accept(POLISHED_SAMSONIT_WALL_ITEM);
            event.accept(SAMSONIT_BRICKS_SLAB_ITEM);
            event.accept(SAMSONIT_BRICKS_STAIRS_ITEM);
            event.accept(SAMSONIT_BRICKS_WALL_ITEM);
        }
        if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(CRIMSON_HONEY_BOTTLE);
        }
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            event.accept(GILDED_GOLEM_SPAWN_EGG);
            event.accept(GHASTLY_SPAWN_EGG);
            event.accept(GUARDIAN_SPAWN_EGG);
            event.accept(BELIEVER_SPAWN_EGG);
            event.accept(BLACKSMITH_SPAWN_EGG);
            event.accept(DOCTOR_SPAWN_EGG);
            event.accept(MAZE_KEY);
            event.accept(BELIEVER_VILLAGER_SPAWN_EGG);
            event.accept(MANIPULATOR_SPAWN_EGG);
            event.accept(STATUE_BOSSUNIT_SPAWN_EGG);
            event.accept(STATUE_SPAWN_EGG);
            event.accept(TRADER_SPAWN_EGG);
            event.accept(AZAZEL_SPAWN_EGG);
        }
        if (event.getTabKey() == CreativeModeTabs.COMBAT || event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(MANIPULATOR_STICK);
            event.accept(CRIMSON_ARROW_ITEM);
            event.accept(CHANCE_TOTEM);
            event.accept(NOTE);
            event.accept(AZAZEL_GUIDE_BOOK_ITEM);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(BLACKSTONE_PLANT_ITEM);
            event.accept(BLACKSTONE_AXON_ITEM);
            event.accept(CRIMSON_HONEY_BLOCK_ITEM);
            event.accept(GHASTLY_NEST_ITEM);
            event.accept(POINTED_BLACKSTONE_ITEM);
        }
    }

    public static class ModEvents {

        public static void registerAttributes(EntityAttributeCreationEvent event) {
            
            event.put(AZAZEL_GUIDE_BOOK_ENTITY.get(), AzazelGuideBookEntity.createAttributes().build());
            event.put(GILDED_GOLEM.get(), GildedGolemEntity.createAttributes().build());
            event.put(AZAZEL.get(), AzazelEntity.createAttributes().build());
            event.put(BELL_GUARDIAN.get(), BellGuardianEntity.createAttributes().build());
            event.put(TOTEMUS_PUZZLE.get(), TotemusPuzzleEntity.createAttributes().build());
            event.put(LASER.get(), LaserEntity.createAttributes().build());
            event.put(STATUE_BOSSUNIT.get(), StatueBossunitEntity.createAttributes().build());
            event.put(BLACKSMITH.get(), BlacksmithEntity.createAttributes().build());
            event.put(DOCTOR.get(), DoctorEntity.createAttributes().build());
            event.put(TRADER.get(), TraderEntity.createAttributes().build());
            event.put(STATUE.get(), StatueEntity.createAttributes().build());
            event.put(BELIEVER.get(), BelieverEntity.createAttributes().build());
            event.put(BELIEVER_VILLAGER.get(), BelieverVillagerEntity.createAttributes().build());
            event.put(PIGLIN_PRISONER.get(), PiglinPrisonerEntity.createAttributes().build());
            event.put(VILLAGER_PRISONER.get(), VillagerPrisonerEntity.createAttributes().build());
            event.put(MANIPULATOR.get(), ManipulatorEntity.createAttributes().build());
            event.put(WELCOMER.get(), WelcomerEntity.createAttributes().build());
            event.put(GHASTLY.get(), GhastlyEntity.createAttributes().build());
            event.put(GUARDIAN.get(), GuardianEntity.createAttributes().build());
        }
    }


    
    public static class ClientModEvents {

        
        public static void onClientSetup(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                
                ItemProperties.register(NetherExp.ALTAR_COMPASS_KEY.get(), ResourceLocation.withDefaultNamespace("angle"), (stack, level, entity, seed) -> {
                    if (entity == null && !stack.isFramed()) return 0.0F;

                    
                    net.minecraft.world.item.component.CustomData customData = stack.getOrDefault(net.minecraft.core.component.DataComponents.CUSTOM_DATA, net.minecraft.world.item.component.CustomData.EMPTY);
                    net.minecraft.nbt.CompoundTag tag = customData.copyTag();

                    
                    if (!tag.contains("TargetX")) {
                        return (float) ((System.currentTimeMillis() % 4000L) / 4000.0);
                    }

                    double targetX = tag.getInt("TargetX");
                    double targetZ = tag.getInt("TargetZ");

                    Entity player = entity != null ? entity : stack.getFrame();
                    if (player == null) return 0.0F;

                    
                    double targetYaw = Math.toDegrees(Math.atan2(targetZ - player.getZ(), targetX - player.getX())) - 90.0;
                    double playerYaw = player.getYRot();
                    double relativeYaw = targetYaw - playerYaw;
                    double angle = 0.5 + (relativeYaw / 360.0);

                    return (float) net.minecraft.util.Mth.positiveModulo(angle, 1.0D);
                });
            });
        }

        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(NetherExp.LABYRINTH_TELEPORT_BE.get(), LabyrinthTeleportRenderer::new);
            event.registerBlockEntityRenderer(VOIDMIDCORNERNETHER_BE.get(), VoidNetherMidCornerRenderer::new);
            event.registerBlockEntityRenderer(VOIDCORNERNETHER_BE.get(), VoidNetherCornerRenderer::new);
            event.registerBlockEntityRenderer(SAMSONITE_BELL_BE.get(), SamsoniteBellRenderer::new);
            event.registerEntityRenderer(BELL_GUARDIAN.get(), BellGuardianRenderer::new);
            event.registerBlockEntityRenderer(VOIDMIDNETHER_BE.get(), VoidNetherMidRenderer::new);
            event.registerBlockEntityRenderer(TRAPHIVE_BE.get(), TraphiveRenderer::new);
            event.registerBlockEntityRenderer(EYE_BE.get(), EyeRenderer::new);
            event.registerBlockEntityRenderer(ALTAR_BE.get(), AltarRenderer::new);
            event.registerBlockEntityRenderer(MAZE_DOOR_BE.get(), MazeDoorRenderer::new);
            event.registerBlockEntityRenderer(FACE_PUZZLE_LEFT_DOWN_BE.get(), FacePuzzleLeftDownRenderer::new);
            event.registerBlockEntityRenderer(FACE_PUZZLE_RIGHT_DOWN_BE.get(), FacePuzzleRightDownRenderer::new);
            event.registerBlockEntityRenderer(FACE_PUZZLE_LEFT_UP_BE.get(), FacePuzzleLeftUpRenderer::new);
            event.registerBlockEntityRenderer(FACE_PUZZLE_RIGHT_UP_BE.get(), FacePuzzleRightUpRenderer::new);
            event.registerBlockEntityRenderer(GRAND_DOOR_BE.get(), GrandDoorRenderer::new);
            event.registerBlockEntityRenderer(POINTED_BLACKSTONE_BE.get(), PointedBlackstoneRenderer::new);
            
            event.registerEntityRenderer(AZAZEL_GUIDE_BOOK_ENTITY.get(), AzazelGuideBookRenderer::new);
            event.registerEntityRenderer(TOTEMUS_PUZZLE.get(), TotemusPuzzleRenderer::new);
            event.registerEntityRenderer(GILDED_GOLEM.get(), GildedGolemRenderer::new);
            event.registerEntityRenderer(CRIMSON_ARROW_ENTITY.get(), com.benji.netherman.client.renderer.entity.CrimsonArrowRenderer::new);
            event.registerEntityRenderer(AZAZEL.get(), AzazelRenderer::new);
            event.registerEntityRenderer(LASER.get(), LaserRenderer::new);
            event.registerEntityRenderer(STATUE_BOSSUNIT.get(), StatueBossunitRenderer::new);
            event.registerEntityRenderer(BLACKSMITH.get(), BlacksmithRenderer::new);
            event.registerEntityRenderer(DOCTOR.get(), DoctorRenderer::new);
            event.registerEntityRenderer(TRADER.get(), TraderRenderer::new);
            event.registerEntityRenderer(STATUE.get(), StatueRenderer::new);
            event.registerEntityRenderer(BELIEVER.get(), BelieverRenderer::new);
            event.registerEntityRenderer(BELIEVER_VILLAGER.get(), BelieverVillagerRenderer::new);
            event.registerEntityRenderer(PIGLIN_PRISONER.get(), PiglinPrisonerRenderer::new);
            event.registerEntityRenderer(VILLAGER_PRISONER.get(), VillagerPrisonerRenderer::new);
            event.registerEntityRenderer(MANIPULATOR.get(), ManipulatorRenderer::new);
            event.registerEntityRenderer(WELCOMER.get(), WelcomerRenderer::new);
            event.registerEntityRenderer(GUARDIAN.get(), GuardianRenderer::new);
            event.registerEntityRenderer(GHASTLY.get(), GhastlyRenderer::new);
        }

        
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            event.registerAboveAll(
                    ResourceLocation.fromNamespaceAndPath(NetherExp.MODID, "manipulation_overlay"),
                    ManipulationOverlay.HUD_OVERLAY
            );
        }
    }
}
