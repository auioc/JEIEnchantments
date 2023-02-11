package org.auioc.mcmod.jeienchantments.jei.recipe;

import java.util.ArrayList;
import java.util.List;
import org.auioc.mcmod.jeienchantments.api.IEnchantmentRecord;
import org.auioc.mcmod.jeienchantments.api.IPaginatedRecord;
import org.auioc.mcmod.jeienchantments.jei.category.DescriptionCategory;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record DescriptionRecipe(Enchantment enchantment, List<FormattedText> description, int page, int pageCount) implements IEnchantmentRecord, IPaginatedRecord {

    @SuppressWarnings("resource")
    public static List<DescriptionRecipe> create(Enchantment enchantment) {
        final var font = Minecraft.getInstance().font;

        var lines = font.getSplitter().splitLines(Utils.desc(enchantment), DescriptionCategory.WIDTH - 4, Style.EMPTY);
        var lineCount = lines.size();

        int maxLines = (DescriptionCategory.HEIGHT - DescriptionCategory.calcHeaderHeight(font, enchantment) - DescriptionCategory.FOOTER_HEIGHT) / font.lineHeight;
        int pageCount = (int) Math.ceil((float) lines.size() / maxLines);

        var recipes = new ArrayList<DescriptionRecipe>();
        for (int i = 0; i < pageCount; ++i) {
            var description = lines.subList(i * maxLines, Math.min((i + 1) * maxLines, lineCount));
            recipes.add(new DescriptionRecipe(enchantment, description, (i + 1), pageCount));
        }

        return recipes;
    }

    public static List<DescriptionRecipe> create(List<Enchantment> enchantments) {
        return enchantments.stream().map(DescriptionRecipe::create).flatMap(List::stream).toList();
    }

}
