package net.botwithus.api.game.skills.smithing;

import com.sun.jdi.Value;
import net.botwithus.rs3.game.js5.types.EnumType;
import net.botwithus.rs3.game.js5.types.ItemType;
import net.botwithus.rs3.game.js5.types.StructType;
import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.script.ScriptConsole;

/**
 * Exposes information related to the players inventory in regard to smithing
 */

public final class Smithing {
    private Smithing() {
    }

    /**
     *
     *   Gets the unfinished item in the given slot.
     *
     *   @param slot The slot to get the unfinished item from.
     *   @return The unfinished item in the given slot.
     */
    public static int getUnfinishedItem(int slot) {
        EnumType etype = ConfigManager.getEnumType(15095);
        int key = VarManager.getInvVarbit(93, slot, 43222);
        return etype.getInputs().get(key);
    }

    /**
     *
     *   Calculates the progress of an unfinished item in a given slot as a percentage.
     *
     *   @param slot The slot of the unfinished item.
     *   @return The progress of the unfinished item as a percentage.
     */
    public static int getProgressAsPercentage(int slot) {
        int heat = getUnfinishedItemHeat(slot);
        int maxHeat = getMaximumHeat(slot);
        return (int) ((heat / (double) maxHeat) * 100);
    }

    /**
     *
     *   Gets the heat of an unfinished item in the specified slot.
     *
     *   @param slot The slot of the unfinished item.
     *   @return The heat of the unfinished item.
     */
    public static int getUnfinishedItemHeat(int slot) {
        return VarManager.getInvVarbit(93, slot, 43225);
    }

    /**
     *
     *   Gets the progress of an unfinished item in the specified inventory slot.
     *
     *   @param slot The inventory slot of the unfinished item.
     *   @return The progress of the unfinished item.
     */
    public static int getUnfinishedItemProgress(int slot) {
        return VarManager.getInvVarbit(93, slot, 43223);
    }

    /**
     *
     *   Gets the amount of experience left in a given slot.
     *
     *   @param slot The slot to get the experience from.
     *   @return The amount of experience left in the given slot.
     */
    public static int getExperiencedLeft(int slot) {
        //cs2 divides so we also divide
        return VarManager.getInvVarbit(93, slot, 43224) / 10;
    }

    /**
     *
     *   Gets the maximum heat of an item in a given slot.
     *
     *   @param slot The slot of the item.
     *   @return The maximum heat of the item, or -1 if the item is not found.
     */
    public static int getMaximumHeat(int slot) {
        EnumType etype = ConfigManager.getEnumType(15095);
        int key = VarManager.getInvVarbit(93, slot, 43222);
        if (etype == null) {
            return -1;
        }
        var value = etype.getInputs().get(key);
        if(value == null) {
            return -1;
        }
        ItemType itemType = ConfigManager.getItemType(value);
        if (itemType == null) {
            return -1;
        }
        int[] ints = script2546(itemType);
        return ints[0];
    }

    private static int[] script2546(ItemType obj0) {
        int smithingSkill = Skills.SMITHING.getLevel();
        int firemakingSkill = Skills.FIREMAKING.getLevel();
        int int2 = 300 + (smithingSkill * 3) + (firemakingSkill * 3);
        int int3 = int2 / 3;
        int int4 = int3 * 2;
        int int5 = 10;
        int int6 = 6;

        switch (script2616(obj0.getId())) {
            case 18 -> {
                int6 = smithingSkill >= 7 ? 2 : smithingSkill >= 3 ? 4 : int6;
                if (smithingSkill >= 4) int2 += 50;
            }
            case 19 -> {
                int6 = smithingSkill >= 18 ? 2 : smithingSkill >= 12 ? 4 : int6;
                if (smithingSkill >= 11) int2 += 50;
                if (smithingSkill >= 15) int2 += 50;
            }
            case 20 -> {
                int6 = smithingSkill >= 26 ? 2 : smithingSkill >= 22 ? 4 : int6;
                if (smithingSkill >= 24) int2 += 50;
                if (smithingSkill >= 29) int2 += 50;
            }
            case 22 -> {
                int6 = smithingSkill >= 39 ? 2 : smithingSkill >= 35 ? 4 : int6;
                if (smithingSkill >= 32) int2 += 50;
                if (smithingSkill >= 33) int2 += 50;
            }
            case 23 -> {
                int6 = smithingSkill >= 44 ? 2 : smithingSkill >= 42 ? 4 : int6;
                if (smithingSkill >= 43) int2 += 50;
                if (smithingSkill >= 45) int2 += 50;
            }
            case 25 -> {
                int6 = smithingSkill >= 58 ? 2 : smithingSkill >= 54 ? 4 : int6;
                if (smithingSkill >= 52) int2 += 50;
                if (smithingSkill >= 55) int2 += 50;
            }
            case 26 -> {
                int6 = smithingSkill >= 69 ? 2 : smithingSkill >= 67 ? 4 : int6;
                if (smithingSkill >= 64) int2 += 50;
                if (smithingSkill >= 66) int2 += 50;
            }
            case 27 -> {
                int6 = smithingSkill >= 78 ? 2 : smithingSkill >= 73 ? 4 : int6;
                if (smithingSkill >= 71) int2 += 50;
                if (smithingSkill >= 79) int2 += 50;
            }
            case 28 -> {
                int6 = smithingSkill >= 85 ? 2 : smithingSkill >= 82 ? 4 : int6;
                if (smithingSkill >= 84) int2 += 50;
                if (smithingSkill >= 88) int2 += 50;
            }
            case 29 -> {
                int6 = smithingSkill >= 96 ? 2 : smithingSkill >= 93 ? 4 : int6;
                if (smithingSkill >= 95) int2 += 50;
                if (smithingSkill >= 97) int2 += 50;
            }
        }

        int int7 = (int2 / int6) + (int2 % int6);
        return new int[]{int2, int3, int4, int5, int7};
    }

