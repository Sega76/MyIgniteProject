package stepik;

import stepik.Direction;

public class Robot {
    int x=0;
    int y=0;
    Direction direction = Direction.UP;

    public Direction getDirection() {

        return direction;
    }
    // текущее направление взгляда


    public int getX() {
        return x;
        // текущая координата X
    }

    @Override
    public String toString() {
        return "stepik.Robot{" +
                "x=" + x +
                ", y=" + y +
                ", direction=" + direction +
                '}';
    }

    public int getY() {
        return y;
        // текущая координата Y
    }

    public void turnLeft() {
        System.out.println("stepik.Robot"+getDirection());
        switch (getDirection()) {
            default:
            case DOWN:
                direction = Direction.RIGHT;
                break;
            case UP:
                direction = Direction.LEFT;
                break;
            case RIGHT:
                direction = Direction.UP;
                break;
            case LEFT:
                direction = Direction.DOWN;
        }
    }

    public void turnRight() {
        System.out.println("stepik.Robot"+getDirection());
        switch (getDirection()) {
            default:
            case DOWN:
                direction = Direction.LEFT;
                break;
            case UP:
                direction = Direction.RIGHT;
                break;
            case RIGHT:
                direction = Direction.DOWN;
                break;
            case LEFT:
                direction = Direction.UP;
        }
    }

    public void stepForward() {
        System.out.println("stepik.Robot"+getDirection());
        switch (getDirection()) {
            default:
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
        }
    }
}