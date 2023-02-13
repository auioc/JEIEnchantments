package org.auioc.mcmod.jeienchantments.record;

import java.util.Collection;
import java.util.List;
import org.auioc.mcmod.jeienchantments.api.record.IEnchantmentRecord;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record EnchantmentCompatibility(Enchantment enchantment, List<Enchantment> incompatibleEnchantments) implements IEnchantmentRecord {

    protected static EnchantmentCompatibility create(Enchantment enchantment, Collection<Enchantment> others) {
        return new EnchantmentCompatibility(
            enchantment,
            others.stream().filter((other) -> !enchantment.isCompatibleWith(other)).toList()
        );
    }

}
