package de.sebastian.blackironstuff.content.custom.blockentities;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.world.World;

public interface TickableBE {
    void tick();

    static <T extends BlockEntity> BlockEntityTicker<T> getTicker(World pWorld) {
        return pWorld.isClient ? null : (world, pos, state, blockEntity) -> {
            if (blockEntity instanceof TickableBE tickableBlockEntity) {
                tickableBlockEntity.tick();
            }
        };
    }
}
