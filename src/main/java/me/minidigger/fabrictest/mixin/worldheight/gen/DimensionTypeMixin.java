package me.minidigger.fabrictest.mixin.worldheight.gen;

import net.minecraft.world.level.dimension.DimensionType;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(DimensionType.class)
public class DimensionTypeMixin {

    @ModifyVariable(method = "<init>(Ljava/util/OptionalLong;ZZZZDZZZZZILnet/minecraft/world/level/biome/BiomeZoomer;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;F)V",  at = @At("HEAD"), ordinal = 0)
    private static int init(int i) {
        return FabricTestMod.HEIGHT;
    }
}
