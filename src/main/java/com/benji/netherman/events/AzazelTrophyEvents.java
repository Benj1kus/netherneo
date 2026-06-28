package com.benji.netherman.events;

import com.benji.netherman.NetherExp;
import com.benji.netherman.common.network.TotemAnimationPayload;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

@EventBusSubscriber(modid = NetherExp.MODID)
public class AzazelTrophyEvents {

    @SubscribeEvent
    public static void onLivingAttack(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack head = player.getItemBySlot(EquipmentSlot.HEAD);

            
            if ((head.is(ModBlocks.AZAZEL_TROPHY.asItem()) || head.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem()) || head.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem()))
                    && AzazelConfig.MASK_FIRE_IMMUNITY.get()) {
                DamageSource source = event.getSource();
                if (source.is(net.minecraft.tags.DamageTypeTags.IS_FIRE) || source.is(net.minecraft.world.damagesource.DamageTypes.LAVA) || source.is(net.minecraft.world.damagesource.DamageTypes.HOT_FLOOR)) {
                    event.setCanceled(true);
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onPlayerDeath(LivingDeathEvent event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            ItemStack mask = player.getItemBySlot(EquipmentSlot.HEAD);

            if (mask.is(ModBlocks.AZAZEL_TROPHY.asItem()) || mask.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem()) || mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem())) {

                
                int stage = 0;
                if (mask.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem())) stage = 1;
                if (mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem())) stage = 2;

                if (stage < 2) {
                    event.setCanceled(true);

                    
                    Item nextItem = stage == 0 ? ModBlocks.AZAZEL_TROPHY_STAGE2.asItem() : ModBlocks.AZAZEL_TROPHY_STAGE3.asItem();
                    ItemStack newMask = new ItemStack(nextItem);

                    
                    CompoundTag tag = new CompoundTag();
                    tag.putLong("LastRegenTick", player.level().getGameTime());
                    newMask.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                    
                    player.setItemSlot(EquipmentSlot.HEAD, newMask);

                    player.setHealth(2.0F);
                    player.removeAllEffects();
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));

                    ServerLevel level = (ServerLevel) player.level();
                    net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player, new TotemAnimationPayload(new ItemStack(ModItems.CHANCE_TOTEM.get())));

                    level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.AZAZEL_DAMAGE_1.get(), SoundSource.PLAYERS, 1.2F, 1.0F);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 1.0F, 1.0F);
                    level.sendParticles(ParticleTypes.TOTEM_OF_UNDYING, player.getX(), player.getY() + 1.0D, player.getZ(), 64, 0.3D, 0.3D, 0.3D, 0.5D);
                }
                else if (stage == 2) {
                    event.setCanceled(true);
                    mask.shrink(1);
                    player.setHealth(1.0F);
                    player.removeAllEffects();

                    ServerLevel currentLevel = (ServerLevel) player.level();
                    currentLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARMOR_EQUIP_GENERIC.value(), SoundSource.PLAYERS, 1.0F, 0.5F);

                    triggerChanceTotemTeleport(player, currentLevel);
                }
            }
        }
    }

    private static void triggerChanceTotemTeleport(ServerPlayer player, ServerLevel currentLevel) {
        net.neoforged.neoforge.network.PacketDistributor.sendToPlayer(player, new TotemAnimationPayload(new ItemStack(ModItems.CHANCE_TOTEM.get())));
        currentLevel.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 1.0D, player.getZ(), 50, 0.5D, 0.5D, 0.5D, 0.05D);
        ServerLevel respawnLevel = currentLevel.getServer().getLevel(player.getRespawnDimension());
        if (respawnLevel == null) respawnLevel = currentLevel.getServer().overworld();
        BlockPos respawnPos = player.getRespawnPosition();
        float respawnAngle = player.getRespawnAngle();

        if (respawnPos != null) {
            player.teleportTo(respawnLevel, respawnPos.getX() + 0.5, respawnPos.getY() + 1.0, respawnPos.getZ() + 0.5, respawnAngle, 0.0F);
        } else {
            BlockPos sharedSpawn = respawnLevel.getSharedSpawnPos();
            player.teleportTo(respawnLevel, sharedSpawn.getX() + 0.5, sharedSpawn.getY() + 1.0, sharedSpawn.getZ() + 0.5, respawnAngle, 0.0F);
        }

        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 4));
        player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600, 0));
        player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 600, 1));
        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 2));
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 4));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 600, 0));

        respawnLevel.playSound(null, player.blockPosition(), ModSounds.RESPAWN_TOTEM.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
        respawnLevel.sendParticles(ParticleTypes.LARGE_SMOKE, player.getX(), player.getY() + 1.0D, player.getZ(), 50, 0.5D, 0.5D, 0.5D, 0.05D);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (!event.getEntity().level().isClientSide()) {
            ServerPlayer player = (ServerPlayer) event.getEntity();
            ItemStack mask = player.getItemBySlot(EquipmentSlot.HEAD);

            
            if (mask.is(ModBlocks.AZAZEL_TROPHY_STAGE2.asItem()) || mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem())) {
                CustomData data = mask.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                CompoundTag tag = data.copyTag();

                long currentTick = player.level().getGameTime();
                long lastRegenTick = tag.getLong("LastRegenTick");

                if (currentTick - lastRegenTick >= AzazelConfig.MASK_REGEN_COOLDOWN.get()) {

                    
                    Item prevItem = mask.is(ModBlocks.AZAZEL_TROPHY_STAGE3.asItem()) ? ModBlocks.AZAZEL_TROPHY_STAGE2.asItem() : ModBlocks.AZAZEL_TROPHY.asItem();
                    ItemStack newMask = new ItemStack(prevItem);

                    tag.putLong("LastRegenTick", currentTick);
                    newMask.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

                    player.setItemSlot(EquipmentSlot.HEAD, newMask);
                    player.playSound(SoundEvents.AMETHYST_BLOCK_CHIME, 1.0F, 1.2F);

                    ServerLevel level = (ServerLevel) player.level();
                    level.sendParticles(ParticleTypes.HAPPY_VILLAGER, player.getX(), player.getY() + 1.7D, player.getZ(), 15, 0.25D, 0.25D, 0.25D, 0.05D);
                }
            }
        }
    }
}
