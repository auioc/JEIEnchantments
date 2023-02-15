package org.auioc.mcmod.jeienchantments.record;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import org.auioc.mcmod.jeienchantments.api.record.IRegistryEntryRecord;
import org.auioc.mcmod.jeienchantments.config.JeieConfig;
import org.auioc.mcmod.jeienchantments.utils.Object2ListMapWarpper;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public class JeieDatasetBuilder {

    private final Collection<ItemStack> allItemStacks;
    private final Collection<Enchantment> allEnchantments;

    private List<Enchantment> enchantments = new ArrayList<>();
    private Map<Enchantment, EnchantmentCompatibility> enchantmentCompatibilityMap = new HashMap<>();
    private Map<Enchantment, EnchantmentApplicability> enchantmentApplicabilityMap;
    private Map<Item, ItemEnchantmentAvailability> itemEnchantmentAvailabilityMap;

    private Object2ListMapWarpper<Enchantment, AppliableItem> _tempMap1 = Object2ListMapWarpper.create();
    private Object2ListMapWarpper<Item, AvailableEnchantment> _tempMap2 = Object2ListMapWarpper.create();

    protected JeieDatasetBuilder() {
        this.allItemStacks = ForgeRegistries.ITEMS.getValues().stream().sorted(Utils.registryEntryComparator()).map(ItemStack::new).toList();
        this.allEnchantments = ForgeRegistries.ENCHANTMENTS.getValues().stream().sorted(Utils.registryEntryComparator()).toList();
    }

    protected JeieDataset build() {
        for (var ench : this.allEnchantments) {
            if (JeieConfig.enchantmentBlacklist.contains(ench)) continue;
            this.enchantments.add(ench);
            this.enchantmentCompatibilityMap.put(ench, EnchantmentCompatibility.create(ench, this.allEnchantments));
            for (var itemStack : allItemStacks) {
                var item = itemStack.getItem();
                if (JeieConfig.itemBlacklist.contains(item)) continue;
                boolean canApply = ench.canEnchant(itemStack);
                boolean canApplyAtTable = !ench.isTreasureOnly() && ench.isDiscoverable() && (itemStack.getItemEnchantability() > 0) && ench.canApplyAtEnchantingTable(itemStack);
                if (canApply || canApplyAtTable) {
                    _tempMap1.putElement(ench, new AppliableItem(item, canApply, canApplyAtTable));
                    _tempMap2.putElement(item, new AvailableEnchantment(ench, canApply, canApplyAtTable));
                }
            }
        }

        this.enchantmentApplicabilityMap = createDataMap(_tempMap1.getMap(), EnchantmentApplicability::create);
        this.itemEnchantmentAvailabilityMap = createDataMap(_tempMap2.getMap(), ItemEnchantmentAvailability::create);

        return new JeieDataset(enchantments, enchantmentCompatibilityMap, enchantmentApplicabilityMap, itemEnchantmentAvailabilityMap);
    }

    // ====================================================================== //

    protected static <K extends ForgeRegistryEntry<?>, T, R extends IRegistryEntryRecord<K>> Map<K, R> createDataMap(Map<K, List<T>> map, BiFunction<K, List<T>, R> maker) {
        return map.entrySet()
            .stream()
            .sorted(Utils.registryEntryMapComparator())
            .map((entry) -> maker.apply(entry.getKey(), entry.getValue()))
            .collect(Collectors.toMap((r) -> r.registryEntry(), (r) -> r, (a, b) -> b, LinkedHashMap::new));
    }

}
