package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class Status extends GameObject {

    private GameStatusTypes gameStatusType;
    private GameObjectCollection gameObjects;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *  @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param gameStatusType
     */
    public Status(Vector2 topLeftCorner, Vector2 widgetDimensions, Renderable renderable, GameObjectCollection gameObjects, GameStatusTypes gameStatusType, Vector2 windowDimensions) {
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

    private void applyEffectOnPaddle(GameObject other) {
        Vector2 paddleDimensions = other.getDimensions();
        if ((paddleDimensions.x() > 25) && (paddleDimensions.x() < windowDimensions.x() / 2))
        {
            switch (this.gameStatusType)
            {
                case WIDE:
                    other.setDimensions(paddleDimensions.mult(2));
                    break;

                case NARROW:
                    other.setDimensions(paddleDimensions.mult(0.5f));
                    break;
            }
        }

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float widgetHeight = this.getCenter().y();

        if (widgetHeight > this.windowDimensions.y()){
            this.gameObjects.removeGameObject(this);
        }
    }
}
