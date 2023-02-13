package org.auioc.mcmod.jeienchantments.record;

import java.util.Collections;
import java.util.List;
import org.auioc.mcmod.jeienchantments.api.record.IEnchantmentRecord;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record EnchantmentApplicability(Enchantment enchantment, List<AppliableItem> appliableItems) implements IEnchantmentRecord {

    protected static EnchantmentApplicability create(Enchantment enchantment, List<AppliableItem> appliableItems) {
        return new EnchantmentApplicability(enchantment, Collections.unmodifiableList(appliableItems));
    }

}
