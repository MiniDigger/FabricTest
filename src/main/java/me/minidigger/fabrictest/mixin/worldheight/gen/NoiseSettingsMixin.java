package me.minidigger.fabrictest.mixin.worldheight.gen;

import net.minecraft.world.level.levelgen.NoiseSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(NoiseSettings.class)
public class NoiseSettingsMixin {

    /**
     * @author MiniDigger
     */
    @Overwrite
    public int height() {
        return FabricTestMod.HEIGHT;
    }
}
