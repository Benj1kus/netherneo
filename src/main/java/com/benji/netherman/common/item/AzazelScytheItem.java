package com.benji.netherman.common.item;

import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import com.benji.netherman.common.entity.AzazelSplashEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.List;

public class AzazelScytheItem extends SwordItem implements GeoItem {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public AzazelScytheItem(Properties properties) {
        // ИСПРАВЛЕНИЕ: В 1.21.1 урон и скорость добавляются через атрибуты внутри Properties
        super(AzazelTier.INSTANCE, properties.attributes(SwordItem.createAttributes(AzazelTier.INSTANCE, 19, -3.0F)));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            if (player.isShiftKeyDown()) {
                triggerAnim(player, GeoItem.getOrAssignId(stack, serverLevel), "controller", "spear_mode");
                level.playSound(null, player.blockPosition(), ModSounds.HIRRING.get(), SoundSource.PLAYERS, 1.0F, 1.0F);

                // ИСПРАВЛЕНИЕ: Новый способ записи данных в 1.21.1 (Data Components)
                CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("TransformTimer", 15));
                return InteractionResultHolder.success(stack);
            } else {
                AzazelSplashEntity splash = ModEntities.SPLASH_ENTITY.get().create(level);
                if (splash != null) {
                    Vec3 look = player.getLookAngle();
                    splash.setPos(player.getX() + look.x * 1.5, player.getEyeY() - 0.2, player.getZ() + look.z * 1.5);
                    splash.setOwner(player);
                    splash.shoot(look.x, look.y, look.z, 1.5F, 0.0F);
                    level.addFreshEntity(splash);
                }

                level.playSound(null, player.blockPosition(), ModSounds.SWING_2.get(), SoundSource.PLAYERS, 2.0F, 0.8F);

                // ИСПРАВЛЕНИЕ: Новый синтаксис поломки предмета в 1.21.1
                EquipmentSlot slot = hand == InteractionHand.MAIN_HAND ? EquipmentSlot.MAINHAND : EquipmentSlot.OFFHAND;
                stack.hurtAndBreak(5, player, slot);

                player.getCooldowns().addCooldown(this, 100);
                return InteractionResultHolder.success(stack);
            }
        }
        return InteractionResultHolder.pass(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component scythe = Component.translatable("tooltip.netherman.scythe")
                .withStyle(ChatFormatting.DARK_RED);

        tooltipComponents.add(Component.translatable("tooltip.netherman.scythe.line1", scythe)
                .withStyle(ChatFormatting.GOLD));

        tooltipComponents.add(scythe);
    }

    @Override
    public void postHurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.level().isClientSide() && attacker instanceof Player player) {
            SoundEvent swingSound = player.getRandom().nextBoolean() ? ModSounds.SWING_1.get() : ModSounds.SWING_2.get();
            player.level().playSound(null, player.blockPosition(), swingSound, SoundSource.PLAYERS, 1.5F, 1.0F);
        }
        super.postHurtEnemy(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (!level.isClientSide() && entity instanceof Player player) {

            // ИСПРАВЛЕНИЕ: Чтение данных через компоненты 1.21.1
            CustomData customData = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
            if (customData.contains("TransformTimer")) {
                int timer = customData.copyTag().getInt("TransformTimer");

                if (timer > 0) {
                    int finalTimer = timer - 1;
                    CustomData.update(DataComponents.CUSTOM_DATA, stack, tag -> tag.putInt("TransformTimer", finalTimer));

                    if (finalTimer <= 0) {
                        ItemStack spearStack = new ItemStack(ModItems.AZAZEL_SPEAR.get());
                        // ИСПРАВЛЕНИЕ: Копируем все компоненты (прочность, чары, имя) на новый предмет
                        spearStack.applyComponents(stack.getComponents());
                        CustomData.update(DataComponents.CUSTOM_DATA, spearStack, tag -> tag.remove("TransformTimer"));
                        player.getInventory().setItem(slotId, spearStack);
                    }
                }
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "controller", 0, event -> PlayState.CONTINUE));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() { return this.cache; }
}