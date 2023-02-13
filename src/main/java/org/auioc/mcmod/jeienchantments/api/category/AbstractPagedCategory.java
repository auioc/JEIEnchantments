package org.auioc.mcmod.jeienchantments.api.category;

import org.auioc.mcmod.jeienchantments.api.record.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractPagedCategory<T extends IPaginatedRecord> extends AbstractRecipeCategory<T> {

    public static final int FOOTER_HEIGHT = OFFSET_4 * 3;

    protected AbstractPagedCategory(RecipeType<T> type, IGuiHelper guiHelper, int width, int height) {
        super(type, guiHelper, width, height);
    }

    protected int getFooterWidth() { return width - OFFSET_4; }

    protected void drawFooter(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        if (recipe.pageCount() > 1) {
            int y = height - OFFSET_4 * 2;
            Utils.drawCenteredText(poseStack, font, String.format("%d/%d", recipe.page(), recipe.pageCount()), getFooterWidth() / 2, y, COLOR_GARY);
            GuiComponent.fill(poseStack, OFFSET_4, (y - OFFSET_2 - OFFSET_1), getFooterWidth(), (y - OFFSET_2), COLOR_GARY);
        }
    }

}
