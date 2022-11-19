package com.drakkens.ssm.setup;

import com.drakkens.ssm.mineallowancesystem.MineAllowanceEvents;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModSetup {
    public ModSetup() {
    }

    public static void init(FMLCommonSetupEvent event) {
    }

    public static void setup() {
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.addGenericListener(Entity.class, MineAllowanceEvents::onAttachCapabilitiesPlayer);
        bus.addListener(MineAllowanceEvents::onPlayerCloned);
        bus.addListener(MineAllowanceEvents::onRegisterCapabilities);
    }
}
