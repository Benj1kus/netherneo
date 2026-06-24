package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.item.*;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;
import java.util.function.Supplier;

public class ModItems {
    private static final Supplier<Item> SIMPLE_SUPPLIER = () -> new Item(new Item.Properties());
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NetherExp.MODID);

    public static final DeferredItem<Item> ALTAR_COMPASS_KEY = register("altar_compass_key", () -> new AltarCompassKeyItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> QUEST_ICON_1 = registerSimple("locker_quest1");
    public static final DeferredItem<Item> QUEST_ICON_2 = registerSimple("locker_quest2");
    public static final DeferredItem<Item> QUEST_ICON_3 = registerSimple("locker_quest3");

    public static final DeferredItem<Item> MANIPULATOR_STICK = register("manipulator_stick", ManipulatorStickItem::new);
    public static final DeferredItem<Item> CHANCE_TOTEM = register("chance_totem", () -> new ChanceTotemItem(new Item.Properties()));
    public static final DeferredItem<Item> NOTE = register("note", () -> new NoteItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<Item> CRIMSON_ARROW = register("crimson_arrow", () -> new CrimsonArrowItem(new Item.Properties()));

    public static final DeferredItem<Item> CRIMSON_HONEY_BOTTLE = register("crimson_honey_bottle", () -> new CrimsonHoneyBottleItem(new Item.Properties()
                    .stacksTo(16)
                    .craftRemainder(net.minecraft.world.item.Items.GLASS_BOTTLE)
                    .food(new net.minecraft.world.food.FoodProperties.Builder().nutrition(6).saturationModifier(0.1F).alwaysEdible().build())));

    public static final DeferredItem<Item> MAZE_KEY = register("maze_key", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> AZAZEL_GUIDE_BOOK = register("azazel_guide_book", () -> new AzazelGuideBookItem(new Item.Properties().stacksTo(1)));

    public static final DeferredItem<Item> GILDED_GOLEM_SPAWN_EGG = registerSpawnEgg("gilded_golem", ModEntities.GILDED_GOLEM);

    public static final DeferredItem<Item> GHASTLY_SPAWN_EGG = registerSpawnEgg("ghastly", ModEntities.GHASTLY);

    public static final DeferredItem<Item> GUARDIAN_SPAWN_EGG = registerSpawnEgg("guardian", ModEntities.GUARDIAN);

    public static final DeferredItem<Item> BELIEVER_SPAWN_EGG = registerSpawnEgg("believer", ModEntities.BELIEVER);

    public static final DeferredItem<Item> BELIEVER_VILLAGER_SPAWN_EGG = registerSpawnEgg("believer_villager", ModEntities.BELIEVER_VILLAGER);

    public static final DeferredItem<Item> BLACKSMITH_SPAWN_EGG = registerSpawnEgg("blacksmith", ModEntities.BLACKSMITH);

    public static final DeferredItem<Item> DOCTOR_SPAWN_EGG = registerSpawnEgg("doctor", ModEntities.DOCTOR);

    public static final DeferredItem<Item> MANIPULATOR_SPAWN_EGG = registerSpawnEgg("manipulator", ModEntities.MANIPULATOR);

    public static final DeferredItem<Item> STATUE_BOSSUNIT_SPAWN_EGG = registerSpawnEgg("statue_bossunit", ModEntities.STATUE_BOSSUNIT);

    public static final DeferredItem<Item> STATUE_SPAWN_EGG = registerSpawnEgg("statue_entity", ModEntities.STATUE);

    public static final DeferredItem<Item> TRADER_SPAWN_EGG = registerSpawnEgg("trader", ModEntities.TRADER);

    public static final DeferredItem<Item> AZAZEL_SPAWN_EGG = registerSpawnEgg("azazel", ModEntities.AZAZEL);

    private static DeferredItem<Item> registerSimple(String name, Item.Properties itemProperties) {
        return register(name, () -> new Item(itemProperties));
    }

    private static DeferredItem<Item> registerSimple(String name) {
        return register(name, SIMPLE_SUPPLIER);
    }

    private static DeferredItem<Item> registerSpawnEgg(String id, Supplier<? extends EntityType<? extends Mob>> type) {
        return register(id + "_spawn_egg", () -> new DeferredSpawnEggItem(type, 0xFFFFFF, 0xFFFFFF, new Item.Properties()));
    }

    private static <T extends Item> DeferredItem<T> register(String id, Supplier<T> pIProp) {
        return ITEMS.register(id.toLowerCase(), pIProp);
    }

    //private-package so block register class can use
    static void registerBlockItem(Holder<Block> blockHolder) {
        registerBlockItem(blockHolder, new Item.Properties());
    }
    static void registerBlockItem(Holder<Block> blockHolder, Item.Properties properties) {
        ITEMS.registerSimpleBlockItem(blockHolder, properties);
    }
    static DeferredItem<BlockItem> registerBlockItem(String name, Supplier<? extends Block> blockHolder, Item.Properties properties) {
        return ITEMS.registerSimpleBlockItem(name, blockHolder, properties);
    }
    static void registerSpecialBlockItem(Holder<Block> blockHolder, Supplier<? extends BlockItem> sup) {
        String id = blockHolder.unwrapKey().orElseThrow().location().getPath();
        ITEMS.register(id, sup);
    }

    public static void init(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static Collection<DeferredHolder<Item, ? extends Item>> getItems() {
        return ITEMS.getEntries();
    }
}
