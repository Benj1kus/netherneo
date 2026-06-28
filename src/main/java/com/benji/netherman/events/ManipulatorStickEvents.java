package com.benji.netherman.events;

import com.benji.netherman.common.entity.AzazelEntity;
import com.benji.netherman.init.ModItems;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

import java.util.UUID;

@EventBusSubscriber(modid = "netherman")
public class ManipulatorStickEvents {

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        Entity entityRaw = event.getEntity();
        if (!(entityRaw instanceof LivingEntity entity)) return;
        if (entity.level().isClientSide() || entity.tickCount % 4 != 0) return;

        
        if (entity instanceof Villager || entity instanceof AbstractPiglin) {
            Player scaringPlayer = entity.level().getNearestPlayer(entity.getX(), entity.getY(), entity.getZ(), 12.0D,
                    p -> p instanceof Player && (((Player) p).getMainHandItem().is(ModItems.MANIPULATOR_STICK.get()) ||
                            ((Player) p).getOffhandItem().is(ModItems.MANIPULATOR_STICK.get())));

            if (scaringPlayer != null) {
                Vec3 awayPos = DefaultRandomPos.getPosAway((net.minecraft.world.entity.PathfinderMob) entity, 16, 7, scaringPlayer.position());
                if (awayPos != null) {
                    ((net.minecraft.world.entity.PathfinderMob) entity).getNavigation().moveTo(awayPos.x, awayPos.y, awayPos.z, 1.3D);
                }
            }
        }

        
        
        if (entity instanceof WitherSkeleton skeleton && skeleton.getPersistentData().contains("SummonerUUID")) {
            if (skeleton.getTarget() == null) { 
                java.util.UUID summonerUUID = skeleton.getPersistentData().getUUID("SummonerUUID");

                
                java.util.List<LivingEntity> potentialTargets = skeleton.level().getEntitiesOfClass(LivingEntity.class, skeleton.getBoundingBox().inflate(16.0D),
                        e -> (e instanceof net.minecraft.world.entity.monster.Monster || e instanceof Player) 
                                && !e.getUUID().equals(summonerUUID) 
                                && !(e instanceof AzazelEntity)      
                                && !(e instanceof WitherSkeleton)    
                );

                if (!potentialTargets.isEmpty()) {
                    
                    skeleton.setTarget(potentialTargets.get(0));
                }
            }
        }
    }

    
    @SubscribeEvent
    public static void onTargetChange(LivingChangeTargetEvent event) {
        if (event.getEntity() instanceof WitherSkeleton skeleton) {
            if (skeleton.getPersistentData().contains("SummonerUUID")) {
                UUID summonerUUID = skeleton.getPersistentData().getUUID("SummonerUUID");
                LivingEntity newTarget = event.getNewAboutToBeSetTarget();

                if (newTarget != null) {
                    if (newTarget.getUUID().equals(summonerUUID)) {
                        event.setCanceled(true);
                        return;
                    }

                    boolean shouldAttack = newTarget instanceof Player || newTarget instanceof Monster;
                    if (newTarget instanceof AzazelEntity || newTarget instanceof WitherSkeleton) {
                        shouldAttack = false;
                    }

                    if (!shouldAttack) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
