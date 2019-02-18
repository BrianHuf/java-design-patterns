class Creature {
    public int attack, health;

    public Creature(int attack, int health) {
        this.attack = attack;
        this.health = health;
    }

    @Override
    public String toString() {
        return String.format("(%d/%d)", attack, health);
    }
}

abstract class CardGame {
    public Creature[] creatures;

    public CardGame(Creature[] creatures) {
        this.creatures = creatures;
    }

    // returns -1 if no clear winner (both alive or both dead)
    public int combat(int creature1, int creature2) {
        Creature first = creatures[creature1];
        Creature second = creatures[creature2];
        hit(first, second);
        hit(second, first);

        if (first.health > 0 && second.health <= 0) {
            return 0;
        }

        if (first.health <= 0 && second.health > 0) {
            return 1;
        }

        return -1;
    }

    // attacker hits other creature
    protected abstract void hit(Creature attacker, Creature other);
}

class TemporaryCardDamageGame extends CardGame {
    public TemporaryCardDamageGame(Creature... creatures) {
        super(creatures);
    }

    protected void hit(Creature attacker, Creature other) {
        if (attacker.attack >= other.health) {
            other.health -= attacker.attack;
        }
    }
}

class PermanentCardDamageGame extends CardGame {
    public PermanentCardDamageGame(Creature... creatures) {
        super(creatures);
    }

    protected void hit(Creature attacker, Creature other) {
        other.health -= attacker.attack;
    }
}

class DemoTemplate {
    public static void main(String[] args) {
        Creature p1 = new Creature(10, 5);
        Creature p2 = new Creature(2, 50);
        CardGame permDamage = new PermanentCardDamageGame(p1, p2);
        playGame(permDamage);

        Creature p3 = new Creature(10, 5);
        Creature p4 = new Creature(2, 50);
        CardGame tempDamage = new TemporaryCardDamageGame(p3, p4);
        playGame(tempDamage);
    }

    private static void playGame(CardGame game) {
        int winner = -1;
        for (int i = 0; i < 100; i++) {
            winner = game.combat(0, 1);
            if (winner != -1) {
                break;
            }
        }

        System.out.println(game.getClass().getSimpleName() + " winner = " + winner);
    }
}