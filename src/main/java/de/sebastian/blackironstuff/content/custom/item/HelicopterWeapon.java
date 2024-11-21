package de.sebastian.blackironstuff.content.custom.item;

import de.sebastian.blackironstuff.content.ModSounds;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.*;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.math.Vec3d;

public class HelicopterWeapon extends SwordItem {
    public HelicopterWeapon(Settings settings) {
        super(ToolMaterials.WOOD, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.getWorld().isClient()) {
            new Thread(() -> {
                try {
                    // Apply slowness effect to freeze movement
                    if(target instanceof MobEntity e) {
                        e.setAiDisabled(true);
                        target.playSound(ModSounds.HELICOPTER, 1f, 1f);

                        // Gradual spinning with increasing speed
                        int totalSpins = 10; // 10 full spins
                        long baseDelay = 100; // Initial delay in ms

                        for (int spin = 1; spin <= totalSpins; spin++) {
                            for (int angle = 0; angle < 360; angle += 10) {
                                Thread.sleep(baseDelay / spin); // Gradually increase spin speed

                                // Prevent entity movement
                                target.setVelocity(Vec3d.ZERO);

                                // Force entity's body and head rotation
                                float newYaw = target.getYaw() + 10;
                                target.setYaw(newYaw); // Rotate the body
                                target.setHeadYaw(newYaw); // Sync head to body
                                target.setPitch(0); // Optional: Ensure the head remains level
                            }
                        }

                        e.setAiDisabled(false);
                        target.setVelocity(new Vec3d(0, 4, 0)); // Apply upward velocity
                        Thread.sleep(1000); // Wait for 1 second while in the air

                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Handle interruptions safely
                    e.printStackTrace();
                }
            }).start();

        }
        return super.postHit(stack, target, attacker);
    }
}