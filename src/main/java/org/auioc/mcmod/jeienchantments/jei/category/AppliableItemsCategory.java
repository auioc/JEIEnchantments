package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractEnchantmentCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.recipe.AppliableItemsRecipe;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AppliableItemsCategory extends AbstractEnchantmentCategory<AppliableItemsRecipe> {

    public static final int SLOTS_X_OFFSET = 8;
    public static final int SLOTS_Y_OFFSET = 4;
    public static final int SLOTS_PRE_ROW = 8;
    public static final int SLOTS_PRE_COL = 4;
    public static final int SLOTS_PRE_PAGE = SLOTS_PRE_ROW * SLOTS_PRE_COL;

    public AppliableItemsCategory(IGuiHelper guiHelper) {
        super(JeieCategories.APPLIABLE_ITEMS, guiHelper);
    }

    @Override
    protected void drawContent(AppliableItemsRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY, int textY, int textWidth) {
        Utils.drawSlot(poseStack, SLOTS_X_OFFSET, textY + SLOTS_Y_OFFSET, SLOT_SIZE * SLOTS_PRE_ROW, SLOT_SIZE * SLOTS_PRE_COL);
    }

    @Override
    protected void setAdditionalRecipe(IRecipeLayoutBuilder builder, AppliableItemsRecipe recipe, IFocusGroup focuses) {
        for (var item : recipe.items().entrySet()) {
            builder.addSlot(RecipeIngredientRole.INPUT, item.getValue().x(), item.getValue().y())
                .addItemStacks(Utils.createEnchantedItems(item.getKey().item(), recipe.enchantment()))
                .addTooltipCallback((recipeSlotView, tooltips) -> {
                    tooltips.add(
                        Utils.indexOfEnchantmentTooltip(recipe.enchantment(), tooltips) + 1,
                        Utils.tooltip("can_apply_at_enchanting_table").withStyle(ChatFormatting.GRAY).append(Utils.guiYesNo(item.getKey().canApplyAtTable()))
                    );
                });
        }
    }

}
