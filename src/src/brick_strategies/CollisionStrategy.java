package src.brick_strategies;

import danogl.GameObject;
import danogl.util.Counter;

/**
 * This class Says what to do when brick collided with ball.
 */
public interface CollisionStrategy {
     void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter);


}
