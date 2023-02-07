package org.auioc.mcmod.jeienchantments.api.category;

import org.auioc.mcmod.jeienchantments.api.IEnchantmentRecord;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractEnchantmentCategory<T extends IEnchantmentRecord> extends AbstractRecipeCategory<T> {

    public static final int WIDTH = AbstractRecipeCategory.DEFAULT_WIDTH;
    public static final int HEIGHT = AbstractRecipeCategory.DEFAULT_HEIGHT;
    public static final int TEXT_WIDTH = WIDTH - OFFSET_4;
    public static final int HEADER_TEXT_WIDTH = WIDTH - SLOT_SIZE - (OFFSET_4 * 2);
    public static final int COLOR_GARY = 0xFFA1A1A1;

    protected AbstractEnchantmentCategory(RecipeType<T> type, IGuiHelper guiHelper, int width, int height) {
        super(type, guiHelper, width, height);
    }

    protected AbstractEnchantmentCategory(RecipeType<T> type, IGuiHelper guiHelper) {
        super(type, guiHelper);
    }

    @Override
    public final void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        slot.draw(poseStack, OFFSET_4, OFFSET_4);

        final var enchantment = recipe.enchantment();

        var name = Utils.nameWithLevels(enchantment);
        var id = Utils.idText(enchantment).withStyle(ChatFormatting.DARK_GRAY);

        int y = OFFSET_4;

        y = Utils.drawTextWarp(poseStack, font, name, OFFSET_4 + SLOT_SIZE + OFFSET_4, y, HEADER_TEXT_WIDTH, 0);
        y = Utils.drawTextWarp(poseStack, font, id, OFFSET_4 + SLOT_SIZE + OFFSET_4, y, HEADER_TEXT_WIDTH, 0);

        y += OFFSET_4;
        GuiComponent.fill(poseStack, OFFSET_4, y - OFFSET_1, TEXT_WIDTH, y, COLOR_GARY);
        y += OFFSET_4;

        this.subDraw(recipe, recipeSlotsView, poseStack, mouseX, mouseY, y, TEXT_WIDTH);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, OFFSET_4 + OFFSET_1, OFFSET_4 + OFFSET_1).addItemStacks(Utils.createBooks(recipe.enchantment()));
    }

    // ====================================================================== //

    protected abstract void subDraw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY, int textY, int textWidth);

    // ====================================================================== //

    public static int calcHeaderHeight(Font font, Enchantment enchantment) {
        int h = SLOT_SIZE + (OFFSET_4 * 3);
        h += (font.split(Utils.nameWithLevels(enchantment), HEADER_TEXT_WIDTH).size() - 1) * font.lineHeight;
        h += (font.split(Utils.idText(enchantment), HEADER_TEXT_WIDTH).size() - 1) * font.lineHeight;
        return h;
    }

}
