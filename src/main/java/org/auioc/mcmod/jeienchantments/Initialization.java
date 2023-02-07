package org.auioc.mcmod.jeienchantments;

import org.auioc.mcmod.jeienchantments.jei.JeiePlugin;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.IExtensionPoint.DisplayTest;
import net.minecraftforge.fml.ModLoadingContext;

@OnlyIn(Dist.CLIENT)
public final class Initialization {

    private Initialization() {}

    public static void init() {
        ModLoadingContext.get().registerExtensionPoint(DisplayTest.class, () -> new DisplayTest(() -> "ANY", (remote, isServer) -> isServer));
        JeiePlugin.init();
    }

}
