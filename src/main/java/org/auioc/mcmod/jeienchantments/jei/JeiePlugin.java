package org.auioc.mcmod.jeienchantments.jei;

import org.auioc.mcmod.jeienchantments.JEIEnchantments;
import org.auioc.mcmod.jeienchantments.jei.category.AppliableItemsCategory;
import org.auioc.mcmod.jeienchantments.jei.category.AvailabilityCategory;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.jei.category.IncompatibilityCategory;
import org.auioc.mcmod.jeienchantments.jei.recipe.AppliableItemsRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.AvailabilityRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.DescriptionRecipe;
import org.auioc.mcmod.jeienchantments.jei.recipe.IncompatibilityRecipe;
import org.auioc.mcmod.jeienchantments.record.JeieDataset;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
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

    public static void init() {}

    @Override
    public ResourceLocation getPluginUid() { return UID; }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        var guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
            new DescriptionCategory(guiHelper),
            new IncompatibilityCategory(guiHelper),
            new AppliableItemsCategory(guiHelper),
            new AvailabilityCategory(guiHelper)
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        long t = System.nanoTime();
        var dataset = JeieDataset.create();
        registration.addRecipes(JeieCategories.DESCRIPTION, DescriptionRecipe.create(dataset.enchantments()));
        registration.addRecipes(JeieCategories.INCOMPATIBILITY, IncompatibilityRecipe.create(dataset.enchantmentCompatibilityMap()));
        registration.addRecipes(JeieCategories.APPLIABLE_ITEMS, AppliableItemsRecipe.create(dataset.enchantmentApplicabilityMap()));
        registration.addRecipes(JeieCategories.AVILABILITY, AvailabilityRecipe.create(dataset.itemEnchantmentAvailabilityMap()));
        System.err.println((System.nanoTime() - t));
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Items.ENCHANTING_TABLE), JeieCategories.DESCRIPTION, JeieCategories.INCOMPATIBILITY, JeieCategories.APPLIABLE_ITEMS);
        registration.addRecipeCatalyst(new ItemStack(Items.ANVIL), JeieCategories.INCOMPATIBILITY, JeieCategories.APPLIABLE_ITEMS);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addGuiContainerHandler(
            EnchantmentScreen.class,
            JeiUtils.createGuiClickableHandler(
                25, 12, 16, 24, Utils.tooltip("show_enchantments"),
                JeieCategories.DESCRIPTION, JeieCategories.INCOMPATIBILITY, JeieCategories.APPLIABLE_ITEMS
            )
        );
    }

}
