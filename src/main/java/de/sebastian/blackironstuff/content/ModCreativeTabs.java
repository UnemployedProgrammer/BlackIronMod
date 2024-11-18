package de.sebastian.blackironstuff.content;


import de.sebastian.blackironstuff.BlackIronMod;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModCreativeTabs {
    public static final RegistryKey<ItemGroup> CUSTOM_ITEM_GROUP_KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), Identifier.of(BlackIronMod.MOD_ID, "item_group"));
    public static final ItemGroup CUSTOM_ITEM_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.BLACK_IRON_INGOT))
            .displayName(Text.translatable("itemGroup.blackironstuff"))
            .build();

    public static void initialize() {
        Registry.register(Registries.ITEM_GROUP, CUSTOM_ITEM_GROUP_KEY, CUSTOM_ITEM_GROUP);

        ItemGroupEvents.modifyEntriesEvent(CUSTOM_ITEM_GROUP_KEY).register(itemGroup -> {
            itemGroup.add(ModBlocks.BLACK_IRON_ORE.asItem());
            itemGroup.add(ModBlocks.BLACK_IRON_DEEPSLATE_ORE.asItem());
            itemGroup.add(ModBlocks.RAW_BLACK_IRON_ORE.asItem());
            itemGroup.add(ModItems.RAW_BLACK_IRON);
            itemGroup.add(ModItems.BLACK_IRON_INGOT);
            itemGroup.add(ModBlocks.BLACK_IRON_BLOCK.asItem());

            itemGroup.add(ModItems.BLACK_IRON_SWORD);
            itemGroup.add(ModItems.BLACK_IRON_PICKAXE);
            itemGroup.add(ModItems.BLACK_IRON_AXE);
            itemGroup.add(ModItems.BLACK_IRON_SHOVEL);
            itemGroup.add(ModItems.BLACK_IRON_HOE);

            itemGroup.add(ModItems.BLACK_IRON_HELMET);
            itemGroup.add(ModItems.BLACK_IRON_CHESTPLATE);
            itemGroup.add(ModItems.BLACK_IRON_LEGGINGS);
            itemGroup.add(ModItems.BLACK_IRON_BOOTS);

            itemGroup.add(ModItems.HELICOPTER);
        });
    }
}
