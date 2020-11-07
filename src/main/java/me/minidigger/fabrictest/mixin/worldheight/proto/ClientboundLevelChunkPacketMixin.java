package me.minidigger.fabrictest.mixin.worldheight.proto;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacket;
import net.minecraft.util.BitStorage;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.netty.buffer.ByteBuf;
import me.minidigger.fabrictest.interfaces.ClientboundLevelChunkPacketExtended;

@Mixin(ClientboundLevelChunkPacket.class)
public abstract class ClientboundLevelChunkPacketMixin implements ClientboundLevelChunkPacketExtended {

    private BitStorage availableSectionsExtended;

    @Shadow
    protected abstract ByteBuf getWriteBuffer();

    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/world/level/chunk/LevelChunk;)V")
    private void init(LevelChunk levelChunk, CallbackInfo ci) {
        this.availableSectionsExtended = this.extractChunkDataExtended(new FriendlyByteBuf(this.getWriteBuffer()), levelChunk);
    }

    public BitStorage extractChunkDataExtended(FriendlyByteBuf friendlyByteBuf, LevelChunk levelChunk) {
//        int i = 0;
        LevelChunkSection[] levelChunkSections = levelChunk.getSections();
        int j = 0;
        BitStorage availableSections = new BitStorage(1, levelChunkSections.length, null);

        for(int k = levelChunkSections.length; j < k; ++j) {
            LevelChunkSection levelChunkSection = levelChunkSections[j];
            if (levelChunkSection != LevelChunk.EMPTY_SECTION && !levelChunkSection.isEmpty()) {
//                i |= 1 << j;
                availableSections.set(j, 1);
                levelChunkSection.write(friendlyByteBuf);
            }
        }

        return availableSections;
    }

    @Inject(at = @At("RETURN"), method = "write(Lnet/minecraft/network/FriendlyByteBuf;)V")
    private void write(FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        friendlyByteBuf.writeLongArray(this.availableSectionsExtended.getRaw());
    }

    @Inject(at = @At("RETURN"), method = "read(Lnet/minecraft/network/FriendlyByteBuf;)V")
    private void read(FriendlyByteBuf friendlyByteBuf, CallbackInfo ci) {
        friendlyByteBuf.readLongArray(this.availableSectionsExtended.getRaw());
    }

    @Override
    public BitStorage getAvailableSectionsExtended() {
        return availableSectionsExtended;
    }
}
