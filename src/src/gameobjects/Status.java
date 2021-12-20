package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 *
 */
public class Status extends GameObject {

    private static final float WIDE_FACTOR = 2;
    private static final float NARROW_FACTOR = 0.5f;
    private final GameStatusTypes gameStatusType;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameStatusType game status type such as WIDE or NARROW
     */
    public Status(Vector2 topLeftCorner, Vector2 widgetDimensions, Renderable renderable,
                  GameObjectCollection gameObjects, GameStatusTypes gameStatusType, Vector2 windowDimensions) {
        super(topLeftCorner, widgetDimensions, renderable);
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.gameStatusType = gameStatusType;
    }


    @Override
    public boolean shouldCollideWith(GameObject other) {
        super.shouldCollideWith(other);
        return other instanceof Paddle;

    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        applyEffectOnPaddle(other);
        this.gameObjects.removeGameObject(this);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float widgetHeight = this.getCenter().y();

        if (widgetHeight > this.windowDimensions.y()){
            this.gameObjects.removeGameObject(this);
        }
    }

    /*this method gets paddle object and make it narrower or wider according to given game status type*/
    private void applyEffectOnPaddle(GameObject other) {
        Vector2 paddleDimensions = other.getDimensions();
            switch (this.gameStatusType) {
                case WIDE:
                    // if the size of the paddle is less then half of screen width
                    if (paddleDimensions.x() < windowDimensions.x() / 2) {
                        other.setDimensions(paddleDimensions.mult(WIDE_FACTOR));
                    }
                    break;

                case NARROW:
                    // if the size of the paddle is bigger than arbitrary small enough number that can be seen.
                    if (paddleDimensions.x() > 20)
                    {
                        other.setDimensions(paddleDimensions.mult(NARROW_FACTOR));
                    }
                    break;
            }
        }
}
