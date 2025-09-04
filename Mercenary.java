package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.PotionListener;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.collectables.potions.Potion;
import dungeonmania.entities.enemies.EnemiesMovement.MoveAllied;
import dungeonmania.entities.enemies.EnemiesMovement.MoveEnemy;
import dungeonmania.entities.enemies.EnemiesMovement.MoveHostile;
import dungeonmania.entities.enemies.EnemiesMovement.MoveInvincible;
import dungeonmania.entities.enemies.EnemiesMovement.MoveRandom;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable, PotionListener {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean wasAdjacentToPlayer = false;
    private int mindControlTicksRemaining = 0;

    /** Type of movement to use */
    private MoveEnemy movementType;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;
        this.movementType = new MoveHostile();
    }

    public boolean isAllied() {
        return allied;
    }

    public boolean getWasAdjacentToPlayer() {
        return wasAdjacentToPlayer;
    }

    public void setWasAdjacentToPlayer(boolean wasAdjacentToPlayer) {
        this.wasAdjacentToPlayer = wasAdjacentToPlayer;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current mercenary can be bribed
     */
    private boolean canBeBribed(Player player) {
        Position playerPos = player.getPosition();
        int distance = Math.abs(playerPos.getX() - getPosition().getX())
                + Math.abs(playerPos.getY() - getPosition().getY());
        return distance <= bribeRadius && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the mercenary
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public void interact(Player player, Game game) {
        if (player.getInventory().count(Sceptre.class) > 0) {
            Sceptre sceptre = player.getInventory().getFirst(Sceptre.class);
            mindControl(sceptre.getMindControlDuration());
        } else {
            bribe(player);
            allied = true;
            movementType = new MoveAllied();
        }
    }

    @Override
    public void move(Game game) {
        Position nextPos = movementType.move(game, this);
        game.getMap().moveTo(this, nextPos);
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }

    @Override
    public void notifyPotion(Potion potion) {
        if (allied)
            return;

        if (potion instanceof InvisibilityPotion)
            movementType = new MoveRandom();
        if (potion instanceof InvincibilityPotion)
            movementType = new MoveInvincible();
    }

    @Override
    public void notifyNoPotion() {
        if (allied)
            return;

        movementType = new MoveHostile();
    }

    public void mindControl(int duration) {
        allied = true;
        mindControlTicksRemaining = duration;
        movementType = new MoveAllied();
    }

    public void onTick() {
        if (mindControlTicksRemaining > 0) {
            mindControlTicksRemaining--;
            if (mindControlTicksRemaining == 0) {
                allied = false;
                movementType = new MoveHostile();
            }
        }
    }

}
