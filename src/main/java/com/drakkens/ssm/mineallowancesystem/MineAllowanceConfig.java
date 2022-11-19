package com.drakkens.ssm.mineallowancesystem;

import net.minecraftforge.common.ForgeConfigSpec;

public class MineAllowanceConfig {
    public static ForgeConfigSpec.IntValue MINE_DAILY_ALLOWANCE;

    public MineAllowanceConfig() {
    }

    public static void registerServerConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
        SERVER_BUILDER.comment("Settings for Mine Allowance").push("mineallowance");
        MINE_DAILY_ALLOWANCE = (ForgeConfigSpec.IntValue)SERVER_BUILDER.comment("Daily Allowance").define("dailyAllowance", 3);
        SERVER_BUILDER.pop();
    }
}
