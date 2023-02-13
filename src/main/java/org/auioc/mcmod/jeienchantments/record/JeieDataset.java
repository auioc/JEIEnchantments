package org.auioc.mcmod.jeienchantments.record;

import java.util.List;
import java.util.Map;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record JeieDataset(
    List<Enchantment> enchantments,
    Map<Enchantment, EnchantmentCompatibility> enchantmentCompatibilityMap,
    Map<Enchantment, EnchantmentApplicability> enchantmentApplicabilityMap,
    Map<Item, ItemEnchantmentAvailability> itemEnchantmentAvailabilityMap
) {

    public static JeieDataset create() {
        return new JeieDatasetBuilder().build();
    }

}
