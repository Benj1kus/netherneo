package com.benji.netherman.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.joml.Matrix4f;

import java.util.*;

@EventBusSubscriber(modid = com.benji.netherman.NetherExp.MODID, value = Dist.CLIENT)
public class AzazelWingTrails {

    public static class TrailData {
        public int ticksRemaining = 0;
        public LinkedList<Vec3> mainLeft = new LinkedList<>();
        public LinkedList<Vec3> mainRight = new LinkedList<>();
        public LinkedList<Vec3> addLeft = new LinkedList<>();
        public LinkedList<Vec3> addRight = new LinkedList<>();
    }

    public static class ShockwaveData {
        public final Vec3 center, right, backUp;
        public int age = 0;
        public final int maxAge = 8;

        public ShockwaveData(Vec3 center, Vec3 right, Vec3 backUp) {
            this.center = center; this.right = right; this.backUp = backUp;
        }
    }

    public static class SparkData {
        public Vec3 pos;
        public final Vec3 velocity;
        public int age = 0;
        public final int maxAge, color;

        public SparkData(Vec3 pos, Vec3 velocity, int maxAge, int color) {
            this.pos = pos; this.velocity = velocity; this.maxAge = maxAge; this.color = color;
        }
    }

    public static class SparkDrillData {
        public int ticksRemaining;
        public Vec3 direction;

        public SparkDrillData(int ticks, Vec3 dir) {
            this.ticksRemaining = ticks;
            this.direction = dir;
        }
    }

    private static final Map<UUID, TrailData> TRAILS = new HashMap<>();
    private static final Map<UUID, SparkDrillData> ACTIVE_SPARK_DRILLS = new HashMap<>();
    private static final List<ShockwaveData> SHOCKWAVES = new ArrayList<>();
    private static final List<SparkData> SPARKS = new ArrayList<>();
    private static final int MAX_TRAIL_LENGTH = 15;

    public static void startBoost(Player player, int ticks) {
        TrailData data = TRAILS.computeIfAbsent(player.getUUID(), k -> new TrailData());
        data.ticksRemaining = ticks;
    }

    public static void spawnShockwave(Player player) {
        Vec3 look = player.getLookAngle();
        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();
        Vec3 backUp = right.cross(look).normalize();
        Vec3 center = player.position().add(0, 0.4D, 0).subtract(look.scale(0.01D));
        SHOCKWAVES.add(new ShockwaveData(center, right, backUp));
    }

    public static void startDrillingSparks(Player player, int ticks, Vec3 look) {
        ACTIVE_SPARK_DRILLS.put(player.getUUID(), new SparkDrillData(ticks, look));
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        TRAILS.clear();
        SHOCKWAVES.clear();
        SPARKS.clear();
        ACTIVE_SPARK_DRILLS.clear();
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide()) return;

        UUID uuid = player.getUUID();
        TrailData data = TRAILS.get(uuid);

        if (!player.isAlive()) {
            if (data != null) data.ticksRemaining = 0;
            ACTIVE_SPARK_DRILLS.remove(uuid);
        }

        if (data != null) {
            if (data.ticksRemaining > 0) {
                data.ticksRemaining--;

                Vec3 look = player.getLookAngle();
                Vec3 up = new Vec3(0, 1, 0);
                Vec3 right = look.cross(up).normalize();
                Vec3 backUp = right.cross(look).normalize();
                Vec3 center = player.position().add(0, 0.4D, 0);

                Vec3 mainRightPos = center.add(right.scale(2.8D)).subtract(look.scale(0.3D)).add(backUp.scale(0.2D));
                Vec3 mainLeftPos  = center.subtract(right.scale(2.8D)).subtract(look.scale(0.3D)).add(backUp.scale(0.2D));
                Vec3 addRightPos = center.add(right.scale(1.5D)).subtract(look.scale(0.1D)).subtract(backUp.scale(0.4D));
                Vec3 addLeftPos  = center.subtract(right.scale(1.5D)).subtract(look.scale(0.1D)).subtract(backUp.scale(0.4D));

                addPoint(data.mainLeft, mainLeftPos);
                addPoint(data.mainRight, mainRightPos);
                addPoint(data.addLeft, addLeftPos);
                addPoint(data.addRight, addRightPos);
            } else {
                shrinkTrail(data.mainLeft);
                shrinkTrail(data.mainRight);
                shrinkTrail(data.addLeft);
                shrinkTrail(data.addRight);
                if (data.mainLeft.isEmpty()) TRAILS.remove(uuid);
            }
        }

