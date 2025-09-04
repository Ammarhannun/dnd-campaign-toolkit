package dungeonmania.entities.enemies.EnemiesMovement;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveHostile implements MoveEnemy {
    @Override
    public Position move(Game game, Entity entity) {
        GameMap map = game.getMap();
        Position playerPos = game.getPlayer().getPosition();
        return map.dijkstraPathFind(entity.getPosition(), playerPos, entity);
    }

}
