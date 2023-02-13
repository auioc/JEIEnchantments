package org.auioc.mcmod.jeienchantments.jei.recipe;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.jeienchantments.api.record.IEnchantmentRecord;
import org.auioc.mcmod.jeienchantments.api.record.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record DescriptionRecipe(Enchantment enchantment, List<FormattedText> description, int page, int pageCount) implements IEnchantmentRecord, IPaginatedRecord {

    @SuppressWarnings("resource")
    public static List<DescriptionRecipe> create(List<Enchantment> enchantments) {
        final var font = Minecraft.getInstance().font;
        return enchantments.stream().map((enchantment) -> create(enchantment, font)).flatMap(List::stream).toList();
    }

    private static List<DescriptionRecipe> create(Enchantment enchantment, Font font) {
        final var lines = new ArrayList<FormattedText>();
        final int width = DescriptionCategory.WIDTH - 4;

        for (var text : createContent(enchantment)) {
            if (text == null) lines.add(Utils.text(""));
            else lines.addAll(Utils.splitText(text, font, width));
        }

        var lineCount = lines.size();
        int maxLines = DescriptionCategory.calcContentHeight(enchantment, font) / (font.lineHeight + DescriptionCategory.TEXT_ROW_SPACING);
        int pageCount = (int) Math.ceil((float) lines.size() / maxLines);

        var recipes = new ArrayList<DescriptionRecipe>();
        for (int p = 0; p < pageCount; ++p) {
            var description = lines.subList(p * maxLines, Math.min((p + 1) * maxLines, lineCount));
            recipes.add(new DescriptionRecipe(enchantment, description, (p + 1), pageCount));
        }

        return recipes;
    }

    private static List<FormattedText> createContent(Enchantment enchantment) {
        final var content = new ArrayList<FormattedText>();
        {
            content.add(Utils.guiListMarker("rarity").append(Utils.rarityText(enchantment)));
            content.add(Utils.guiListMarker("tradeable").append(Utils.guiYesNo(enchantment.isTradeable())));
            content.add(Utils.guiListMarker("treasure_only").append(Utils.guiYesNo(enchantment.isTreasureOnly())));
            content.add(null);
            content.add(Utils.desc(enchantment));
        }
        return content;
    }

}
