package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractPagedCategory;
import org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.JeieIngredientTypes;
import org.auioc.mcmod.jeienchantments.jei.recipe.AvailabilityRecipe;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
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
public class AvailabilityCategory extends AbstractPagedCategory<AvailabilityRecipe> {

    public static final int WIDTH = AbstractRecipeCategory.DEFAULT_WIDTH;
    public static final int HEIGHT = AbstractRecipeCategory.DEFAULT_HEIGHT;
    public static final int SLOT_X = OFFSET_4;
    public static final int SLOT_Y = OFFSET_4;
    public static final int CONTENT_WIDTH = WIDTH - SLOT_SIZE - OFFSET_4 * 2;
    public static final int CONTENT_HEIGHT = HEIGHT - FOOTER_HEIGHT;
    public static final int CONTENT_X = SLOT_X + SLOT_SIZE + OFFSET_4;
    public static final int CONTENT_Y = SLOT_Y;

    public AvailabilityCategory(IGuiHelper guiHelper) {
        super(JeieCategories.AVILABILITY, guiHelper, WIDTH, HEIGHT);
    }

    @Override
    public void draw(AvailabilityRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        slot.draw(poseStack, SLOT_X, SLOT_Y);
        drawFooter(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
        for (var b : recipe.enchantmentNames()) {
            b.render(poseStack, mouseX, mouseY);
        }
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, AvailabilityRecipe recipe, IFocusGroup focuses) {
        for (var enchantment : recipe.enchantments()) {
            builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).addIngredient(JeieIngredientTypes.ENCHANTMENT, enchantment);
        }
        builder.addSlot(RecipeIngredientRole.INPUT, SLOT_X + SLOT_PADDING, SLOT_Y + SLOT_PADDING).addItemStack(new ItemStack(recipe.item()));
    }

    @Override
    public boolean handleInput(AvailabilityRecipe recipe, double mouseX, double mouseY, Key input) {
        if (input.getType() == InputConstants.Type.MOUSE) {
            for (var b : recipe.enchantmentNames()) {
                if (b.mouseClicked(mouseX, mouseY, input.getValue())) {
                    return true;
                }
            }
        }
        return false;
    }

}
