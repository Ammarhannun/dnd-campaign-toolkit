package dungeonmania.entities.buildables;

import dungeonmania.entities.inventory.InventoryItem;
import java.util.Map;
import java.util.function.Supplier;

public class BowRecipe extends Recipe {
    public BowRecipe(Map<Class<? extends InventoryItem>, Integer> ingredients, Supplier<Buildable> builder) {
        super(ingredients, builder);
    }
}
