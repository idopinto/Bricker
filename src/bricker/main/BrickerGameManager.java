package bricker.main;

import bricker.gameobjects.Ball;
import bricker.gameobjects.UserPaddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class BrickerGameManager extends GameManager {
    private static final float BORDER_WIDTH = 10;
    private static final Color BORDER_COLOR = Color.BLACK;

    BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        Vector2 windowDimensions = windowController.getWindowDimensions();
        initializeBackground(windowDimensions,imageReader);
        createBall(imageReader,soundReader,windowDimensions);
        createPaddles(imageReader,windowDimensions);
        createBorders(windowDimensions);
    }

    private void createBall(ImageReader imageReader,SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(20, 20), ballImage,collisionSound);
        ball.setVelocity(Vector2.DOWN.mult(300));
        ball.setCenter(windowDimensions.mult(0.5f));
        this.gameObjects().addGameObject(ball);
    }

    private void createPaddles(ImageReader imageReader, Vector2 windowDimensions)
    {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);

        // create user paddle
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(100, 15), paddleImage);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2,(int)windowDimensions.y() - 30));
        this.gameObjects().addGameObject(userPaddle);

        // create AI paddle
        GameObject aiPaddle = new GameObject(Vector2.ZERO, new Vector2(100, 15), paddleImage);
        aiPaddle.setCenter(new Vector2(windowDimensions.x() / 2,30));
        this.gameObjects().addGameObject(aiPaddle);

    }

    private void initializeBackground(Vector2 windowDimensions,ImageReader imageReader){
        GameObject background = new GameObject(Vector2.ZERO,windowDimensions,imageReader.readImage("assets/DARK_BG2_small.jpeg",false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBorders(Vector2 windowDimensions){
        GameObject leftBorder = new GameObject(Vector2.ZERO,new Vector2(BORDER_WIDTH, windowDimensions.y()),new RectangleRenderable(BORDER_COLOR));
        GameObject rightBorder = new GameObject(new Vector2(windowDimensions.x()-BORDER_WIDTH,0),new Vector2(BORDER_WIDTH, windowDimensions.y()),new RectangleRenderable(BORDER_COLOR));
        GameObject topBorder = new GameObject(Vector2.ZERO,new Vector2(windowDimensions.x(),(int)BORDER_WIDTH),new RectangleRenderable(BORDER_COLOR));
        this.gameObjects().addGameObject(rightBorder);
        this.gameObjects().addGameObject(leftBorder);
        this.gameObjects().addGameObject(topBorder);

    }
    public static void main(String[] args) {
        Vector2 v = new Vector2(700, 500);
        BrickerGameManager a = new BrickerGameManager("Bricker", v);
        a.run();
    }
}
