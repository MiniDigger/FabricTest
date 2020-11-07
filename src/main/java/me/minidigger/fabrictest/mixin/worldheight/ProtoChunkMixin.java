package me.minidigger.fabrictest.mixin.worldheight;

import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.ProtoChunk;
import net.minecraft.world.level.levelgen.GenerationStep;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.BitSet;
import java.util.Map;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(ProtoChunk.class)
public abstract class ProtoChunkMixin implements ChunkAccess {

    @Shadow
    @Final
    private Map<GenerationStep.Carving, BitSet> carvingMasks;

    @Shadow @Final private LevelChunkSection[] sections;

    @Override
    public int getSectionsCount() {
        return FabricTestMod.SECTION_COUNT;
    }

    @Override
    public int getMinSection() {
        return FabricTestMod.MIN_SECTION;
    }

    /**
     * @author MiniDigger
     */
    @Overwrite
    public BitSet getOrCreateCarvingMask(GenerationStep.Carving carving) {
        return (BitSet) this.carvingMasks.computeIfAbsent(carving, (carvingx) -> {
            return new BitSet(65536 * 2);
        });
    }

    /**
     * @author MiniDigger
     */
    @Overwrite // dum hack
    public LevelChunkSection getOrCreateSection(int i) {
        if (i >= sections.length) i = sections.length - 1;
        if (this.sections[i] == LevelChunk.EMPTY_SECTION) {
            this.sections[i] = new LevelChunkSection(this.getSectionYFromSectionIndex(i));
        }

        return this.sections[i];
    }
}
