import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Snake extends Application
{
	 private static  int WIDTH = 800;
	 private static  int HEIGHT = WIDTH;
	 private static final int ROWS = 20;
	 private static final int COLUMNS = ROWS;
	 private static final int SQUARE_SIZE = WIDTH / ROWS;
	 private static final String[] FOODS_IMAGE = new String[] {"ic_apple.png",
			 												   "ic_berry.png",
			 												   "ic_cherry.png",
			 												   "ic_coconut_.png",
			 												   "ic_orange.png",
			 												   "ic_peach.png",
			 												   "ic_pomegranate.png",
			 												   "ic_tomato.png",
			 												   "ic_watermelon.png"};
	 
	 private List<Corner> snakeBody = new ArrayList();
	 private Corner snakeHead;
	 private Image foodImage;
	 private int foodX;
	 private int foodY;
	 private static final int RIGHT = 0;
	 private static final int LEFT = 1;
	 private static final int UP = 2;
	 private static final int DOWN = 3;
	 private boolean gameOver;
	 private int currentDirection;
	 private int score = 0;
	 
	 private GraphicsContext gc;
	 
	 
	 
	 
	 
	 
	public static void main(String[] args) 
	{
		
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
	
		Group root = new Group();
		Canvas canvas = new Canvas(WIDTH, HEIGHT);
		root.getChildren().add(canvas);
		 Scene scene = new Scene(root);
		 primaryStage.setScene(scene);
		 primaryStage.setTitle("Snake Game");
		 primaryStage.setResizable(false);
		 primaryStage.show();
		 gc = canvas.getGraphicsContext2D();
		
		 scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
		 {
	            @Override
	            public void handle(KeyEvent event) 
	            {
	                KeyCode code = event.getCode();
	                if (code == KeyCode.RIGHT || code == KeyCode.D) 
	                {
	                    if (currentDirection != LEFT) 
	                    {
	                        currentDirection = RIGHT;
	                    }
	                }
	                else if (code == KeyCode.LEFT || code == KeyCode.A) 
	                {
	                    if (currentDirection != RIGHT) 
	                    {
	                        currentDirection = LEFT;
	                    }
	                } 
	                else if (code == KeyCode.UP || code == KeyCode.W)
	                {
	                    if (currentDirection != DOWN) 
	                    {
	                        currentDirection = UP;
	                    }
	                } 
	                else if (code == KeyCode.DOWN || code == KeyCode.S) 
	                {
	                    if (currentDirection != UP) 
	                    {
	                        currentDirection = DOWN;
	                    }
	                }
	            }
	        });
		 
		 
		 for (int i = 0; i < 3; i++) 
		 {
	            snakeBody.add(new Corner(5, ROWS / 2));
	     }
		 snakeHead = snakeBody.get(0);
		 
		 generateFood() ;
		 Timeline timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> run(gc)));
	     timeline.setCycleCount(Animation.INDEFINITE);
	     timeline.play();
		 
		
		 
	}
	
	private void run(GraphicsContext gc)
	{
		if (gameOver) 
		{
            gc.setFill(Color.RED);
            gc.setFont(new Font("Digital-7", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            return;
        }
				
		
		drawBackground(gc);
		drawFood(gc);
		drawSnake(gc);
		drawScore();
		eatFood();

        for (int i = snakeBody.size() - 1; i >= 1; i--) 
        {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }
		
        switch (currentDirection) 
        {
        case RIGHT:
            moveRight();
            break;
        case LEFT:
            moveLeft();
            break;
        case UP:
            moveUp();
            break;
        case DOWN:
            moveDown();
            break;
    }
gameOver();

		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	private void drawBackground(GraphicsContext gc) 
    {
        for (int i = 0; i < ROWS; i++) 
        {
            for (int j = 0; j < COLUMNS; j++) 
            {
                if ((i + j) % 2 == 0) 
                {
                    gc.setFill(Color.web("AAD751"));
                } 
                else 
                {
                    gc.setFill(Color.web("A2D149"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }
	
	private void generateFood() 
    {
        start:
        while (true) 
        {
            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLUMNS);

            for (Corner snake : snakeBody) 
            {
                if (snake.getX() == foodX && snake.getY() == foodY) 
                {
                    continue start;
                }
            }
            foodImage = new Image(FOODS_IMAGE[(int) (Math.random() * FOODS_IMAGE.length)]);
            break;
        }
    }	
	
	private void drawFood(GraphicsContext gc) 
    {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }
		
	
	private void drawSnake(GraphicsContext gc) 
	{
	        gc.setFill(Color.web("4674E9"));
	        gc.fillRoundRect(snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

	        for (int i = 1; i < snakeBody.size(); i++) 
	        {
	            gc.fillRoundRect(snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
	                    SQUARE_SIZE - 1, 20, 20);
	        }
	}
	
	
	private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() 
    {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        //destroy itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }
    
    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Corner(-1, -1));
            generateFood();
            score += 5;
        }
    }
    
    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Digital-7", 35));
        gc.fillText("Score: " + score, 10, 35);
    }
    
    
static class Corner
{
	int x; 
	int y;
	
	public Corner(int x, int y) 
	{
		this.x = x;
		this.y = y;
	}	
	
	public int getX() 
    {
        return x;
    }
	
	public int getY() 
    {
        return y;
    }
			
	
				
}
	
	
	
	
	

}
