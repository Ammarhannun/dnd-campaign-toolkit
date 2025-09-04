package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.BowRecipe;
import dungeonmania.entities.buildables.Recipe;
import dungeonmania.entities.buildables.ShieldRecipe;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Useable;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.enemies.ZombieToast;

/**
 * Represents the contents of the player's inventory, containing all their collected and crafted items.
 */
public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();
    private Map<String, Recipe> recipes = new HashMap<>();
    private EntityFactory factory;

    // Pass EntityFactory so we can build items properly
    public Inventory(EntityFactory factory) {
        this.factory = factory;
        initRecipes();
    }

    // Initialize recipe map
    private void initRecipes() {
        recipes.put("bow", new BowRecipe(Map.of(Wood.class, 1, Arrow.class, 3), () -> factory.buildBow()));
        recipes.put("shield", new ShieldRecipe(Map.of(Wood.class, 2), () -> factory.buildShield()));
        recipes.put("midnight_armour",
                new Recipe(Map.of(Sword.class, 1, SunStone.class, 1), () -> factory.buildMidnightArmour()));
    }

    /** Add the given item to the inventory */
    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    /** Remove the given item from the inventory */
    public void remove(InventoryItem item) {
        items.remove(item);
    }

    /** Get the list of possible buildables */
    public List<String> getBuildables() {
        List<String> result = new ArrayList<>();

        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class);
        int keys = count(Key.class);
        int sunstone = count(SunStone.class);

        if (wood >= 1 && arrows >= 3) {
            result.add("bow");
        }
        if (wood >= 2 && (treasure >= 1 || keys >= 1)) {
            result.add("shield");
        }

        if ((wood >= 1 || arrows >= 2) && (keys >= 1 || treasure >= 1) && sunstone >= 1) {
            result.add("sceptre");
        }

        if (count(Sword.class) >= 1 && sunstone >= 1) {
            result.add("midnight_armour");
        }

        return result;
    }

    /**
     * Check whether a player has the supplies to build a particular buildable. If so, build the item.
     *
     * Currently since there are only two buildables we have a boolean to keep track of which buildable it is.
     *
     * @param p player object
     * @param remove whether to remove the build materials from the inventory while crafting the item.
     * @param forceShield if `true` always craft a shield, otherwise craft a bow if possible, otherwise a shield.
     * @param factory entity factory
     */
    public InventoryItem checkBuildCriteria(Player p, boolean remove, boolean forceShield, EntityFactory factory,
            Game game) {
        if (forceShield) {
            Recipe shieldRecipe = recipes.get("shield");
            if (shieldRecipe.canBuild(items)) {
                if (remove)
                    shieldRecipe.consumeItems(items);
                return shieldRecipe.build();
            }
            return null;
        }

        Recipe bowRecipe = recipes.get("bow");
        if (bowRecipe.canBuild(items)) {
            if (remove)
                bowRecipe.consumeItems(items);
            return bowRecipe.build();
        }

        Recipe shieldRecipe = recipes.get("shield");
        if (shieldRecipe.canBuild(items)) {
            if (remove)
                shieldRecipe.consumeItems(items);
            return shieldRecipe.build();
        }

        if (p.getInventory().getBuildables().contains("sceptre")) {
            boolean hasWoodOrArrows = count(Wood.class) >= 1 || count(Arrow.class) >= 2;
            boolean hasKeyOrTreasure = count(Key.class) >= 1 || count(Treasure.class) >= 1;
            boolean hasSunStone = count(SunStone.class) >= 1;

            if (hasWoodOrArrows && hasKeyOrTreasure && hasSunStone) {
                if (remove) {
                    if (count(Wood.class) >= 1)
                        consumeOne(Wood.class);
                    else
                        consumeN(Arrow.class, 2);
                    if (count(Key.class) >= 1)
                        consumeOne(Key.class);
                    else if (count(Treasure.class) >= 1)
                        consumeOne(Treasure.class);
                    else
                        consumeOne(SunStone.class);
                }
                return factory.buildSceptre();
            }
        }

        Recipe midnightRecipe = recipes.get("midnight_armour");
        if (midnightRecipe.canBuild(items) && game.getMap().getEntities(ZombieToast.class).isEmpty()) {
            if (remove)
                midnightRecipe.consumeItems(items);
            return midnightRecipe.build();
        }

        return null;
    }

    /**
     * Return the first instance of an item matching the given type, or `null` if none are found.
     *
     * For example, to find the first piece of treasure, you could pass `Treasure.class`.
     *
     * This uses an `isInstance` check, so you can use interfaces and base classes too.
     */
    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    /**
     * Return the number of items which match the given type.
     *
     * For example, to determine how rich the player is, you could pass `Treasure.class` to count their treasure.
     *
     * This uses an `isInstance` check, so you can use interfaces and base classes too.
     */
    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    /** Return a reference to the entity with the given ID, else `null` if not found */
    public Entity getEntity(String entityId) {
        for (InventoryItem item : items)
            if (item.getId().equals(entityId))
                return item;
        return null;
    }

    /** Return all entities in the inventory */
    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    /**
     * Return a list of inventory items which match the given type.
     *
     * For example, to plunder the player's riches, you could pass `Treasure.class` to find all their treasure.
     *
     * This uses an `isInstance` check, so you can use interfaces and base classes too.
     */
    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    /** Return whether the player has a sword or a bow. */
    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    /**
     * Returns a reference to the player's active weapon. If the player has a sword, it uses that, otherwise, it uses a
     * bow.
     *
     * If the player has no weapons, it returns `null`.
     */
    public Useable getWeapon() {
        Useable weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

    public List<InventoryItem> getItems() {
        return items;
    }

    private void consumeOne(Class<? extends InventoryItem> clazz) {
        for (InventoryItem item : items) {
            if (clazz.isInstance(item)) {
                items.remove(item);
                break;
            }
        }
    }

    private void consumeN(Class<? extends InventoryItem> clazz, int n) {
        int consumed = 0;
        var it = items.iterator();
        while (it.hasNext() && consumed < n) {
            InventoryItem item = it.next();
            if (clazz.isInstance(item)) {
                it.remove();
                consumed++;
            }
        }
    }

}
