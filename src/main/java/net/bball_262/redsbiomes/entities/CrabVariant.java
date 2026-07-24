package net.bball_262.redsbiomes.entities;

import net.minecraft.ChatFormatting;
import net.minecraft.util.ByIdMap;

import java.util.function.IntFunction;

public enum CrabVariant {
    GRAY(0, "GRAY", true, ChatFormatting.GRAY.getColor()),
    RED(1, "RED", true, ChatFormatting.RED.getColor()),
    PURPLE(2, "PURPLE", false, ChatFormatting.DARK_PURPLE.getColor());

    public static final IntFunction<CrabVariant> BY_ID = ByIdMap.continuous(CrabVariant::getId, values(), ByIdMap.OutOfBoundsStrategy.WRAP);
    private final int id;
    private final String name;
    private final boolean common;
    private final int color;

    CrabVariant(int id, String name, boolean common, int color) {
        this.id = id;
        this.name = name;
        this.common = common;
        this.color = color;
    }

    public boolean isCommon() {
        return common;
    }
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getColor() {
        return color;
    }
}