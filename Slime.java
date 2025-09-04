package dungeonmania.entities.enemies;

import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public abstract class Slime extends Enemy {
    private double damage;
    private Direction movementDirection;

    public static final double DEFAULT_HEALTH = 10.0;

    public Slime(Position position, double health, double damage, Direction movementDirection) {
        super(position, health, damage);
        this.damage = damage;
        this.movementDirection = movementDirection;
        getBattleStatistics().setAttack(damage);
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
        getBattleStatistics().setAttack(damage);
    }

    public Direction getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(Direction direction) {
        this.movementDirection = direction;
    }

    public void reverseDirection() {
        switch (movementDirection) {
        case UP -> movementDirection = Direction.DOWN;
        case DOWN -> movementDirection = Direction.UP;
        case LEFT -> movementDirection = Direction.RIGHT;
        case RIGHT -> movementDirection = Direction.LEFT;
        default -> throw new IllegalStateException("Unexpected value: " + movementDirection);
        }
    }

    public abstract Slime absorb(Slime other);

    public static Slime mergeSlimes(List<Slime> slimes, double maxHealth) {
        if (slimes == null || slimes.isEmpty())
            return null;

        List<Slime> sortedSlimes = new ArrayList<>(slimes);
        sortedSlimes.sort((s1, s2) -> {
            int p1 = s1.getAbsorptionPriority();
            int p2 = s2.getAbsorptionPriority();
            if (p1 != p2) {
                return Integer.compare(p1, p2);
            }
            return Double.compare(s2.getDamage(), s1.getDamage());
        });

        Slime merged = sortedSlimes.get(0);
        for (int i = 1; i < sortedSlimes.size(); i++) {
            merged = merged.absorb(sortedSlimes.get(i));
        }

        merged.setHealth(Math.min(merged.getHealth(), maxHealth));

        merged.getBattleStatistics().setHealth(merged.getHealth());
        merged.getBattleStatistics().setAttack(merged.getDamage());

        return merged;
    }

    public abstract int getAbsorptionPriority();

    public void move(GameMap map) {
        Position nextPos = Position.translateBy(getPosition(), movementDirection);
        if (!map.canMoveTo(this, nextPos)) {
            reverseDirection();
            nextPos = Position.translateBy(getPosition(), movementDirection);
            if (!map.canMoveTo(this, nextPos)) {
                return;
            }
        }
        map.moveTo(this, movementDirection);
    }

    public double getHealth() {
        return getBattleStatistics().getHealth();
    }

    public void setHealth(double health) {
        getBattleStatistics().setHealth(health);
    }
}
