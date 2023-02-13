package org.auioc.mcmod.jeienchantments.record;

import org.auioc.mcmod.jeienchantments.api.record.IEnchantmentRecord;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AvailableEnchantment(Enchantment enchantment, boolean canApply, boolean canApplyAtTable) implements IEnchantmentRecord {

}
