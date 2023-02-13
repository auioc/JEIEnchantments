package org.auioc.mcmod.jeienchantments.api.record;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public interface IRegistryEntryRecord<T extends ForgeRegistryEntry<?>> {

    T registryEntry();

}
