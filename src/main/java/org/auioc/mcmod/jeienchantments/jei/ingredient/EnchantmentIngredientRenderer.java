package org.auioc.mcmod.jeienchantments.jei.ingredient;

import java.util.List;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnchantmentIngredientRenderer implements IIngredientRenderer<Enchantment> {

    @Override
    public void render(PoseStack poseStack, Enchantment ingredient) {
        if (ingredient != null) {
            var fakeBook = new ItemStack(Items.ENCHANTED_BOOK);
            var modelViewStack = RenderSystem.getModelViewStack();
            modelViewStack.pushPose();
            {
                modelViewStack.mulPoseMatrix(poseStack.last().pose());
                RenderSystem.enableDepthTest();
                var minecraft = Minecraft.getInstance();
                var font = getFontRenderer(minecraft, ingredient);
                var itemRenderer = minecraft.getItemRenderer();
                itemRenderer.renderAndDecorateFakeItem(fakeBook, 0, 0);
                itemRenderer.renderGuiItemDecorations(font, fakeBook, 0, 0);
                RenderSystem.disableBlend();
            }
            modelViewStack.popPose();
            RenderSystem.applyModelViewMatrix();
        }
    }

    @Override
    public List<Component> getTooltip(Enchantment ingredient, TooltipFlag tooltipFlag) {
        return List.of(
            Utils.name(ingredient),
            Utils.idText(ingredient).withStyle(ChatFormatting.DARK_GRAY)
        );
    }

}
