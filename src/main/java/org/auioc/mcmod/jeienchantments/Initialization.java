package org.auioc.mcmod.jeienchantments;

import org.auioc.mcmod.jeienchantments.config.JeieConfig;
import org.auioc.mcmod.jeienchantments.jei.JeiePlugin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@OnlyIn(Dist.CLIENT)
public final class Initialization {

    private Initialization() {}

    private static final IEventBus MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();

    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> "ANY", (remote, isServer) -> isServer));
        modSetup();
        registerConfig();
        JeiePlugin.init();
    }

    private static void registerConfig() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, JeieConfig.SPEC);
    }

    private static void modSetup() {
        MOD_BUS.addListener(Initialization::onCommonSetup);
        MOD_BUS.addListener(Initialization::onConfigEvent);
    }

    // ====================================================================== //

    private static void onCommonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(
            () -> {
                JEIEnchantments.setupCompleted();
                JeieConfig.onLoad(null);
            }
        );
    }

    private static void onConfigEvent(final ModConfigEvent event) {
        var config = event.getConfig();
        if (config.getModId().equals(JEIEnchantments.MOD_ID) && config.getType() == ModConfig.Type.CLIENT) {
            JeieConfig.onLoad(config.getConfigData());
        }
    }

}
