package org.auioc.mcmod.jeienchantments.jei;

import org.auioc.mcmod.jeienchantments.JEIEnchantments;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.jei.category.IncompatibilityCategory;
import org.auioc.mcmod.jeienchantments.jei.recipe.DescriptionRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.IncompatibilityRecipe;
import org.auioc.mcmod.jeienchantments.utils.EnchantmentInfo;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class JeiePlugin implements IModPlugin {

    private static final ResourceLocation UID = JEIEnchantments.id(JEIEnchantments.MOD_ID);

    public static void init() {}

    @Override
    public ResourceLocation getPluginUid() { return UID; }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
            new DescriptionCategory(guiHelper),
            new IncompatibilityCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var infos = Utils.createEnchantmentInfos();
        var enchantments = infos.parallelStream().map(EnchantmentInfo::enchantment).toList();
        registration.addRecipes(JeieCategories.DESCRIPTION, DescriptionRecipe.create(enchantments));
        registration.addRecipes(JeieCategories.INCOMPATIBILITY, IncompatibilityRecipe.create(infos));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Items.ENCHANTING_TABLE), JeieCategories.DESCRIPTION);
        registration.addRecipeCatalyst(new ItemStack(Items.ANVIL), JeieCategories.INCOMPATIBILITY);
    }

}
