package dungeonmania.entities.enemies.EnemiesMovement;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveAllied implements MoveEnemy {
    @Override
    public Position move(Game game, Entity entity) {
        Position nextPos = null;
        GameMap map = game.getMap();
        Player player = game.getPlayer();
        boolean wasAdjacentToPlayer = ((Mercenary) entity).getWasAdjacentToPlayer();
        boolean isAdjacentToPlayer = Position.isAdjacent(player.getPosition(), entity.getPosition());
        if (wasAdjacentToPlayer && !isAdjacentToPlayer) {
            nextPos = player.getPreviousDistinctPosition();
        } else {
            // If currently still adjacent, wait in place. Else pursue the player.
            nextPos = isAdjacentToPlayer ? entity.getPosition()
                    : map.dijkstraPathFind(entity.getPosition(), player.getPosition(), entity);
            ((Mercenary) entity).setWasAdjacentToPlayer(Position.isAdjacent(player.getPosition(), nextPos));
        }

        return nextPos;
    }

}
