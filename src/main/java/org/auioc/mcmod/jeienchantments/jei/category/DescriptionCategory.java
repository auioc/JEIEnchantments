package org.auioc.mcmod.jeienchantments.jei.category;

import org.auioc.mcmod.jeienchantments.api.category.AbstractEnchantmentCategory;
import org.auioc.mcmod.jeienchantments.jei.JeieCategories;
import org.auioc.mcmod.jeienchantments.jei.recipe.DescriptionRecipe;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class DescriptionCategory extends AbstractEnchantmentCategory<DescriptionRecipe> {

    public static final int FOOTER_HEIGHT = OFFSET_4 * 3;

    public DescriptionCategory(IGuiHelper guiHelper) {
        super(JeieCategories.DESCRIPTION, guiHelper, WIDTH, HEIGHT);
    }

    @Override
    protected void subDraw(DescriptionRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY, int textY, int textWidth) {
        Utils.drawMultilineText(poseStack, font, recipe.description(), OFFSET_4, textY, font.lineHeight, 0);
        if (recipe.pageCount() > 1) {
            int y0 = height - OFFSET_4 * 2;
            Utils.drawCenteredText(poseStack, font, String.format("%d/%d", recipe.page(), recipe.pageCount()), textWidth / 2, y0, 0xFFA1A1A1);
            GuiComponent.fill(poseStack, OFFSET_4, (y0 - OFFSET_2 - OFFSET_1), textWidth, (y0 - OFFSET_2), COLOR_GARY);
        }
    }

}
