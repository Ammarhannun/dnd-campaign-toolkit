package dungeonmania.entities.enemies.EnemiesMovement;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.util.Position;

public interface MoveEnemy {
    public Position move(Game game, Entity entity);
}
