package me.minidigger.fabrictest.mixin.worldheight.gen;

import net.minecraft.world.level.levelgen.carver.CaveWorldCarver;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Random;

import me.minidigger.fabrictest.FabricTestMod;

@Mixin(CaveWorldCarver.class)
public class CaveWorldCarverMixin {

    /**
     * @author MiniDigger
     */
    @Overwrite
    public int getCaveY(Random random) {
        return random.nextInt(random.nextInt(FabricTestMod.HEIGHT - 12) + 8);
    }
}
