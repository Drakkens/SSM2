package com.drakkens.ssm.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class SSMLanguageProvider extends LanguageProvider {
    public SSMLanguageProvider(DataGenerator gen, String locale) {
        super(gen, "ssm", locale);
    }

    protected void addTranslations() {
        this.add("message.noallowance", "No more mines allowed today");
        this.add("message.allowance", "You can still place %s mines");
    }
}
