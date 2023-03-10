package org.auioc.mcmod.jeienchantments.api.record;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IPaginatedRecord {

    int page();

    int pageCount();

}
