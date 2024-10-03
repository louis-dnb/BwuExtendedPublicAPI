package net.botwithus.api.game;

import net.botwithus.rs3.game.vars.VarManager;

import java.util.concurrent.Callable;

public enum Buff {
    OVERLOAD_POTION("Overload", () -> {
        var val = VarManager.getVarbitValue(48834);
        return new Integer[] {
            val * 15,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    IRIT_STICKS("Irit incense sticks", () -> {
        var val = VarManager.getVarbitValue(43714);
        return new Integer[] {
            val * 15,
            VarManager.getVarbitValue(43696),
            val > 0 ? 1 : 0
        };
    }),
    KWUARM_STICKS("Kwuarm incense sticks", () -> {
        var val = VarManager.getVarbitValue(43725);
        return new Integer[] {
            val * 15,
            VarManager.getVarbitValue(43707),
            val > 0 ? 1 : 0
        };
    }),
    LANTADYME_STICKS("Lantadyme incense sticks", () -> {
        var val = VarManager.getVarbitValue(43717);
        return new Integer[] {
            val * 15,
            VarManager.getVarbitValue(43699),
            val > 0 ? 1 : 0
        };
    }),
    ANTIFIRE_POTION("Antifire", () -> {
        var val = VarManager.getVarbitValue(497);
        return new Integer[] {
            val * 15,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    SUPER_ANTIFIRE_POTION("Super Antifire", () -> {
        var val = VarManager.getVarbitValue(498);
        return new Integer[] {
            val * 15,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    POWDER_OF_PROTECTION("Powder of Protection", () -> {
        var val = VarManager.getVarbitValue(50837);
        return new Integer[] {
            Integer.MIN_VALUE,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    WEAPON_POISON("Weapon Poison", () -> {
        var val = VarManager.getVarbitValue(2102);
        return new Integer[] {
            Integer.MIN_VALUE,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    AGGRESSION_POTION("Aggression", () -> {
        var val = VarManager.getVarbitValue(33448);
        return new Integer[] {
            Integer.MIN_VALUE,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    PRAYER_RENEWAL_POTION("Prayer Renewal", () -> {
        var val = VarManager.getVarbitValue(2100);
        return new Integer[] {
            Integer.MIN_VALUE,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    SUPER_PRAYER_RENEWAL_POTION("Super Prayer Renewal", () -> {
        var val = VarManager.getVarbitValue(25852);
        return new Integer[] {
            Integer.MIN_VALUE,
            val > 0 ? 1 : 0,
            val > 0 ? 1 : 0
        };
    }),
    ;

    private String name;
    private Callable<Integer[]> logic; // timeRemaining, potency, isActive;

    Buff(String name, Callable<Integer[]> logic) {
        this.name = name;
        this.logic = logic;
    }

    public int getTimeRemaining() {
        return getValueFromLogic(0);
    }

    public int getPotency() {
        return getValueFromLogic(1);
    }

    public boolean isActive() {
        return getValueFromLogic(2) == 1;
    }

    private int getValueFromLogic(int index) {
        try {
            return logic.call()[index];
        } catch (Exception e) {
            throw new RuntimeException("Error executing logic for " + name, e);
        }
    }
}
