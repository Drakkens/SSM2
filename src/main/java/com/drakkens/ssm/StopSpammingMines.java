package com.drakkens.ssm;

import com.drakkens.ssm.setup.ClientSetup;
import com.drakkens.ssm.setup.ModSetup;
import com.drakkens.ssm.setup.Registration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("ssm")
public class StopSpammingMines {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "ssm";

    public StopSpammingMines() {
        ModSetup.setup();
        Registration.init();
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        modBus.addListener(ModSetup::init);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.addListener(ClientSetup::init));
    }
}
