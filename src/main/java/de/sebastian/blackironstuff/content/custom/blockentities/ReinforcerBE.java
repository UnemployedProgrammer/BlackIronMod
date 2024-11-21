package de.sebastian.blackironstuff.content.custom.blockentities;

import de.sebastian.blackironstuff.content.BlockEntityTypes;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReinforcerBE extends SimpleInventoryBlockEntity implements TickableBE {

    private int tickCounter = 0; // Counter to track the number of ticks
    private int timeRecorded = 0; // Keep track of the time we record (in ticks)
    private boolean[] signalHistory = new boolean[100]; // Array to store the redstone signal history (for 5 seconds)

    public ReinforcerBE(BlockPos pos, BlockState state) {
        super(BlockEntityTypes.REINFORCER_BE, pos, state);
    }


    @Override
    public void tick() {
        if (this.world == null || this.world.isClient) {
            return; // Don't run on the client side
        }

        boolean currentSignalState = world.isReceivingRedstonePower(pos);

        timeRecorded++;

        signalHistory[Math.clamp(timeRecorded, 0, 99)] = currentSignalState;

        if(timeRecorded == 20) {
            processItems();
            timeRecorded = 0;
        }

    }

    private void processItems() {
        // Count how many ticks the redstone signal was active (true)
        int activeSignalCount = 0;

        for (boolean signal : signalHistory) {
            if (signal) {
                activeSignalCount++;
            }
        }


        double ticksPerSecond = activeSignalCount;
        int itemsToProcess = (int) (20 - ticksPerSecond);

        if (getInventory().containsAny(Set.of(Items.NETHERITE_INGOT)) && ticksPerSecond != 0) {
            int items = getInventory().count(Items.NETHERITE_INGOT);
            getInventory().removeItem(Items.NETHERITE_INGOT, Math.clamp(itemsToProcess, 0, items));
            outputGems(Math.clamp(ticksPerSecond == 0 ? 0 : itemsToProcess, 0, items), ItemStack.EMPTY);
        } else if (!getInventory().isEmpty()) {
            for (ItemStack heldStack : getInventory().heldStacks) {
                if(heldStack.isOf(Items.NETHERITE_INGOT))
                    continue;
                if(!heldStack.isEmpty())
                    outputGems(0, heldStack);
            }
        }
    }

    private void outputGems(int amount, ItemStack additionalTrash) {
        List<ItemStack> drops = new ArrayList<>();
        if(amount != 0) {
            drops.add(new ItemStack(Items.DIAMOND, amount));
        }
        if(!additionalTrash.isEmpty()) {
            drops.add(additionalTrash);
        }

        Storage<ItemVariant> aboveStorage = findItemStorage((ServerWorld) this.world, this.pos.up());
        if(aboveStorage != null && aboveStorage.supportsInsertion()) {
            insertDrops(drops, aboveStorage);
        }

        if(!drops.isEmpty()) {
            spawnDrops(drops, (ServerWorld) this.world, this.pos);
        }
    }

    public static int[] booleanArrayToIntArray(boolean[] booleanArray) {
        int[] intArray = new int[booleanArray.length];
        for (int i = 0; i < booleanArray.length; i++) {
            intArray[i] = booleanArray[i] ? 1 : 0;  // Encode: true -> 1, false -> 0
        }
        return intArray;
    }

    public static boolean[] intArrayToBooleanArray(int[] intArray) {
        boolean[] booleanArray = new boolean[intArray.length];
        for (int i = 0; i < intArray.length; i++) {
            booleanArray[i] = intArray[i] == 1;  // Umwandlung: 1 -> true, 0 -> false
        }
        return booleanArray;
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        // Load tick counter and time recorded
        if (nbt.contains("tickCounter")) {
            this.tickCounter = nbt.getInt("tickCounter");
        }
        if (nbt.contains("timeRecorded")) {
            this.timeRecorded = nbt.getInt("timeRecorded");
        }

        // Load the signal history from the NBT, or default to an empty array
        if (nbt.contains("signalHistory")) {
            this.signalHistory = intArrayToBooleanArray(nbt.getIntArray("signalHistory"));
        } else {
            this.signalHistory = new boolean[100]; // Initialize to an empty array if not found
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        // Save tick counter and time recorded
        nbt.putInt("tickCounter", tickCounter);
        nbt.putInt("timeRecorded", timeRecorded);

        // Save the signal history as a boolean array
        nbt.putIntArray("signalHistory", booleanArrayToIntArray(signalHistory));
    }

    //OLD CODE:
    //// Record the signal state
    //        if (timeRecorded < signalHistory.length) { // Keep records for 1 second (20 ticks)
    //            signalHistory[(int) timeRecorded] = currentSignalState;
    //            timeRecorded++;
    //        } else {
    //            // If history is full (after 20 ticks), shift and record the new state
    //            System.arraycopy(signalHistory, 1, signalHistory, 0, signalHistory.length - 1);
    //            signalHistory[signalHistory.length - 1] = currentSignalState;
    //        }
    //
    //        // After 20 ticks (1 second), broadcast and reset
    //        if (timeRecorded >= signalHistory.length) {
    //            broadcastSignalRate();
    //            timeRecorded = 0; // Reset after broadcasting
    //        }

    //UTIL 2.0 :)

    private static Storage<ItemVariant> findItemStorage(ServerWorld world, BlockPos pos) {
        return ItemStorage.SIDED.find(world, pos, Direction.DOWN);
    }

    private static void insertDrops(List<ItemStack> drops, Storage<ItemVariant> aboveStorage) {
        for (ItemStack drop : drops) {
            try(Transaction transaction = Transaction.openOuter()) {
                long inserted = aboveStorage.insert(ItemVariant.of(drop), drop.getCount(), transaction);
                if(inserted > 0) {
                    drop.decrement((int) inserted);
                    transaction.commit();
                }
            }
        }

        drops.removeIf(ItemStack::isEmpty);
    }

    private static void spawnDrops(List<ItemStack> drops, ServerWorld world, BlockPos pos) {
        for (ItemStack drop : drops) {
            ItemScatterer.spawn(world, pos.getX() + 0.5D, pos.getY() + 1.0D, pos.getZ() + 1.0D, drop);
        }
    }
}


