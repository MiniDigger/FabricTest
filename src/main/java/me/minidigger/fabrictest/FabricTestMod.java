package me.minidigger.fabrictest;

import net.fabricmc.api.ModInitializer;

public class FabricTestMod implements ModInitializer {

    public static final int MIN_HEIGHT = -1024;
    public static final int MAX_HEIGHT = 2048;

    public static final int MIN_SECTION = MIN_HEIGHT / 16;
    public static final int MAX_SECTION = MAX_HEIGHT / 16;
    public static final int SECTION_COUNT = MIN_SECTION * -1 + MAX_SECTION;
    public static final int HEIGHT = SECTION_COUNT * 16 + MIN_SECTION * 16;

    @Override
    public void onInitialize() {

    }
}
