package me.minidigger.fabrictest.mixin;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ProtoChunk;

import org.spongepowered.asm.mixin.Mixin;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin implements ChunkAccess {

    @Override
    public int getSectionsCount() {
        return FabricTestMod.SECTION_COUNT;
    }

    @Override
    public int getMinSection() {
        return FabricTestMod.MIN_SECTION;
    }
}
