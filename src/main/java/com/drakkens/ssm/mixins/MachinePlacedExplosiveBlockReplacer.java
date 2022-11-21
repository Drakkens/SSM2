package com.drakkens.ssm.mixins;

import com.drakkens.ssm.StopSpammingMines;
import net.geforcemods.securitycraft.blocks.OwnableBlock;
import net.geforcemods.securitycraft.blocks.mines.ExplosiveBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OwnableBlock.class)
public abstract class MachinePlacedExplosiveBlockReplacer {

    @Inject(method = "setPlacedBy", at = @At("HEAD"))
    public void injectSetPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack, CallbackInfo ci) {
        if (pState.getBlock() instanceof ExplosiveBlock && ((pPlacer instanceof FakePlayer) || !(pPlacer instanceof Player))) {
            pLevel.setBlock(pPos, Blocks.AIR.defaultBlockState(), 18);

            StopSpammingMines.LOGGER.info("Replaced ExplosiveBlock at: " + pPos);
        }
    }
}