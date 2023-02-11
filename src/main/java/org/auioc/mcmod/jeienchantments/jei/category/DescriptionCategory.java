package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractEnchantmentCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.recipe.DescriptionRecipe;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DescriptionCategory extends AbstractEnchantmentCategory<DescriptionRecipe> {

    public DescriptionCategory(IGuiHelper guiHelper) {
        super(JeieCategories.DESCRIPTION, guiHelper);
    }

    @Override
    protected void drawContent(DescriptionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY, int textY, int textWidth) {
        Utils.drawMultilineText(poseStack, font, recipe.description(), OFFSET_4, textY, font.lineHeight + TEXT_ROW_SPACING, 0);
    }

}
