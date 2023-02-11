package org.auioc.mcmod.jeienchantments.jei;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import mezz.jei.api.gui.handlers.IGuiClickableArea;
import mezz.jei.api.gui.handlers.IGuiContainerHandler;
import mezz.jei.api.recipe.IFocusFactory;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.runtime.IRecipesGui;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class JeiUtils {

    public static IGuiClickableArea createGuiClickableArea(Rect2i area, List<Component> tooltips, List<RecipeType<?>> recipeTypes) {
        return new IGuiClickableArea() {
            @Override
            public Rect2i getArea() { return area; }

            @Override
            public List<Component> getTooltipStrings() { return tooltips; }

            @Override
            public void onClick(IFocusFactory focusFactory, IRecipesGui recipesGui) { recipesGui.showTypes(recipeTypes); }
        };
    }

    public static <T extends AbstractContainerScreen<?>> IGuiContainerHandler<T> createGuiClickableHandler(Rect2i area, List<Component> tooltips, List<RecipeType<?>> recipeTypes) {
        return new IGuiContainerHandler<T>() {
            @Override
            public Collection<IGuiClickableArea> getGuiClickableAreas(T containerScreen, double mouseX, double mouseY) {
                return List.of(JeiUtils.createGuiClickableArea(area, tooltips, recipeTypes));
            }
        };
    }

    public static <T extends AbstractContainerScreen<?>> IGuiContainerHandler<T> createGuiClickableHandler(int x, int y, int w, int h, Component tooltip, RecipeType<?>... recipeTypes) {
        return createGuiClickableHandler(new Rect2i(x, y, w, h), List.of(tooltip), Arrays.asList(recipeTypes));
    }

}
