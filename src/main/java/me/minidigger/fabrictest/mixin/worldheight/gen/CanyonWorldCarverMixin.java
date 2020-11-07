package me.minidigger.fabrictest.mixin.worldheight.gen;

import net.minecraft.world.level.levelgen.carver.CanyonWorldCarver;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(CanyonWorldCarver.class)
public class CanyonWorldCarverMixin {

    @ModifyConstant(method = "genCanyon(Lnet/minecraft/world/level/chunk/ChunkAccess;Ljava/util/function/Function;JIIIDDDFFFIIDLjava/util/BitSet;)V", constant = @Constant(intValue = 256))
    public int correctArrayIndex(int oldValue) {
//        return FabricTestMod.HEIGHT;
        return 1024;
    }
}
