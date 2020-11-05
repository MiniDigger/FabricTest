package me.minidigger.fabrictest.mixin;


import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import org.spongepowered.asm.mixin.Mixin;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(Level.class)
public abstract class WorldMixin implements LevelAccessor, AutoCloseable {

    @Override
    public int getSectionsCount() {
        return FabricTestMod.SECTION_COUNT;
    }

    @Override
    public int getMinSection() {
        return FabricTestMod.MIN_SECTION;
    }
}