    private static int script2616(int objId) {
        ItemType obj0 = ConfigManager.getItemType(objId);
        if (obj0.getIntParam(7802) == 1) {
            if (obj0.getIntParam(7806) != -1) {
                obj0 = ConfigManager.getItemType(obj0.getIntParam(7806));
            } else {
                obj0 = ConfigManager.getItemType(obj0.getIntParam(2655));
            }
        }
        int[] result = script2617(obj0.getId());
        return result[0];
    }

    private static int[] script2617(int objId) {
        int int1 = 0;
        int int2 = 0;
        StructType struct3 = null;
        ItemType obj4 = null;

        ItemType obj0 = ConfigManager.getItemType(objId);

        if (obj0.getIntParam(2655) == 47066) {
            int1 = obj0.getIntParam(5456);
            int2 = obj0.getIntParam(2665);
            return new int[]{int1, int2};
        }

        struct3 = ConfigManager.getStructType(obj0.getIntParam(2675));
        if (struct3 != null) {
            int[] script2615Result = script2615(struct3.getId(), 1);
            obj4 = ConfigManager.getItemType(script2615Result[0]);
            if (obj4.getId() == 47066) {
                return new int[]{script2615Result[1], script2615Result[2]};
            }

            script2615Result = script2615(struct3.getId(), 2);
            obj4 = ConfigManager.getItemType(script2615Result[0]);
            if (obj4.getId() == 47066) {
                return new int[]{script2615Result[1], script2615Result[2]};
            }
        }

        if (obj0.getIntParam(2656) == 47066) {
            int1 = obj0.getIntParam(5457);
            int2 = obj0.getIntParam(2666);
            return new int[]{int1, int2};
        }

        struct3 = ConfigManager.getStructType(obj0.getIntParam(2676));
        if (struct3 != null) {
            int[] script2615Result = script2615(struct3.getId(), 1);
            obj4 = ConfigManager.getItemType(script2615Result[0]);
            if (obj4.getId() == 47066) {
                return new int[]{script2615Result[1], script2615Result[2]};
            }

            script2615Result = script2615(struct3.getId(), 2);
            obj4 = ConfigManager.getItemType(script2615Result[0]);
            if (obj4.getId() == 47066) {
                return new int[]{script2615Result[1], script2615Result[2]};
            }
        }

        return new int[]{0, 0};
    }

