package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;
import java.util.Vector;

public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 400;
    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 10;

    private UserInputListener inputListener;
    private float windowWidth;

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDirection = Vector2.ZERO;
        movementDirection = setMovementDirection(movementDirection);
        setVelocity(movementDirection.mult(MOVEMENT_SPEED));
        validatePaddleBoundaries();
    }

    private void validatePaddleBoundaries()
    {
        if ((MIN_DISTANCE_FROM_SCREEN_EDGE > getTopLeftCorner().x()))
        {
            transform().setTopLeftCornerX(MIN_DISTANCE_FROM_SCREEN_EDGE);
        }
        if ((getTopLeftCorner().x() > windowWidth - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x()))
        {
            transform().setTopLeftCornerX(windowWidth - MIN_DISTANCE_FROM_SCREEN_EDGE - getDimensions().x());
        }
    }

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
    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, UserInputListener inputListener, float windowWidth) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowWidth = windowWidth;
    }
}
