package org.auioc.mcmod.jeienchantments.jei;

import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JeieIngredientTypes {

    /**
     * Should not be displayed in the ingredient list.
     */
    public static final IIngredientType<Enchantment> ENCHANTMENT = () -> Enchantment.class;

}
