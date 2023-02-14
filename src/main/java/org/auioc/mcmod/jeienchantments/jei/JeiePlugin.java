package org.auioc.mcmod.jeienchantments.jei;

import org.auioc.mcmod.jeienchantments.JEIEnchantments;
import org.auioc.mcmod.jeienchantments.jei.category.ApplicabilityCategory;
import org.auioc.mcmod.jeienchantments.jei.category.AvailabilityCategory;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.jei.category.IncompatibilityCategory;
import org.auioc.mcmod.jeienchantments.jei.recipe.ApplicabilityRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.AvailabilityRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.DescriptionRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.IncompatibilityRecipe;
import org.auioc.mcmod.jeienchantments.record.JeieDataset;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IJeiHelpers;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@JeiPlugin
@OnlyIn(Dist.CLIENT)
public class JeiePlugin implements IModPlugin {

    private static final ResourceLocation UID = JEIEnchantments.id(JEIEnchantments.MOD_ID);

    private static IJeiRuntime jeiRuntime;
    private static IJeiHelpers jeiHelpers;

    public static void init() {}

    @Override
    public ResourceLocation getPluginUid() { return UID; }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
            new DescriptionCategory(guiHelper),
            new IncompatibilityCategory(guiHelper),
            new ApplicabilityCategory(guiHelper),
            new AvailabilityCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        JeiePlugin.jeiHelpers = registration.getJeiHelpers();

        var dataset = JeieDataset.create();
        registration.addRecipes(JeieCategories.DESCRIPTION, DescriptionRecipe.create(dataset.enchantments()));
        registration.addRecipes(JeieCategories.INCOMPATIBILITY, IncompatibilityRecipe.create(dataset.enchantmentCompatibilityMap()));
        registration.addRecipes(JeieCategories.APPLICABILITY, ApplicabilityRecipe.create(dataset.enchantmentApplicabilityMap()));
        registration.addRecipes(JeieCategories.AVILABILITY, AvailabilityRecipe.create(dataset.itemEnchantmentAvailabilityMap()));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Items.ENCHANTING_TABLE), JeieCategories.DESCRIPTION, JeieCategories.INCOMPATIBILITY, JeieCategories.APPLICABILITY);
        registration.addRecipeCatalyst(new ItemStack(Items.ANVIL), JeieCategories.INCOMPATIBILITY, JeieCategories.APPLICABILITY);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(
            EnchantmentScreen.class,
            JeiUtils.createGuiClickableHandler(
                25, 12, 16, 24, Utils.tooltip("show_enchantments"),
                JeieCategories.DESCRIPTION, JeieCategories.INCOMPATIBILITY, JeieCategories.APPLICABILITY
            )
        );
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        JeiePlugin.jeiRuntime = jeiRuntime;
    }

    // ====================================================================== //

    public static IJeiRuntime getJeiRuntime() { return jeiRuntime; }

    public static IJeiHelpers getJeiHelpers() { return jeiHelpers; }

}
