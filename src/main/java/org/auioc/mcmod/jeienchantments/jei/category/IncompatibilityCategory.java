package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.recipe.IncompatibilityRecipe;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IncompatibilityCategory extends AbstractRecipeCategory<IncompatibilityRecipe> {

    private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("minecraft:textures/gui/container/anvil.png");

    private final IDrawableStatic anvilBackground;
    private final IDrawableStatic anvilRedCross;

    public IncompatibilityCategory(IGuiHelper guiHelper) {
        super(JeieCategories.INCOMPATIBILITY, guiHelper, 125, 38);
        anvilBackground = guiHelper.createDrawable(ANVIL_GUI_TEXTURE, 26, 46, 125, 18);
        anvilRedCross = guiHelper.createDrawable(ANVIL_GUI_TEXTURE, 179, 3, 22, 15);
    }

    @Override
    public void draw(IncompatibilityRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        anvilBackground.draw(poseStack, 0, 0);
        anvilRedCross.draw(poseStack, 76, 2);
        font.draw(poseStack, Utils.name(recipe.enchantment()), 0, 18 + 2, 0xFF808080);
        font.draw(poseStack, Utils.name(recipe.otherEnchantment()), 0, 27 + 3, 0xFF808080);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncompatibilityRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1).addItemStacks(Utils.createBooks(recipe.enchantment()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 50, 1).addItemStacks(Utils.createBooks(recipe.otherEnchantment()));
    }

}
