package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import java.sql.Array;

/**
 * Display a graphic object on the game window showing as many widgets as lives left.
 */
public class GraphicLifeCounter extends GameObject {
    private  Counter livesCounter;
    private  GameObjectCollection gameObjectCollection;

    private int numOfLives;
    private GameObject[] livesArray;
    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param widgetDimensions    Width and height in window coordinates.
     * @param livesCounter global lives counter of game.
     * @param renderable   image to use for widgets.
     * @param gameObjectCollection - global game object collection managed by game manage.
     * @param numOfLives - global setting of number of lives a player will have in a game.
     */
    public GraphicLifeCounter(Vector2 topLeftCorner, Vector2 widgetDimensions,Counter livesCounter, Renderable renderable, GameObjectCollection gameObjectCollection,int numOfLives) {
        super(topLeftCorner, widgetDimensions, renderable);
        this.livesCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.numOfLives = numOfLives;
        this.livesArray = new GameObject[numOfLives];
        createHearts();
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(numOfLives > livesCounter.value()) {
            this.numOfLives--;
            this.gameObjectCollection.removeGameObject(this.livesArray[this.numOfLives], Layer.BACKGROUND);
        }
    }

    private void createHearts(){
        for (int i = 0; i < this.livesArray.length; i++) {
            Vector2 currentHeartVector = this.getTopLeftCorner().add(new Vector2(i * 50,0));
            GameObject heart = new GameObject(currentHeartVector,this.getDimensions(),this.renderer().getRenderable());
            this.livesArray[i] = heart;
            this.gameObjectCollection.addGameObject(heart, Layer.BACKGROUND);
        }
    }
}
