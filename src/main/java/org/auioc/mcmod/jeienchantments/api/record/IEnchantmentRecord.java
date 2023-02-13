package org.auioc.mcmod.jeienchantments.api.record;

import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IEnchantmentRecord extends IRegistryEntryRecord<Enchantment> {

    Enchantment enchantment();

    @Override
    default Enchantment registryEntry() {
        return enchantment();
    }

}
