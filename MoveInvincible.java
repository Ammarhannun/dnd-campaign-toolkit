package dungeonmania.entities.enemies.EnemiesMovement;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class MoveInvincible implements MoveEnemy {
    @Override
    public Position move(Game game, Entity entity) {
        GameMap map = game.getMap();
        Position playerPos = game.getPlayer().getPosition();
        Position current = entity.getPosition();

        Position diff = Position.calculatePositionBetween(playerPos, current);

        Position moveX = (diff.getX() >= 0) ? Position.translateBy(current, Direction.RIGHT)
                : Position.translateBy(current, Direction.LEFT);
        Position moveY = (diff.getY() >= 0) ? Position.translateBy(current, Direction.DOWN)
                : Position.translateBy(current, Direction.UP);

        Position offset = current;

        if (diff.getY() == 0 && map.canMoveTo(entity, moveX)) {
            offset = moveX;
        } else if (diff.getX() == 0 && map.canMoveTo(entity, moveY)) {
            offset = moveY;
        } else if (Math.abs(diff.getX()) >= Math.abs(diff.getY())) {
            if (map.canMoveTo(entity, moveY))
                offset = moveY;
            else if (map.canMoveTo(entity, moveX))
                offset = moveX;
        } else {
            if (map.canMoveTo(entity, moveX))
                offset = moveX;
            else if (map.canMoveTo(entity, moveY))
                offset = moveY;
        }
        return offset;
    }

}
