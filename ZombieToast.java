package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.entities.PotionListener;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.EnemiesMovement.MoveEnemy;
import dungeonmania.entities.enemies.EnemiesMovement.MoveRandom;
import dungeonmania.entities.enemies.EnemiesMovement.MoveRunaway;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy implements PotionListener {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    private MoveEnemy movementType;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);
        this.movementType = new MoveRandom();
    }

    @Override
    public void move(Game game) {
        Position nextPos = movementType.move(game, this);
        game.getMap().moveTo(this, nextPos);

    }

    @Override
    public void notifyPotion(Potion potion) {
        if (potion instanceof InvincibilityPotion)
            movementType = new MoveRunaway();
    }

    @Override
    public void notifyNoPotion() {
        movementType = new MoveRandom();
    }

}
