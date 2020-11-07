package me.minidigger.fabrictest.mixin.worldheight.gen;

import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(NoiseGeneratorSettings.class)
public class NoiseGeneratorSettingsMixin {

    @ModifyConstant(method = "lambda$static$0(Lcom/mojang/serialization/codecs/RecordCodecBuilder$Instance;)Lcom/mojang/datafixers/kinds/App;", constant = @Constant(intValue = 255))
    private static int actualNoiseHeight(int oldValue) {
        return FabricTestMod.HEIGHT;
    }
}
