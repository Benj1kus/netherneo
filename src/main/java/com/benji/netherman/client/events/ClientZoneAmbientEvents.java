package com.benji.netherman.client.events;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.sound.ZoneAmbientSoundInstance;
import com.benji.netherman.init.ModEffects;
import com.benji.netherman.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = NetherExp.MODID, value = Dist.CLIENT)
public class ClientZoneAmbientEvents {

    private static ZoneAmbientSoundInstance currentAmbientSound = null;
    private static int lastZoneType = -1;

    private static int bossMusicTimer = 0;
    private static boolean isPlayingBossIntro = false;
    private static int shakeTimer = 0;
    private static float shakeIntensity = 0.0F;

    private static int screenCooldown = 0;

    public static void startScreenShake(int ticks, float intensity) {
        shakeTimer = ticks;
        shakeIntensity = intensity;
    }

    public static void flagClick() {
        screenCooldown = 40;
    }

    @SubscribeEvent
    public static void onCameraSetup(net.neoforged.neoforge.client.event.ViewportEvent.ComputeCameraAngles event) {
        if (shakeTimer > 0) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.isPaused()) return;

            shakeTimer--;

            float shakeX = (mc.player.getRandom().nextFloat() - 0.5F) * shakeIntensity;
            float shakeY = (mc.player.getRandom().nextFloat() - 0.5F) * shakeIntensity;
            float shakeZ = (mc.player.getRandom().nextFloat() - 0.5F) * shakeIntensity;

            event.setPitch(event.getPitch() + shakeX);
            event.setYaw(event.getYaw() + shakeY);
            event.setRoll(event.getRoll() + shakeZ);

            if (shakeTimer < 10) {
                shakeIntensity *= 0.8F;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity() != Minecraft.getInstance().player) return;
        LocalPlayer player = (LocalPlayer) event.getEntity();

        if (screenCooldown > 0) {
            screenCooldown--;
        }

        if (Minecraft.getInstance().screen == null && screenCooldown <= 0) {
            for (com.benji.netherman.common.entity.AzazelHumanEntity boss : player.level().getEntitiesOfClass(com.benji.netherman.common.entity.AzazelHumanEntity.class, player.getBoundingBox().inflate(20.0D))) {
                int state = boss.getEntityData().get(com.benji.netherman.common.entity.AzazelHumanEntity.BOSS_STATE);
                if (state == 2 || state == 3 || state == 100) {
                    Minecraft.getInstance().setScreen(new com.benji.netherman.client.gui.AzazelHumanCutsceneScreen(boss));
                    break;
                }
            }
        }

        int currentZoneType = -1;
        Holder<MobEffect> activeEffect = null;

        if (player.hasEffect(ModEffects.PRAEMIUM)) {
            currentZoneType = 5;
            activeEffect = ModEffects.PRAEMIUM;
        } else if (player.hasEffect(ModEffects.ALERTNESS)) {
            currentZoneType = 4;
            activeEffect = ModEffects.ALERTNESS;
        } else if (player.hasEffect(ModEffects.ANXIETY)) {
            currentZoneType = 3;
            activeEffect = ModEffects.ANXIETY;
        } else if (player.hasEffect(ModEffects.FAITH)) {
            currentZoneType = 2;
            activeEffect = ModEffects.FAITH;
        } else if (player.hasEffect(ModEffects.EXCITEMENT)) {
            currentZoneType = 1;
            activeEffect = ModEffects.EXCITEMENT;
        } else if (player.hasEffect(ModEffects.FEAR)) {
            currentZoneType = 0;
            activeEffect = ModEffects.FEAR;
        }

        if ((currentZoneType == 3 || currentZoneType == 5) && isPlayingBossIntro) {
            bossMusicTimer--;
            if (bossMusicTimer <= 0) {
                isPlayingBossIntro = false;
                if (currentAmbientSound != null) {
                    Minecraft.getInstance().getSoundManager().stop(currentAmbientSound);
                }

                var loopSound = (currentZoneType == 5) ? ModSounds.AZAZEL_FIGHT_LOOP.get() : ModSounds.BOSS_FIGHT_LOOP.get();
                currentAmbientSound = new ZoneAmbientSoundInstance(loopSound, player, activeEffect, true);
                Minecraft.getInstance().getSoundManager().play(currentAmbientSound);
            }
        }

        if (currentZoneType != lastZoneType) {
            if (currentAmbientSound != null) {
                Minecraft.getInstance().getSoundManager().stop(currentAmbientSound);
                currentAmbientSound = null;
            }

            if (currentZoneType != -1) {
                if (currentZoneType == 3 || currentZoneType == 5) {
                    var introSound = (currentZoneType == 5) ? ModSounds.AZAZEL_FIGHT.get() : ModSounds.BOSS_FIGHT.get();
                    currentAmbientSound = new ZoneAmbientSoundInstance(introSound, player, activeEffect, false);
                    Minecraft.getInstance().getSoundManager().play(currentAmbientSound);

                    bossMusicTimer = (currentZoneType == 5) ? 6020 : 2900;
                    isPlayingBossIntro = true;
                } else {
                    var soundEvent = switch (currentZoneType) {
                        case 4 -> ModSounds.MAZE_AMBIENT.get();
                        case 2 -> ModSounds.CHURCH_AMBIENT.get();
                        case 1 -> ModSounds.CITY_AMBIENT.get();
                        default -> ModSounds.CAVE_AMBIENT.get();
                    };
                    isPlayingBossIntro = false;
                    currentAmbientSound = new ZoneAmbientSoundInstance(soundEvent, player, activeEffect, true);
                    Minecraft.getInstance().getSoundManager().play(currentAmbientSound);
                }
            } else {
                isPlayingBossIntro = false;
            }

            lastZoneType = currentZoneType;
        }

        if (currentZoneType == -1 && lastZoneType != -1) {
            lastZoneType = -1;
            currentAmbientSound = null;
            isPlayingBossIntro = false;
        }
    }
}