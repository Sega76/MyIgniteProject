package stepik.robot2;

public class RobotMain2 {
    public static void main(String[] args) {

    }

    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
        boolean connect = false;
        RobotConnection robotConnection = null;
        for (int i = 0; i < 3; i++) {
            try {
                robotConnection = robotConnectionManager.getConnection();
                robotConnection.moveRobotTo(toX, toY);
                connect = true;
            } catch (RobotConnectionException e) {
                if (i == 2) throw e;
            } finally {
                if (robotConnection != null) {
                    try {
                        robotConnection.close();
                    } catch (RuntimeException e) {
                        //
                    }
                }
            }
        }
    }
}
