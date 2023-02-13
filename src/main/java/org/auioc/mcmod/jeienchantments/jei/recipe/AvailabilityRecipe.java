package org.auioc.mcmod.jeienchantments.jei.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.auioc.mcmod.jeienchantments.api.record.IItemRecord;
import org.auioc.mcmod.jeienchantments.api.record.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.jei.category.AvailabilityCategory;
import org.auioc.mcmod.jeienchantments.record.ItemEnchantmentAvailability;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AvailabilityRecipe(Item item, List<FormattedText> enchantmentNames, int page, int pageCount) implements IItemRecord, IPaginatedRecord {

    public static List<AvailabilityRecipe> create(Map<Item, ItemEnchantmentAvailability> map) {
        return map.entrySet().stream().map(AvailabilityRecipe::create).flatMap(List::stream).toList();
    }

    @SuppressWarnings("resource")
    private static List<AvailabilityRecipe> create(Entry<Item, ItemEnchantmentAvailability> entry) {
        final var font = Minecraft.getInstance().font;
        return create(entry.getKey(), entry.getValue(), font);
    }

    private static List<AvailabilityRecipe> create(Item item, ItemEnchantmentAvailability record, Font font) {
        final var lines = new ArrayList<FormattedText>();

        for (var text : createContent(record)) {
            if (text == null) lines.add(Utils.text(""));
            else lines.addAll(Utils.splitText(text, font, AvailabilityCategory.CONTENT_WIDTH));
        }

        var lineCount = lines.size();
        int maxLines = AvailabilityCategory.CONTENT_HEIGHT / (font.lineHeight + AvailabilityCategory.TEXT_ROW_SPACING);
        int pageCount = (int) Math.ceil((float) lines.size() / maxLines);

        var recipes = new ArrayList<AvailabilityRecipe>();
        for (int p = 0; p < pageCount; ++p) {
            recipes.add(
                new AvailabilityRecipe(
                    item,
                    lines.subList(p * maxLines, Math.min((p + 1) * maxLines, lineCount)),
                    (p + 1), pageCount
                )
            );
        }

        return recipes;
    }

    private static List<FormattedText> createContent(ItemEnchantmentAvailability record) {
        final var content = new ArrayList<FormattedText>();
        {
            for (var a : record.availableEnchantments()) {
                content.add(Utils.nameWithLevels(a.enchantment()));
            }
        }
        return content;
    }

}
