package me.minidigger.fabrictest.mixin;

import net.minecraft.server.MinecraftServer;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Redirect(method = "getMaxBuildHeight()I", at = @At(value = "FIELD", target = "Lnet/minecraft/server/MinecraftServer;maxBuildHeight:I", opcode = Opcodes.GETFIELD))
    private int getWorldHeight(MinecraftServer minecraftServer) {
        return FabricTestMod.HEIGHT;
    }
}