    private static int[] script2615(int structId, int int1) {
        int obj2 = 0;
        int int3 = 0;
        int int4 = 0;

        StructType struct = ConfigManager.getStructType(structId);
        if (struct == null) {
            ScriptConsole.println("[PublixAPI - Smithing] Script2615: struct is null");
            return new int[]{0, 0};
        }

        switch (int1) {
            case 1 -> {
                obj2 = struct.getParams().containsKey(2655) ? (int)(struct.getParams().get(2655)) : 0;
                int3 = struct.getParams().containsKey(5456) ? (int)(struct.getParams().get(5456)) : 0;
                int4 = struct.getParams().containsKey(2665) ? (int)(struct.getParams().get(2665)) : 0;
            }
            case 2 -> {
                obj2 = struct.getParams().containsKey(2656) ? (int)(struct.getParams().get(2656)) : 0;
                int3 = struct.getParams().containsKey(5457) ? (int)(struct.getParams().get(5457)) : 0;
                int4 = struct.getParams().containsKey(2666) ? (int)(struct.getParams().get(2666)) : 0;
            }
            case 3 -> {
                obj2 = struct.getParams().containsKey(2657) ? (int)(struct.getParams().get(2657)) : 0;
                int3 = struct.getParams().containsKey(5458) ? (int)(struct.getParams().get(5458)) : 0;
                int4 = struct.getParams().containsKey(2667) ? (int)(struct.getParams().get(2667)) : 0;
            }
            case 4 -> {
                obj2 = struct.getParams().containsKey(2658) ? (int)(struct.getParams().get(2658)) : 0;
                int3 = struct.getParams().containsKey(5459) ? (int)(struct.getParams().get(5459)) : 0;
                int4 = struct.getParams().containsKey(2668) ? (int)(struct.getParams().get(2668)) : 0;
            }
            case 5 -> {
                obj2 = struct.getParams().containsKey(2659) ? (int)(struct.getParams().get(2659)) : 0;
                int3 = struct.getParams().containsKey(5460) ? (int)(struct.getParams().get(5460)) : 0;
                int4 = struct.getParams().containsKey(2669) ? (int)(struct.getParams().get(2669)) : 0;
            }
            case 6 -> {
                obj2 = struct.getParams().containsKey(2660) ? (int)(struct.getParams().get(2660)) : 0;
                int3 = struct.getParams().containsKey(5461) ? (int)(struct.getParams().get(5461)) : 0;
                int4 = struct.getParams().containsKey(2670) ? (int)(struct.getParams().get(2670)) : 0;
            }
            case 7 -> {
                obj2 = struct.getParams().containsKey(2661) ? (int)(struct.getParams().get(2661)) : 0;
                int3 = struct.getParams().containsKey(5462) ? (int)(struct.getParams().get(5462)) : 0;
                int4 = struct.getParams().containsKey(2671) ? (int)(struct.getParams().get(2671)) : 0;
            }
            case 8 -> {
                obj2 = struct.getParams().containsKey(2662) ? (int)(struct.getParams().get(2662)) : 0;
                int3 = struct.getParams().containsKey(5463) ? (int)(struct.getParams().get(5463)) : 0;
                int4 = struct.getParams().containsKey(2672) ? (int)(struct.getParams().get(2672)) : 0;
            }
            case 9 -> {
                obj2 = struct.getParams().containsKey(2663) ? (int)(struct.getParams().get(2663)) : 0;
                int3 = struct.getParams().containsKey(5464) ? (int)(struct.getParams().get(5464)) : 0;
                int4 = struct.getParams().containsKey(2673) ? (int)(struct.getParams().get(2673)) : 0;
            }
            case 10 -> {
                obj2 = struct.getParams().containsKey(2664) ? (int)(struct.getParams().get(2664)) : 0;
                int3 = struct.getParams().containsKey(5465) ? (int)(struct.getParams().get(5465)) : 0;
                int4 = struct.getParams().containsKey(2674) ? (int)(struct.getParams().get(2674)) : 0;
            }
            case 11 -> {
                obj2 = struct.getParams().containsKey(5451) ? (int)(struct.getParams().get(5451)) : 0;
                int3 = struct.getParams().containsKey(5466) ? (int)(struct.getParams().get(5466)) : 0;
                int4 = struct.getParams().containsKey(5471) ? (int)(struct.getParams().get(5471)) : 0;
            }
            case 12 -> {
                obj2 = struct.getParams().containsKey(5452) ? (int)(struct.getParams().get(5452)) : 0;
                int3 = struct.getParams().containsKey(5467) ? (int)(struct.getParams().get(5467)) : 0;
                int4 = struct.getParams().containsKey(5472) ? (int)(struct.getParams().get(5472)) : 0;
            }
            case 13 -> {
                obj2 = struct.getParams().containsKey(5453) ? (int)(struct.getParams().get(5453)) : 0;
                int3 = struct.getParams().containsKey(5468) ? (int)(struct.getParams().get(5468)) : 0;
                int4 = struct.getParams().containsKey(5473) ? (int)(struct.getParams().get(5473)) : 0;
            }
            case 14 -> {
                obj2 = struct.getParams().containsKey(5454) ? (int)(struct.getParams().get(5454)) : 0;
                int3 = struct.getParams().containsKey(5469) ? (int)(struct.getParams().get(5469)) : 0;
                int4 = struct.getParams().containsKey(5474) ? (int)(struct.getParams().get(5474)) : 0;
            }
            case 15 -> {
                obj2 = struct.getParams().containsKey(5455) ? (int)(struct.getParams().get(5455)) : 0;
                int3 = struct.getParams().containsKey(5470) ? (int)(struct.getParams().get(5470)) : 0;
                int4 = struct.getParams().containsKey(5475) ? (int)(struct.getParams().get(5475)) : 0;
            }
        }

        return new int[]{obj2, int3, int4};
    }


}
