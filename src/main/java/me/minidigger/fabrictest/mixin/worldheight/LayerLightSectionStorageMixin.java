package me.minidigger.fabrictest.mixin.worldheight;

import it.unimi.dsi.fastutil.longs.LongSet;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.lighting.DataLayerStorageMap;
import net.minecraft.world.level.lighting.LayerLightSectionStorage;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LayerLightSectionStorage.class)
public abstract class LayerLightSectionStorageMixin<M extends DataLayerStorageMap<M>> {

    @Shadow @Nullable protected abstract DataLayer getDataLayer(long l, boolean bl);

    @Shadow @Final protected LongSet changedSections;

    @Shadow @Final protected LongSet sectionsAffectedByLightUpdates;

    @Shadow @Final protected M updatingSectionData;

    /**
     * @author MiniDigger
     */
    @Overwrite
    public int getStoredLevel(long l) {
        long m = SectionPos.blockToSection(l);
        DataLayer dataLayer = this.getDataLayer(m, true);
        if (dataLayer == null) return 0; // mod
        return dataLayer.get(SectionPos.sectionRelative(BlockPos.getX(l)), SectionPos.sectionRelative(BlockPos.getY(l)), SectionPos.sectionRelative(BlockPos.getZ(l)));
    }

    /**
     * @author MiniDigger
     */
    @Overwrite
    public void setStoredLevel(long l, int i) {
        long m = SectionPos.blockToSection(l);
        if (this.changedSections.add(m) && this.getDataLayer(l, true) != null) {
            this.updatingSectionData.copyDataLayer(m);
        }

        DataLayer dataLayer = this.getDataLayer(m, true);
        if (dataLayer != null) { // mod added if
            dataLayer.set(SectionPos.sectionRelative(BlockPos.getX(l)), SectionPos.sectionRelative(BlockPos.getY(l)), SectionPos.sectionRelative(BlockPos.getZ(l)), i);
        }

        for(int j = -1; j <= 1; ++j) {
            for(int k = -1; k <= 1; ++k) {
                for(int n = -1; n <= 1; ++n) {
                    this.sectionsAffectedByLightUpdates.add(SectionPos.blockToSection(BlockPos.offset(l, k, n, j)));
                }
            }
        }

    }
}
