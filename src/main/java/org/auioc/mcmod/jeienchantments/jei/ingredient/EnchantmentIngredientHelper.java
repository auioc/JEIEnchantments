package org.auioc.mcmod.jeienchantments.jei.ingredient;

import org.auioc.mcmod.jeienchantments.jei.JeieIngredientTypes;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import org.jetbrains.annotations.Nullable;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentIngredientHelper implements IIngredientHelper<Enchantment> {

    @Override
    public IIngredientType<Enchantment> getIngredientType() {
        return JeieIngredientTypes.ENCHANTMENT;
    }

    @Override
    public String getDisplayName(Enchantment ingredient) {
        return Utils.name(ingredient).getString();
    }

    @Override
    public String getUniqueId(Enchantment ingredient, UidContext context) {
        return String.format("enchantment:%s", ingredient.getRegistryName());
    }

    @Override
    public ResourceLocation getResourceLocation(Enchantment ingredient) {
        return ingredient.getRegistryName();
    }

    @Override
    public String getModId(Enchantment ingredient) {
        return ingredient.getRegistryName().getNamespace();
    }

    @Override
    public String getResourceId(Enchantment ingredient) {
        return ingredient.getRegistryName().getPath();
    }

    @Override
    public Enchantment copyIngredient(Enchantment ingredient) {
        return ingredient;
    }

    @Override
    public ItemStack getCheatItemStack(Enchantment ingredient) {
        return Utils.createBook(ingredient, ingredient.getMaxLevel());
    }

    @Override
    public String getErrorInfo(@Nullable Enchantment ingredient) {
        if (ingredient == null) return "null";
        return String.format("Enchantment(%s)", ingredient.getRegistryName());
    }

}
