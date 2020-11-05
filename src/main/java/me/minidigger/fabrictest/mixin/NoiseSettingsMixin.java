package me.minidigger.fabrictest.mixin;

import net.minecraft.world.level.levelgen.NoiseSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NoiseSettings.class)
public class NoiseSettingsMixin {

    /**
     * @author
     */
    @Overwrite
    public int height() {
        return 400;
    }
}
