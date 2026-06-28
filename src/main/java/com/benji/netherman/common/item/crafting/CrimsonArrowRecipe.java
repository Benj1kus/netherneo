package com.benji.netherman.common.item.crafting;

import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class CrimsonArrowRecipe extends CustomRecipe {
    public CrimsonArrowRecipe(CraftingBookCategory category) {
        super(category);
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        int bottles = 0;
        int arrows = 0;

        for (int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.is(ModItems.CRIMSON_HONEY_BOTTLE.get())) {
                    bottles++;
                } else if (stack.is(Items.ARROW)) {
                    arrows += stack.getCount();
                } else {
                    return false; 
                }
            }
        }
        
        return bottles == 1 && arrows >= 1 && arrows <= 5;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registries) {
        int arrows = 0;
        for (int i = 0; i < input.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (stack.is(Items.ARROW)) {
                arrows += stack.getCount();
            }
        }
        
        return new ItemStack(ModItems.CRIMSON_ARROW.get(), arrows);
    }

    @Override
    public net.minecraft.core.NonNullList<ItemStack> getRemainingItems(CraftingInput input) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(input.size(), ItemStack.EMPTY);

        for (int i = 0; i < remaining.size(); ++i) {
            ItemStack stack = input.getItem(i);
            if (!stack.isEmpty()) {
                
                if (stack.is(ModItems.CRIMSON_HONEY_BOTTLE.get())) {
                    remaining.set(i, new ItemStack(Items.GLASS_BOTTLE));
                }
                
                else if (stack.is(Items.ARROW)) {
                    
                    
                    
                    stack.setCount(1);
                }
            }
        }
        return remaining;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.CRIMSON_ARROW_COATING.get();
    }
}
