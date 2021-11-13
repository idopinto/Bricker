package bricker.main;

import bricker.brick_strategies.CollisionStrategy;
import bricker.gameobjects.Ball;
import bricker.gameobjects.Brick;
import bricker.gameobjects.Paddle;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import java.awt.*;
import java.util.Random;

public class BrickerGameManager extends GameManager {
    private static final int WINDOW_WIDTH = 900;
    private static final int WINDOW_HEIGHT = 700;
    private static final int BORDER_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BRICK_HEIGHT = 15;
    private static final int ROWS = 3;
    private static final int COLS = 3;
    private static final int BORDER_BRICK_DIFF = 5;
    private static final int BRICK_BRICK_DIFF = 5;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 500;
    private static final Color BORDER_COLOR = Color.BLACK;
    private Vector2 windowDimensions;
    private GameObject ball;
    private WindowController windowController;
    private Counter brickCounter;

    BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.brickCounter = new Counter();
        windowDimensions = windowController.getWindowDimensions();

        initializeBackground(windowDimensions, imageReader);

        createBricks(imageReader, windowDimensions);

        createBall(imageReader, soundReader, windowDimensions);
        createPaddle(imageReader, inputListener, windowDimensions);
        createBorders(windowDimensions);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowDimensions.y()) {
            prompt = "You Lose!";
        } else if (this.brickCounter.value() == 0) {
            prompt = "You Win!";
        }
        if (!prompt.isEmpty()) {
            prompt += " Do you want to play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        }
    }
    private void createBricks(ImageReader imageReader, Vector2 windowDimensions) {
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);
        CollisionStrategy collisionStrategy = new CollisionStrategy(gameObjects(), this.brickCounter);

        float brickWidth = (windowDimensions.x() - 2 * (BORDER_WIDTH + BORDER_BRICK_DIFF) - COLS * (BRICK_BRICK_DIFF)) / COLS;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                Vector2 brickPosition = new Vector2(BORDER_WIDTH + BORDER_BRICK_DIFF + j * (brickWidth + BRICK_BRICK_DIFF), BORDER_WIDTH + BORDER_BRICK_DIFF + i * (BRICK_HEIGHT + BRICK_BRICK_DIFF));
                Vector2 brickDimensions = new Vector2(brickWidth, BRICK_HEIGHT);
                GameObject brick = new Brick(brickPosition, brickDimensions, brickImage, collisionStrategy);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                this.brickCounter.increment();
            }
        }
    }

    private void createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        ball.setVelocity(reflectVelocityVectorRandomly());
        ball.setCenter(windowDimensions.mult(0.5f));
        this.gameObjects().addGameObject(ball);
    }

    private Vector2 reflectVelocityVectorRandomly() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelX *= -1;
        } else {
            ballVelY *= -1;
        }
        return new Vector2(ballVelX, ballVelY);
    }

    private void createPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);

        // create user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage, inputListener, windowDimensions.x());
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
        this.gameObjects().addGameObject(userPaddle);

    }

    private void initializeBackground(Vector2 windowDimensions, ImageReader imageReader) {
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void createBorders(Vector2 windowDimensions) {
        GameObject leftBorder = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()), new RectangleRenderable(BORDER_COLOR));
        GameObject rightBorder = new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0), new Vector2(BORDER_WIDTH, windowDimensions.y()), new RectangleRenderable(BORDER_COLOR));
        GameObject topBorder = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH), new RectangleRenderable(BORDER_COLOR));
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
