package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * a fierce blue-colored predator that enjoys nothing more than snacking on
 * hapless Plips.
 *
 * @author Lxs
 */
public class Clorus extends Creature {

    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;
    /**
     * fraction of energy to retain when replicating.
     */
    private static final double repEnergyRetained = 0.5;
    /**
     * fraction of energy to bestow upon offspring.
     */
    private static final double repEnergyGiven = 0.5;

    private static final int minEnergy = 0;

    /**
     * creates plip with energy equal to E.
     */
    public Clorus(double e) {
        super("clorus");
        energy = e;
        r = 34;
        g = 0;
        b = 231;
    }

    /**
     * creates a plip with energy equal to 1.
     */
    public Clorus() {
        this(1);
    }

    /**
     * Should return a color with red = 99, blue = 76, and green that varies
     * linearly based on the energy of the Plip. If the plip has zero energy, it
     * should have a green value of 63. If it has max energy, it should have a green
     * value of 255. The green value should vary with energy linearly in between
     * these two extremes. It's not absolutely vital that you get this exactly
     * correct.
     */
    @Override
    public Color color() {
        return color(r, g, b);
    }

    /**
     * If a Clorus attacks another creature, it should gain that creature’s energy.
     */
    @Override

    public void attack(Creature c) {
        energy += c.energy();
    }

    /**
     * Plips should lose 0.15 units of energy when moving. If you want to to avoid
     * the magic number warning, you'll need to make a private static final
     * variable. This is not required for this lab.
     */
    @Override
    public void move() {
        energy = energy - 0.03;
        if (energy < minEnergy) {
            energy = minEnergy;
        }
    }

    /**
     * Plips gain 0.2 energy when staying due to photosynthesis.
     */
    @Override
    public void stay() {
        energy = energy - 0.01;
    }

    /**
     * Clorus and their offspring each get 50% of the energy, with none lost to the
     * process. Now that's efficiency! Returns a baby Clorus.
     */
    @Override
    public Clorus replicate() {
        double tmp = energy;
        energy = tmp * repEnergyRetained;
        double babyEnergy = tmp * repEnergyGiven;
        return new Clorus(babyEnergy);
    }

    /**
     * Cloruses should obey exactly the following behavioral rules:
     * 1. If there are no empty squares, the Clorus will STAY (even if there are Plips
     * nearby they could attack since plip squares do not count as empty squares).
     * 2. Otherwise, if any Plips are seen, the Clorus will ATTACK one of them randomly.
     * 3. Otherwise, if the Clorus has energy greater than or equal to one, it will
     * REPLICATE to a random empty square
     * 4. Otherwise, the Clorus will MOVE to a random empty square.
     */
    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plipNeighbors = new ArrayDeque<>();

        for (Direction dir : neighbors.keySet()) {
            Occupant occ = neighbors.get(dir);
            if ("empty".equals(occ.name())) {
                emptyNeighbors.addLast(dir);
            } else if ("plip".equals(occ.name())) {
                plipNeighbors.addLast(dir);
            }
        }

        if (emptyNeighbors.size() == 0) {
            return new Action(Action.ActionType.STAY);
        } else if (plipNeighbors.size() != 0) {
            return new Action(Action.ActionType.ATTACK, HugLifeUtils.randomEntry(plipNeighbors));
        } else if (energy > 1) {
            return new Action(Action.ActionType.REPLICATE, HugLifeUtils.randomEntry(emptyNeighbors));
        } else {
            return new Action(Action.ActionType.MOVE, HugLifeUtils.randomEntry(emptyNeighbors));
        }
    }
}
