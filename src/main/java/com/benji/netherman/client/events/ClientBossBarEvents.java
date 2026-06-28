package com.benji.netherman.client.events;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.entity.AzazelEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent;
import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(modid = NetherExp.MODID, value = Dist.CLIENT)
public class ClientBossBarEvents {

    
    private static final ResourceLocation FRAME_TEXTURE = NetherExp.location("textures/gui/azazel_frame.png");
    private static final ResourceLocation PROGRESS_TEXTURE = NetherExp.location("textures/gui/azazel_progress.png");
    private static final ResourceLocation SUN_TEXTURE = NetherExp.location("textures/gui/azazel_frame_sun.png");
    private static final ResourceLocation CINEMATIC_TEXTURE = NetherExp.location("textures/gui/cinematic.png");
    private static final ResourceLocation SUN_LOWHP_TEXTURE = NetherExp.location("textures/gui/azazel_frame_sun_lowhp.png");

    @SubscribeEvent
    public static void onRenderMercyText(RenderGuiLayerEvent.Post event) {
        if (!event.getName().equals(VanillaGuiLayers.HOTBAR)) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        com.benji.netherman.common.entity.AzazelEntity azazel = null;
        for (AzazelEntity entity : mc.level.getEntitiesOfClass(AzazelEntity.class, mc.player.getBoundingBox().inflate(30.0D))) {
            int state = entity.getEntityData().get(com.benji.netherman.common.entity.AzazelEntity.ATTACK_STATE);
            
            if (state >= 6 && state <= 9) {
                azazel = entity;
                break;
            }
        }

        if (azazel != null) {
            int state = azazel.getEntityData().get(com.benji.netherman.common.entity.AzazelEntity.ATTACK_STATE);
            GuiGraphics graphics = event.getGuiGraphics();
            int screenWidth = graphics.guiWidth();
            int screenHeight = graphics.guiHeight();

            
            if (state == 8 || state == 9) {
                graphics.blit(CINEMATIC_TEXTURE, 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
            }
            
            if (state == 8) return;

            int mercyTick = azazel.getEntityData().get(com.benji.netherman.common.entity.AzazelEntity.MERCY_TICK);
            String fullText = "";

            
            if (state == 6) fullText = I18n.get("entity.netherman.azazel.surrender");
            else if (state == 7) fullText = I18n.get("entity.netherman.azazel.mercy");
            else if (state == 9) fullText = I18n.get("entity.netherman.azazel.death");

            int charsToShow = Math.min(fullText.length(), mercyTick / 2);
            String textToRender = fullText.substring(0, charsToShow);

            int boxWidth = mc.font.width(fullText) + 20;
            int boxHeight = 24;
            int boxX = (screenWidth - boxWidth) / 2;
            int boxY = screenHeight - 120;

            graphics.fill(boxX, boxY, boxX + boxWidth, boxY + boxHeight, 0x99000000);
            graphics.drawString(mc.font, textToRender, boxX + 10, boxY + 8, 0xFFFF55, false);
        }
    }

    @SubscribeEvent
    public static void onRenderBossBar(CustomizeGuiOverlayEvent.BossEventProgress event) {
        Component name = event.getBossEvent().getName();

        
        if (name.getString().contains("Azazel")) {

            
            event.setCanceled(true);

            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.level == null) return;

            
            com.benji.netherman.common.entity.AzazelEntity azazel = null;
            
            for (AzazelEntity entity : mc.level.getEntitiesOfClass(AzazelEntity.class, mc.player.getBoundingBox().inflate(100.0D))) {
                azazel = entity;
                break;
            }

            GuiGraphics guiGraphics = event.getGuiGraphics();
            int screenWidth = guiGraphics.guiWidth();
            int y = event.getY();

            
            int frameWidth = 186;
            int frameHeight = 42;
            int frameX = (screenWidth / 2) - (frameWidth / 2);
            int frameY = y;

            
            guiGraphics.blit(FRAME_TEXTURE, frameX, frameY, 0, 0, frameWidth, frameHeight, frameWidth, frameHeight);

            
            float progress = event.getBossEvent().getProgress();
            int progressMaxWidth = 182;
            int progressHeight = 5;

            int currentProgressWidth = (int) (progressMaxWidth * progress);
            int progressX = frameX + 8; 
            int progressOffsetY = 18;
            int progressY = frameY + progressOffsetY;

            
            if (currentProgressWidth > 0) {
                guiGraphics.blit(PROGRESS_TEXTURE, progressX, progressY, 0, 0, currentProgressWidth, progressHeight, progressMaxWidth, progressHeight);
            }

            
            ResourceLocation currentSunTexture = SUN_TEXTURE;
            if (azazel != null && azazel.getEntityData().get(AzazelEntity.PHASE_STATE) == 2) {
                currentSunTexture = SUN_LOWHP_TEXTURE; 
            }

            
            guiGraphics.blit(currentSunTexture, frameX, frameY, 0, 0, frameWidth, frameHeight, frameWidth, frameHeight);

            
            event.setIncrement(frameHeight + 5);
        }
    }
}
