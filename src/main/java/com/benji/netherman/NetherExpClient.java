package com.benji.netherman;

import com.benji.netherman.client.ManipulationOverlay;
import com.benji.netherman.client.renderer.*;
import com.benji.netherman.client.renderer.entity.*;
import com.benji.netherman.client.renderer.item.GeoBlockItemRenderer;
import com.benji.netherman.common.item.GeoBlockItem;
import com.benji.netherman.init.ModBlockEntities;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;

@Mod(value = NetherExp.MODID, dist = Dist.CLIENT)
public class NetherExpClient {

    public NetherExpClient(IEventBus modBus, ModContainer container) {
        modBus.addListener(this::clientInit);
        modBus.addListener(this::registerClientExtensions);
        modBus.addListener(this::registerGuiOverlays);
        modBus.addListener(this::registerRenderers);
    }

    public void clientInit(FMLClientSetupEvent event) {
        registerModelPredicates();

        BlockEntityRenderers.register(ModBlockEntities.LABYRINTH_TELEPORT.get(), LabyrinthTeleportRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOIDMIDCORNERNETHER.get(), VoidNetherMidCornerRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOIDCORNERNETHER.get(), VoidNetherCornerRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.VOIDMIDNETHER.get(), VoidNetherMidRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.SAMSONITE_BELL.get(), SamsoniteBellRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.TRAPHIVE.get(), TraphiveRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.EYE.get(), EyeRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.ALTAR.get(), AltarRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.GRAND_DOOR.get(), GrandDoorRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.POINTED_BLACKSTONE.get(), PointedBlackstoneRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.MAZE_DOOR.get(), MazeDoorRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FACE_PUZZLE_LEFT_DOWN.get(), FacePuzzleLeftDownRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FACE_PUZZLE_RIGHT_DOWN.get(), FacePuzzleRightDownRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FACE_PUZZLE_LEFT_UP.get(), FacePuzzleLeftUpRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.FACE_PUZZLE_RIGHT_UP.get(), FacePuzzleRightUpRenderer::new);
    }

    public void registerClientExtensions(RegisterClientExtensionsEvent event) {
        for (var block : ModItems.getItems()) {
            if (block.get() instanceof GeoBlockItem geoBlockItem) {
                event.registerItem(new IClientItemExtensions() {
                    @Override
                    public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {return new GeoBlockItemRenderer(geoBlockItem);}
                }, geoBlockItem);
            }
        }
    }

    public void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.AZAZEL_GUIDE_BOOK.get(), AzazelGuideBookRenderer::new);
        event.registerEntityRenderer(ModEntities.TOTEMUS_PUZZLE.get(), TotemusPuzzleRenderer::new);
        event.registerEntityRenderer(ModEntities.GILDED_GOLEM.get(), GildedGolemRenderer::new);
        event.registerEntityRenderer(ModEntities.CRIMSON_ARROW.get(), CrimsonArrowRenderer::new);
        event.registerEntityRenderer(ModEntities.AZAZEL.get(), AzazelRenderer::new);
        event.registerEntityRenderer(ModEntities.LASER.get(), LaserRenderer::new);
        event.registerEntityRenderer(ModEntities.STATUE_BOSSUNIT.get(), StatueBossunitRenderer::new);
        event.registerEntityRenderer(ModEntities.BLACKSMITH.get(), BlacksmithRenderer::new);
        event.registerEntityRenderer(ModEntities.DOCTOR.get(), DoctorRenderer::new);
        event.registerEntityRenderer(ModEntities.AZAZEL_HUMAN.get(), AzazelHumanRenderer::new);
        event.registerEntityRenderer(ModEntities.SPLASH_ENTITY.get(), AzazelSplashRenderer::new);
        event.registerEntityRenderer(ModEntities.SPIKE_PROJECTILE_ENTITY.get(), AzazelSpikeProjectileRenderer::new);
        event.registerEntityRenderer(ModEntities.SPIKE_ENTITY.get(), AzazelSpikeRenderer::new);
        event.registerEntityRenderer(ModEntities.TRADER.get(), TraderRenderer::new);
        event.registerEntityRenderer(ModEntities.STATUE.get(), StatueRenderer::new);
        event.registerEntityRenderer(ModEntities.BELIEVER.get(), BelieverRenderer::new);
        event.registerEntityRenderer(ModEntities.BELIEVER_VILLAGER.get(), BelieverVillagerRenderer::new);
        event.registerEntityRenderer(ModEntities.PIGLIN_PRISONER.get(), PiglinPrisonerRenderer::new);
        event.registerEntityRenderer(ModEntities.VILLAGER_PRISONER.get(), VillagerPrisonerRenderer::new);
        event.registerEntityRenderer(ModEntities.MANIPULATOR.get(), ManipulatorRenderer::new);
        event.registerEntityRenderer(ModEntities.WELCOMER.get(), WelcomerRenderer::new);
        event.registerEntityRenderer(ModEntities.GUARDIAN.get(), GuardianRenderer::new);
        event.registerEntityRenderer(ModEntities.GHASTLY.get(), GhastlyRenderer::new);
        event.registerEntityRenderer(ModEntities.BELL_GUARDIAN.get(), BellGuardianRenderer::new);
    }

    public void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(NetherExp.location("manipulation_overlay"), ManipulationOverlay.HUD_OVERLAY);
    }

    public static void registerModelPredicates() {
        ItemProperties.register(ModItems.ALTAR_COMPASS_KEY.get(), ResourceLocation.withDefaultNamespace("angle"), (stack, level, entity, seed) -> {
            if (entity == null && !stack.isFramed()) return 0.0F;

            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            CompoundTag tag = customData.copyTag();

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
    }
}
