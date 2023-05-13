package com.drakkens.ssm.mineallowancesystem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class MineAllowanceEvents {
    public MineAllowanceEvents() {
    }

    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        Object var2 = event.getObject();
        if (var2 instanceof Player player) {
            if (!player.getCapability(PlayerMineAllowanceProvider.PLAYER_MINE_ALLOWANCE).isPresent()) {
                event.addCapability(new ResourceLocation("ssm", "playermineallowance"), new PlayerMineAllowanceProvider());
            }
        }

    }

    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            Player oldPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();
            oldPlayer.reviveCaps();
            oldPlayer.getCapability(PlayerMineAllowanceProvider.PLAYER_MINE_ALLOWANCE).ifPresent((oldPlayerAllowance) -> {
                newPlayer.getCapability(PlayerMineAllowanceProvider.PLAYER_MINE_ALLOWANCE).ifPresent((newPlayerAllowance) -> {
                    newPlayerAllowance.copyFrom(oldPlayerAllowance);
                });
            });
        }

    }

    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerMineAllowance.class);
    }
}
