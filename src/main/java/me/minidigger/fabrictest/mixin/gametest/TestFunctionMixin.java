package me.minidigger.fabrictest.mixin.gametest;

import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.world.level.block.Rotation;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.Consumer;

import me.minidigger.fabrictest.interfaces.TestFunctionExtended;

@Mixin(TestFunction.class)
public class TestFunctionMixin implements TestFunctionExtended {

    @Mutable
    @Final
    @Shadow
    private String batchName;
    @Mutable
    @Final
    @Shadow
    private String testName;
    @Mutable
    @Final
    @Shadow
    private String structureName;
    @Mutable
    @Final
    @Shadow
    private boolean required;
    @Mutable
    @Final
    @Shadow
    private int maxAttempts;
    @Mutable
    @Final
    @Shadow
    private int requiredSuccesses;
    @Mutable
    @Final
    @Shadow
    private Consumer<GameTestHelper> function;
    @Mutable
    @Final
    @Shadow
    private int maxTicks;
    @Mutable
    @Final
    @Shadow
    private long setupTicks;
    @Mutable
    @Final
    @Shadow
    private Rotation rotation;

    @Override
    public void init(String batchName, String testName, String structureName, boolean required, int maxAttempts, int requiredSuccesses, Consumer<GameTestHelper> function, int maxTicks, long setupTicks, Rotation rotation) {
        this.batchName = batchName;
        this.testName = testName;
        this.structureName = structureName;
        this.required = required;
        this.maxAttempts = maxAttempts;
        this.requiredSuccesses = requiredSuccesses;
        this.function = function;
        this.maxTicks = maxTicks;
        this.setupTicks = setupTicks;
        this.rotation = rotation;
    }
}
