package com.drakkens.ssm.mineallowancesystem;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class PlayerMineAllowanceProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMineAllowance> PLAYER_MINE_ALLOWANCE = CapabilityManager.get(new CapabilityToken<>() {
    });
    private PlayerMineAllowance playerMineAllowance;
    private final LazyOptional<PlayerMineAllowance> opt = LazyOptional.of(this::createPlayerMineAllowance);

    public PlayerMineAllowanceProvider() {
    }

    @Nonnull
    private PlayerMineAllowance createPlayerMineAllowance() {
        if (this.playerMineAllowance == null) {
            this.playerMineAllowance = new PlayerMineAllowance();
        }

        return this.playerMineAllowance;
    }

    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return this.getCapability(cap);
    }

    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return cap == PLAYER_MINE_ALLOWANCE ? this.opt.cast() : LazyOptional.empty();
    }

    public CompoundTag serializeNBT() {
        CompoundTag compoundTag = new CompoundTag();
        this.createPlayerMineAllowance().saveNBTData(compoundTag);
        return compoundTag;
    }

    public void deserializeNBT(CompoundTag nbt) {
        this.createPlayerMineAllowance().loadNBTData(nbt);
    }
}
