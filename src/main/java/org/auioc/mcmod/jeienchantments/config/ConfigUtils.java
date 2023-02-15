package org.auioc.mcmod.jeienchantments.config;

import java.util.List;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

/**
 * @see <a href="https://github.com/auioc/arnicalib-mcmod/blob/4dbac981f15a80b8ffdbe1392bf061270a43f7cd/src/main/java/org/auioc/mcmod/arnicalib/game/config/ConfigUtils.java">ArnicaLib: ConfigUtils.java</a>
 */
@OnlyIn(Dist.CLIENT)
public class ConfigUtils {

    protected static final Splitter DOT_SPLITTER = Splitter.on(".");

    protected static List<String> split(String _path) {
        return Lists.newArrayList(DOT_SPLITTER.split(_path));
    }

    protected static ConfigValue<List<? extends String>> defineStringList(Builder _specBuilder, String _path) {
        return _specBuilder.defineListAllowEmpty(split(_path), List::of, (o) -> o instanceof String);
    }

}
