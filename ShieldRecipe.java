package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.Treasure;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ShieldRecipe extends Recipe {
    public ShieldRecipe(Map<Class<? extends InventoryItem>, Integer> ingredients, Supplier<Buildable> builder) {
        super(ingredients, builder);
    }

    @Override
    public boolean canBuild(List<InventoryItem> inventory) {
        boolean base = super.canBuild(inventory);

        boolean hasTreasureOrKey = inventory.stream()
                .anyMatch(item -> item.getClass().equals(Treasure.class) || item.getClass().equals(Key.class));

        return base && hasTreasureOrKey;
    }

    @Override
    public void consumeItems(List<InventoryItem> inventory) {
        // Consume base ingredients (wood etc)
        super.consumeItems(inventory);

        // Consume either one Treasure OR one Key
        Iterator<InventoryItem> it = inventory.iterator();
        while (it.hasNext()) {
            InventoryItem item = it.next();
            if (item.getClass().equals(Treasure.class) || item.getClass().equals(Key.class)) {
                it.remove();
                break;
            }
        }
    }
}
