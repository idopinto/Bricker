package src;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import src.gameobjects.*;

import java.awt.*;
import java.util.Random;

/**
 * Game manager - this class is responsible for game initialization,
 * holding references for game objects and calling update methods for every update iteration.
 * Entry point for code should be in a main method in this class.
 */
public class BrickerGameManager extends GameManager {

    public static final int BORDER_WIDTH = 20; // from the API

    private static final int MIN_DISTANCE_FROM_SCREEN_EDGE = 20;
    private static final int PADDLE_HEIGHT = 15;
    private static final int PADDLE_WIDTH = 100;
    private static final int BRICK_HEIGHT = 15;
    private static final int ROWS = 4;
    private static final int COLS = 4;
    private static final int DIFFERENCE = 5;
    private static final int BALL_RADIUS = 20;
    private static final float BALL_SPEED = 350;
    private static final Color BORDER_COLOR = Color.BLACK;
    private static final int NUM_OF_LIVES = 4;
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 500;

    private Vector2 windowDimensions;
    private GameObject ball;
    private WindowController windowController;
    private Counter brickCounter;
    private Counter lifeCounter;
    private static final int HEART_SIZE = 25;

    BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }


    /**
     * Calling this function should initialize the game window. It should initialize objects in the game window - ball, paddle, walls, life counters, bricks.
     * This version of the game has 5 rows, 8 columns of bricks.
     *
     * @param imageReader      - an ImageReader instance for reading images from files for rendering of objects.
     * @param soundReader      - a SoundReader instance for reading sound clips from files for rendering event sounds.
     * @param inputListener    - an InputListener instance for reading user input.
     * @param windowController - controls visual rendering of the game window and object renderables.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;
        this.windowController.setTimeScale(0.5f);
        this.windowDimensions = windowController.getWindowDimensions();

        initializeBackground(windowDimensions, imageReader);
        createLifeCounters(imageReader);
        createBricks(imageReader,soundReader,inputListener, windowDimensions);

        createBall(imageReader, soundReader, windowDimensions);
        createPaddle(imageReader, inputListener, windowDimensions);
        createBorders(windowDimensions);
    }


    /**
     * Code in this function is run every frame update.
     *
     * @param deltaTime -  time between updates. For internal use by game engine. You do not need to call this method yourself.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = ball.getCenter().y();
        String prompt = "";
        if (ballHeight > windowDimensions.y()) {
            this.lifeCounter.decrement();
            if (this.lifeCounter.value() > 0) {
                this.ball.setVelocity(reflectVelocityVectorRandomly());
                this.ball.setCenter(windowDimensions.mult(0.5f));
            } else if (this.lifeCounter.value() == 0) {
                prompt = "You Lose!";
            }

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


    /*  This method */
    private void initializeBackground(Vector2 windowDimensions, ImageReader imageReader) {
        GameObject background = new GameObject(Vector2.ZERO, windowDimensions, imageReader.readImage("assets/DARK_BG2_small.jpeg", false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    /*  This method */
    private void createBorders(Vector2 windowDimensions) {
        GameObject leftBorder = new GameObject(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()), new RectangleRenderable(BORDER_COLOR));
        GameObject rightBorder = new GameObject(new Vector2(windowDimensions.x() - BORDER_WIDTH, 0), new Vector2(BORDER_WIDTH, windowDimensions.y()), new RectangleRenderable(BORDER_COLOR));
        GameObject topBorder = new GameObject(Vector2.ZERO, new Vector2(windowDimensions.x(), (float) BORDER_WIDTH), new RectangleRenderable(BORDER_COLOR));
        this.gameObjects().addGameObject(rightBorder);
        this.gameObjects().addGameObject(leftBorder);
        this.gameObjects().addGameObject(topBorder);

    }

    /*  This method */
    private void createBall(ImageReader imageReader, SoundReader soundReader, Vector2 windowDimensions) {
        Renderable ballImage = imageReader.readImage("assets/ball.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_RADIUS, BALL_RADIUS), ballImage, collisionSound);
        this.ball.setVelocity(reflectVelocityVectorRandomly());
        this.ball.setCenter(windowDimensions.mult(0.5f));
        this.gameObjects().addGameObject(ball);
    }

    /*  This method */
    private void createBricks(ImageReader imageReader,SoundReader soundReader,UserInputListener inputListener,Vector2 windowDimensions) {
        Renderable brickImage = imageReader.readImage("assets/brick.png", false);

        BrickStrategyFactory brickStrategyFactory = new BrickStrategyFactory(gameObjects(),this,imageReader,
                                                        soundReader,inputListener,windowController, windowDimensions);

        float brickWidth = (windowDimensions.x() - 2 * (BORDER_WIDTH + DIFFERENCE) - COLS * (DIFFERENCE)) / COLS;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                CollisionStrategy collisionStrategy = brickStrategyFactory.getStrategy();
                Vector2 brickPosition = new Vector2(BORDER_WIDTH + DIFFERENCE + j * (brickWidth + DIFFERENCE), BORDER_WIDTH + DIFFERENCE + i * (BRICK_HEIGHT + DIFFERENCE));
                Vector2 brickDimensions = new Vector2(brickWidth, BRICK_HEIGHT);
                GameObject brick = new Brick(brickPosition, brickDimensions, brickImage, collisionStrategy, this.brickCounter);
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
                this.brickCounter.increment();
            }
        }
    }

    /*  This method */
    private void createPaddle(ImageReader imageReader, UserInputListener inputListener, Vector2 windowDimensions) {
        Renderable paddleImage = imageReader.readImage("assets/paddle.png", true);

        // create user paddle
        GameObject userPaddle = new Paddle(Vector2.ZERO, new Vector2(PADDLE_WIDTH, PADDLE_HEIGHT), paddleImage,
                                            inputListener, windowDimensions, MIN_DISTANCE_FROM_SCREEN_EDGE);
        userPaddle.setCenter(new Vector2(windowDimensions.x() / 2, (int) windowDimensions.y() - 30));
        this.gameObjects().addGameObject(userPaddle);

    }

    /*  This method */
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

    private NumericLifeCounter getNumericLifeCounter() {

        Vector2 textPosition = new Vector2(BORDER_WIDTH + DIFFERENCE, windowDimensions.y() - 4 * BORDER_WIDTH);
        Vector2 textDimensions = new Vector2(HEART_SIZE, HEART_SIZE);
        return new NumericLifeCounter(this.lifeCounter, textPosition, textDimensions, gameObjects());
    }

    private GraphicLifeCounter getGraphicLifeCounter(ImageReader imageReader) {
        Renderable heartImage = imageReader.readImage("assets/heart.png", true);
        Vector2 widgetStartPosition = new Vector2(BORDER_WIDTH + DIFFERENCE, windowDimensions.y() - 2 * BORDER_WIDTH);
        Vector2 widgetDimensions = new Vector2(HEART_SIZE, HEART_SIZE);
        return new GraphicLifeCounter(widgetStartPosition, widgetDimensions, this.lifeCounter, heartImage, gameObjects(), NUM_OF_LIVES);
    }

    private void createLifeCounters(ImageReader imageReader) {
        this.brickCounter = new Counter();
        this.lifeCounter = new Counter(NUM_OF_LIVES);
        NumericLifeCounter numericLifeCounter = getNumericLifeCounter();
        gameObjects().addGameObject(numericLifeCounter, Layer.BACKGROUND);
        GraphicLifeCounter graphicLifeCounter = getGraphicLifeCounter(imageReader);
        gameObjects().addGameObject(graphicLifeCounter, Layer.BACKGROUND);
    }

    /* Entry point for game.
        Should contain:
        1. An instantiation call to BrickerGameManager constructor.
        2. A call to run() method of instance of BrickerGameManager.
        Should initialize game window of dimensions (x,y) = (700,500).
     */
    public static void main(String[] args) {
        BrickerGameManager a = new BrickerGameManager("Bricker", new Vector2(WINDOW_WIDTH, WINDOW_HEIGHT));
        a.run();
    }
}
