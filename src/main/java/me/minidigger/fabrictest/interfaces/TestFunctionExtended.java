package me.minidigger.fabrictest.interfaces;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Rotation;

import java.util.function.Consumer;

public interface TestFunctionExtended {
    void init(String batchName, String testName, String structureName, boolean required, int maxAttempts, int requiredSuccesses, Consumer<GameTestHelper> function, int maxTicks, long setupTicks, Rotation rotation);
}
