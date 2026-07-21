package net.bball_262.redsbiomes.entities;

import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

public enum CrabVariant {
    GRAY(0),
    RED(1);

    public static final IntFunction<CrabVariant> BY_ID = ByIdMap.continuous(CrabVariant::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    private final int id;

    CrabVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}