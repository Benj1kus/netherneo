package com.benji.netherman.common.network;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class ModMessages {

    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1.0");


        registrar.playToServer(
                AzazelBoostPacket.TYPE,
                AzazelBoostPacket.STREAM_CODEC,
                AzazelBoostPacket::handle
        );

        registrar.playToClient(
                FogSyncS2CPacket.TYPE,
                FogSyncS2CPacket.STREAM_CODEC,
                FogSyncS2CPacket::handleData
        );

        
        registrar.playToClient(
                TotemAnimationPayload.TYPE,
                TotemAnimationPayload.CODEC,
                ClientPayloadHandler::handleCustomTotemAnimation
        );
    }

    
    public static void sendToPlayer(FogSyncS2CPacket message, ServerPlayer player) {
        PacketDistributor.sendToPlayer(player, message);
    }
}
