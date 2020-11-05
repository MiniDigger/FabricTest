package me.minidigger.fabrictest;

import net.fabricmc.api.ModInitializer;

public class FabricTestMod implements ModInitializer {

    public static final int MIN_SECTION = -5;
    public static final int SECTION_COUNT = 16 * 2 + 4;
    public static final int HEIGHT = SECTION_COUNT * 16 - MIN_SECTION * 16;

    @Override
    public void onInitialize() {

    }
}
