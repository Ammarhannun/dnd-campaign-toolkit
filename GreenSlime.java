package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class GreenSlime extends Slime {
    public static final double DEFAULT_DAMAGE = 5.0;

    public GreenSlime(Position position, double damage) {
        super(position, DEFAULT_HEALTH, damage, Direction.UP);
    }

    @Override
    public Slime absorb(Slime other) {
        double newDamage = this.getDamage();

        if (other instanceof RedSlime) {
            newDamage += other.getDamage() / 2.0;
            GreenSlime result = new GreenSlime(this.getPosition(), newDamage);
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            return result;

        } else if (other instanceof GreenSlime) {
            newDamage += other.getDamage() / 2.0;
            GreenSlime result = new GreenSlime(this.getPosition(), newDamage);
            result.setHealth(Math.max(this.getHealth(), other.getHealth()));
            return result;

        } else if (other instanceof BlueSlime) {
            return other.absorb(this);
        }

        return this;
    }

    @Override
    public int getAbsorptionPriority() {
        return 1;
    }

    @Override
    public void move(Game game) {
        super.move(game.getMap());
    }
}
