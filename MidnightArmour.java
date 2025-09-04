package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Useable;

public class MidnightArmour extends Buildable implements Useable {
    private double attackBonus;
    private double defenceBonus;

    public MidnightArmour(double attackBonus, double defenceBonus) {
        super(null);
        this.attackBonus = attackBonus;
        this.defenceBonus = defenceBonus;
    }

    @Override
    public void use(Game game) {

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, attackBonus, defenceBonus, 1, 1));
    }

    @Override
    public int getDurability() {
        return -1;
    }
}
