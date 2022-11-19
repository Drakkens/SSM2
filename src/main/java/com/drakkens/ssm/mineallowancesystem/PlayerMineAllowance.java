package com.drakkens.ssm.mineallowancesystem;

import net.minecraft.nbt.CompoundTag;

public class PlayerMineAllowance {
//TODO: Config this
    private int mineAllowance = 3;

    public PlayerMineAllowance() {
    }

    public int getMineAllowance() {
        return this.mineAllowance;
    }

    public void setMineAllowance(int mineAllowance) {
        this.mineAllowance = mineAllowance;
    }

    public void addMineAllowance(int mineAllowance) {
        this.mineAllowance += mineAllowance;
    }

    public void subtractMineAllowance(int mineAllowance) {
        this.mineAllowance -= mineAllowance;
    }

    public void copyFrom(PlayerMineAllowance source) {
        this.mineAllowance = source.mineAllowance;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("mineallowance", this.mineAllowance);
    }

    public void loadNBTData(CompoundTag tag) {
        this.mineAllowance = tag.getInt("mineallowance");
    }
}
