package org.auioc.mcmod.jeienchantments.jei.recipe;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.auioc.mcmod.jeienchantments.record.EnchantmentCompatibility;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record IncompatibilityRecipe(Enchantment enchantment, Enchantment otherEnchantment) {

    public static List<IncompatibilityRecipe> create(Map<Enchantment, EnchantmentCompatibility> map) {
        return map.entrySet().stream().map(IncompatibilityRecipe::create).flatMap(List::stream).toList();
    }

    private static List<IncompatibilityRecipe> create(Entry<Enchantment, EnchantmentCompatibility> entry) {
        return create(entry.getKey(), entry.getValue());
    }

    private static List<IncompatibilityRecipe> create(Enchantment enchantment, EnchantmentCompatibility record) {
        return record.incompatibleEnchantments()
            .stream()
            .filter((other) -> other != enchantment)
            .map((other) -> new IncompatibilityRecipe(enchantment, other))
            .toList();
    }

}
