package com.benji.netherman;

import com.benji.netherman.NetherExp;
import com.benji.netherman.init.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.WeakHashMap;

@EventBusSubscriber(modid = NetherExp.MODID)
public class ModGameEvents {

    private static final WeakHashMap<Player, Integer> BOOST_TRAILS = new WeakHashMap<>();

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
                }
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Pre event) {
        Player player = event.getEntity();

        if (player.level().isClientSide()) {
            Integer ticks = BOOST_TRAILS.get(player);

            if (ticks != null && ticks > 0) {
                BOOST_TRAILS.put(player, ticks - 1);

                Vec3 look = player.getLookAngle();
                Vec3 up = new Vec3(0, 1, 0);
                Vec3 rightOffset = look.cross(up).normalize().scale(0.65D);
                Vec3 centerPos = player.position().add(0, 0.4D, 0);

                Vec3 leftTrail = centerPos.add(rightOffset).subtract(look.scale(0.4D));
                Vec3 rightTrail = centerPos.subtract(rightOffset).subtract(look.scale(0.4D));

                player.level().addParticle(ParticleTypes.CLOUD,
                        leftTrail.x, leftTrail.y, leftTrail.z, 0, 0, 0);
                player.level().addParticle(ParticleTypes.CLOUD,
                        rightTrail.x, rightTrail.y, rightTrail.z, 0, 0, 0);
            } else if (ticks != null) {
                BOOST_TRAILS.remove(player);
            }
        }
    }
}