        SparkDrillData drillData = ACTIVE_SPARK_DRILLS.get(uuid);
        if (drillData != null) {
            if (drillData.ticksRemaining > 0 && player.isAlive()) {
                drillData.ticksRemaining--;
                Vec3 origin = player.position().add(0, 0.8D, 0).add(drillData.direction.scale(0.5D));
                RandomSource rand = player.getRandom();

                for (int i = 0; i < 8; i++) {
                    Vec3 sparkVel = drillData.direction.scale(0.15D).add(
                            (rand.nextDouble() - 0.5D) * 0.5D,
                            (rand.nextDouble() - 0.5D) * 0.5D,
                            (rand.nextDouble() - 0.5D) * 0.5D
                    );

                    int maxAge = 6 + rand.nextInt(10);
                    int colorIndex = rand.nextInt(3);
                    int color = colorIndex == 0 ? 0xFF0000 : (colorIndex == 1 ? 0xFF6600 : 0xFFCC00);

                    SPARKS.add(new SparkData(origin, sparkVel, maxAge, color));
                }
            } else {
                ACTIVE_SPARK_DRILLS.remove(uuid);
            }
        }
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Iterator<ShockwaveData> iterator = SHOCKWAVES.iterator();
        while (iterator.hasNext()) {
            ShockwaveData wave = iterator.next();
            wave.age++;
            if (wave.age >= wave.maxAge) iterator.remove();
        }

