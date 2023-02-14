package org.auioc.mcmod.jeienchantments.jei.gui;

import java.util.List;
import org.auioc.mcmod.jeienchantments.gui.SimpleTextButton;
import org.auioc.mcmod.jeienchantments.jei.JeieIngredientTypes;
import org.auioc.mcmod.jeienchantments.jei.JeiePlugin;
import org.auioc.mcmod.jeienchantments.record.AvailableEnchantment;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AvailableEnchantmentTextButton extends SimpleTextButton {

    private final AvailableEnchantment availableEnchantment;

    public AvailableEnchantmentTextButton(int x, int y, AvailableEnchantment availableEnchantment) {
        super(x, y, Utils.nameWithLevels(availableEnchantment.enchantment(), false));
        this.availableEnchantment = availableEnchantment;
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        JeiePlugin.getJeiRuntime().getRecipesGui().show(
            JeiePlugin.getJeiHelpers().getFocusFactory().createFocus(
                RecipeIngredientRole.INPUT,
                JeieIngredientTypes.ENCHANTMENT,
                availableEnchantment.enchantment()
            )
        );
    }

    @Override
    public List<Component> getTooltips() {
        return List.of(
            this.getMessage(),
            Utils.tooltip("can_apply_at_enchanting_table").withStyle(ChatFormatting.GRAY).append(Utils.guiYesNo(availableEnchantment.canApplyAtTable()))
        );
    }

}
