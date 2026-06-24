package com.benji.netherman;

import com.benji.netherman.common.network.ModMessages;
import com.benji.netherman.config.AzazelConfig;
import com.benji.netherman.init.*;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;

@Mod(NetherExp.MODID)
public class NetherExp {
    public static final String MODID = "netherman";
    private static final Logger LOGGER = LogUtils.getLogger();

    @Contract("_ -> new")
    public static ResourceLocation location(String path) {
        if (path.contains(":")) {
            return ResourceLocation.tryParse(path);
        }
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }

    public NetherExp(IEventBus modEventBus, ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.COMMON, AzazelConfig.SPEC);

        ModBlocks.init(modEventBus);
        ModItems.init(modEventBus);
        ModBlockEntities.init(modEventBus);
        ModEntities.init(modEventBus);
        ModEffects.init(modEventBus);
        ModCreativeTabs.init(modEventBus);
        ModRecipeSerializers.init(modEventBus);
        ModStructureTypes.init(modEventBus);
        ModSounds.SOUNDS.register(modEventBus);

        modEventBus.addListener(ModMessages::register);
    }
}
