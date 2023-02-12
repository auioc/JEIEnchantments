package org.auioc.mcmod.jeienchantments.api.category;

import org.auioc.mcmod.jeienchantments.api.IEnchantmentRecord;
import org.auioc.mcmod.jeienchantments.api.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class AbstractEnchantmentCategory<T extends IEnchantmentRecord & IPaginatedRecord> extends AbstractRecipeCategory<T> {

    public static final int WIDTH = AbstractRecipeCategory.DEFAULT_WIDTH;
    public static final int HEIGHT = AbstractRecipeCategory.DEFAULT_HEIGHT;
    public static final int TEXT_WIDTH = WIDTH - OFFSET_4;
    public static final int TEXT_ROW_SPACING = 1;
    public static final int HEADER_TEXT_WIDTH = WIDTH - SLOT_SIZE - (OFFSET_4 * 2);
    public static final int FOOTER_HEIGHT = OFFSET_4 * 3;
    public static final int COLOR_GARY = 0xFFA1A1A1;

    protected final IDrawable icon;

    protected AbstractEnchantmentCategory(RecipeType<T> type, IGuiHelper guiHelper) {
        super(type, guiHelper, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(Items.ENCHANTED_BOOK));
    }

    @Override
    public IDrawable getIcon() { return this.icon; }

    @Override
    public final void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        int y = drawHeader(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
        drawContent(recipe, recipeSlotsView, poseStack, mouseX, mouseY, y, TEXT_WIDTH);
        drawFooter(recipe, recipeSlotsView, poseStack, mouseX, mouseY);
    }

    @Override
    public final void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, OFFSET_4 + SLOT_PADDING, OFFSET_4 + SLOT_PADDING).addItemStacks(Utils.createBooks(recipe.enchantment()));
        setAdditionalRecipe(builder, recipe, focuses);
    }

    // ====================================================================== //

    private int drawHeader(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
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

        return y;
    }

    private void drawFooter(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
        if (recipe.pageCount() > 1) {
            int y = height - OFFSET_4 * 2;
            Utils.drawCenteredText(poseStack, font, String.format("%d/%d", recipe.page(), recipe.pageCount()), TEXT_WIDTH / 2, y, 0xFFA1A1A1);
            GuiComponent.fill(poseStack, OFFSET_4, (y - OFFSET_2 - OFFSET_1), TEXT_WIDTH, (y - OFFSET_2), COLOR_GARY);
        }
    }

    // ====================================================================== //

    protected void drawContent(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY, int textY, int textWidth) {}

    protected void setAdditionalRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses) {}

    // ====================================================================== //

    public static int calcHeaderHeight(Enchantment enchantment, Font font) {
        int h = SLOT_SIZE + (OFFSET_4 * 3);
        h += (font.split(Utils.nameWithLevels(enchantment), HEADER_TEXT_WIDTH).size() - 1) * font.lineHeight;
        h += (font.split(Utils.idText(enchantment), HEADER_TEXT_WIDTH).size() - 1) * font.lineHeight;
        return h;
    }

    public static int calcContentHeight(Enchantment enchantment, Font font) {
        return HEIGHT - calcHeaderHeight(enchantment, font) - FOOTER_HEIGHT;
    }

}
