package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.collectables.Useable;

public class Sceptre extends Buildable implements Useable {
    private int mindControlDuration;

    public Sceptre(int mindControlDuration) {
        super(null);
        this.mindControlDuration = mindControlDuration;
    }

    public int getMindControlDuration() {
        return mindControlDuration;
    }

    @Override
    public void use(Game game) {

    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return origin;
    }

    @Override
    public int getDurability() {
        return -1;
    }
}
