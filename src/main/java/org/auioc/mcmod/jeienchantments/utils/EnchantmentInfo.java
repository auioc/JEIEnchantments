package org.auioc.mcmod.jeienchantments.utils;

import java.util.Collection;
import java.util.List;
import org.auioc.mcmod.jeienchantments.api.IEnchantmentRecord;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

@OnlyIn(Dist.CLIENT)
public record EnchantmentInfo(Enchantment enchantment, List<AppliableItem> appliableItems, List<Enchantment> incompatibleEnchantments) implements IEnchantmentRecord {

    public static List<EnchantmentInfo> createAll() {
        var itemStacks = ForgeRegistries.ITEMS.getValues().parallelStream().map(ItemStack::new).toList();
        var enchantments = ForgeRegistries.ENCHANTMENTS.getValues();
        return enchantments
            .parallelStream()
            .map(
                (enchantment) -> new EnchantmentInfo(
                    enchantment,
                    filterItem(itemStacks, enchantment),
                    filterEnch(enchantments, enchantment)
                )
            )
            .toList();
    }

    private static List<AppliableItem> filterItem(List<ItemStack> itemStacks, Enchantment enchantment) {
        return itemStacks
            .parallelStream()
            .map(
                (itemStack) -> new AppliableItem(
                    itemStack.getItem(),
                    enchantment.canEnchant(itemStack),
                    !enchantment.isTreasureOnly() && enchantment.isDiscoverable() && (itemStack.getItemEnchantability() > 0) && enchantment.canApplyAtEnchantingTable(itemStack)
                )
            )
            .filter((appliableItem) -> appliableItem.canApply() || appliableItem.canApplyAtTable())
            .toList();
    }

    private static List<Enchantment> filterEnch(Collection<Enchantment> enchantments, Enchantment enchantment) {
        return enchantments.parallelStream().filter((other) -> !enchantment.isCompatibleWith(other)).toList();
    }

}
