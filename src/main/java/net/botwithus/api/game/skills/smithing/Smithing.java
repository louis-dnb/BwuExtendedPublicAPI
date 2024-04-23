package net.botwithus.api.game.skills.smithing;

import com.sun.jdi.Value;
import net.botwithus.rs3.game.cs2.ScriptBuilder;
import net.botwithus.rs3.game.cs2.layouts.Layout;
import net.botwithus.rs3.game.js5.types.EnumType;
import net.botwithus.rs3.game.js5.types.ItemType;
import net.botwithus.rs3.game.js5.types.StructType;
import net.botwithus.rs3.game.js5.types.configs.ConfigManager;
import net.botwithus.rs3.game.skills.Skills;
import net.botwithus.rs3.game.vars.VarManager;
import net.botwithus.rs3.script.Script;
import net.botwithus.rs3.script.ScriptConsole;

import java.util.Arrays;

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
//        try {
//            EnumType etype = ConfigManager.getEnumType(15095);
//            int key = VarManager.getInvVarbit(93, slot, 43222);
//            if (etype == null) {
//                ScriptConsole.println("[Smithing] Could not find enum type: 15095");
//                return -1;
//            }
//            if (etype.getInputs().size() <= key) {
//                ScriptConsole.println("[Smithing] EType Inputs size (" + etype.getInputs().size() + ") is less than key size of: " + key);
//                return -1;
//            }
//            var value = etype.getInputs().get(key);
//            if (value == null) {
//                ScriptConsole.println("[Smithing] Could not find item for key: " + key);
//                return -1;
//            }
//            ItemType itemType = ConfigManager.getItemType(value);
//            if (itemType == null) {
//                ScriptConsole.println("[Smithing] Could not find item type for item: " + value);
//                return -1;
//            }
//            int[] ints = script2546(itemType);
//            ScriptConsole.println("[Smithing] Maximum heat for item: " + itemType.getName() + " is : " + Arrays.toString(ints));
//            return ints[0];
//        } catch (Exception e) {
//            e.printStackTrace();
//            ScriptConsole.println("[Smithing] Error getting maximum heat: \n" + e.toString() + "\n" + e.getMessage());
//            return -1;
//        }

        int productId = getProductId(VarManager.getInvVarbit(93, slot, 43222));
        int maxHeat = get_max_heat.invokeExact(productId).get(0).asInt();
        return maxHeat;
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

    public static final ScriptBuilder get_max_heat = ScriptBuilder.of(2547).args(Layout.INT).returns(Layout.INT);
    public static double getHeatPercentage(int slot) {
        int productId = getProductId(VarManager.getInvVarbit(93, slot, 43222));
        double currentHeat = (double) VarManager.getInvVarbit(93, slot, 43225);
        double maxHeat = (double) get_max_heat.invokeExact(productId).get(0).asInt();
        return currentHeat / maxHeat * 100.0f;
    }

    public static int getProductId(int key) {
        switch (key) {
            case 2048:
                return 821;
            case 2049:
                return 822;
            case 1:
                return 1205;
            case 2050:
                return 823;
            case 2:
                return 25692;
            case 2051:
                return 824;
            case 3:
                return 1422;
            case 2052:
                return 9375;
            case 4:
                return 25674;
            case 2053:
                return 9377;
            case 5:
                return 1277;
            case 2054:
                return 9378;
            case 6:
                return 25710;
            case 2055:
                return 9379;
            case 7:
                return 1321;
            case 2056:
                return 9380;
            case 8:
                return 25743;
            case 2057:
                return 9381;
            case 9:
                return 1291;
            case 2058:
                return 9420;
            case 10:
                return 25725;
            case 2059:
                return 9423;
            case 11:
                return 1337;
            case 2060:
                return 9425;
            case 12:
                return 25779;
            case 2061:
                return 9427;
            case 13:
                return 1375;
            case 2062:
                return 9429;
            case 14:
                return 25761;
            case 2063:
                return 9431;
            case 15:
                return 3095;
            case 2064:
                return 1794;
            case 16:
                return 25933;
            case 2065:
                return 864;
            case 17:
                return 1307;
            case 2066:
                return 25897;
            case 18:
                return 1155;
            case 2067:
                return 863;
            case 19:
                return 1139;
            case 2068:
                return 25896;
            case 20:
                return 1075;
            case 2069:
                return 865;
            case 21:
                return 1087;
            case 2070:
                return 25898;
            case 22:
                return 1117;
            case 2071:
                return 866;
            case 23:
                return 1103;
            case 2072:
                return 25899;
            case 24:
                return 1173;
            case 2073:
                return 867;
            case 25:
                return 1189;
            case 2074:
                return 25900;
            case 26:
                return 4119;
            case 2075:
                return 868;
            case 27:
                return 45431;
            case 2076:
                return 25901;
            case 28:
                return 1265;
            case 2077:
                return 800;
            case 29:
                return 1203;
            case 2078:
                return 25903;
            case 30:
                return 45842;
            case 2079:
                return 801;
            case 31:
                return 25694;
            case 2080:
                return 25904;
            case 32:
                return 45846;
            case 2081:
                return 802;
            case 33:
                return 1420;
            case 2082:
                return 25905;
            case 34:
                return 45850;
            case 2083:
                return 803;
            case 35:
                return 25676;
            case 2084:
                return 25906;
            case 36:
                return 45854;
            case 2085:
                return 804;
            case 37:
                return 1279;
            case 2086:
                return 25907;
            case 38:
                return 45858;
            case 2087:
                return 805;
            case 39:
                return 25712;
            case 2088:
                return 25908;
            case 40:
                return 45862;
            case 2089:
                return 4540;
            case 41:
                return 1323;
            case 2090:
                return 2370;
            case 42:
                return 45866;
            case 2091:
                return 4544;
            case 43:
                return 25745;
            case 2092:
                return 667;
            case 44:
                return 45870;
            case 2093:
                return 34825;
            case 45:
                return 1293;
            case 2094:
                return 9376;
            case 46:
                return 45874;
            case 2095:
                return 9422;
            case 47:
                return 25727;
            case 2096:
                return 26126;
            case 48:
                return 45878;
            case 2097:
                return 26124;
            case 49:
                return 1335;
            case 2098:
                return 26134;
            case 50:
                return 45882;
            case 2099:
                return 26136;
            case 51:
                return 25781;
            case 2100:
                return 26138;
            case 52:
                return 45886;
            case 2101:
                return 26140;
            case 53:
                return 1363;
            case 2102:
                return 26132;
            case 54:
                return 45890;
            case 2103:
                return 26130;
            case 55:
                return 25763;
            case 2104:
                return 26128;
            case 56:
                return 45894;
            case 2105:
                return 44834;
            case 57:
                return 3096;
            case 2106:
                return 47071;
            case 58:
                return 45898;
            case 2107:
                return 1351;
            case 59:
                return 25935;
            case 2108:
                return 1349;
            case 60:
                return 45902;
            case 2109:
                return 1353;
            case 61:
                return 1309;
            case 2110:
                return 1355;
            case 62:
                return 45906;
            case 2111:
                return 1357;
            case 63:
                return 1153;
            case 2112:
                return 1359;
            case 64:
                return 45910;
            case 2113:
                return 45321;
            case 65:
                return 1137;
            case 2114:
                return 45328;
            case 66:
                return 45914;
            case 2115:
                return 45335;
            case 67:
                return 1067;
            case 2116:
                return 45342;
            case 68:
                return 45918;
            case 2117:
                return 45349;
            case 69:
                return 1081;
            case 2118:
                return 45356;
            case 70:
                return 45922;
            case 2119:
                return 45363;
            case 71:
                return 1115;
            case 2120:
                return 45370;
            case 72:
                return 45926;
            case 2121:
                return 45377;
            case 73:
                return 1101;
            case 2122:
                return 45384;
            case 74:
                return 45930;
            case 2123:
                return 45391;
            case 75:
                return 1175;
            case 2124:
                return 45398;
            case 76:
                return 45934;
            case 2125:
                return 9416;
            case 77:
                return 1191;
            case 2126:
                return 21823;
            case 78:
                return 45938;
            case 2127:
                return 21828;
            case 79:
                return 4121;
            case 2128:
                return 21833;
            case 80:
                return 45942;
            case 2129:
                return 21838;
            case 81:
                return 45945;
            case 2130:
                return 21843;
            case 82:
                return 45948;
            case 2131:
                return 21853;
            case 83:
                return 1267;
            case 2132:
                return 21848;
            case 84:
                return 45952;
            case 2133:
                return 21858;
            case 85:
                return 45441;
            case 2134:
                return 45965;
            case 86:
                return 46953;
            case 2135:
                return 45960;
            case 87:
                return 45442;
            case 2136:
                return 45970;
            case 88:
                return 46957;
            case 2137:
                return 45975;
            case 89:
                return 45443;
            case 2138:
                return 45439;
            case 90:
                return 46961;
            case 2139:
                return 46038;
            case 91:
                return 45444;
            case 2140:
                return 46053;
            case 92:
                return 46965;
            case 2141:
                return 46058;
            case 93:
                return 45445;
            case 2142:
                return 46043;
            case 94:
                return 46969;
            case 2143:
                return 46048;
            case 95:
                return 45446;
            case 2144:
                return 45999;
            case 96:
                return 46973;
            case 2145:
                return 7225;
            case 97:
                return 45447;
            case 2146:
                return 47872;
            case 98:
                return 46977;
            case 99:
                return 45448;
            case 100:
                return 46981;
            case 101:
                return 45449;
            case 102:
                return 46985;
            case 103:
                return 45450;
            case 104:
                return 46989;
            case 105:
                return 45451;
            case 106:
                return 46993;
            case 107:
                return 45452;
            case 108:
                return 46997;
            case 109:
                return 45453;
            case 110:
                return 47001;
            case 111:
                return 45454;
            case 112:
                return 47005;
            case 113:
                return 45455;
            case 114:
                return 47009;
            case 115:
                return 45456;
            case 116:
                return 47013;
            case 117:
                return 45457;
            case 118:
                return 47017;
            case 119:
                return 45458;
            case 120:
                return 47021;
            case 121:
                return 45459;
            case 122:
                return 47025;
            case 123:
                return 45460;
            case 124:
                return 47029;
            case 125:
                return 45461;
            case 126:
                return 47033;
            case 127:
                return 45462;
            case 128:
                return 47037;
            case 129:
                return 45463;
            case 130:
                return 47041;
            case 131:
                return 45464;
            case 132:
                return 47045;
            case 133:
                return 45465;
            case 134:
                return 47049;
            case 135:
                return 45466;
            case 136:
                return 47053;
            case 137:
                return 47056;
            case 138:
                return 47059;
            case 139:
                return 45467;
            case 140:
                return 47063;
            case 141:
                return 45468;
            case 142:
                return 46097;
            case 143:
                return 46100;
            case 144:
                return 45469;
            case 145:
                return 46104;
            case 146:
                return 46107;
            case 147:
                return 45470;
            case 148:
                return 46111;
            case 149:
                return 46114;
            case 150:
                return 45471;
            case 151:
                return 46118;
            case 152:
                return 46121;
            case 153:
                return 45472;
            case 154:
                return 46125;
            case 155:
                return 46128;
            case 156:
                return 45473;
            case 157:
                return 46132;
            case 158:
                return 46135;
            case 159:
                return 45474;
            case 160:
                return 46139;
            case 161:
                return 46142;
            case 162:
                return 45475;
            case 163:
                return 46146;
            case 164:
                return 46149;
            case 165:
                return 45476;
            case 166:
                return 46153;
            case 167:
                return 46156;
            case 168:
                return 45477;
            case 169:
                return 46160;
            case 170:
                return 46163;
            case 171:
                return 45478;
            case 172:
                return 46167;
            case 173:
                return 46170;
            case 174:
                return 45479;
            case 175:
                return 46174;
            case 176:
                return 46177;
            case 177:
                return 45480;
            case 178:
                return 46181;
            case 179:
                return 46184;
            case 180:
                return 45481;
            case 181:
                return 46188;
            case 182:
                return 46191;
            case 183:
                return 45482;
            case 184:
                return 46195;
            case 185:
                return 46198;
            case 186:
                return 45483;
            case 187:
                return 46202;
            case 188:
                return 46205;
            case 189:
                return 45484;
            case 190:
                return 46209;
            case 191:
                return 46212;
            case 192:
                return 45485;
            case 193:
                return 46216;
            case 194:
                return 46219;
            case 195:
                return 45486;
            case 196:
                return 46223;
            case 197:
                return 46226;
            case 198:
                return 45487;
            case 199:
                return 46230;
            case 200:
                return 46233;
            case 201:
                return 45488;
            case 202:
                return 46237;
            case 203:
                return 46240;
            case 204:
                return 45489;
            case 205:
                return 46244;
            case 206:
                return 46247;
            case 207:
                return 45490;
            case 208:
                return 46251;
            case 209:
                return 46254;
            case 210:
                return 45491;
            case 211:
                return 46258;
            case 212:
                return 46261;
            case 213:
                return 45492;
            case 214:
                return 46265;
            case 215:
                return 46268;
            case 216:
                return 45493;
            case 217:
                return 46272;
            case 218:
                return 46275;
            case 219:
                return 46278;
            case 220:
                return 46281;
            case 221:
                return 46284;
            case 222:
                return 45494;
            case 223:
                return 46288;
            case 224:
                return 46291;
            case 225:
                return 45495;
            case 226:
                return 44847;
            case 227:
                return 44850;
            case 228:
                return 44853;
            case 229:
                return 45496;
            case 230:
                return 44855;
            case 231:
                return 44858;
            case 232:
                return 44861;
            case 233:
                return 45497;
            case 234:
                return 44863;
            case 235:
                return 44866;
            case 236:
                return 44869;
            case 237:
                return 45498;
            case 238:
                return 44871;
            case 239:
                return 44874;
            case 240:
                return 44877;
            case 241:
                return 45499;
            case 242:
                return 44879;
            case 243:
                return 44882;
            case 244:
                return 44885;
            case 245:
                return 45500;
            case 246:
                return 44887;
            case 247:
                return 44890;
            case 248:
                return 44893;
            case 249:
                return 45501;
            case 250:
                return 44895;
            case 251:
                return 44898;
            case 252:
                return 44901;
            case 253:
                return 45502;
            case 254:
                return 44903;
            case 255:
                return 44906;
            case 256:
                return 44909;
            case 257:
                return 45503;
            case 258:
                return 44911;
            case 259:
                return 44914;
            case 260:
                return 44917;
            case 261:
                return 45504;
            case 262:
                return 44919;
            case 263:
                return 44922;
            case 264:
                return 44925;
            case 265:
                return 44926;
            case 266:
                return 44929;
            case 267:
                return 44932;
            case 268:
                return 44935;
            case 269:
                return 44936;
            case 270:
                return 44939;
            case 271:
                return 44942;
            case 272:
                return 44945;
            case 273:
                return 45507;
            case 274:
                return 44947;
            case 275:
                return 44950;
            case 276:
                return 44953;
            case 277:
                return 45508;
            case 278:
                return 44955;
            case 279:
                return 44958;
            case 280:
                return 44961;
            case 281:
                return 45509;
            case 282:
                return 44963;
            case 283:
                return 44966;
            case 284:
                return 44969;
            case 285:
                return 45510;
            case 286:
                return 44971;
            case 287:
                return 44974;
            case 288:
                return 44977;
            case 289:
                return 45511;
            case 290:
                return 44979;
            case 291:
                return 44982;
            case 292:
                return 44985;
            case 293:
                return 45512;
            case 294:
                return 44987;
            case 295:
                return 44990;
            case 296:
                return 44993;
            case 297:
                return 45513;
            case 298:
                return 44995;
            case 299:
                return 44998;
            case 300:
                return 45001;
            case 301:
                return 45514;
            case 302:
                return 45003;
            case 303:
                return 45006;
            case 304:
                return 45009;
            case 305:
                return 45515;
            case 306:
                return 45011;
            case 307:
                return 45014;
            case 308:
                return 45017;
            case 309:
                return 45516;
            case 310:
                return 45019;
            case 311:
                return 45022;
            case 312:
                return 45025;
            case 313:
                return 45517;
            case 314:
                return 45027;
            case 315:
                return 45030;
            case 316:
                return 45033;
            case 317:
                return 45518;
            case 318:
                return 45035;
            case 319:
                return 45038;
            case 320:
                return 45041;
            case 321:
                return 45519;
            case 322:
                return 45043;
            case 323:
                return 45046;
            case 324:
                return 45049;
            case 325:
                return 45520;
            case 326:
                return 45051;
            case 327:
                return 45054;
            case 328:
                return 45057;
            case 329:
                return 45058;
            case 330:
                return 45061;
            case 331:
                return 45064;
            case 332:
                return 45067;
            case 333:
                return 45521;
            case 334:
                return 45069;
            case 335:
                return 45072;
            case 336:
                return 45075;
            case 337:
                return 45522;
            case 338:
                return 46669;
            case 339:
                return 46672;
            case 340:
                return 46675;
            case 341:
                return 46678;
            case 342:
                return 45523;
            case 343:
                return 46679;
            case 344:
                return 46682;
            case 345:
                return 46685;
            case 346:
                return 46688;
            case 347:
                return 45524;
            case 348:
                return 46689;
            case 349:
                return 46692;
            case 350:
                return 46695;
            case 351:
                return 46698;
            case 352:
                return 45525;
            case 353:
                return 46699;
            case 354:
                return 46702;
            case 355:
                return 46705;
            case 356:
                return 46708;
            case 357:
                return 45526;
            case 358:
                return 46709;
            case 359:
                return 46712;
            case 360:
                return 46715;
            case 361:
                return 46718;
            case 362:
                return 45527;
            case 363:
                return 46719;
            case 364:
                return 46722;
            case 365:
                return 46725;
            case 366:
                return 46728;
            case 367:
                return 45528;
            case 368:
                return 46729;
            case 369:
                return 46732;
            case 370:
                return 46735;
            case 371:
                return 46738;
            case 372:
                return 45529;
            case 373:
                return 46739;
            case 374:
                return 46742;
            case 375:
                return 46745;
            case 376:
                return 46748;
            case 377:
                return 45530;
            case 378:
                return 46749;
            case 379:
                return 46752;
            case 380:
                return 46755;
            case 381:
                return 46758;
            case 382:
                return 45531;
            case 383:
                return 46759;
            case 384:
                return 46762;
            case 385:
                return 46765;
            case 386:
                return 46768;
            case 387:
                return 45532;
            case 388:
                return 46769;
            case 389:
                return 46772;
            case 390:
                return 46775;
            case 391:
                return 46778;
            case 392:
                return 45533;
            case 393:
                return 46779;
            case 394:
                return 46782;
            case 395:
                return 46785;
            case 396:
                return 46788;
            case 397:
                return 45534;
            case 398:
                return 46789;
            case 399:
                return 46792;
            case 400:
                return 46795;
            case 401:
                return 46798;
            case 402:
                return 45535;
            case 403:
                return 46799;
            case 404:
                return 46802;
            case 405:
                return 46805;
            case 406:
                return 46808;
            case 407:
                return 45536;
            case 408:
                return 46809;
            case 409:
                return 46812;
            case 410:
                return 46815;
            case 411:
                return 46818;
            case 412:
                return 45537;
            case 413:
                return 46819;
            case 414:
                return 46822;
            case 415:
                return 46825;
            case 416:
                return 46828;
            case 417:
                return 45538;
            case 418:
                return 46829;
            case 419:
                return 46832;
            case 420:
                return 46835;
            case 421:
                return 46838;
            case 422:
                return 45539;
            case 423:
                return 46839;
            case 424:
                return 46842;
            case 425:
                return 46845;
            case 426:
                return 46848;
            case 427:
                return 45540;
            case 428:
                return 46849;
            case 429:
                return 46852;
            case 430:
                return 46855;
            case 431:
                return 46858;
            case 432:
                return 45541;
            case 433:
                return 46859;
            case 434:
                return 46862;
            case 435:
                return 46865;
            case 436:
                return 46868;
            case 437:
                return 45542;
            case 438:
                return 46869;
            case 439:
                return 46872;
            case 440:
                return 46875;
            case 441:
                return 46878;
            case 442:
                return 45543;
            case 443:
                return 46879;
            case 444:
                return 46882;
            case 445:
                return 46885;
            case 446:
                return 46888;
            case 447:
                return 45544;
            case 448:
                return 46889;
            case 449:
                return 46892;
            case 450:
                return 46895;
            case 451:
                return 46898;
            case 452:
                return 45545;
            case 453:
                return 46899;
            case 454:
                return 46902;
            case 455:
                return 46905;
            case 456:
                return 46908;
            case 457:
                return 45546;
            case 458:
                return 46909;
            case 459:
                return 46912;
            case 460:
                return 46915;
            case 461:
                return 46918;
            case 462:
                return 45547;
            case 463:
                return 46919;
            case 464:
                return 46922;
            case 465:
                return 46925;
            case 466:
                return 46928;
            case 467:
                return 46929;
            case 468:
                return 46932;
            case 469:
                return 46935;
            case 470:
                return 46938;
            case 471:
                return 46941;
            case 472:
                return 45548;
            case 473:
                return 46942;
            case 474:
                return 46945;
            case 475:
                return 46948;
            case 476:
                return 46951;
            case 477:
                return 46539;
            case 478:
                return 46542;
            case 479:
                return 46545;
            case 480:
                return 46548;
            case 481:
                return 46551;
            case 482:
                return 46552;
            case 483:
                return 46555;
            case 484:
                return 46558;
            case 485:
                return 46561;
            case 486:
                return 46564;
            case 487:
                return 46565;
            case 488:
                return 46568;
            case 489:
                return 46571;
            case 490:
                return 46574;
            case 491:
                return 46577;
            case 492:
                return 46578;
            case 493:
                return 46581;
            case 494:
                return 46584;
            case 495:
                return 46587;
            case 496:
                return 46590;
            case 497:
                return 46591;
            case 498:
                return 46594;
            case 499:
                return 46597;
            case 500:
                return 46600;
            case 501:
                return 46603;
            case 502:
                return 46604;
            case 503:
                return 46607;
            case 504:
                return 46610;
            case 505:
                return 46613;
            case 506:
                return 46616;
            case 507:
                return 46617;
            case 508:
                return 46620;
            case 509:
                return 46623;
            case 510:
                return 46626;
            case 511:
                return 46629;
            case 512:
                return 46630;
            case 513:
                return 46633;
            case 514:
                return 46636;
            case 515:
                return 46639;
            case 516:
                return 46642;
            case 517:
                return 46643;
            case 518:
                return 46646;
            case 519:
                return 46649;
            case 520:
                return 46652;
            case 521:
                return 46655;
            case 522:
                return 46656;
            case 523:
                return 46659;
            case 524:
                return 46662;
            case 525:
                return 46665;
            case 526:
                return 46668;
            case 527:
                return 46294;
            case 528:
                return 46299;
            case 529:
                return 46304;
            case 530:
                return 46309;
            case 531:
                return 46314;
            case 532:
                return 46319;
            case 533:
                return 46320;
            case 534:
                return 46325;
            case 535:
                return 46330;
            case 536:
                return 46335;
            case 537:
                return 46340;
            case 538:
                return 46345;
            case 539:
                return 46346;
            case 540:
                return 46351;
            case 541:
                return 46356;
            case 542:
                return 46361;
            case 543:
                return 46366;
            case 544:
                return 46371;
            case 545:
                return 46372;
            case 546:
                return 46374;
            case 547:
                return 46376;
            case 548:
                return 46378;
            case 549:
                return 46380;
            case 550:
                return 46382;
            case 551:
                return 46383;
            case 552:
                return 46388;
            case 553:
                return 46393;
            case 554:
                return 46398;
            case 555:
                return 46403;
            case 556:
                return 46408;
            case 557:
                return 46409;
            case 558:
                return 46414;
            case 559:
                return 46419;
            case 560:
                return 46424;
            case 561:
                return 46429;
            case 562:
                return 46434;
            case 563:
                return 46435;
            case 564:
                return 46440;
            case 565:
                return 46445;
            case 566:
                return 46450;
            case 567:
                return 46455;
            case 568:
                return 46460;
            case 569:
                return 46461;
            case 570:
                return 46466;
            case 571:
                return 46471;
            case 572:
                return 46476;
            case 573:
                return 46481;
            case 574:
                return 46486;
            case 575:
                return 46487;
            case 576:
                return 46492;
            case 577:
                return 46497;
            case 578:
                return 46502;
            case 579:
                return 46507;
            case 580:
                return 46512;
            case 581:
                return 46513;
            case 582:
                return 46518;
            case 583:
                return 46523;
            case 584:
                return 46528;
            case 585:
                return 46533;
            case 586:
                return 46538;
            case 587:
                return 45076;
            case 588:
                return 45081;
            case 589:
                return 45086;
            case 590:
                return 45091;
            case 591:
                return 45096;
            case 592:
                return 45101;
            case 593:
                return 45102;
            case 594:
                return 45107;
            case 595:
                return 45112;
            case 596:
                return 45117;
            case 597:
                return 45122;
            case 598:
                return 45127;
            case 599:
                return 45128;
            case 600:
                return 45133;
            case 601:
                return 45138;
            case 602:
                return 45143;
            case 603:
                return 45148;
            case 604:
                return 45153;
            case 605:
                return 45154;
            case 606:
                return 45156;
            case 607:
                return 45158;
            case 608:
                return 45160;
            case 609:
                return 45162;
            case 610:
                return 45164;
            case 611:
                return 45165;
            case 612:
                return 45170;
            case 613:
                return 45175;
            case 614:
                return 45180;
            case 615:
                return 45185;
            case 616:
                return 45190;
            case 617:
                return 45191;
            case 618:
                return 45196;
            case 619:
                return 45201;
            case 620:
                return 45206;
            case 621:
                return 45211;
            case 622:
                return 45216;
            case 623:
                return 45217;
            case 624:
                return 45222;
            case 625:
                return 45227;
            case 626:
                return 45232;
            case 627:
                return 45237;
            case 628:
                return 45242;
            case 629:
                return 45243;
            case 630:
                return 45248;
            case 631:
                return 45253;
            case 632:
                return 45258;
            case 633:
                return 45263;
            case 634:
                return 45268;
            case 635:
                return 45269;
            case 636:
                return 45274;
            case 637:
                return 45279;
            case 638:
                return 45284;
            case 639:
                return 45289;
            case 640:
                return 45294;
            case 641:
                return 45295;
            case 642:
                return 45300;
            case 643:
                return 45305;
            case 644:
                return 45310;
            case 645:
                return 45315;
            case 646:
                return 45320;
            case 647:
                return 45549;
            case 648:
                return 45554;
            case 649:
                return 45559;
            case 650:
                return 45564;
            case 651:
                return 45569;
            case 652:
                return 45574;
            case 653:
                return 45579;
            case 654:
                return 45580;
            case 655:
                return 45585;
            case 656:
                return 45590;
            case 657:
                return 45595;
            case 658:
                return 45600;
            case 659:
                return 45605;
            case 660:
                return 45610;
            case 661:
                return 45611;
            case 662:
                return 45616;
            case 663:
                return 45621;
            case 664:
                return 45626;
            case 665:
                return 45631;
            case 666:
                return 45636;
            case 667:
                return 45641;
            case 668:
                return 45642;
            case 669:
                return 45644;
            case 670:
                return 45646;
            case 671:
                return 45648;
            case 672:
                return 45650;
            case 673:
                return 45652;
            case 674:
                return 45654;
            case 675:
                return 45655;
            case 676:
                return 45660;
            case 677:
                return 45665;
            case 678:
                return 45670;
            case 679:
                return 45675;
            case 680:
                return 45680;
            case 681:
                return 45685;
            case 682:
                return 45686;
            case 683:
                return 45691;
            case 684:
                return 45696;
            case 685:
                return 45701;
            case 686:
                return 45706;
            case 687:
                return 45711;
            case 688:
                return 45716;
            case 689:
                return 45717;
            case 690:
                return 45722;
            case 691:
                return 45727;
            case 692:
                return 45732;
            case 693:
                return 45737;
            case 694:
                return 45742;
            case 695:
                return 45747;
            case 696:
                return 45748;
            case 697:
                return 45753;
            case 698:
                return 45758;
            case 699:
                return 45763;
            case 700:
                return 45768;
            case 701:
                return 45773;
            case 702:
                return 45778;
            case 703:
                return 45779;
            case 704:
                return 45784;
            case 705:
                return 45789;
            case 706:
                return 45794;
            case 707:
                return 45799;
            case 708:
                return 45804;
            case 709:
                return 45809;
            case 710:
                return 45810;
            case 711:
                return 45815;
            case 712:
                return 45820;
            case 713:
                return 45825;
            case 714:
                return 45830;
            case 715:
                return 45835;
            case 716:
                return 45840;
            case 717:
                return 49539;
            case 718:
                return 49542;
            case 719:
                return 49545;
            case 720:
                return 49548;
            case 721:
                return 49551;
            case 722:
                return 49554;
            case 723:
                return 49557;
            case 724:
                return 49560;
            case 725:
                return 49562;
            case 726:
                return 49564;
            case 727:
                return 49969;
            case 728:
                return 49970;
            case 2000:
                return 44779;
            case 2001:
                return 44781;
            case 2002:
                return 44783;
            case 2003:
                return 44785;
            case 2004:
                return 44787;
            case 2005:
                return 44789;
            case 2006:
                return 44791;
            case 2007:
                return 44793;
            case 2008:
                return 44795;
            case 2009:
                return 44797;
            case 2012:
                return 45993;
            case 2013:
                return 45995;
            case 2014:
                return 45997;
            case 2015:
                return 46003;
            case 2016:
                return 46036;
            case 2017:
                return 45435;
            case 2018:
                return 45436;
            case 2019:
                return 45437;
            case 2020:
                return 45438;
            case 2021:
                return 45440;
            case 2022:
                return 11367;
            case 2023:
                return 11369;
            case 2024:
                return 11371;
            case 2025:
                return 11373;
            case 2026:
                return 11375;
            case 2027:
                return 11377;
            case 2028:
                return 1237;
            case 2029:
                return 1239;
            case 2030:
                return 1241;
            case 2031:
                return 1243;
            case 2032:
                return 1245;
            case 2033:
                return 1247;
            case 2034:
                return 4819;
            case 2035:
                return 4820;
            case 2036:
                return 1539;
            case 2037:
                return 4822;
            case 2038:
                return 4823;
            case 2039:
                return 4824;
            case 2040:
                return 39;
            case 2041:
                return 40;
            case 2042:
                return 41;
            case 2043:
                return 42;
            case 2044:
                return 43;
            case 2045:
                return 44;
            case 2046:
                return 819;
            case 2047:
                return 820;
        }
        return -1;
    }
}
