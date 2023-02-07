package org.auioc.mcmod.jeienchantments.jei.recipe;

import java.util.List;
import org.auioc.mcmod.jeienchantments.utils.EnchantmentInfo;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record IncompatibilityRecipe(Enchantment enchantment, Enchantment otherEnchantment) {

    public static List<IncompatibilityRecipe> create(EnchantmentInfo info) {
        var ench = info.enchantment();
        return info.incompatibleEnchantments()
            .parallelStream()
            .filter((other) -> other != ench)
            .map((other) -> new IncompatibilityRecipe(ench, other))
            .toList();
    }

    public static List<IncompatibilityRecipe> create(List<EnchantmentInfo> infos) {
        return infos.parallelStream().map(IncompatibilityRecipe::create).flatMap(List::stream).toList();
    }

}
