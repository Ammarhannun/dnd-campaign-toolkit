package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.InventoryItem;

import java.util.*;
import java.util.function.Supplier;

public class Recipe {
    private final Map<Class<? extends InventoryItem>, Integer> ingredients;
    private final Supplier<Buildable> builder;

    public Recipe(Map<Class<? extends InventoryItem>, Integer> ingredients, Supplier<Buildable> builder) {
        this.ingredients = ingredients;
        this.builder = builder;
    }

    public boolean canBuild(List<InventoryItem> inventory) {
        Map<Class<? extends InventoryItem>, Integer> countMap = new HashMap<>();
        for (InventoryItem item : inventory) {
            countMap.put(item.getClass(), countMap.getOrDefault(item.getClass(), 0) + 1);
        }

        for (Map.Entry<Class<? extends InventoryItem>, Integer> entry : ingredients.entrySet()) {
            if (countMap.getOrDefault(entry.getKey(), 0) < entry.getValue())
                return false;
        }

        return true;
    }

    public void consumeItems(List<InventoryItem> inventory) {
        for (Map.Entry<Class<? extends InventoryItem>, Integer> entry : ingredients.entrySet()) {
            int needed = entry.getValue();
            Iterator<InventoryItem> it = inventory.iterator();
            while (it.hasNext() && needed > 0) {
                InventoryItem item = it.next();
                if (item.getClass().equals(entry.getKey())) {
                    it.remove();
                    needed--;
                }
            }
        }
    }

    public Buildable build() {
        return builder.get();
    }
}
