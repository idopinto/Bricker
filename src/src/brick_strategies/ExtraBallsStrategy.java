package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.*;

public class ExtraBallsStrategy extends RemoveBrickStrategy{
    public ExtraBallsStrategy(GameObjectCollection gameObjects) {
        super(gameObjects);
    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter brickCounter) {
        super.onCollision(thisObj, otherObj, brickCounter);

    }


}
