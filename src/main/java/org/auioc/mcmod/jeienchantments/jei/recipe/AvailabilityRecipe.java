package org.auioc.mcmod.jeienchantments.jei.recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.auioc.mcmod.jeienchantments.api.record.IItemRecord;
import org.auioc.mcmod.jeienchantments.api.record.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.jei.category.AvailabilityCategory;
import org.auioc.mcmod.jeienchantments.jei.gui.AvailableEnchantmentTextButton;
import org.auioc.mcmod.jeienchantments.record.ItemEnchantmentAvailability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AvailabilityRecipe(Item item, List<AvailableEnchantmentTextButton> enchantmentNames, List<Enchantment> enchantments, int page, int pageCount) implements IItemRecord, IPaginatedRecord {

    public static List<AvailabilityRecipe> create(Map<Item, ItemEnchantmentAvailability> map) {
        return map.entrySet().stream().map(AvailabilityRecipe::create).flatMap(List::stream).toList();
    }

    @SuppressWarnings("resource")
    private static List<AvailabilityRecipe> create(Entry<Item, ItemEnchantmentAvailability> entry) {
        final var font = Minecraft.getInstance().font;
        return create(entry.getKey(), entry.getValue(), font);
    }

    private static List<AvailabilityRecipe> create(Item item, ItemEnchantmentAvailability record, Font font) {
        final var lines = new ArrayList<AvailableEnchantmentTextButton>();

        int maxLines = AvailabilityCategory.CONTENT_HEIGHT / (font.lineHeight + AvailabilityCategory.TEXT_ROW_SPACING);

        var availableEnchantments = record.availableEnchantments();
        var enchantments = new ArrayList<Enchantment>(availableEnchantments.size());

        int i = 0;
        for (var availableEnchantment : availableEnchantments) {
            int y = AvailabilityCategory.CONTENT_Y + i * (font.lineHeight + AvailabilityCategory.TEXT_ROW_SPACING);
            lines.add(new AvailableEnchantmentTextButton(AvailabilityCategory.CONTENT_X, y, availableEnchantment));
            enchantments.add(availableEnchantment.enchantment());
            i++;
            if (i >= maxLines) i = 0;
        }

        var lineCount = lines.size();
        int pageCount = (int) Math.ceil((float) lines.size() / maxLines);

        var recipes = new ArrayList<AvailabilityRecipe>();
        for (int p = 0; p < pageCount; ++p) {
            recipes.add(
                new AvailabilityRecipe(
                    item,
                    lines.subList(p * maxLines, Math.min((p + 1) * maxLines, lineCount)),
                    enchantments,
                    (p + 1), pageCount
                )
            );
        }

        return recipes;
    }

}
