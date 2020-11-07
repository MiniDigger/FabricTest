package me.minidigger.fabrictest.mixin.worldheight.proto;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.ChunkBiomeContainer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

import me.minidigger.fabrictest.interfaces.LevelChunkExtended;

import static net.minecraft.world.level.chunk.LevelChunk.EMPTY_SECTION;

@Mixin(LevelChunk.class)
public abstract class LevelChunkMixin implements LevelChunkExtended {

    @Shadow
    @Final
    private Map<BlockPos, BlockEntity> blockEntities;
    @Shadow
    private ChunkBiomeContainer biomes;
    @Shadow
    @Final
    private LevelChunkSection[] sections;

    @Shadow
    public abstract void setHeightmap(Heightmap.Types types, long[] ls);

    @Shadow
    protected abstract void onBlockEntityRemove(BlockEntity blockEntity);

    @Override
    public void replaceWithPacketDataExtended(@Nullable ChunkBiomeContainer chunkBiomeContainer, FriendlyByteBuf friendlyByteBuf, CompoundTag compoundTag, BitStorage bitStorage) {
        boolean bl = chunkBiomeContainer != null;
        if (bl) {
            this.blockEntities.values().forEach(this::onBlockEntityRemove);
            this.blockEntities.clear();
        } else {
            this.blockEntities.values().removeIf((blockEntity) -> {
                if (this.isPositionInSectionExtended(bitStorage, blockEntity.getBlockPos())) { // modified
                    blockEntity.setRemoved();
                    return true;
                } else {
                    return false;
                }
            });
        }

        for (int j = 0; j < this.sections.length; ++j) {
            LevelChunkSection levelChunkSection = this.sections[j];
            if ((bitStorage.get(j)) == 0) { // modified
                if (bl && levelChunkSection != EMPTY_SECTION) {
                    this.sections[j] = EMPTY_SECTION;
                }
            } else {
                if (levelChunkSection == EMPTY_SECTION) {
                    levelChunkSection = new LevelChunkSection(((LevelChunk)(Object)this).getSectionYFromSectionIndex(j)); // modified
                    this.sections[j] = levelChunkSection;
                }

                levelChunkSection.read(friendlyByteBuf);
            }
        }

        if (chunkBiomeContainer != null) {
            this.biomes = chunkBiomeContainer;
        }

        Heightmap.Types[] var11 = Heightmap.Types.values();
        int var12 = var11.length;

        for (int var8 = 0; var8 < var12; ++var8) {
            Heightmap.Types types = var11[var8];
            String string = types.getSerializationKey();
            if (compoundTag.contains(string, 12)) {
                this.setHeightmap(types, compoundTag.getLongArray(string));
            }
        }
    }

    private boolean isPositionInSectionExtended(BitStorage bitStorage, BlockPos blockPos) {
        return bitStorage.get(((LevelChunk) (Object) this).getSectionIndex(blockPos.getY())) != 0;
    }
}
