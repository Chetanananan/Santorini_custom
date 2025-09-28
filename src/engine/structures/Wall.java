package engine.structures;

import java.awt.*;

public class Wall extends Structure {
    private static final int MAX_LEVEL = 2;
    public Wall () {
        super(StructureType.WALL, 0);

    }
    /** Current build phase: 0 = none, 1 = partial, 2 = full. */
    public int getBuildLevel() {
        return super.getLevel();
    }

    @Override
    public String getLabel() {
        int lvl = getBuildLevel();
        return lvl > 0 ? "W" + lvl : "";
    }

    @Override
    public Color getColor() {
        switch (getBuildLevel()) {
            case 1:
                return Color.LIGHT_GRAY;
            case 2:
                return Color.DARK_GRAY;
            default:
                // no wall
                return new Color(0, 0, 0, 0); // fully transparent
        }
    }

    /** Returns true only when the wall has been built to level 2. */
    public boolean isFullyBuilt() {
        return getBuildLevel() == MAX_LEVEL;
    }

    /** Advance the wall one build phase, up to level 2. */
    public void buildPhase() {
        if (getBuildLevel() < MAX_LEVEL) {
            super.buildWallPhase();
        }
    }

    /** Break the wall back one phase; if at 0 it remains absent. */
    public void breakPhase() {
        if (getBuildLevel() > 0) {
            super.breakWallPhase();
        }
    }
}
