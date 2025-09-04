package dungeonmania.entities.enemies.EnemiesMovement;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MoveRunaway implements MoveEnemy {
    @Override
    public Position move(Game game, Entity entity) {
        Position nextPos = null;
        GameMap map = game.getMap();
        // Check whether the zombie should flee left or right & up or down
        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), entity.getPosition());
        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(entity.getPosition(), Direction.RIGHT)
                : Position.translateBy(entity.getPosition(), Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(entity.getPosition(), Direction.DOWN)
                : Position.translateBy(entity.getPosition(), Direction.UP);
        Position offset = entity.getPosition();
        // If on the same Y axis and can flee left or right, do so.
        if (plrDiff.getY() == 0 && map.canMoveTo(entity, moveX))
            offset = moveX;
        // Or if on the same X axis and can flee up or down, do so.
        else if (plrDiff.getX() == 0 && map.canMoveTo(entity, moveY))
            offset = moveY;
        // Prioritise Y movement if further away on the X axis
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(entity, moveY))
                offset = moveY;
            else if (map.canMoveTo(entity, moveX))
                offset = moveX;
            else
                offset = entity.getPosition();
            // Prioritise X movement if further away on the Y axis
        } else {
            if (map.canMoveTo(entity, moveX))
                offset = moveX;
            else if (map.canMoveTo(entity, moveY))
                offset = moveY;
            else
                offset = entity.getPosition();
        }
        nextPos = offset;

        return nextPos;
    }
}
