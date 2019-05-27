package test;

import com.sun.javafx.scene.traversal.Direction;
import main.model.Object;
import main.model.Snake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SnakeTest {
    private Snake snake;
    private java.util.List<Object> body;

    @BeforeEach
    void setup() {
        snake = new Snake(100, 100);
        body = snake.getBody();
    }

    @Test
    void testConstructor() {
        for (int i = 0; i < body.size(); i++) {
            assertEquals(100, body.get(i).getPosX());
            assertEquals(100 + i* Snake.HEIGHT, body.get(i).getPosY());
        }
    }

    @Test
    void testMoveUp() {
        snake.move();
        body = snake.getBody();
        for (int i = 0; i < body.size(); i++) {
            assertEquals(100, body.get(i).getPosX());
            assertEquals((100 - Snake.HEIGHT) + i* Snake.HEIGHT, body.get(i).getPosY());
        }
    }

    @Test
    void testMoveRight() {
        snake.directRight();
        snake.move();
        body = snake.getBody();
        assertEquals(100 + Snake.WIDTH, body.get(0).getPosX());
        assertEquals(100, body.get(0).getPosY());
        for (int i = 1; i < body.size(); i++) {
            assertEquals(100, body.get(i).getPosX());
            assertEquals((100 - Snake.HEIGHT) + i* Snake.HEIGHT, body.get(i).getPosY());
        }
    }

    @Test
    void testMoveLeft() {
        snake.directLeft();
        snake.move();
        body = snake.getBody();
        assertEquals(100 - Snake.WIDTH, body.get(0).getPosX());
        assertEquals(100, body.get(0).getPosY());
        for (int i = 1; i < body.size(); i++) {
            assertEquals(100, body.get(i).getPosX());
            assertEquals((100 - Snake.HEIGHT) + i* Snake.HEIGHT, body.get(i).getPosY());
        }
    }

    @Test
    void testMoveDown() {
        snake.directRight();
        snake.move();
        snake.directDown();
        snake.move();
        body = snake.getBody();
        assertEquals(100 + Snake.WIDTH, body.get(0).getPosX());
        assertEquals(100 + Snake.HEIGHT, body.get(0).getPosY());
        assertEquals(100 + Snake.WIDTH, body.get(1).getPosX());
        assertEquals(100, body.get(1).getPosY());
        for (int i = 2; i < body.size(); i++) {
            assertEquals(100, body.get(i).getPosX());
            assertEquals((100 - 2* Snake.HEIGHT) + i* Snake.HEIGHT, body.get(i).getPosY());
        }
    }

    @Test
    void testGrow() {
        assertEquals(4, body.size());
        snake.grow();
        body = snake.getBody();

        assertEquals(5, body.size());
        assertEquals(snake.getBody().get(body.size() - 2).getPosX(), snake.getBody().get(body.size() - 1).getPosX());
        assertEquals(snake.getBody().get(body.size() - 2).getPosY(), snake.getBody().get(body.size() - 1).getPosY());
        assertEquals(snake.getBody().get(body.size() - 2).getDirection(), snake.getBody().get(body.size() - 1).getDirection());
    }

    @Test
    void directUpValid() {
        snake.getHead().setDirection(Direction.LEFT);
        snake.directUp();
        assertEquals(Direction.UP, snake.getHead().getDirection());

        snake.getHead().setDirection(Direction.RIGHT);
        snake.directUp();
        assertEquals(Direction.UP, snake.getHead().getDirection());
    }

    @Test
    void directUpInvalid() {
        snake.getHead().setDirection(Direction.DOWN);
        snake.directUp();
        assertEquals(Direction.DOWN, snake.getHead().getDirection());

        body.get(1).setDirection(Direction.DOWN);
        snake.directUp();
        assertEquals(Direction.DOWN, snake.getHead().getDirection());
    }

    @Test
    void directDownValid() {
        snake.getHead().setDirection(Direction.LEFT);
        snake.getBody().get(1).setDirection(Direction.LEFT);
        snake.directDown();
        assertEquals(Direction.DOWN, snake.getHead().getDirection());

        snake.getHead().setDirection(Direction.RIGHT);
        snake.directDown();
        assertEquals(Direction.DOWN, snake.getHead().getDirection());
    }

    @Test
    void directDownInvalid() {
        snake.getHead().setDirection(Direction.UP);
        snake.directDown();
        assertEquals(Direction.UP, snake.getHead().getDirection());

        body.get(1).setDirection(Direction.UP);
        snake.directDown();
        assertEquals(Direction.UP, snake.getHead().getDirection());
    }

    @Test
    void directLeftValid() {
        snake.getHead().setDirection(Direction.UP);
        snake.directLeft();
        assertEquals(Direction.LEFT, snake.getHead().getDirection());

        snake.getHead().setDirection(Direction.DOWN);
        snake.directLeft();
        assertEquals(Direction.LEFT, snake.getHead().getDirection());
    }

    @Test
    void directLeftInvalid() {
        snake.getHead().setDirection(Direction.RIGHT);
        snake.getBody().get(1).setDirection(Direction.RIGHT);
        snake.directLeft();
        assertEquals(Direction.RIGHT, snake.getHead().getDirection());
    }

    @Test
    void directRightValid() {
        snake.getHead().setDirection(Direction.UP);
        snake.getBody().get(1).setDirection(Direction.UP);
        snake.directRight();
        assertEquals(Direction.RIGHT, snake.getHead().getDirection());

        snake.getHead().setDirection(Direction.DOWN);
        snake.directRight();
        assertEquals(Direction.RIGHT, snake.getHead().getDirection());
    }

    @Test
    void directRightInvalid() {
        snake.getHead().setDirection(Direction.LEFT);
        body.get(1).setDirection(Direction.LEFT);
        snake.directRight();
        assertEquals(Direction.LEFT, snake.getHead().getDirection());
    }

    @Test
    void testIsSelfCollided() {
        assertFalse(snake.isSelfCollided());

        snake.grow(); // need 5 body parts to self collide
        snake.directRight();
        snake.move();
        snake.directDown();
        snake.move();
        snake.directLeft();
        snake.move();

        assertTrue(snake.isSelfCollided());
    }

    @Test
    void testContainsCoordinate() {
        assertTrue(snake.containsCoordinate(100,100));
        assertTrue(snake.containsCoordinate(100,100 + 2* Snake.HEIGHT));
        assertFalse(snake.containsCoordinate(90,100));
        assertFalse(snake.containsCoordinate(100,500));
    }

}
