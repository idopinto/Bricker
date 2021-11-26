package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;


public class RemoveBrickStrategy implements CollisionStrategy{
    private  GameObjectCollection gameObjects;

    public RemoveBrickStrategy(GameObjectCollection gameObjects)
    {
        this.gameObjects = gameObjects;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {

        if (this.gameObjects.removeGameObject(thisObj,Layer.STATIC_OBJECTS))
        {
            brickCounter.decrement();
            System.out.println(brickCounter.value());

        }

    }

    @Override
    public GameObjectCollection getGameObjectCollection() {
        return this.gameObjects;
    }
}
