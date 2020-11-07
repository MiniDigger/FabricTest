package me.minidigger.fabrictest.mixin.worldheight.gen;

import net.minecraft.world.level.levelgen.carver.WorldCarver;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(WorldCarver.class)
public class WorldCarverMixin {

    @ModifyConstant(method = "<clinit>()V", constant = @Constant(intValue = 256))
    private static int actualCaveHeight(int oldValue) {
        return FabricTestMod.HEIGHT;
    }
}
