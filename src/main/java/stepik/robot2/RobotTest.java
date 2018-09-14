package stepik.robot2;

public class RobotTest {
    public static void main(String[] args) {
moveRobot(5,6);
    }

    public static void moveRobot( int toX, int toY) {
        boolean connect = false;
        try {
            for (int i = 0; i < 3; i++) {
                try {
                    System.out.println("try");
                    connect = true;
                    return;
                } catch (RobotConnectionException e) {
                    if (i == 2) throw e;
                }
            }
        } catch (RobotConnectionException e) {
            if (!connect) throw e;
        }


    }
}
