package com.benji.netherman.common.item;

import com.benji.netherman.common.entity.CrimsonArrowEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class CrimsonArrowItem extends ArrowItem {
    public CrimsonArrowItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component arrowb = Component.translatable("tooltip.netherman.arrow")
                .withStyle(ChatFormatting.WHITE);
        tooltipComponents.add(arrowb);
    }

    @Override
    public net.minecraft.world.entity.projectile.AbstractArrow createArrow(Level level, ItemStack stack, LivingEntity shooter, @org.jetbrains.annotations.Nullable ItemStack weapon) {
        return new CrimsonArrowEntity(level, shooter);
    }
}
