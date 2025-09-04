package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class RedSlime extends Slime {
    public static final double DEFAULT_DAMAGE = 5.0;

    public RedSlime(Position position, double damage) {
        super(position, DEFAULT_HEALTH, damage, Direction.DOWN);
    }

    @Override
    public Slime absorb(Slime other) {
        double newDamage = this.getDamage();

        if (other instanceof RedSlime) {
            newDamage += other.getDamage();
            RedSlime result = new RedSlime(this.getPosition(), newDamage);
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            return result;

        } else if (other instanceof GreenSlime) {
            double greenDamage = newDamage / 2.0 + other.getDamage();
            GreenSlime result = new GreenSlime(this.getPosition(), greenDamage);
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            return result;

        } else if (other instanceof BlueSlime) {
            BlueSlime result = new BlueSlime(this.getPosition(), newDamage);
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            result.setHealingPower(false);
            return result;
        }

        return this;
    }

    @Override
    public int getAbsorptionPriority() {
        return 0;
    }

    @Override
    public void move(Game game) {
        super.move(game.getMap());
    }
}
