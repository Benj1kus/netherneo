package com.benji.netherman.common.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class AzazelTier implements Tier {
    public static final AzazelTier INSTANCE = new AzazelTier();

    @Override
    public int getUses() { return 4200; }

    @Override
    public float getSpeed() { return 9.0F; }

    @Override
    public float getAttackDamageBonus() { return 0.0F; }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_NETHERITE_TOOL;
    }

    @Override
    public int getEnchantmentValue() { return 22; }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(Items.NETHERITE_INGOT);
    }
}