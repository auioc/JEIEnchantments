package org.auioc.mcmod.jeienchantments.api.record;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public interface IItemRecord extends ItemLike {

    Item item();

    @Override
    default Item asItem() {
        return item();
    }

}
