package com.drakkens.ssm.commands;

import com.drakkens.ssm.mineallowancesystem.PlayerMineAllowanceProvider;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public class ResetMines {

    //Command Structure. Command Name, Argument(Name, Type)..., Executes(command -> function())
    public ResetMines(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("setmine")
            .requires((sourceStack) -> sourceStack.hasPermission(4))
            .then(Commands.argument("player", EntityArgument.players())
            .then(Commands.argument("count", IntegerArgumentType.integer(1))
            .executes((command) ->
                this.resetMines(command.getSource(), EntityArgument.getPlayers(command, "player"), IntegerArgumentType.getInteger(command, "count"))))));
    }

    private int resetMines(CommandSourceStack sourceStack, Collection<ServerPlayer> pTargets, int pCount) {

        for (ServerPlayer pTarget : pTargets) {
            pTarget.getCapability(PlayerMineAllowanceProvider.PLAYER_MINE_ALLOWANCE).ifPresent((playerMineAllowance) -> {
                playerMineAllowance.setMineAllowance(pCount);
            });
        }

        sourceStack.sendSuccess((Component.literal("Success!")).withStyle(ChatFormatting.GREEN), true);
        return 1;
    }
}
