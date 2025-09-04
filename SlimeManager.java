package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

import java.util.List;

public class SlimeManager {
    public void moveAllSlimes(Game game) {
        GameMap map = game.getMap();
        List<Slime> slimes = map.getEntities(Slime.class);
        for (Slime slime : slimes) {
            slime.move(map);
        }
    }

    public void mergeSlimes(Game game, double maxHealth) {
        GameMap map = game.getMap();

        List<Position> positions = List.copyOf(map.getNodes().keySet());

        for (Position pos : positions) {
            List<Slime> slimes = map.getEntities(pos).stream().filter(e -> e instanceof Slime).map(e -> (Slime) e)
                    .toList();

            if (slimes.size() > 1) {
                Slime merged = Slime.mergeSlimes(slimes, maxHealth);
                if (merged != null) {
                    for (Slime slime : slimes) {
                        map.removeNode(slime);
                    }
                    map.addEntity(merged);
                }
            }
        }
    }
}
