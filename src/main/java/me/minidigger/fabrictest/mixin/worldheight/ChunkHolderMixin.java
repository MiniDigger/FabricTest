package me.minidigger.fabrictest.mixin.worldheight;

import it.unimi.dsi.fastutil.shorts.ShortArraySet;
import it.unimi.dsi.fastutil.shorts.ShortSet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ChunkHolder;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.chunk.LevelChunk;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkHolder.class)
public abstract class ChunkHolderMixin {

    @Shadow @Nullable public abstract LevelChunk getTickingChunk();

    @Shadow @Final private LevelHeightAccessor levelHeightAccessor;

    @Shadow @Final private ShortSet[] changedBlocksPerSection;

    @Shadow private boolean hasChangedSections;

    /**
     * @author MiniDigger
     */
    @Overwrite
    public void blockChanged(BlockPos blockPos) {
        LevelChunk levelChunk = this.getTickingChunk();
        if (levelChunk != null) {
            int b = this.levelHeightAccessor.getSectionIndex(blockPos.getY());
            if (this.changedBlocksPerSection[b] == null) {
                this.hasChangedSections = true;
                this.changedBlocksPerSection[b] = new ShortArraySet();
            }

            this.changedBlocksPerSection[b].add(SectionPos.sectionRelativePos(blockPos));
        }
    }
}
