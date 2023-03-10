package org.auioc.mcmod.jeienchantments.jei;

import org.auioc.mcmod.jeienchantments.JEIEnchantments;
import org.auioc.mcmod.jeienchantments.jei.recipe.ApplicabilityRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.AvailabilityRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.DescriptionRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.IncompatibilityRecipe;
import mezz.jei.api.recipe.RecipeType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class JeieCategories {

    public static final RecipeType<DescriptionRecipe> DESCRIPTION = create("description", DescriptionRecipe.class);
    public static final RecipeType<IncompatibilityRecipe> INCOMPATIBILITY = create("incompatibility", IncompatibilityRecipe.class);
    public static final RecipeType<ApplicabilityRecipe> APPLICABILITY = create("applicability", ApplicabilityRecipe.class);
    public static final RecipeType<AvailabilityRecipe> AVILABILITY = create("availability", AvailabilityRecipe.class);

    // ====================================================================== //

    public static <T> RecipeType<T> create(String _uidPath, Class<? extends T> _recipeClass) {
        return RecipeType.create(JEIEnchantments.MOD_ID, _uidPath, _recipeClass);
    }

}
