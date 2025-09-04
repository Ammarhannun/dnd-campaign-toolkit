package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class BlueSlime extends Slime {
    private boolean hasHealingPower;
    public static final double DEFAULT_DAMAGE = 5.0;

    public BlueSlime(Position position, double damage) {
        super(position, DEFAULT_HEALTH, damage, Direction.RIGHT);
        this.hasHealingPower = true;
        getBattleStatistics().setAttack(damage);
    }

    @Override
    public Slime absorb(Slime other) {
        this.hasHealingPower = false;

        if (other instanceof RedSlime || other instanceof GreenSlime) {
            BlueSlime result = new BlueSlime(this.getPosition(), other.getDamage());
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            result.setHealingPower(false);
            return result;

        } else if (other instanceof BlueSlime) {
            double reversedDamage = this.getDamage() - other.getDamage();
            if (reversedDamage < 0)
                reversedDamage = 0;

            BlueSlime result = new BlueSlime(this.getPosition(), reversedDamage);
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            result.setHealingPower(false);
            return result;
        }

        return this;
    }

    public boolean hasHealingPower() {
        return hasHealingPower;
    }

    public void setHealingPower(boolean power) {
        this.hasHealingPower = power;
    }

    @Override
    public int getAbsorptionPriority() {
        return 2;
    }

    @Override
    public void move(Game game) {
        super.move(game.getMap());
    }
}
