package de.sebastian.blackironstuff.gametest;

import net.minecraft.SharedConstants;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.test.GameTest;
import net.minecraft.test.GameTestException;
import net.minecraft.test.TestContext;
import net.minecraft.util.math.BlockPos;

public class ModGameTests {

    public static void initialize() {}

    @GameTest(templateName = "minecraft:reinforcer_no_output_test", tickLimit = 200)
    public void reinforcer_no_output_test(TestContext ctx) {
        ctx.expectEmptyContainer(100, new BlockPos(0, 3, 1));
        ctx.complete();
    }

    @GameTest(templateName = "minecraft:reinforcer_works", tickLimit = 200)
    public void reinforcer_works(TestContext ctx) {
        ctx.toggleLever(4,2, 3);
        expectContainerWithIn100Ticks(ctx, new BlockPos(0, 3, 1), Items.CHEST);
        expectContainerWithIn100Ticks(ctx, new BlockPos(0, 3, 3), Items.NETHERITE_INGOT);
    }

    @GameTest(templateName = "minecraft:mcdefault.pathfinding", tickLimit = 200)
    public void mcdefault_pathfinding2(TestContext ctx) {
        VillagerEntity entity = ctx.spawnEntity(EntityType.VILLAGER, 3,1, 3);
        expectContainerWithIn100Ticks(ctx, new BlockPos(0, 3, 1), Items.CHEST);
        expectContainerWithIn100Ticks(ctx, new BlockPos(0, 3, 3), Items.NETHERITE_INGOT);
    }

    public void expectContainerWithIn100Ticks(TestContext ctx, BlockPos pos, Item item) {
        ctx.runAtTick(100, () -> {
            BlockPos blockPos = ctx.getAbsolutePos(pos);
            BlockEntity blockEntity = ctx.getWorld().getBlockEntity(blockPos);
            if (!(blockEntity instanceof LockableContainerBlockEntity)) {
                String var10002 = String.valueOf(pos);
                throw new GameTestException("Expected a container at " + var10002 + ", found " + String.valueOf(Registries.BLOCK_ENTITY_TYPE.getId(blockEntity.getType())));
            } else if (((LockableContainerBlockEntity)blockEntity).count(item) >= 1) {
                throw new GameTestException("Container should contain: " + String.valueOf(item));
            }
        });
    }
}
