package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.recipe.AvailabilityRecipe;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AvailabilityCategory extends AbstractRecipeCategory<AvailabilityRecipe> {

    public static final int WIDTH = AbstractRecipeCategory.DEFAULT_WIDTH;
    public static final int HEIGHT = AbstractRecipeCategory.DEFAULT_HEIGHT;

    public AvailabilityCategory(IGuiHelper guiHelper) {
        super(JeieCategories.AVILABILITY, guiHelper);
    }

    @Override
    public void draw(AvailabilityRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        slot.draw(poseStack, OFFSET_4, OFFSET_4);
        Utils.drawMultilineText(poseStack, font, recipe.enchantmentNames(), OFFSET_4 + SLOT_SIZE + OFFSET_4, OFFSET_4, font.lineHeight + 1, 0);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AvailabilityRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, OFFSET_4 + SLOT_PADDING, OFFSET_4 + SLOT_PADDING).addItemStack(new ItemStack(recipe.item()));
    }

}
