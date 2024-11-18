package de.sebastian.blackironstuff.content;

import de.sebastian.blackironstuff.BlackIronMod;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ArmorMaterials {
    public static void initialize() {};
    public static RegistryEntry<ArmorMaterial> registerMaterial(String id, Map<ArmorItem.Type, Integer> defensePoints, int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredientSupplier, float toughness, float knockbackResistance, boolean dyeable) {
        // Get the supported layers for the armor material
        List<ArmorMaterial.Layer> layers = List.of(
                // The ID of the texture layer, the suffix, and whether the layer is dyeable.
                // We can just pass the armor material ID as the texture layer ID.
                // We have no need for a suffix, so we'll pass an empty string.
                // We'll pass the dyeable boolean we received as the dyeable parameter.
                new ArmorMaterial.Layer(Identifier.of(BlackIronMod.MOD_ID, id), "", dyeable)
        );

        ArmorMaterial material = new ArmorMaterial(defensePoints, enchantability, equipSound, repairIngredientSupplier, layers, toughness, knockbackResistance);
        // Register the material within the ArmorMaterials registry.
        material = Registry.register(Registries.ARMOR_MATERIAL, Identifier.of(BlackIronMod.MOD_ID, id), material);

        // The majority of the time, you'll want the RegistryEntry of the material - especially for the ArmorItem constructor.
        return RegistryEntry.of(material);
    }

    //MATERIALS

    public static final RegistryEntry<ArmorMaterial> BLACK_IRON = registerMaterial("black_iron",
            // Defense (protection) point values for each armor piece.
            Map.of(
                    ArmorItem.Type.HELMET, 6,
                    ArmorItem.Type.CHESTPLATE, 16,
                    ArmorItem.Type.LEGGINGS, 12,
                    ArmorItem.Type.BOOTS, 6
            ),
            // Enchantability. For reference, leather has 15, iron has 9, and diamond has 10.
            10,
            // The sound played when the armor is equipped.
            SoundEvents.ITEM_ARMOR_EQUIP_IRON,
            // The ingredient(s) used to repair the armor.
            () -> Ingredient.ofItems(ModItems.BLACK_IRON_INGOT),
            3.0F,
            1.0F,
            // Guidite is NOT dyeable, so we will pass false.
            false);
}
