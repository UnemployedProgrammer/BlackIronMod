package de.sebastian.blackironstuff.content;

import net.minecraft.block.Block;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;

public class ToolMaterials {

    public static BlackIronMaterial BLACK_IRON_MATERIAL = new BlackIronMaterial();

    public static class BlackIronMaterial implements ToolMaterial {
        @Override
        public int getDurability() {
            return 2000;
        }

        @Override
        public float getMiningSpeedMultiplier() {
            return 14.0F;
        }

        @Override
        public float getAttackDamage() {
            return 5;
        }

        @Override
        public TagKey<Block> getInverseTag() {
            return null;
        }

        @Override
        public int getEnchantability() {
            return 22;
        }

        @Override
        public Ingredient getRepairIngredient() {
            return Ingredient.ofItems(ModItems.BLACK_IRON_INGOT);
        }
    }


}
