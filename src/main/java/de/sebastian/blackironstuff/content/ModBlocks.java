package de.sebastian.blackironstuff.content;

import de.sebastian.blackironstuff.BlackIronMod;
import de.sebastian.blackironstuff.content.custom.block.ReinforcerBeBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;

public class ModBlocks {
    public static void initialize() {}
    public static <T extends Block> T register(T block, String name, boolean shouldRegisterItem) {
        // Register the block and its item.
        Identifier id = Identifier.of(BlackIronMod.MOD_ID, name);

        // Sometimes, you may not want to register an item for the block.
        // Eg: if it's a technical block like `minecraft:air` or `minecraft:end_gateway`
        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Settings());
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static final Block BLACK_IRON_ORE = register(
            new ExperienceDroppingBlock(UniformIntProvider.create(1, 4),AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)),
            "black_iron_ore",
            true
    );
    public static final Block BLACK_IRON_DEEPSLATE_ORE = register(
            new ExperienceDroppingBlock(UniformIntProvider.create(1, 4),AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)),
            "black_iron_deepslate_ore",
            true
    );

    public static final Block RAW_BLACK_IRON_ORE = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)),
            "raw_black_iron_block",
            true
    );

    public static final Block BLACK_IRON_BLOCK = register(
            new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.STONE)),
            "black_iron_block",
            true
    );

    //BE

    public static final ReinforcerBeBlock REINFORCER_BE_BLOCK = register(
            new ReinforcerBeBlock(AbstractBlock.Settings.create()
                    .strength(1.5F, 6.0F)
                    .requiresTool()), "reinforcer_be", true);

}
