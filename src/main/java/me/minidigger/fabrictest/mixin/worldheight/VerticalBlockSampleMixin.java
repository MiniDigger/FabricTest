package me.minidigger.fabrictest.mixin.worldheight;

import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.world.level.BlockAndTintGetter;

import org.spongepowered.asm.mixin.Mixin;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(RenderChunkRegion.class)
public abstract class VerticalBlockSampleMixin implements BlockAndTintGetter {

    @Override
    public int getSectionsCount() {
        return FabricTestMod.SECTION_COUNT;
    }

    @Override
    public int getMinSection() {
        return FabricTestMod.MIN_SECTION;
    }
}
