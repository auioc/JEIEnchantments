package org.auioc.mcmod.jeienchantments.utils;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @see <a href="https://github.com/auioc/arnicalib-mcmod/blob/0cc23362520194015993bf4cfaa4f60d972a7688/src/main/java/org/auioc/mcmod/arnicalib/base/tuple/IntPair.java">ArnicaLib: IntPair.java</a>
 */
@OnlyIn(Dist.CLIENT)
public record IntPair(int x, int y) {

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj instanceof IntPair other) {
            return (x == other.x()) && (y == other.y());
        }
        return false;
    }

    @Override
    public int hashCode() { return (x * 17) + y; }

}
