package org.auioc.mcmod.jeienchantments.jei.ingredient;

import java.util.List;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentIngredientRenderer implements IIngredientRenderer<Enchantment> {

    /**
     * Just render a purple square.
     *
     * @see org.auioc.mcmod.jeienchantments.jei.JeieIngredientTypes#ENCHANTMENT
     *      For recipe lookup only.
     */
    @Override
    public void render(PoseStack poseStack, Enchantment ingredient) {
        GuiComponent.fill(poseStack, 0, 0, 16, 16, 0xFFFF00FF);
    }

    @Override
    public List<Component> getTooltip(Enchantment ingredient, TooltipFlag tooltipFlag) {
        return List.of(Utils.name(ingredient));
    }

}
