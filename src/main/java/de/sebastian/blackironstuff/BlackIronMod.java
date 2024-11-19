package de.sebastian.blackironstuff;

import de.sebastian.blackironstuff.content.*;
import de.sebastian.blackironstuff.gametest.ModGameTests;
import net.fabricmc.api.ModInitializer;
import net.minecraft.SharedConstants;

public class BlackIronMod implements ModInitializer {

    public static String MOD_ID = "blackironstuff";

    @Override
    public void onInitialize() {
        ModItems.initialize();
        ModBlocks.initialize();
        ModCreativeTabs.initialize();
        ArmorMaterials.initialize();
        ModSounds.initialize();
        ModGameTests.initialize();
    }

    //TODO: Create a new project Tomorrow, nothing is working anymore!
}
