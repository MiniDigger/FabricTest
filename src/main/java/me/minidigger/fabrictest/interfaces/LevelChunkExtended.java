package me.minidigger.fabrictest.interfaces;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;

import org.jetbrains.annotations.Nullable;

public interface LevelChunkExtended {
    void replaceWithPacketDataExtended(@Nullable ChunkBiomeContainer chunkBiomeContainer, FriendlyByteBuf friendlyByteBuf, CompoundTag compoundTag, BitStorage bitStorage);
}
