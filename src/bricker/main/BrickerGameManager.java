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
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int WINDOW_WIDTH= 700;
    private static final int WINDOW_HEIGHT = 500;
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 300;
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
        createPaddles(imageReader,inputListener,windowDimensions);
        createBorders(windowDimensions);
    }

    private void createBall(ImageReader imageReader,SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage,collisionSound);
        ball.setVelocity(reflectVelocityVectorRandomly());
        ball.setCenter(windowDimensions.mult(0.5f));
        this.gameObjects().addGameObject(ball);
    }

    private Vector2 reflectVelocityVectorRandomly()
    {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean()){
            ballVelX *= -1;
        }
        else {
            ballVelY *= -1;
        }
        return new Vector2(ballVelX,ballVelY);
    }

    private void createPaddles(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions)
    {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png",true);

        // create user paddle
        GameObject userPaddle = new UserPaddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,inputListener,windowDimensions.x());
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2,(int)windowDimensions.y() - 30));
        this.gameObjects().addGameObject(userPaddle);

        // create AI paddle
        GameObject aiPaddle = new GameObject(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage);
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
        Vector2 v = new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT);
        BrickerGameManager a = new BrickerGameManager("Bricker", v);
        a.run();
    }
}
