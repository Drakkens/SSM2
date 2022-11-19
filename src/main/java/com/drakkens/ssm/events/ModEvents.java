package com.drakkens.ssm.events;

import com.drakkens.ssm.commands.ResetMines;
import com.drakkens.ssm.mineallowancesystem.MineAllowanceManager;
import com.drakkens.ssm.mineallowancesystem.PlayerMineAllowanceProvider;
import net.geforcemods.securitycraft.blocks.mines.ExplosiveBlock;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraftforge.server.command.ConfigCommand;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;

@EventBusSubscriber(
        modid = "ssm"
)
public class ModEvents {
    public static final String MESSAGE_NO_ALLOWANCE = "message.noallowance";
    public static final String MESSAGE_REMAINING = "message.allowance";
    private static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();

    public ModEvents() {
    }

    @SubscribeEvent
    public static void cancelMinePlacement(BlockEvent.EntityPlaceEvent event) {
        if (event.getPlacedBlock().getBlock() instanceof ExplosiveBlock) {
            Entity var2 = event.getEntity();
            if (var2 instanceof Player player) {
                player.getCapability(PlayerMineAllowanceProvider.PLAYER_MINE_ALLOWANCE).ifPresent((playerMineAllowance) -> {
                    if (playerMineAllowance.getMineAllowance() == 0) {
                        player.sendMessage((new TranslatableComponent("message.noallowance")).withStyle(ChatFormatting.RED), Util.NIL_UUID);
                        event.setCanceled(true);
                    } else if (playerMineAllowance.getMineAllowance() == 1) {
                        playerMineAllowance.subtractMineAllowance(1);
                        player.sendMessage((new TranslatableComponent("message.noallowance")).withStyle(ChatFormatting.YELLOW), Util.NIL_UUID);
                    } else {
                        playerMineAllowance.subtractMineAllowance(1);
                        player.sendMessage((new TranslatableComponent("message.allowance", new Object[]{playerMineAllowance.getMineAllowance()})).withStyle(ChatFormatting.GREEN), Util.NIL_UUID);
                    }

                });
            }
        }

    }

    @SubscribeEvent
    public static void resetPlayerMines(TickEvent.ServerTickEvent event) {
        if (SERVER == null) {
            SERVER = ServerLifecycleHooks.getCurrentServer();
        }

        if (event.phase != Phase.END) {
            LocalDate currentDate = LocalDate.now();
            LocalDate lastResetDate = MineAllowanceManager.getData(SERVER).lastResetToDate();
            if (!currentDate.equals(lastResetDate)) {
                List<ServerPlayer> players = SERVER.getPlayerList().getPlayers();

                for (ServerPlayer player : players) {
                    player.getCapability(PlayerMineAllowanceProvider.PLAYER_MINE_ALLOWANCE).ifPresent((playerMineAllowance) -> {
                        //TODO: Config this
                        playerMineAllowance.setMineAllowance(3);
                    });
                    MineAllowanceManager.getData(SERVER).setLastReset(currentDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
                }
            }
        }

    }

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        new ResetMines(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
