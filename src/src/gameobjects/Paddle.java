package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * One of the main game objects. Repels the ball against the bricks.
 */
public class Paddle extends GameObject {
    /* Paddle constants */
    private static final float MOVEMENT_SPEED = 400;

    /* Fields */
    private final Vector2 windowDimensions;
    private final int minDistanceFromEdge;
    private final UserInputListener inputListener;


    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener input listener
     * @param windowDimensions window dimensions
     * @param minDistanceFromEdge  border for paddle movement
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener,
                  Vector2 windowDimensions,int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.minDistanceFromEdge = minDistanceFromEdge;
        this.windowDimensions =  windowDimensions;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDirection = Vector2.ZERO;
        movementDirection = setMovementDirection(movementDirection);
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));
        validatePaddleBoundaries();
    }

    /* this method ensure that the paddle will stay in the game boundaries */
    private void validatePaddleBoundaries()
    {
        if ((minDistanceFromEdge > getTopLeftCorner().x()))
        {
            transform().setTopLeftCornerX(minDistanceFromEdge);
        }
        if ((getTopLeftCorner().x() > this.windowDimensions.x() - minDistanceFromEdge - getDimensions().x()))
        {
            transform().setTopLeftCornerX(this.windowDimensions.x() - minDistanceFromEdge - getDimensions().x());
        }
    }

    /* this method sets the movement direction of the paddle according to user keyboard input*/
    private Vector2 setMovementDirection(Vector2 movementDirection)
    {
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
        {
            movementDirection = movementDirection.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
        {
            movementDirection = movementDirection.add(Vector2.RIGHT);
        }
        return movementDirection;
    }
}
