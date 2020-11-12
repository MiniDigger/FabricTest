package me.minidigger.fabrictest;

import net.fabricmc.api.ModInitializer;
import net.minecraft.SharedConstants;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestRegistry;
import net.minecraft.gametest.framework.TestFunction;
import net.minecraft.world.level.block.Rotation;

import sun.reflect.ReflectionFactory;

import java.lang.reflect.Constructor;
import java.util.function.Consumer;

import me.minidigger.fabrictest.interfaces.TestFunctionExtended;

public class FabricTestMod implements ModInitializer {

    public static final int MIN_HEIGHT = 0;
    public static final int MAX_HEIGHT = 512;

    public static final int MIN_SECTION = MIN_HEIGHT / 16;
    public static final int MAX_SECTION = MAX_HEIGHT / 16;
    public static final int SECTION_COUNT = MIN_SECTION * -1 + MAX_SECTION;
    public static final int HEIGHT = SECTION_COUNT * 16 + MIN_SECTION * 16;

    @Override
    public void onInitialize() {
        SharedConstants.IS_RUNNING_IN_IDE = true;
        TestFunctionExtended testFunction = (TestFunctionExtended) create(TestFunction.class, Object.class);
        Consumer<GameTestHelper> f = (h) -> {
            System.out.println("idk what am doing");
        };
        testFunction.init("testbatch", "test1", "test1", true, 5, 1, f, 20 * 10, 20 * 1, Rotation.NONE);
        GameTestRegistry.getAllTestFunctions().add((TestFunction) testFunction);
    }

    public static <T> T create(Class<T> clazz, Class<? super T> parent) {
        try {
            ReflectionFactory rf = ReflectionFactory.getReflectionFactory();
            Constructor objDef = parent.getDeclaredConstructor();
            Constructor intConstr = rf.newConstructorForSerialization(clazz, objDef);
            return clazz.cast(intConstr.newInstance());
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalStateException("Cannot create object", e);
        }
    }
}
