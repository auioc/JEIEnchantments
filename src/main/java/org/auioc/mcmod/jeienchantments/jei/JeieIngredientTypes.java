package org.auioc.mcmod.jeienchantments.jei;

import mezz.jei.api.ingredients.IIngredientType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JeieIngredientTypes {

    /**
     * For recipe lookup only, should not be displayed in the ingredient list.
     *
     * @see org.auioc.mcmod.jeienchantments.jei.ingredient.EnchantmentIngredientRenderer#render
     *      It will render as a purple square if not hidden.
     */
    public static final IIngredientType<Enchantment> ENCHANTMENT = () -> Enchantment.class;

}
