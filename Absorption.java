package dungeonmania.entities.enemies;

public class Absorption {
    private final String previousType;
    private final double previousDamage;

    public Absorption(String previousType, double previousDamage) {
        this.previousType = previousType;
        this.previousDamage = previousDamage;
    }

    public String getPreviousType() {
        return previousType;
    }

    public double getDamage() {
        return previousDamage;
    }
}
