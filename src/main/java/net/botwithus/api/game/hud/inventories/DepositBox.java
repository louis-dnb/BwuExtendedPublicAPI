package net.botwithus.api.game.hud.inventories;

import net.botwithus.rs3.game.*;
import net.botwithus.rs3.game.Item;
import net.botwithus.rs3.game.hud.interfaces.Component;
import net.botwithus.rs3.game.hud.interfaces.Interfaces;
import net.botwithus.rs3.game.minimenu.*;
import net.botwithus.rs3.game.minimenu.actions.*;
import net.botwithus.rs3.game.queries.builders.components.ComponentQuery;
import net.botwithus.rs3.game.queries.builders.items.InventoryItemQuery;
import net.botwithus.rs3.game.queries.builders.objects.SceneObjectQuery;
import net.botwithus.rs3.game.queries.results.*;
import net.botwithus.rs3.script.*;
import net.botwithus.rs3.script.ScriptConsole;
import net.botwithus.rs3.util.Regex;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.botwithus.api.game.hud.inventories.Backpack.BACKPACK;

public final class DepositBox {
    private DepositBox() {
    }

    /**
     * Opens the nearest deposit box and deposits all items.
     *
     * @return true if the deposit box was opened and all items were deposited, false otherwise
     */
    public static boolean open() {
        var obj = SceneObjectQuery.newQuery().option("Deposit").option("Deposit-All",
                                                                       "Deposit all").results().nearest();
        return obj != null && obj.interact("Deposit");
    }

    /**
     * Checks if the interface is open
     *
     * @return true if the interface is open, false otherwise
     */
    public static boolean isOpen() {
        return Interfaces.isOpen(11);
    }

    /**
     * Closes the interface with the given query.
     *
     * @return true if the interface was closed, false otherwise
     */
    public static boolean close() {
        var result =  ComponentQuery.newQuery(11).option("Close").results().first();
        return result != null && result.interact();
    }

    /**
     * Attempts to deposit all carried items.
     *
     * @return true if the action was successful, false otherwise
     */
    public static boolean depositAll() {
        var result = ComponentQuery.newQuery(11).option("Deposit Carried Items").results().first();
        return result != null && result.interact();
    }

    public static boolean depositAll(Pattern... patterns) {
        if (!DepositBox.isOpen()) {
            if (!DepositBox.open()) {
                return false;
            }
        }
        ResultSet<Item> itemsToDeposit = InventoryItemQuery.newQuery(new int[]{93}).name(patterns).results();
        if (itemsToDeposit.size() == 0) {
            return true;
        }
        var slots = itemsToDeposit.stream().mapToInt(Item::getSlot).toArray();

        for (int slotNum : slots) {
            if (net.botwithus.rs3.game.inventories.Backpack.getSlot(slotNum) == null) {
                continue;
            }
            // don't think I can use a ComponentQuery here
            if (!MiniMenu.interact(ComponentAction.COMPONENT.getType(), 4, slotNum, 720915)) { // deposit-all at slot_num
                return false;
            }
            if (!Execution.delayUntil(1000, () -> !(net.botwithus.rs3.game.inventories.Backpack.getSlot(slotNum) == null))) {
                return false;
            }
        }
        DepositBox.close();
        return true;
    }

    public static boolean depositAllExcept(String... itemNames) {
        var items = ComponentQuery.newQuery(11).itemName(Regex.getPatternForNotContainingAnyString(itemNames)).option(
                "Deposit-All").results();
        return depositAllExcept(items.stream().mapToInt(Component::getItemId).toArray());
//        return !items.stream().map(i -> i.interact(4)).toList().contains(false);
    }

    public static boolean depositAllExcept(int... ids) {
        var idSet = Arrays.stream(ids).boxed().collect(Collectors.toSet());
        var items = ComponentQuery.newQuery(11).results().stream().filter(i -> !idSet.contains(i.getItemId())).toArray(Component[]::new);
        var itemIds = Arrays.stream(items).map(Component::getItemId).distinct().toArray();
        ScriptConsole.println("[DepositBox#depositAllExcept] Items: " + Arrays.toString(itemIds));
        return !Arrays.stream(items).distinct().map(i -> {
            var result = i.interact(4);
            ScriptConsole.println("[DepositBox#depositAllExcept] Interact result: " + result);
            return result;
        }).toList().contains(false);
    }

    public static boolean depositAllExcept(Pattern... patterns) {
        var itemIds = InventoryItemQuery.newQuery(93).name(patterns).results().stream().mapToInt(Item::getId).toArray();
        var itemIds2 = Backpack.getItems().stream().filter(i -> Arrays.stream(patterns).anyMatch(p -> i.getName() != null && p.matcher(i.getName()).matches())).mapToInt(Item::getId).toArray();
        return depositAllExcept(itemIds2);
    }

    /**
     * Attempts to deposit all worn items.
     *
     * @return {@code true} if the action was successful, {@code false} otherwise
     */
    public static boolean depositWornItems() {
        var result = ComponentQuery.newQuery(11).option("Deposit Worn Items").results().first();
        return result != null && result.interact();
    }

    /**
     * Attempts to deposit the familiar's items.
     *
     * @return true if the action was successful, false otherwise
     */
    public static boolean depositFamiliarItems() {
        var result = ComponentQuery.newQuery(11).option("Deposit Familiar's Items").results().first();
        return result != null && result.interact();
    }

    /**
     * Attempts to deposit the money pouch.
     *
     * @return true if the money pouch was successfully deposited, false otherwise
     */
    public static boolean depositMoneyPouch() {
        var result = ComponentQuery.newQuery(11).option("Deposit Money Pouch").results().first();
        return result != null && result.interact();
    }
}
