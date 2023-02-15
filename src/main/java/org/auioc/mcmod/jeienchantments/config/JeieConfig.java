package org.auioc.mcmod.jeienchantments.config;

import static org.auioc.mcmod.jeienchantments.JEIEnchantments.LOGGER;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.auioc.mcmod.jeienchantments.JEIEnchantments;
import com.electronwill.nightconfig.core.CommentedConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@OnlyIn(Dist.CLIENT)
public class JeieConfig {

    protected static final Marker MARKER = MarkerManager.getMarker(JeieConfig.class.getSimpleName());

    public static final ForgeConfigSpec SPEC;

    private static final ConfigValue<List<? extends String>> _enchantmentBlacklist;
    private static final ConfigValue<List<? extends String>> _itemBlacklist;

    static {
        final ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        _enchantmentBlacklist = ConfigUtils.defineStringList(b, "enchantmentBlacklist");
        _itemBlacklist = ConfigUtils.defineStringList(b, "itemBlacklist");

        SPEC = b.build();
    }

    // ====================================================================== //

    public static final List<Enchantment> enchantmentBlacklist = new ArrayList<>();
    public static final List<Item> itemBlacklist = new ArrayList<>();

    public static void onLoad(@Nullable CommentedConfig commentedConfig) {
        if (JEIEnchantments.isSetupCompleted()) {
            loadList(enchantmentBlacklist, ForgeRegistries.ENCHANTMENTS, _enchantmentBlacklist);
            loadList(itemBlacklist, ForgeRegistries.ITEMS, _itemBlacklist);
        }
    }

    private static <T extends IForgeRegistryEntry<T>> void loadList(List<T> _list, IForgeRegistry<T> _registry, ConfigValue<List<? extends String>> _configValue) {
        _list.clear();
        var configValue = _configValue.get();
        for (var str : configValue) {
            try {
                var id = new ResourceLocation(str);
                if (_registry.containsKey(id)) {
                    _list.add(_registry.getValue(id));
                } else {
                    LOGGER.warn(MARKER, "Unknown registry entry '{}'", id.toString());
                }
            } catch (Exception e) {
                LOGGER.warn(MARKER, "Invalid namespaced identifier '{}'", str);
            }
        }
        if (configValue.size() != _list.size()) {
            var values = _list.stream().map(IForgeRegistryEntry::getRegistryName).map(ResourceLocation::toString).toList();
            LOGGER.warn(
                MARKER, "Incorrect key {} was corrected from [{}] to [{}]",
                String.join("/", _configValue.getPath().toArray(String[]::new)),
                String.join(",", configValue.toArray(String[]::new)),
                String.join(",", values.toArray(String[]::new))
            );
            _configValue.set(values);
            SPEC.save();
        }
    }

}
