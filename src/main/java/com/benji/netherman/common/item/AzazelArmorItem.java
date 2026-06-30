package com.benji.netherman.common.item;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelArmorItem extends ArmorItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AzazelArmorItem(Holder<ArmorMaterial> material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties.fireResistant());
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        return this.getType() == ArmorItem.Type.CHESTPLATE;
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (!entity.level().isClientSide) {
            if ((flightTicks + 1) % 20 == 0) {
                // ИСПРАВЛЕНИЕ: Новый синтаксис 1.21.1
                stack.hurtAndBreak(1, entity, EquipmentSlot.CHEST);
            }
        }
        return true;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 10, event -> {
            net.minecraft.world.entity.Entity entity = event.getData(DataTickets.ENTITY);

            if (entity instanceof LivingEntity wearer) {
                if (wearer.isFallFlying()) {
                    return event.setAndContinue(RawAnimation.begin().thenLoop("wings_fly"));
                } else if (wearer.fallDistance > 0.5F && !wearer.onGround()) {
                    return event.setAndContinue(RawAnimation.begin().thenLoop("fall"));
                } else {
                    return event.setAndContinue(RawAnimation.begin().thenLoop("wings_idle"));
                }
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}