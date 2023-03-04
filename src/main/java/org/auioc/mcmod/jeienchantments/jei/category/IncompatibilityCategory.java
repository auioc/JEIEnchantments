package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.JeieIngredientTypes;
import org.auioc.mcmod.jeienchantments.jei.recipe.IncompatibilityRecipe;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class IncompatibilityCategory extends AbstractRecipeCategory<IncompatibilityRecipe> {

    private static final int WIDTH = 125;
    private static final int HEIGHT = 38;

    private static final ResourceLocation ANVIL_GUI_TEXTURE = new ResourceLocation("minecraft:textures/gui/container/anvil.png");
    private static final int ANVIL_RED_CROSS_U = 179;
    private static final int ANVIL_RED_CROSS_V = 3;
    private static final int ANVIL_RED_CROSS_WIDTH = 22;
    private static final int ANVIL_RED_CROSS_HEIGHT = 15;
    private static final int ANVIL_RED_CROSS_X = 76;
    private static final int ANVIL_RED_CROSS_Y = 2;
    private static final int ANVIL_BACKGROUND_U = 26;
    private static final int ANVIL_BACKGROUND_V = 46;
    private static final int ANVIL_BACKGROUND_WIDTH = 125;
    private static final int ANVIL_BACKGROUND_HEIGHT = 18;
    private static final int ANVIL_BACKGROUND_X = 0;
    private static final int ANVIL_BACKGROUND_Y = 0;

    private static final int SLOT_1_X = 1;
    private static final int SLOT_2_X = 50;
    private static final int SLOTS_Y = 1;
    private static final int TEXT_X = 0;
    private static final int TEXT_Y = ANVIL_BACKGROUND_HEIGHT + OFFSET_2;

    private static final int TEXT_COLOR = 0xFF808080;

    private final IDrawable icon;
    private final IDrawableStatic anvilBackground;
    private final IDrawableStatic anvilRedCross;

    public IncompatibilityCategory(IGuiHelper guiHelper) {
        super(JeieCategories.INCOMPATIBILITY, guiHelper, WIDTH, HEIGHT);
        anvilBackground = guiHelper.createDrawable(ANVIL_GUI_TEXTURE, ANVIL_BACKGROUND_U, ANVIL_BACKGROUND_V, ANVIL_BACKGROUND_WIDTH, ANVIL_BACKGROUND_HEIGHT);
        anvilRedCross = guiHelper.createDrawable(ANVIL_GUI_TEXTURE, ANVIL_RED_CROSS_U, ANVIL_RED_CROSS_V, ANVIL_RED_CROSS_WIDTH, ANVIL_RED_CROSS_HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(Items.ENCHANTED_BOOK));
    }

    @Override
    public void draw(IncompatibilityRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        anvilBackground.draw(poseStack, ANVIL_BACKGROUND_X, ANVIL_BACKGROUND_Y);
        anvilRedCross.draw(poseStack, ANVIL_RED_CROSS_X, ANVIL_RED_CROSS_Y);
        font.draw(poseStack, Utils.name(recipe.enchantment()), TEXT_X, TEXT_Y, TEXT_COLOR);
        font.draw(poseStack, Utils.name(recipe.otherEnchantment()), TEXT_X, TEXT_Y + font.lineHeight, TEXT_COLOR);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, IncompatibilityRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, SLOT_1_X, SLOTS_Y).addItemStacks(Utils.createBooks(recipe.enchantment()));
        builder.addSlot(RecipeIngredientRole.OUTPUT, SLOT_2_X, SLOTS_Y).addItemStacks(Utils.createBooks(recipe.otherEnchantment()));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addIngredient(JeieIngredientTypes.ENCHANTMENT, recipe.otherEnchantment());
    }

    @Override
    public IDrawable getIcon() { return this.icon; }

}
