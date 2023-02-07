package org.auioc.mcmod.jeienchantments.utils;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AppliableItem(Item item, boolean canApply, boolean canApplyAtTable) {

}
