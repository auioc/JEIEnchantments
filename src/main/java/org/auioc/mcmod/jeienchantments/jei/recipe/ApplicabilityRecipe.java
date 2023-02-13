package org.auioc.mcmod.jeienchantments.jei.recipe;

import static org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory.SLOT_PADDING;
import static org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory.SLOT_SIZE;
import static org.auioc.mcmod.jeienchantments.jei.category.ApplicabilityCategory.SLOTS_PRE_PAGE;
import static org.auioc.mcmod.jeienchantments.jei.category.ApplicabilityCategory.SLOTS_PRE_ROW;
import static org.auioc.mcmod.jeienchantments.jei.category.ApplicabilityCategory.SLOTS_X_OFFSET;
import static org.auioc.mcmod.jeienchantments.jei.category.ApplicabilityCategory.SLOTS_Y_OFFSET;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.auioc.mcmod.jeienchantments.api.record.IEnchantmentRecord;
import org.auioc.mcmod.jeienchantments.api.record.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.record.AppliableItem;
import org.auioc.mcmod.jeienchantments.record.EnchantmentApplicability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record ApplicabilityRecipe(Enchantment enchantment, List<AppliableItem.Slot> appliableItemSlots, int page, int pageCount) implements IEnchantmentRecord, IPaginatedRecord {

    public static List<ApplicabilityRecipe> create(Map<Enchantment, EnchantmentApplicability> map) {
        return map.entrySet().stream().map(ApplicabilityRecipe::create).flatMap(List::stream).toList();
    }

    @SuppressWarnings("resource")
    private static List<ApplicabilityRecipe> create(Entry<Enchantment, EnchantmentApplicability> entry) {
        final var font = Minecraft.getInstance().font;
        return create(entry.getKey(), entry.getValue(), font);
    }

    private static List<ApplicabilityRecipe> create(Enchantment enchantment, EnchantmentApplicability record, Font font) {
        var items = record.appliableItems();

        int itemCount = items.size();
        int pageCount = (int) Math.ceil((float) itemCount / SLOTS_PRE_PAGE);

        int y = DescriptionCategory.calcHeaderHeight(enchantment, font);

        var recipes = new ArrayList<ApplicabilityRecipe>();
        for (int p = 0; p < pageCount; ++p) {
            recipes.add(
                createPage(
                    enchantment,
                    items.subList(p * SLOTS_PRE_PAGE, Math.min((p + 1) * SLOTS_PRE_PAGE, itemCount)),
                    y, p + 1, pageCount
                )
            );
        }

        return recipes;
    }

    private static ApplicabilityRecipe createPage(Enchantment enchantment, List<AppliableItem> appliableItems, int y, int page, int pageCount) {
        int itemCount = appliableItems.size();
        int rowCount = (int) Math.ceil((float) itemCount / SLOTS_PRE_ROW);

        var appliableItemSlots = new ArrayList<AppliableItem.Slot>();
        for (int r = 0; r < rowCount; ++r) {
            for (int c = 0; c < SLOTS_PRE_ROW; ++c) {
                int i = (r * SLOTS_PRE_ROW) + c;
                if (i < itemCount) {
                    appliableItemSlots.add(
                        new AppliableItem.Slot(
                            appliableItems.get(i),
                            ((c * SLOT_SIZE) + SLOTS_X_OFFSET + SLOT_PADDING),
                            ((r * SLOT_SIZE) + y + SLOTS_Y_OFFSET + SLOT_PADDING)
                        )
                    );
                }
            }
        }

        return new ApplicabilityRecipe(enchantment, appliableItemSlots, page, pageCount);
    }

}
