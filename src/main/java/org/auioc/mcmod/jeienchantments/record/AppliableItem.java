package org.auioc.mcmod.jeienchantments.record;

import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public record AppliableItem(Item item, boolean canApply, boolean canApplyAtTable) {

    @OnlyIn(Dist.CLIENT)
    public static record Slot(Item item, boolean canApplyAtTable, int x, int y) {

        public Slot(AppliableItem appliableItem, int x, int y) {
            this(appliableItem.item(), appliableItem.canApplyAtTable(), x, y);
        }

    }

}
