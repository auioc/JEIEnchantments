package org.auioc.mcmod.jeienchantments;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod(JEIEnchantments.MOD_ID)
public final class JEIEnchantments {

    public static final String MOD_ID = "jeienchantments";
    public static final String MOD_NAME = "JEIEnchantments";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME);

    public JEIEnchantments() {
        if (FMLEnvironment.dist == Dist.CLIENT) Initialization.init();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static String i18n(String key) {
        return MOD_ID + "." + key;
    }

}
