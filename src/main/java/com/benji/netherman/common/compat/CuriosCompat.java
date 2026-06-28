package com.benji.netherman.common.compat;

import com.benji.netherman.init.ModItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

import java.util.Optional;

public class CuriosCompat {

    public static ItemStack getTotemFromCurios(Player player) {
        Optional<SlotResult> result = CuriosApi.getCuriosInventory(player).flatMap((iCuriosItemHandler) -> iCuriosItemHandler.findFirstCurio(stack -> stack.is(ModItems.CHANCE_TOTEM.get())));
        return result.map(SlotResult::stack).orElse(null);
    }
}
