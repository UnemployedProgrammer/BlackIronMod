package de.sebastian.blackironstuff.content;

import de.sebastian.blackironstuff.BlackIronMod;
import de.sebastian.blackironstuff.content.custom.block.ReinforcerBeBlock;
import de.sebastian.blackironstuff.content.custom.blockentities.ReinforcerBE;
import de.sebastian.blackironstuff.content.custom.blockentities.SimpleInventoryBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlockEntityTypes {
    public static <T extends BlockEntity> BlockEntityType<T> register(String name, BlockEntityType<T> type) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Identifier.of(BlackIronMod.MOD_ID, name), type);
    }

    public static void initialize() {
        //DEFINE SIDED INV BLOCK_INVENTORIES!!!
        ItemStorage.SIDED.registerForBlockEntity(SimpleInventoryBlockEntity::getInventoryProvider, REINFORCER_BE); //DEFAULT, ALL SIDES: SimpleInventoryBlockEntity::getInventoryProvider
    }

    public static final BlockEntityType<ReinforcerBE> REINFORCER_BE = register("reinforcer_be",
            BlockEntityType.Builder.create(ReinforcerBE::new, ModBlocks.REINFORCER_BE_BLOCK)
                    .build());
}
