package org.auioc.mcmod.jeienchantments.api.category;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
@SuppressWarnings("resource")
public abstract class AbstractRecipeCategory<T> implements IRecipeCategory<T> {

    protected final RecipeType<T> type;
    protected final Component title;
    protected final IDrawable slot;
    protected final IDrawable background;
    protected final Font font;
    protected final IDrawable icon;
    protected final int width;
    protected final int height;

    public static final int DEFAULT_WIDTH = 160;
    public static final int DEFAULT_HEIGHT = 125;
    public static final int SLOT_SIZE = 18;
    public static final int OFFSET_1 = 1;
    public static final int OFFSET_2 = 2;
    public static final int OFFSET_4 = 4;

    protected AbstractRecipeCategory(RecipeType<T> type, IGuiHelper guiHelper, int width, int height) {
        this.type = type;
        this.title = new TranslatableComponent(String.format("gui.%s.category.%s", type.getUid().getNamespace(), type.getUid().getPath()));
        this.width = width;
        this.height = height;
        this.background = guiHelper.createBlankDrawable(width, height);
        this.slot = guiHelper.getSlotDrawable();
        this.icon = guiHelper.createDrawableItemStack(new ItemStack(Items.ENCHANTED_BOOK));
        this.font = Minecraft.getInstance().font;
    }

    protected AbstractRecipeCategory(RecipeType<T> type, IGuiHelper guiHelper) {
        this(type, guiHelper, DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    @Override
    public abstract void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY);

    @Override
    public abstract void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focuses);

    // ====================================================================== //

    @Override
    public RecipeType<T> getRecipeType() { return this.type; }


    @Override
    public ResourceLocation getUid() { return this.type.getUid(); }

    @Override
    public Class<? extends T> getRecipeClass() { return this.type.getRecipeClass(); }

    @Override
    public Component getTitle() { return this.title; }

    @Override
    public IDrawable getBackground() { return this.background; }

    @Override
    public IDrawable getIcon() { return this.icon; }

    // ====================================================================== //

    protected static int center(int _a) {
        return _a / 2;
    }

    protected static int center(int _a, int _b) {
        return _a / 2 - _b / 2;
    }

}
