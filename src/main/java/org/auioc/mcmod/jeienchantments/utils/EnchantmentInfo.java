package org.auioc.mcmod.jeienchantments.utils;

import java.util.List;
import org.auioc.mcmod.jeienchantments.api.IEnchantmentRecord;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record EnchantmentInfo(Enchantment enchantment, List<AppliableItem> appliableItems, List<Enchantment> incompatibleEnchantments) implements IEnchantmentRecord {

}
