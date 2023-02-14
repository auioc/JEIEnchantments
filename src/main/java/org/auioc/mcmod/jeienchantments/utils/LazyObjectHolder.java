package org.auioc.mcmod.jeienchantments.utils;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @see <a href="https://github.com/auioc/arnicalib-mcmod/blob/4dbac981f15a80b8ffdbe1392bf061270a43f7cd/src/main/java/org/auioc/mcmod/arnicalib/base/holder/LazyObjectHolder.java">ArnicaLib: LazyObjectHolder.java</a>
 */
@OnlyIn(Dist.CLIENT)
public class LazyObjectHolder<T> {

    private final Supplier<T> supplier;
    @Nullable
    private T resolved;

    public LazyObjectHolder(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    @Nullable
    public T get() {
        if (this.resolved == null) {
            this.resolved = this.supplier.get();
        }
        return this.resolved;
    }

}
