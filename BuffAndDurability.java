package dungeonmania.entities.collectables;

import dungeonmania.battles.BattleStatistics;

public interface BuffAndDurability {
    /**
    * Use this inventory item to apply a buff to the player's battle statistics (eg having a sword increases the
    * player's attacking power).
    */
    public BattleStatistics applyBuff(BattleStatistics origin);

    /** Returns the durability of the item. */
    public int getDurability();
}
