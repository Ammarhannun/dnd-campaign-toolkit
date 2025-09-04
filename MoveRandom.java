package dungeonmania.entities.enemies.EnemiesMovement;

import java.util.List;
import java.util.Random;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class MoveRandom implements MoveEnemy {
    @Override
    public Position move(Game game, Entity entity) {
        Position nextPos = null;
        GameMap map = game.getMap();
        Random randGen = new Random();
        List<Position> pos = entity.getPosition().getCardinallyAdjacentPositions();
        pos = pos.stream().filter(p -> map.canMoveTo(entity, p)).toList();
        if (pos.size() == 0) {
            nextPos = entity.getPosition();
            map.moveTo(entity, nextPos);
        } else {
            nextPos = pos.get(randGen.nextInt(pos.size()));
            map.moveTo(entity, nextPos);
        }

        return nextPos;
    }

}
