package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class MockPaddle extends Paddle{

    public static boolean isInstantiated;
    private GameObjectCollection gameObjects;
    private int numCollisionsToDisappear;
    private int minDistanceFromEdge;
    private Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner       Position of the object, in window coordinates (pixels).
     *                            Note that (0,0) is the top-left corner of the window.
     * @param dimensions          Width and height in window coordinates.
     * @param renderable          The renderable representing the object. Can be null, in which case
     * @param inputListener
     * @param windowDimensions
     * @param minDistanceFromEdge
     */
    public MockPaddle(danogl.util.Vector2 topLeftCorner,
                       danogl.util.Vector2 dimensions,
                       danogl.gui.rendering.Renderable renderable,
                       danogl.gui.UserInputListener inputListener,
                       danogl.util.Vector2 windowDimensions,
                       danogl.collisions.GameObjectCollection gameObjectCollection,
                       int minDistanceFromEdge,
                       int numCollisionsToDisappear){
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);

        this.gameObjects = gameObjectCollection;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.minDistanceFromEdge = minDistanceFromEdge;
        this.windowDimensions = windowDimensions;
        isInstantiated = true;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.numCollisionsToDisappear > 1){
            this.numCollisionsToDisappear--;
        }
        else{
            this.gameObjects.removeGameObject(this);
            isInstantiated = false;
        }
    }

}
