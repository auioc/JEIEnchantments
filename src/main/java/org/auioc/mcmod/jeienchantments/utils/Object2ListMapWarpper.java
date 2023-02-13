package org.auioc.mcmod.jeienchantments.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Object2ListMapWarpper<K, T> {

    private final Map<K, List<T>> map;

    public Object2ListMapWarpper() {
        this.map = new HashMap<K, List<T>>();
    }

    public static <K, T> Object2ListMapWarpper<K, T> create() {
        return new Object2ListMapWarpper<>();
    }

    public void putElement(K key, T element) {
        if (this.map.containsKey(key)) {
            this.map.get(key).add(element);
        } else {
            var list = new ArrayList<T>();
            list.add(element);
            this.map.put(key, list);
        }
    }

    public Map<K, List<T>> getMap() {
        return this.map;
    }

    @Nullable
    public List<T> getList(K key) {
        return this.map.get(key);
    }

}
