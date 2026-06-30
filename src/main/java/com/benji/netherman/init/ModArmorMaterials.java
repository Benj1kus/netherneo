package com.benji.netherman.init;

import com.benji.netherman.NetherExp;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;

public class ModArmorMaterials {
    public static final DeferredRegister<ArmorMaterial> ARMOR_MATERIALS = DeferredRegister.create(Registries.ARMOR_MATERIAL, NetherExp.MODID);

    public static final Holder<ArmorMaterial> AZAZEL_MATERIAL = ARMOR_MATERIALS.register("azazel_armor", () -> new ArmorMaterial(
            Util.make(new EnumMap<>(ArmorItem.Type.class), map -> {
                map.put(ArmorItem.Type.HELMET, 3);
                map.put(ArmorItem.Type.CHESTPLATE, 8);
                map.put(ArmorItem.Type.LEGGINGS, 6);
                map.put(ArmorItem.Type.BOOTS, 3);
            }),
            15,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            () -> Ingredient.of(Items.NETHERITE_INGOT),
            List.of(new ArmorMaterial.Layer(NetherExp.location("azazel_armor"))),
            5.0F,
            0.1F
    ));

    public static void init(net.neoforged.bus.api.IEventBus bus) {
        ARMOR_MATERIALS.register(bus);
    }
}