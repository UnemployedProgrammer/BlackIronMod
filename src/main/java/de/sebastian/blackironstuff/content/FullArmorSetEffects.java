package de.sebastian.blackironstuff.content;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.client.render.block.entity.EnchantingTableBlockEntityRenderer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;

public class FullArmorSetEffects {
    // Method to apply effect if the entity is wearing full diamond armor
    private static void applyEffectIfWearingDiamondArmor(LivingEntity entity) {
        // Get the armor items
        ItemStack helmet = entity.getEquippedStack(EquipmentSlot.HEAD);
        ItemStack chestplate = entity.getEquippedStack(EquipmentSlot.CHEST);
        ItemStack leggings = entity.getEquippedStack(EquipmentSlot.LEGS);
        ItemStack boots = entity.getEquippedStack(EquipmentSlot.FEET);

        // Check if the entity is wearing a full set of diamond armor
        if (helmet.getItem() == ModItems.BLACK_IRON_HELMET &&
                chestplate.getItem() == ModItems.BLACK_IRON_CHESTPLATE &&
                leggings.getItem() == ModItems.BLACK_IRON_LEGGINGS &&
                boots.getItem() == ModItems.BLACK_IRON_BOOTS) {

            applyEffects(entity);
        }
    }

    // Apply a potion effect (e.g., Speed effect)
    private static void applyEffects(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 10, false, false)); // 20 ticks = 1 seconds
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 40, 4, false, false)); // 20 ticks = 1 seconds
    }

    private static void onServerTick(MinecraftServer server) {
        // Iterate over all worlds loaded on the server
        for (ServerWorld world : server.getWorlds()) {
            if (world == null) continue; // Skip if the world is null

            // Iterate over all entities in the world
            world.getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), EntityPredicates.VALID_ENTITY).forEach(entity -> {
                if (entity instanceof LivingEntity) {
                    // Check if the entity is wearing full diamond armor
                    applyEffectIfWearingDiamondArmor((LivingEntity) entity);
                }
            });
        }
    }

    public static void initialize() {
        ServerTickEvents.START_SERVER_TICK.register(FullArmorSetEffects::onServerTick);
    }


}
