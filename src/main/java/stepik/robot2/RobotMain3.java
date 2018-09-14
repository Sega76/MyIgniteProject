package stepik.robot2;

public class RobotMain3 {
    public static void main(String[] args) {

    }

    public static void moveRobot(RobotConnectionManager robotConnectionManager, int toX, int toY) {

        boolean conn = false;
        try (RobotConnection robotConnection = robotConnectionManager.getConnection()){

            robotConnection.moveRobotTo(toX, toY);
            conn = true;
        } catch (RobotConnectionException e) {


        }

        if (!conn) {
            try (RobotConnection robotConnection = robotConnectionManager.getConnection();){
                robotConnection.moveRobotTo(toX, toY);
                conn = true;
            } catch (RobotConnectionException e) {


            }
        }

        if (!conn) {
            try (RobotConnection robotConnection = robotConnectionManager.getConnection()){
                robotConnection.moveRobotTo(toX, toY);
                conn = true;
            } catch (RobotConnectionException e) {
                throw e;


            }
        }
    }
}
