package org.auioc.mcmod.jeienchantments.jei.recipe;

import static org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory.SLOT_PADDING;
import static org.auioc.mcmod.jeienchantments.api.category.AbstractRecipeCategory.SLOT_SIZE;
import static org.auioc.mcmod.jeienchantments.jei.category.AppliableItemsCategory.SLOTS_PRE_PAGE;
import static org.auioc.mcmod.jeienchantments.jei.category.AppliableItemsCategory.SLOTS_PRE_ROW;
import static org.auioc.mcmod.jeienchantments.jei.category.AppliableItemsCategory.SLOTS_X_OFFSET;
import static org.auioc.mcmod.jeienchantments.jei.category.AppliableItemsCategory.SLOTS_Y_OFFSET;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.auioc.mcmod.jeienchantments.api.IEnchantmentRecord;
import org.auioc.mcmod.jeienchantments.api.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.utils.AppliableItem;
import org.auioc.mcmod.jeienchantments.utils.EnchantmentInfo;
import org.auioc.mcmod.jeienchantments.utils.IntPair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AppliableItemsRecipe(Enchantment enchantment, Map<AppliableItem, IntPair> items, int page, int pageCount) implements IEnchantmentRecord, IPaginatedRecord {

    @SuppressWarnings("resource")
    public static List<AppliableItemsRecipe> create(List<EnchantmentInfo> infos) {
        final var font = Minecraft.getInstance().font;
        return infos.stream().map((info) -> create(info, font)).flatMap(List::stream).toList();
    }

    public static List<AppliableItemsRecipe> create(EnchantmentInfo info, Font font) {
        final var enchantment = info.enchantment();
        var items = info.appliableItems();

        int itemCount = items.size();
        int pageCount = (int) Math.ceil((float) itemCount / SLOTS_PRE_PAGE);

        int y = DescriptionCategory.calcHeaderHeight(enchantment, font);

        var recipes = new ArrayList<AppliableItemsRecipe>();
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

    private static AppliableItemsRecipe createPage(Enchantment enchantment, List<AppliableItem> items, int y, int page, int pageCount) {
        int itemCount = items.size();
        int rowCount = (int) Math.ceil((float) itemCount / SLOTS_PRE_ROW);

        var map = new HashMap<AppliableItem, IntPair>();
        for (int r = 0; r < rowCount; ++r) {
            for (int c = 0; c < SLOTS_PRE_ROW; c++) {
                int i = r * SLOTS_PRE_ROW + c;
                if (i < itemCount) {
                    map.put(
                        items.get(i),
                        new IntPair((c * SLOT_SIZE + SLOTS_X_OFFSET + SLOT_PADDING), (r * SLOT_SIZE + y + SLOTS_Y_OFFSET + SLOT_PADDING))
                    );
                }
            }
        }

        return new AppliableItemsRecipe(enchantment, map, page, pageCount);
    }

}
