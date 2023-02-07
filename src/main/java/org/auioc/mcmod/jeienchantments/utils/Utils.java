package org.auioc.mcmod.jeienchantments.utils;

import java.util.List;
import java.util.stream.IntStream;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;

public class Utils {

    public static List<ItemStack> createBooks(Enchantment enchantment) {
        return IntStream.range(1, enchantment.getMaxLevel() + 1)
            .mapToObj((lvl) -> EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, lvl)))
            .toList();
    }

    // ====================================================================== //

    public static TextComponent text(String _text) {
        return new TextComponent(_text);
    }

    public static TextComponent text(String format, Object... args) {
        return new TextComponent(String.format(format, args));
    }

    public static TranslatableComponent i10n(String _key) {
        return new TranslatableComponent(_key);
    }

    public static TranslatableComponent i10n(String _key, Object... _args) {
        return new TranslatableComponent(_key, _args);
    }

    // ====================================================================== //

    public static Style colorName(Style style, Enchantment enchantment) {
        return (enchantment.isCurse()) ? style.applyFormat(ChatFormatting.DARK_RED) : style;
    }

    public static MutableComponent name(Enchantment enchantment) {
        return i10n(enchantment.getDescriptionId()).withStyle((style) -> colorName(style, enchantment));
    }

    public static MutableComponent nameWithLevels(Enchantment enchantment) {
        var lvlText = Utils.text(" (").append(Utils.lvlText(1));
        if (enchantment.getMaxLevel() > 1) lvlText.append(Utils.text("-").append(Utils.lvlText(enchantment.getMaxLevel())));
        lvlText.append(")");
        return text("").append(i10n(enchantment.getDescriptionId()).withStyle(ChatFormatting.BOLD)).append(lvlText).withStyle((style) -> colorName(style, enchantment));
    }

    public static MutableComponent idText(Enchantment enchantment) {
        return text(enchantment.getRegistryName().toString());
    }

    public static MutableComponent desc(Enchantment enchantment) {
        return i10n(enchantment.getDescriptionId() + ".desc");
    }

    public static MutableComponent lvlText(int lvl) {
        return i10n("enchantment.level." + lvl);
    }

    public static MutableComponent rarityText(String rarity) {
        return i10n("enchantment.rarity." + rarity);
    }

    // ====================================================================== //

    public static int drawCharSequence(PoseStack poseStack, Font font, List<FormattedCharSequence> lines, int x, int y, int lineHeight, int color) {
        for (var line : lines) {
            font.draw(poseStack, line, x, y, color);
            y += lineHeight;
        }
        return y;
    }

    public static int drawMultilineText(PoseStack poseStack, Font font, List<FormattedText> texts, int x, int y, int color) {
        return drawCharSequence(poseStack, font, Language.getInstance().getVisualOrder(texts), x, y, font.lineHeight, color);
    }

    public static int drawMultilineText(PoseStack poseStack, Font font, List<FormattedText> texts, int x, int y, int lineHeight, int color) {
        return drawCharSequence(poseStack, font, Language.getInstance().getVisualOrder(texts), x, y, lineHeight, color);
    }

    public static int drawTextWarp(PoseStack poseStack, Font font, FormattedText text, int x, int y, int width, int color) {
        return drawCharSequence(poseStack, font, font.split(text, width), x, y, font.lineHeight, color);
    }

    // ====================================================================== //

    public static void drawCenteredText(PoseStack poseStack, Font font, String text, int x, int y, int color) {
        font.draw(poseStack, text, (float) (x - font.width(text) / 2), (float) y, color);
    }

}
