package de.sebastian.blackironstuff.content;

import de.sebastian.blackironstuff.BlackIronMod;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static Item register(Item item, String id) {
        // Create the identifier for the item.
        Identifier itemID = Identifier.of(BlackIronMod.MOD_ID, id);

        // Register the item.
        Item registeredItem = Registry.register(Registries.ITEM, itemID, item);

        // Return the registered item!
        return registeredItem;
    }
    public static void initialize() {
    }

    //ITEMS

    public static final int BLACK_IRON_DURABILITY_MULTIPLIER = 200;

    public static final Item BLACK_IRON_INGOT = register(
            new Item(new Item.Settings()),
            "black_iron_ingot"
    );

    public static final Item RAW_BLACK_IRON = register(
            new Item(new Item.Settings()),
            "raw_black_iron"
    );

    public static final Item BLACK_IRON_SWORD = register(new SwordItem(ToolMaterials.BLACK_IRON_MATERIAL, (new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.BLACK_IRON_MATERIAL, 20, 4F))), "black_iron_sword");
    public static final Item BLACK_IRON_PICKAXE = register(new PickaxeItem(ToolMaterials.BLACK_IRON_MATERIAL, new Item.Settings().attributeModifiers(PickaxeItem.createAttributeModifiers(ToolMaterials.BLACK_IRON_MATERIAL, 1, 0.1F))), "black_iron_pickaxe");
    public static final Item BLACK_IRON_SHOVEL = register(new ShovelItem(ToolMaterials.BLACK_IRON_MATERIAL, (new Item.Settings()).attributeModifiers(ShovelItem.createAttributeModifiers(ToolMaterials.BLACK_IRON_MATERIAL, 1, 0.1F))), "black_iron_shovel");
    public static final Item BLACK_IRON_AXE = register(new AxeItem(ToolMaterials.BLACK_IRON_MATERIAL, (new Item.Settings()).attributeModifiers(AxeItem.createAttributeModifiers(ToolMaterials.BLACK_IRON_MATERIAL, 1, 0.1F))), "black_iron_axe");
    public static final Item BLACK_IRON_HOE = register(new HoeItem(ToolMaterials.BLACK_IRON_MATERIAL, (new Item.Settings()).attributeModifiers(HoeItem.createAttributeModifiers(ToolMaterials.BLACK_IRON_MATERIAL, 1, 0.1F))), "black_iron_hoe");
    public static final Item HELICOPTER = register(new HelicopterWeapon((new Item.Settings()).attributeModifiers(SwordItem.createAttributeModifiers(net.minecraft.item.ToolMaterials.WOOD, 0, 0.1F))), "helicopter");


    public static final Item BLACK_IRON_HELMET = register(new ArmorItem(ArmorMaterials.BLACK_IRON, ArmorItem.Type.HELMET, new Item.Settings().maxDamage(ArmorItem.Type.HELMET.getMaxDamage(BLACK_IRON_DURABILITY_MULTIPLIER))), "black_iron_helmet");
    public static final Item BLACK_IRON_CHESTPLATE = register(new ArmorItem(ArmorMaterials.BLACK_IRON, ArmorItem.Type.CHESTPLATE, new Item.Settings().maxDamage(ArmorItem.Type.CHESTPLATE.getMaxDamage(BLACK_IRON_DURABILITY_MULTIPLIER))), "black_iron_chestplate");
    public static final Item BLACK_IRON_LEGGINGS = register(new ArmorItem(ArmorMaterials.BLACK_IRON, ArmorItem.Type.LEGGINGS, new Item.Settings().maxDamage(ArmorItem.Type.LEGGINGS.getMaxDamage(BLACK_IRON_DURABILITY_MULTIPLIER))), "black_iron_leggings");
    public static final Item BLACK_IRON_BOOTS = register(new ArmorItem(ArmorMaterials.BLACK_IRON, ArmorItem.Type.BOOTS, new Item.Settings().maxDamage(ArmorItem.Type.BOOTS.getMaxDamage(BLACK_IRON_DURABILITY_MULTIPLIER))), "black_iron_boots");
}
