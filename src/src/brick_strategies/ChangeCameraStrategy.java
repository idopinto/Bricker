package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator.
 * Changes camera focus from ground to ball until ball collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator {
    private static final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;
    private WindowController windowController;
    private BrickerGameManager gameManager;

    /**
     * @param toBeDecorated    - Collision strategy object to be decorated.
     * @param windowController
     * @param gameManager
     */

    ChangeCameraStrategy(CollisionStrategy toBeDecorated, WindowController windowController, BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;}

    /**
     * Change camere position on collision and delegate to held CollisionStrategy.
     * @param thisObj
     * @param otherObj
     * @param brickCounter  global brick counter
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        super.onCollision(thisObj, otherObj, brickCounter);
        if ((gameManager.getCamera() == null) && (!(otherObj instanceof Puck))){
            turnOnCameraChange((Ball) otherObj); // TODO downcasting from GameObject to Ball
        }
    }

    private void turnOnCameraChange(Ball ball)
    {
        Camera camera =  new Camera(ball, Vector2.ZERO,
                                    windowController.getWindowDimensions().mult(1.2f),
                                    windowController.getWindowDimensions());
        gameManager.setCamera(camera);
        BallCollisionCountdownAgent countDownAgent = new BallCollisionCountdownAgent(ball,
                                                    this,
                                            ball.getCollisionCount() + NUM_BALL_COLLISIONS_TO_TURN_OFF);
        this.getGameObjectCollection().addGameObject(countDownAgent);

    }
    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange()
    {
        if (gameManager.getCamera() != null){
            gameManager.setCamera(null);
        }
    }
}
