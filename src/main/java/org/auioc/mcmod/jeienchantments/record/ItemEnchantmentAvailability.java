package org.auioc.mcmod.jeienchantments.record;

import java.util.Collections;
import java.util.List;
import org.auioc.mcmod.jeienchantments.api.record.IItemRecord;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ItemEnchantmentAvailability(Item item, List<AvailableEnchantment> availableEnchantments) implements IItemRecord {

    protected static ItemEnchantmentAvailability create(Item item, List<AvailableEnchantment> availableEnchantments) {
        return new ItemEnchantmentAvailability(item, Collections.unmodifiableList(availableEnchantments));
    }

}
