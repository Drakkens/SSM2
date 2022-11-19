package com.drakkens.ssm.mineallowancesystem;

import net.minecraftforge.common.ForgeConfigSpec;

public class PlayerAllowanceConfig {
    public static ForgeConfigSpec.IntValue DAILY_MINES;

    public static void registerServerConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
        SERVER_BUILDER.comment("Settings for mine allowance").push("allowance");

        DAILY_MINES = SERVER_BUILDER.comment("Daily Allowance")
                              .defineInRange("dailyAllowance", 10, 0, 20);

        SERVER_BUILDER.pop();
    }
}
