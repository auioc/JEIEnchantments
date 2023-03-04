package org.auioc.mcmod.jeienchantments.utils;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import org.auioc.mcmod.jeienchantments.JEIEnchantments;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public class Utils {

    public static <K extends ForgeRegistryEntry<?>> Comparator<K> registryEntryComparator() {
        return new Comparator<K>() {
            @Override
            public int compare(K o1, K o2) { return o1.getRegistryName().compareTo(o2.getRegistryName()); }
        };
    }

    public static <K extends ForgeRegistryEntry<?>, V> Comparator<Map.Entry<K, V>> registryEntryMapComparator() {
        return Map.Entry.comparingByKey(registryEntryComparator());
    }

    public static <K extends ForgeRegistryEntry<?>, V> LinkedHashMap<K, V> sortRegistryEntryMap(Map<K, V> _map) {
        var linkedMap = new LinkedHashMap<K, V>(_map.size(), 1.0F);
        _map.entrySet()
            .stream()
            .sorted(registryEntryMapComparator())
            .forEach((e) -> linkedMap.put(e.getKey(), e.getValue()));
        return linkedMap;
    }

    // ====================================================================== //

    public static ItemStack emptyBook() {
        return new ItemStack(Items.ENCHANTED_BOOK);
    }

    public static ItemStack createBook(Enchantment enchantment, int lvl) {
        return EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, lvl));
    }

    public static ItemStack createBook(Enchantment enchantment) {
        return createBook(enchantment, 1);
    }

    public static List<ItemStack> createBooks(Enchantment enchantment) {
        return IntStream.range(1, enchantment.getMaxLevel() + 1).mapToObj((lvl) -> createBook(enchantment, lvl)).toList();
    }

    public static List<ItemStack> createEnchantedItems(ItemLike item, Enchantment enchantment) {
        return IntStream.range(1, enchantment.getMaxLevel() + 1)
            .mapToObj((lvl) -> {
                var itemStack = new ItemStack(item);
                EnchantmentHelper.setEnchantments(Map.of(enchantment, lvl), itemStack);
                return itemStack;
            })
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
        return (enchantment.isCurse()) ? style.applyFormat(ChatFormatting.RED) : style;
    }

    public static MutableComponent name(Enchantment enchantment) {
        return i10n(enchantment.getDescriptionId()).withStyle((style) -> colorName(style, enchantment));
    }

    public static MutableComponent nameWithLevels(Enchantment enchantment, boolean boldName) {
        var lvlText = Utils.text(" (").append(Utils.lvlText(1));
        if (enchantment.getMaxLevel() > 1) lvlText.append(Utils.text("-").append(Utils.lvlText(enchantment.getMaxLevel())));
        lvlText.append(")");
        var name = i10n(enchantment.getDescriptionId());
        if (boldName) name.withStyle(ChatFormatting.BOLD);
        return text("").append(name).append(lvlText).withStyle((style) -> colorName(style, enchantment));
    }

    public static MutableComponent nameWithLevels(Enchantment enchantment) {
        return nameWithLevels(enchantment, true);
    }

    public static MutableComponent idText(Enchantment enchantment) {
        return text(enchantment.getRegistryName().toString());
    }

    public static MutableComponent desc(Enchantment enchantment) {
        var key = enchantment.getDescriptionId() + ".desc";
        return (Language.getInstance().has(key)) ? i10n(key) : guiText("missing_description").withStyle(ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC);
    }

    public static MutableComponent lvlText(int lvl) {
        return i10n("enchantment.level." + lvl);
    }

    public static MutableComponent rarityText(Enchantment enchantment) {
        return i10n("enchantment.rarity." + enchantment.getRarity().name().toLowerCase());
    }

    // ====================================================================== //

    /**
     * @see <a href="https://github.com/auioc/arnicalib-mcmod/blob/4dbac981f15a80b8ffdbe1392bf061270a43f7cd/src/main/java/org/auioc/mcmod/arnicalib/base/collection/ListUtils.java#L25-L33">ArnicaLib: ListUtils.java</a>
     */
    public static <T> int indexOf(List<T> list, Predicate<T> predicate, int ordinal) {
        for (int i = 0, l = list.size(); i < l; ++i) {
            if (predicate.test(list.get(i))) {
                if (ordinal <= 0) return i;
                ordinal--;
            }
        }
        return -1;
    }

    public static int indexOfEnchantmentTooltip(Enchantment enchantment, List<Component> tooltips) {
        return indexOf(tooltips, (l) -> (l instanceof TranslatableComponent c) ? (c.getKey().equals(enchantment.getDescriptionId()) ? true : false) : false, 0);
    }

    // ====================================================================== //

    public static MutableComponent tooltip(String key) {
        return i10n(JEIEnchantments.MOD_ID + ".tooltip." + key);
    }

    public static MutableComponent guiText(String key) {
        return i10n("gui." + JEIEnchantments.MOD_ID + ".text." + key);
    }

    public static MutableComponent guiListMarker() {
        return guiText("list_marker");
    }

    public static MutableComponent guiListMarker(String key) {
        return guiListMarker().append(guiText(key));
    }

    public static MutableComponent guiYesNo(boolean _bool) {
        return guiText(_bool ? "yes" : "no");
    }

    // ====================================================================== //

    /**
     * @see <a href="https://github.com/auioc/arnicalib-mcmod/blob/439877bc4f68aa73c74fc5631c1ab50891ffa767/src/main/java/org/auioc/mcmod/arnicalib/game/chat/TextUtils.java#L48-L59">ArnicaLib: TextUtils.java</a>
     */
    public static MutableComponent joinText(List<Component> texts, Component separator) {
        var r = text("");
        if (texts.isEmpty()) return r;

        int s = texts.size();
        for (int i = 0, l = s - 1; i < l; ++i) {
            r.append(texts.get(i)).append(separator);
        }
        r.append(texts.get(s - 1));

        return r;
    }

    public static MutableComponent joinText(List<Component> texts) {
        return joinText(texts, text(", "));
    }

    // ====================================================================== //

    public static List<FormattedText> splitText(FormattedText text, Font font, int width) {
        return font.getSplitter().splitLines(text, width, Style.EMPTY);
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

    public static void drawCenteredText(PoseStack poseStack, Font font, Component text, int x, int y, int color) {
        font.draw(poseStack, text, (float) (x - font.width(text) / 2), (float) y, color);
    }

    // ====================================================================== //

    public static void drawTable(PoseStack poseStack, int x0, int y0, int[] cols, int[] rows, int color) {
        int tableWidth = IntStream.of(cols).sum();
        int tableHeight = IntStream.of(rows).sum();
        GuiComponent.fill(poseStack, x0, y0, x0 + tableWidth, y0 + 1, color);
        GuiComponent.fill(poseStack, x0, y0, x0 + 1, y0 + tableHeight + 1, color);

        int y = y0;
        for (int row = 0; row < rows.length; ++row) {
            y += rows[row];
            GuiComponent.fill(poseStack, x0, y, x0 + tableWidth, y + 1, color);
        }

        int x = x0;
        for (int col = 0; col < cols.length; ++col) {
            x += cols[col];
            GuiComponent.fill(poseStack, x, y0, x + 1, y0 + tableHeight + 1, color);
        }
    }

    // ====================================================================== //

    public static void drawSlot(PoseStack poseStack, int x, int y, int width, int height) {
        int x2 = x + width;
        int y2 = y + height;
        GuiComponent.fill(poseStack, x, y, x2, y2, 0xFF8B8B8B);
        GuiComponent.fill(poseStack, x, y, x2 - 1, y2 - 1, 0xFF373737);
        GuiComponent.fill(poseStack, x + 1, y + 1, x2, y2, 0xFFFFFFFF);
        GuiComponent.fill(poseStack, x + 1, y + 1, x2 - 1, y2 - 1, 0xFF8B8B8B);
    }

}
