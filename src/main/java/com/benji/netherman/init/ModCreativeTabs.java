package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NetherExp.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> NETHERMAN_TAB = CREATIVE_MODE_TABS.register("netherman_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.AZAZEL_TROPHY.get()))
                    .title(Component.translatable("creativetab.netherman_tab"))
                    .displayItems((parameters, output) -> {
                        for (DeferredHolder<Item, ? extends Item> registry : ModItems.getItems()) {
                            if (registry.get() instanceof BlockItem)
                                continue;
                            output.accept(registry.get());
                        }
                        for (DeferredHolder<Block, ? extends Block> registry : ModBlocks.getBlocks()) {
                            if (registry.get().asItem() == Items.AIR) continue;
                            output.accept(registry.get());
                        }
                    })
                    .build()
    );

    public static void init(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);
    }
}
