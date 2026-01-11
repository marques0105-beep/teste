package view;

import controller.Simulation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Intersection;
import model.Vehicle;
import model.EmergencyVehicle;

public class CanvasView extends Canvas {

    private Simulation simulation;
    private GraphicsContext gc;

    public CanvasView(Simulation simulation) {
        super(800, 400);
        this.simulation = simulation;
        this.gc = getGraphicsContext2D();
    }

    public void render() {
        gc.clearRect(0, 0, getWidth(), getHeight());

        drawRoad();
        drawVehicles();
        drawTrafficLights();
        drawStats();
    }


    private void drawRoad() {
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 180, 800, 40);
    }

    private void drawVehicles() {

        for (Vehicle v : simulation.getRoad().getVehicles()) {

            if (v instanceof EmergencyVehicle) {
                gc.setFill(Color.RED);
            } else {
                gc.setFill(Color.BLUE);
            }

            gc.fillRect(v.getPosition(), 185, 20, 30);
        }
    }


    private void drawTrafficLights() {

        for (Intersection i : simulation.getIntersections()) {

            String state = i.getTrafficLight().getStateName();
            Color color;

            switch (state) {
                case "GREEN" -> color = Color.GREEN;
                case "YELLOW" -> color = Color.YELLOW;
                default -> color = Color.RED;
            }

            gc.setFill(color);
            gc.fillOval(i.getPosition(), 120, 30, 30);

            gc.setFill(Color.BLACK);
            gc.fillText(state, i.getPosition(), 110);
        }
    }


    private void drawStats() {
        gc.setFill(Color.BLACK);
        gc.fillText(
                "Waiting cars: " + simulation.countWaitingCars(),
                10, 20
        );
    }
}