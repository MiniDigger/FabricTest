package me.minidigger.fabrictest.mixin.worldheight.proto;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.SectionPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.PacketUtils;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.lighting.LevelLightEngine;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Iterator;

import me.minidigger.fabrictest.interfaces.ClientChunkCacheExtended;
import me.minidigger.fabrictest.interfaces.ClientboundLevelChunkPacketExtended;

@Mixin(ClientPacketListener.class)
public class ClientPacketListenerMixin {

    @Shadow @Final
    private Minecraft minecraft;
    @Shadow
    private RegistryAccess registryAccess;
    @Shadow private ClientLevel level;

    /**
     * @author MiniDigger
     */
    @Overwrite
    public void handleLevelChunk(ClientboundLevelChunkPacket clientboundLevelChunkPacket) {
        PacketUtils.ensureRunningOnSameThread(clientboundLevelChunkPacket, (ClientGamePacketListener) this, this.minecraft);
        int i = clientboundLevelChunkPacket.getX();
        int j = clientboundLevelChunkPacket.getZ();
        ChunkBiomeContainer chunkBiomeContainer = clientboundLevelChunkPacket.getBiomes() == null ? null : new ChunkBiomeContainer(this.registryAccess.registryOrThrow(Registry.BIOME_REGISTRY), clientboundLevelChunkPacket.getBiomes());
        // modified
        LevelChunk levelChunk = ((ClientChunkCacheExtended) this.level.getChunkSource()).replaceWithPacketDataExtended(i, j, chunkBiomeContainer, clientboundLevelChunkPacket.getReadBuffer(), clientboundLevelChunkPacket.getHeightmaps(), ((ClientboundLevelChunkPacketExtended) clientboundLevelChunkPacket).getAvailableSectionsExtended());

        for(int k = this.level.getMinSection(); k < this.level.getMaxSection(); ++k) {
            this.level.setSectionDirtyWithNeighbors(i, k, j);
        }

        if (levelChunk != null) {
            Iterator var10 = clientboundLevelChunkPacket.getBlockEntitiesTags().iterator();

            while(var10.hasNext()) {
                CompoundTag compoundTag = (CompoundTag)var10.next();
                BlockPos blockPos = new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
                BlockEntity blockEntity = levelChunk.getBlockEntity(blockPos, LevelChunk.EntityCreationType.IMMEDIATE);
                if (blockEntity != null) {
                    blockEntity.load(compoundTag);
                }
            }
        }

    }

    /**
     * @author MinIDigger
     */
    @Overwrite
    private void readSectionList(int i, int j, LevelLightEngine levelLightEngine, LightLayer lightLayer, long l, long m, Iterator<byte[]> iterator, boolean bl) {
        for(int k = 0; k < levelLightEngine.getLightSectionCount(); ++k) {
            if (!iterator.hasNext()) return;
            int n = levelLightEngine.getMinLightSection() + k;
            boolean bl2 = (l & 1L << k) != 0L;
            boolean bl3 = (m & 1L << k) != 0L;
            if (bl2 || bl3) {
                levelLightEngine.queueSectionData(lightLayer, SectionPos.of(i, n, j), bl2 ? new DataLayer((byte[])((byte[])iterator.next()).clone()) : new DataLayer(), bl);
                this.level.setSectionDirtyWithNeighbors(i, n, j);
            }
        }

    }
}
