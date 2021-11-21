package src.gameobjects;

import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.brick_strategies.ChangeCameraStrategy;

/**
 * An object of this class is instantiated on collision of ball with a brick with a change camera strategy.
 * It checks ball's collision counter every frame,
 * and once the it finds the ball has collided countDownValue times since instantiation,
 * it calls the strategy to reset the camera to normal.
 */
public class BallCollisionCountdownAgent extends GameObject {

    private Ball ball;
    private ChangeCameraStrategy owner;
    private int countDownValue;

    /**
     *
     * @param ball
     * @param owner
     * @param countDownValue
     */
    public BallCollisionCountdownAgent(Ball ball,ChangeCameraStrategy owner, int countDownValue) {
        super(Vector2.ZERO, Vector2.ZERO, null);

        this.ball = ball;
        this.owner = owner;
        this.countDownValue = countDownValue;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.ball.getCollisionCount() == this.countDownValue){
            this.owner.turnOffCameraChange();
        }
    }
}
