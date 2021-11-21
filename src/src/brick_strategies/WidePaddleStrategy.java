package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Status;
import src.gameobjects.GameStatusTypes;

import java.util.Random;

public class WidePaddleStrategy extends RemoveBrickStrategyDecorator{
    private ImageReader imageReader;
    private Vector2 windowDimensions;

    /**
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    public WidePaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,Vector2 windowDimensions)
    {
        super(toBeDecorated);

        this.imageReader = imageReader;
        this.windowDimensions = windowDimensions;
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        super.onCollision(thisObj, otherObj, brickCounter);
        createWidenOrNarrowGameStatus(thisObj);
    }

    private void createWidenOrNarrowGameStatus(GameObject thisObj) {
        Random random = new Random();
        GameStatusTypes gameStatusType;
        Renderable statusImage;
        boolean r = random.nextBoolean();
        if (r){
            gameStatusType = GameStatusTypes.NARROW;
            statusImage = imageReader.readImage("assets/buffNarrow.png", true);
        }
        else{
            gameStatusType = GameStatusTypes.WIDE;
            statusImage = imageReader.readImage("assets/buffWiden.png", true);

        }

        // create buff widget
        Status status = new Status(Vector2.ZERO, new Vector2(30, 30), statusImage,getGameObjectCollection() ,gameStatusType,this.windowDimensions);
        status.setCenter(thisObj.getCenter());
        status.setVelocity(Vector2.DOWN.mult(300));
        this.getGameObjectCollection().addGameObject(status);
    }

}
