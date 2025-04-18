package pcd.ass01.common;

import java.awt.*;

public class Context {

    public static final int DYNAMIC_CORE_NUMBER = Runtime.getRuntime().availableProcessors() + 1;

    public static final String TITLE = "Boids | ";
    public static final String DEFAULT_BOIDS_NUMBER = "1500";
    public static final int FRAMERATE = 25;

    public static final double SEPARATION_WEIGHT = 1.0;
    public static final double ALIGNMENT_WEIGHT = 1.0;
    public static final double COHESION_WEIGHT = 1.0;

    public static final int ENVIRONMENT_WIDTH = 1000;
    public static final int ENVIRONMENT_HEIGHT = 1000;
    public static final double MAX_SPEED = 4.0;
    public static final double PERCEPTION_RADIUS = 50.0;
    public static final double AVOID_RADIUS = 20.0;

    /*public static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int SCREEN_WIDTH = (int)(screenSize.getWidth() / 1.50);
    public static final int SCREEN_HEIGHT = (int)(screenSize.getHeight() / 1.75);*/

    // Suppresses default constructor, ensuring non-instantiability.
    private Context() {}

}