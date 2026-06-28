package com.benji.netherman.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class AzazelTrophyItem extends BlockItem implements Equipable {

    public AzazelTrophyItem(Block block, Properties properties) {
        super(block, properties.fireResistant().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Component trophy = Component.translatable("tooltip.netherman.trophy")
                .withStyle(ChatFormatting.YELLOW);

        tooltipComponents.add(Component.translatable("tooltip.netherman.trophy.line1", trophy)
                .withStyle(ChatFormatting.GOLD));

        tooltipComponents.add(trophy);
    }

    @Override
    public EquipmentSlot getEquipmentSlot() {
        return EquipmentSlot.HEAD;
    }
}
