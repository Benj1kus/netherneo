package com.benji.netherman.common.data;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class TeleportDestinationData extends SavedData {
    private final Set<BlockPos> destinations = new HashSet<>();

    public void addDestination(BlockPos pos) {
        if (destinations.add(pos)) {
            this.setDirty(); 
        }
    }

    public void removeDestination(BlockPos pos) {
        if (destinations.remove(pos)) {
            this.setDirty();
        }
    }

    public BlockPos getClosestDestination(BlockPos sourcePos) {
        if (destinations.isEmpty()) return null;

        BlockPos closest = null;
        double minDistanceSq = Double.MAX_VALUE;

        for (BlockPos pos : destinations) {
            if (pos.equals(sourcePos)) continue;
            double distSq = pos.distSqr(sourcePos);
            if (distSq < minDistanceSq) {
                minDistanceSq = distSq;
                closest = pos;
            }
        }
        return closest;
    }

    @Override
    public CompoundTag save(CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        net.minecraft.nbt.ListTag list = new net.minecraft.nbt.ListTag();
        for (BlockPos pos : destinations) {
            list.add(net.minecraft.nbt.LongTag.valueOf(pos.asLong())); 
        }
        tag.put("Destinations", list);
        return tag;
    }

    public static TeleportDestinationData load(CompoundTag tag, net.minecraft.core.HolderLookup.Provider provider) {
        TeleportDestinationData data = new TeleportDestinationData();
        net.minecraft.nbt.ListTag list = tag.getList("Destinations", net.minecraft.nbt.Tag.TAG_LONG);
        for (int i = 0; i < list.size(); i++) {
            data.destinations.add(BlockPos.of(((net.minecraft.nbt.LongTag) list.get(i)).getAsLong()));
        }
        return data;
    }

    public static TeleportDestinationData get(ServerLevel level) {
        return level.getDataStorage().computeIfAbsent(
                new net.minecraft.world.level.saveddata.SavedData.Factory<>(
                        TeleportDestinationData::new,
                        TeleportDestinationData::load,
                        null
                ),
                "netherman_teleports"
        );
    }
}
