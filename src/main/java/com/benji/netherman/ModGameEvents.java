package com.benji.netherman;

import com.benji.netherman.NetherExp;
import com.benji.netherman.client.renderer.AzazelWingTrails;
import com.benji.netherman.init.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.WeakHashMap;

@EventBusSubscriber(modid = NetherExp.MODID)
public class ModGameEvents {

    public static class DrillData {
        public int ticks;
        public Vec3 direction;

        public DrillData(int ticks, Vec3 direction) {
            this.ticks = ticks;
            this.direction = direction;
        }
    }

    private static final WeakHashMap<Player, Integer> BOOST_TRAILS = new WeakHashMap<>();
    public static final WeakHashMap<Player, DrillData> ACTIVE_DRILLS = new WeakHashMap<>();

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {

            if (event.getSource().is(DamageTypes.FALL)) {
                boolean hasFullSet = player.getItemBySlot(EquipmentSlot.HEAD).is(ModItems.AZAZEL_HELMET.get()) &&
                        player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.AZAZEL_CHESTPLATE.get()) &&
                        player.getItemBySlot(EquipmentSlot.LEGS).is(ModItems.AZAZEL_LEGGINGS.get()) &&
                        player.getItemBySlot(EquipmentSlot.FEET).is(ModItems.AZAZEL_BOOTS.get());

                if (hasFullSet) {
                    float originalAmount = event.getAmount();
                    event.setAmount(originalAmount * 0.2F);
                }
            }

            if (event.getSource().is(DamageTypes.FLY_INTO_WALL)) {
                if (player.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.AZAZEL_CHESTPLATE.get())) {

                    event.setCanceled(true);

                    Vec3 look = player.getLookAngle().normalize();
                    ACTIVE_DRILLS.put(player, new DrillData(12, look));

                    player.level().playSound(
                            null,
                            player.blockPosition(),
                            SoundEvents.GENERIC_EXPLODE.value(),
                            SoundSource.PLAYERS,
                            1.5F,
                            1.2F
                    );

                    if (player.level().isClientSide()) {
                        AzazelWingTrails.startDrillingSparks(player, 12, look);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();
        DrillData drill = ACTIVE_DRILLS.get(player);

        if (drill != null && drill.ticks > 0) {
            drill.ticks--;

            player.setDeltaMovement(drill.direction.scale(0.85D));
            player.hurtMarked = true;

            if (!player.level().isClientSide() && player.level() instanceof ServerLevel serverLevel) {
                Vec3 targetPos = player.position().add(drill.direction.scale(1.2D));
                BlockPos centerBlock = BlockPos.containing(targetPos.x, targetPos.y + 0.6D, targetPos.z);

                int r = 1;
                for (int x = -r; x <= r; x++) {
                    for (int y = -r; y <= r + 1; y++) {
                        for (int z = -r; z <= r; z++) {
                            BlockPos targetBlock = centerBlock.offset(x, y, z);
                            BlockState state = serverLevel.getBlockState(targetBlock);

                            if (!state.isAir() && state.getDestroySpeed(serverLevel, targetBlock) >= 0) {
                                serverLevel.destroyBlock(targetBlock, true, player);
                            }
                        }
                    }
                }

                // Стандартные огненные партиклы
                serverLevel.sendParticles(ParticleTypes.FLAME, player.getX(), player.getY() + 0.8D, player.getZ(), 6, 0.4D, 0.4D, 0.4D, 0.1D);
                serverLevel.sendParticles(ParticleTypes.LAVA, player.getX(), player.getY() + 0.8D, player.getZ(), 3, 0.3D, 0.3D, 0.3D, 0.1D);
            }

            if (drill.ticks <= 0) {
                ACTIVE_DRILLS.remove(player);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTakeDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            boolean hasShieldInMainHand = player.getMainHandItem().is(ModItems.AZAZEL_SHIELD.get());
            boolean hasShieldInOffHand = player.getOffhandItem().is(ModItems.AZAZEL_SHIELD.get());

            if (hasShieldInMainHand || hasShieldInOffHand) {
                if (!event.getSource().is(DamageTypes.STARVE) &&
                        !event.getSource().is(DamageTypes.FELL_OUT_OF_WORLD) &&
                        !event.getSource().is(DamageTypes.MAGIC)) {

                    if (event.getSource().getSourcePosition() != null) {
                        Vec3 damagePos = event.getSource().getSourcePosition();
                        Vec3 playerLook = player.getLookAngle();
                        Vec3 toDamage = damagePos.subtract(player.position()).normalize();

                        if (playerLook.dot(toDamage) > 0.0) {
                            event.setCanceled(true);

                            player.level().playSound(null, player.blockPosition(), com.benji.netherman.init.ModSounds.DODGE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

                            ItemStack shieldStack = hasShieldInMainHand ? player.getMainHandItem() : player.getOffhandItem();
                            EquipmentSlot slot = hasShieldInMainHand ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;

                            int durabilityToConsume = Math.max(1, (int) event.getAmount());
                            shieldStack.hurtAndBreak(durabilityToConsume, player, slot);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerRightClickEmpty(PlayerInteractEvent.RightClickEmpty event) {
        Player player = event.getEntity();

        if (player.isFallFlying()) {
            ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);

            if (chestStack.is(ModItems.AZAZEL_CHESTPLATE.get())) {
                if (player.getItemInHand(event.getHand()).isEmpty()) {

                    Vec3 look = player.getLookAngle();
                    player.setDeltaMovement(player.getDeltaMovement().add(look.scale(0.85D)));

                    player.level().playSound(
                            player,
                            player.blockPosition(),
                            SoundEvents.ENDER_DRAGON_FLAP,
                            SoundSource.PLAYERS,
                            1.5F,
                            1.0F
                    );

                    player.level().playSound(
                            player,
                            player.blockPosition(),
                            SoundEvents.PHANTOM_FLAP,
                            SoundSource.PLAYERS,
                            1.0F,
                            1.3F
                    );

                    BOOST_TRAILS.put(player, 35);
                    com.benji.netherman.client.renderer.AzazelWingTrails.spawnShockwave(player);
                    com.benji.netherman.client.renderer.AzazelWingTrails.startBoost(player, 160);
                }
            }
        }
    }
}