        Iterator<SparkData> sparkIterator = SPARKS.iterator();
        while (sparkIterator.hasNext()) {
            SparkData spark = sparkIterator.next();
            spark.pos = spark.pos.add(spark.velocity);
            spark.age++;
            if (spark.age >= spark.maxAge) sparkIterator.remove();
        }
    }

    private static void addPoint(LinkedList<Vec3> list, Vec3 point) {
        list.addFirst(point);
        if (list.size() > MAX_TRAIL_LENGTH) list.removeLast();
    }

    private static void shrinkTrail(LinkedList<Vec3> list) {
        if (!list.isEmpty()) list.removeLast();
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT_BLOCKS) return;
        if (TRAILS.isEmpty() && SHOCKWAVES.isEmpty() && SPARKS.isEmpty()) return;

        Vec3 camPos = event.getCamera().getPosition();
        PoseStack poseStack = event.getPoseStack();
        float partialTick = event.getPartialTick().getGameTimeDeltaTicks();

        RenderSystem.disableCull();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.depthMask(false);

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder buffer = tesselator.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        poseStack.pushPose();
        poseStack.translate(-camPos.x, -camPos.y, -camPos.z);
        Matrix4f matrix = poseStack.last().pose();

        if (!TRAILS.isEmpty()) {
            for (Map.Entry<UUID, TrailData> entry : TRAILS.entrySet()) {
                TrailData data = entry.getValue();
                drawRibbon(buffer, matrix, data.mainLeft, camPos, 0.02F);
                drawRibbon(buffer, matrix, data.mainRight, camPos, 0.02F);
                drawRibbon(buffer, matrix, data.addLeft, camPos, 0.02F);
                drawRibbon(buffer, matrix, data.addRight, camPos, 0.02F);

                if (data.ticksRemaining > 0 && Minecraft.getInstance().level != null) {
                    Player player = Minecraft.getInstance().level.getPlayerByUUID(entry.getKey());
                    if (player != null && player.isAlive()) {
                        Vec3 playerPos = player.getPosition(partialTick).add(0, 0.4D, 0);
                        Vec3 look = player.getViewVector(partialTick);
                        Vec3 right = look.cross(new Vec3(0, 1, 0)).normalize();
                        Vec3 backUp = right.cross(look).normalize();

                        float boostProgress = Math.min(1.0F, data.ticksRemaining / 15.0F);
                        drawVaporCone(buffer, matrix, playerPos, look, right, backUp, boostProgress);
                    }
                }
            }
        }

        if (!SHOCKWAVES.isEmpty()) {
            for (ShockwaveData wave : SHOCKWAVES) {
                drawShockwaveRing(buffer, matrix, wave, partialTick);
            }
        }

        if (!SPARKS.isEmpty()) {
            for (SparkData spark : SPARKS) {
                drawSparkLine(buffer, matrix, spark, camPos);
            }
        }

        var meshData = buffer.build();
        if (meshData != null) {
            BufferUploader.drawWithShader(meshData);
        }

        poseStack.popPose();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    private static void drawSparkLine(VertexConsumer buffer, Matrix4f matrix, SparkData spark, Vec3 camPos) {
        float progress = (float) spark.age / spark.maxAge;
        int alpha = (int) (255 * (1.0F - progress));
        int r = (spark.color >> 16) & 0xFF, g = (spark.color >> 8) & 0xFF, b = spark.color & 0xFF;

        Vec3 p1 = spark.pos;
        Vec3 p2 = spark.pos.subtract(spark.velocity.scale(0.75D));
        float width = 0.025F * (1.0F - progress);

        Vec3 toCam = camPos.subtract(p1).normalize();
        Vec3 dir = p1.subtract(p2).normalize();
        Vec3 right = dir.cross(toCam).normalize().scale(width);

        addVertex(buffer, matrix, p1.add(right), r, g, b, alpha);
        addVertex(buffer, matrix, p1.subtract(right), r, g, b, alpha);
        addVertex(buffer, matrix, p2.subtract(right), r, g, b, alpha);
        addVertex(buffer, matrix, p2.add(right), r, g, b, alpha);
    }

    private static void drawVaporCone(VertexConsumer buffer, Matrix4f matrix, Vec3 center, Vec3 look, Vec3 right, Vec3 backUp, float boostProgress) {
        if (boostProgress <= 0.01F) return;
        int maxAlpha = (int) (60 * boostProgress);
        int rings = 6, segments = 24;
        float length = 0.3F, maxRadius = 1.7F;
        Vec3[] prevRing = new Vec3[segments + 1];

        for (int rIndex = 0; rIndex <= rings; rIndex++) {
            float rProgress = (float) rIndex / rings;
            float currentRadius = maxRadius * (float)Math.sqrt(rProgress);
            float zOffset = length * (1.0F - rProgress) - 0.3F;
            Vec3 currentRingCenter = center.add(look.scale(zOffset));
            Vec3[] currentRing = new Vec3[segments + 1];

            for (int i = 0; i <= segments; i++) {
                double angle = (i * 2.0 * Math.PI) / segments;
                Vec3 dirVec = right.scale(Math.cos(angle)).add(backUp.scale(Math.sin(angle)));
                currentRing[i] = currentRingCenter.add(dirVec.scale(currentRadius));

                if (rIndex > 0 && i > 0) {
                    int a1 = (int) (maxAlpha * (1.0F - (float)(rIndex - 1) / rings));
                    int a2 = (int) (maxAlpha * (1.0F - (float)rIndex / rings));
                    addVertex(buffer, matrix, prevRing[i - 1], 255, 255, 255, a1);
                    addVertex(buffer, matrix, currentRing[i - 1], 255, 255, 255, a2);
                    addVertex(buffer, matrix, currentRing[i], 255, 255, 255, a2);
                    addVertex(buffer, matrix, prevRing[i], 255, 255, 255, a1);
                }
            }
            prevRing = currentRing;
        }
    }

    private static void drawRibbon(VertexConsumer buffer, Matrix4f matrix, LinkedList<Vec3> points, Vec3 camPos, float startWidth) {
        if (points.size() < 2) return;
        for (int i = 0; i < points.size() - 1; i++) {
            Vec3 p1 = points.get(i), p2 = points.get(i + 1);
            float p1Prog = (float) i / points.size(), p2Prog = (float) (i + 1) / points.size();
            float w1 = startWidth * (1.0F - p1Prog), w2 = startWidth * (1.0F - p2Prog);
            int a1 = (int) (200 * (1.0F - p1Prog)), a2 = (int) (200 * (1.0F - p2Prog));

            int r1 = (int) lerp(255, 100, p1Prog), g1 = (int) lerp(255, 100, p1Prog), b1 = (int) lerp(255, 120, p1Prog);
            int r2 = (int) lerp(255, 100, p2Prog), g2 = (int) lerp(255, 100, p2Prog), b2 = (int) lerp(255, 120, p2Prog);

            Vec3 right1 = p2.subtract(p1).normalize().cross(camPos.subtract(p1).normalize()).normalize().scale(w1);
            Vec3 right2 = p2.subtract(p1).normalize().cross(camPos.subtract(p2).normalize()).normalize().scale(w2);

            addVertex(buffer, matrix, p1.add(right1), r1, g1, b1, a1);
            addVertex(buffer, matrix, p1.subtract(right1), r1, g1, b1, a1);
            addVertex(buffer, matrix, p2.subtract(right2), r2, g2, b2, a2);
            addVertex(buffer, matrix, p2.add(right2), r2, g2, b2, a2);
        }
    }

    private static void drawShockwaveRing(VertexConsumer buffer, Matrix4f matrix, ShockwaveData wave, float partialTick) {
        float progress = (wave.age + partialTick) / (float) wave.maxAge;
        if (progress > 1.0F) return;

        float maxRadius = 4.5F;
        float currentOuterRadius = 0.2F + (maxRadius - 0.2F) * progress;
        float thickness = 0.4F * (1.0F - progress);
        float currentInnerRadius = Math.max(0.0F, currentOuterRadius - thickness);
        int alpha = (int) (180 * (1.0F - progress));

        int segments = 32;
        Vec3 prevInner = null, prevOuter = null, firstInner = null, firstOuter = null;

        for (int i = 0; i <= segments; i++) {
            double angle = (i * 2.0 * Math.PI) / segments;
            Vec3 dirVec = wave.right.scale(Math.cos(angle)).add(wave.backUp.scale(Math.sin(angle)));
            Vec3 innerPos = wave.center.add(dirVec.scale(currentInnerRadius));
            Vec3 outerPos = wave.center.add(dirVec.scale(currentOuterRadius));

            if (i == 0) {
                firstInner = innerPos; firstOuter = outerPos;
            } else {
                addVertex(buffer, matrix, prevInner, 255, 255, 255, alpha);
                addVertex(buffer, matrix, prevOuter, 120, 120, 140, alpha);
                addVertex(buffer, matrix, outerPos, 120, 120, 140, alpha);
                addVertex(buffer, matrix, innerPos, 255, 255, 255, alpha);
            }
            prevInner = innerPos; prevOuter = outerPos;
        }
        if (prevInner != null && firstInner != null) {
            addVertex(buffer, matrix, prevInner, 255, 255, 255, alpha);
            addVertex(buffer, matrix, prevOuter, 120, 120, 140, alpha);
            addVertex(buffer, matrix, firstOuter, 120, 120, 140, alpha);
            addVertex(buffer, matrix, firstInner, 255, 255, 255, alpha);
        }
    }

    private static void addVertex(VertexConsumer buffer, Matrix4f matrix, Vec3 pos, int r, int g, int b, int a) {
        buffer.addVertex(matrix, (float) pos.x, (float) pos.y, (float) pos.z).setColor(r, g, b, a);
    }

    private static float lerp(float start, float end, float delta) {
        return start + delta * (end - start);
    }
}