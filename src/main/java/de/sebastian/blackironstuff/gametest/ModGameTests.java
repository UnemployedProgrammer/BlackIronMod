package de.sebastian.blackironstuff.gametest;

import net.minecraft.SharedConstants;
import net.minecraft.block.Blocks;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestContext;

public class ModGameTests {

    public static void initialize() {}

    @GameTest(templateName = "minecraft:powdersnowtest")
    public void powderSnowTest(TestContext ctx) {
        ctx.pushButton(1, 2, 0);
        ctx.expectBlockAtEnd(Blocks.POWDER_SNOW, 1, 3, 1);
    }
}
