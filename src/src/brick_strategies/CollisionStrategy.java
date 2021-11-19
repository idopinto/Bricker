package src.brick_strategies;


import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * This class Says what to do when brick collided with ball.
 */
public class CollisionStrategy {

    private final GameObjectCollection gameObjects;

    public CollisionStrategy(GameObjectCollection gameObjects)
    {
        this.gameObjects = gameObjects;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        this.gameObjects.removeGameObject(thisObj,Layer.STATIC_OBJECTS);
        brickCounter.decrement();
    }

}
