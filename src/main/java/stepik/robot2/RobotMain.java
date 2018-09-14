package stepik.robot2;

public class RobotMain {
    public static void main(String[] args) {

    }

    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {
        boolean connect = false;
        try{
            for (int i = 0; i < 3; i++) {
                try (RobotConnection robotConnection = robotConnectionManager.getConnection()) {
                    robotConnection.moveRobotTo(toX, toY);
                    connect=true;
                    return;
                } catch (RobotConnectionException e){
                if (i==2) throw e;
                }
            }
        } catch (RobotConnectionException e){
            if (!connect) throw e;
        }



    }
}
