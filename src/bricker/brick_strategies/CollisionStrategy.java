package bricker.brick_strategies;


import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

public class CollisionStrategy {

    private final GameObjectCollection gameObjects;
    private Counter brickCounter;

    public CollisionStrategy(GameObjectCollection gameObjects, Counter brickCounter)
    {

        this.gameObjects = gameObjects;
        this.brickCounter = brickCounter;
    }
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        this.gameObjects.removeGameObject(thisObj,Layer.STATIC_OBJECTS);
        this.brickCounter.decrement();

    }

}
