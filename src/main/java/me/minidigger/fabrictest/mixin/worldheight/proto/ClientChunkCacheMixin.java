package me.minidigger.fabrictest.mixin.worldheight.proto;

import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.lighting.LevelLightEngine;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import me.minidigger.fabrictest.interfaces.ClientChunkCacheExtended;
import me.minidigger.fabrictest.interfaces.LevelChunkExtended;

@Mixin(ClientChunkCache.class)
public abstract class ClientChunkCacheMixin implements ClientChunkCacheExtended {

    @Shadow
    @Final
    private static Logger LOGGER;
    @Shadow
    private volatile ClientChunkCache.Storage storage;
    @Shadow
    @Final
    private ClientLevel level;

    @Shadow
    public abstract LevelLightEngine getLightEngine();

    @Nullable
    @Override
    public LevelChunk replaceWithPacketDataExtended(int i, int j, @Nullable ChunkBiomeContainer chunkBiomeContainer, FriendlyByteBuf friendlyByteBuf, CompoundTag compoundTag, BitStorage bitStorage) {
        if (!this.storage.inRange(i, j)) {
            LOGGER.warn("Ignoring chunk since it's not in the view range: {}, {}", i, j);
            return null;
        } else {
            int l = this.storage.getIndex(i, j);
            LevelChunk levelChunk = (LevelChunk) this.storage.chunks.get(l);
            ChunkPos chunkPos = new ChunkPos(i, j);
            if (!ClientChunkCache.isValidChunk(levelChunk, i, j)) {
                if (chunkBiomeContainer == null) {
                    LOGGER.warn("Ignoring chunk since we don't have complete data: {}, {}", i, j);
                    return null;
                }

                levelChunk = new LevelChunk(this.level, chunkPos, chunkBiomeContainer);
                ((LevelChunkExtended) levelChunk).replaceWithPacketDataExtended(chunkBiomeContainer, friendlyByteBuf, compoundTag, bitStorage); // modified
                this.storage.replace(l, levelChunk);
            } else {
                ((LevelChunkExtended) levelChunk).replaceWithPacketDataExtended(chunkBiomeContainer, friendlyByteBuf, compoundTag, bitStorage); // modified
            }

            LevelChunkSection[] levelChunkSections = levelChunk.getSections();
            LevelLightEngine levelLightEngine = this.getLightEngine();
            levelLightEngine.enableLightSources(chunkPos, true);

            for (int m = 0; m < levelChunkSections.length; ++m) {
                LevelChunkSection levelChunkSection = levelChunkSections[m];
                int n = this.level.getSectionYFromSectionIndex(m);
                levelLightEngine.updateSectionStatus(SectionPos.of(i, n, j), LevelChunkSection.isEmpty(levelChunkSection));
            }

            this.level.onChunkLoaded(chunkPos);
            return levelChunk;
        }
    }
}
