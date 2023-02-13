package org.auioc.mcmod.jeienchantments.api.record;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IEnchantmentRecord {

    Enchantment enchantment();

}
