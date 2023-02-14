package org.auioc.mcmod.jeienchantments.jei.gui;

import java.util.List;
import org.auioc.mcmod.jeienchantments.gui.SimpleTextButton;
import org.auioc.mcmod.jeienchantments.jei.JeiePlugin;
import org.auioc.mcmod.jeienchantments.record.AvailableEnchantment;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.IFocus;
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
        var factory = JeiePlugin.getJeiHelpers().getFocusFactory();
        JeiePlugin.getJeiRuntime().getRecipesGui().show(
            Utils.createBooks(availableEnchantment.enchantment())
                .stream()
                .<IFocus<?>>map((book) -> factory.createFocus(RecipeIngredientRole.INPUT, VanillaTypes.ITEM_STACK, book))
                .toList()
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
