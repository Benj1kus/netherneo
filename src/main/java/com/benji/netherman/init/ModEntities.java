package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, NetherExp.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<CrimsonArrowEntity>> CRIMSON_ARROW = ENTITIES.register("crimson_arrow",
            () -> EntityType.Builder.<CrimsonArrowEntity>of(CrimsonArrowEntity::new, MobCategory.MISC)
                    .sized(0.5F, 0.5F)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(NetherExp.location("crimson_arrow").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<AzazelEntity>> AZAZEL = ENTITIES.register("azazel",
            () -> EntityType.Builder.of(AzazelEntity::new, MobCategory.MONSTER)
                    .sized(3.0F, 4.5F)
                    .fireImmune()
                    .build(NetherExp.location("azazel").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<GildedGolemEntity>> GILDED_GOLEM = ENTITIES.register("gilded_golem",
            () -> EntityType.Builder.of(GildedGolemEntity::new, MobCategory.MISC)
                    .sized(1.4F, 2.7F)
                    .fireImmune()
                    .build(NetherExp.location("gilded_golem").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<StatueBossunitEntity>> STATUE_BOSSUNIT = ENTITIES.register("statue_bossunit",
            () -> EntityType.Builder.of(StatueBossunitEntity::new, MobCategory.MONSTER)
                    .sized(0.625F, 2.125F)
                    .fireImmune()
                    .build(NetherExp.location("statue_bossunit").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<LaserEntity>> LASER = ENTITIES.register("laser",
            () -> EntityType.Builder.of(LaserEntity::new, MobCategory.MISC)
                    .sized(3.0F, 18.75F)
                    .fireImmune()
                    .build(NetherExp.location("laser").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<StatueEntity>> STATUE = ENTITIES.register("statue_entity",
            () -> EntityType.Builder.of(StatueEntity::new, MobCategory.MONSTER)
                    .sized(0.625F, 2.125F)
                    .fireImmune()
                    .build(NetherExp.location("statue_entity").toString()));


    public static final DeferredHolder<EntityType<?>, EntityType<TraderEntity>> TRADER = ENTITIES.register("trader",
            () -> EntityType.Builder.of(TraderEntity::new, MobCategory.CREATURE)
                    .sized(1.125F, 1.5F)
                    .build(NetherExp.location("trader").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<DoctorEntity>> DOCTOR = ENTITIES.register("doctor",
            () -> EntityType.Builder.of(DoctorEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .build(NetherExp.location("doctor").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BlacksmithEntity>> BLACKSMITH = ENTITIES.register("blacksmith",
            () -> EntityType.Builder.of(BlacksmithEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .build(NetherExp.location("blacksmith").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BelieverEntity>> BELIEVER = ENTITIES.register("believer",
            () -> EntityType.Builder.of(BelieverEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .build(NetherExp.location("believer").toString()));

    public static final DeferredHolder<EntityType<?>, EntityType<BelieverVillagerEntity>> BELIEVER_VILLAGER = ENTITIES.register("believer_villager",
            () -> EntityType.Builder.of(BelieverVillagerEntity::new, MobCategory.CREATURE)
                    .sized(0.6F, 1.95F)
                    .build(NetherExp.location("believer_villager").toString()));

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

    public static final DeferredHolder<EntityType<?>, EntityType<AzazelGuideBookEntity>> AZAZEL_GUIDE_BOOK = ENTITIES.register("azazel_guide_book",
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

    public static final DeferredHolder<EntityType<?>, EntityType<TotemusPuzzleEntity>> TOTEMUS_PUZZLE = ENTITIES.register("totemus_puzzle",
            () -> EntityType.Builder.of(TotemusPuzzleEntity::new, MobCategory.MONSTER)
                    .sized(0.5F, 2.25F)
                    .fireImmune()
                    .build("totemus_puzzle"));

    public static void init(IEventBus bus) {
        ENTITIES.register(bus);
    }
}
