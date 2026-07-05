package com.benji.netherman.common.network;

import com.benji.netherman.NetherExp;
import com.benji.netherman.init.ModItems;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AzazelBoostPacket() implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<AzazelBoostPacket> TYPE =
            new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(NetherExp.MODID, "azazel_boost"));

    public static final StreamCodec<FriendlyByteBuf, AzazelBoostPacket> STREAM_CODEC =
            StreamCodec.unit(new AzazelBoostPacket());

    @Override
    public CustomPacketPayload.Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(final AzazelBoostPacket data, final IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.player() instanceof ServerPlayer player) {
                if (player.isFallFlying()) {
                    ItemStack chestStack = player.getItemBySlot(EquipmentSlot.CHEST);
                    if (chestStack.is(ModItems.AZAZEL_CHESTPLATE.get())) {

                        if (player.level() instanceof ServerLevel serverLevel) {
                            chestStack.hurtAndBreak(10, serverLevel, player, (item) -> {
                                player.onEquippedItemBroken(item, EquipmentSlot.CHEST);
                            });
                        }
                    }
                }
            }
        });
    }
}