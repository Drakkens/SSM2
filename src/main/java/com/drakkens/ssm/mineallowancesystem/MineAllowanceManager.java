package com.drakkens.ssm.mineallowancesystem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;

import javax.annotation.Nonnull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class MineAllowanceManager extends SavedData {
    private Long lastReset = 0L;

    @Nonnull
    public static MineAllowanceManager getData(MinecraftServer server) {
        return server.overworld().getDataStorage().computeIfAbsent(MineAllowanceManager::new, MineAllowanceManager::new, "lastreset");
    }

    public MineAllowanceManager() {
    }

    public MineAllowanceManager(CompoundTag tag) {
        this.lastReset = tag.getLong("lastreset");
    }

    public CompoundTag save(CompoundTag pCompoundTag) {
        pCompoundTag.putLong("lastreset", this.lastReset);
        return pCompoundTag;
    }

    public void setLastReset(Long lastReset) {
        this.lastReset = lastReset;
        this.setDirty();
    }

    public LocalDate lastResetToDate() {
        return Instant.ofEpochMilli(this.lastReset).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
