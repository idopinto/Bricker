package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter extends GameObject {
    private static final TextRenderable textRenderable = new TextRenderable("");
    private GameObjectCollection gameObjectCollection;
    private Counter livesCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjectCollection  global game object collection.
     */
    public NumericLifeCounter(Counter livesCounter, Vector2 topLeftCorner, Vector2 dimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, textRenderable);
        this.livesCounter = livesCounter;
        textRenderable.setString(String.format("%d of lives left",livesCounter.value()));
        textRenderable.setColor(Color.BLACK);
        this.gameObjectCollection = gameObjectCollection;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(livesCounter.value() > 0) {
            String textString = String.format("%d of lives left",this.livesCounter.value());
            textRenderable.setString(textString);
        }
    }
}
