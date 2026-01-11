package view;

import controller.Simulation;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Intersection;
import model.Vehicle;
import model.TrafficLight;
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

        drawBackground();
        drawRoad();
        drawCrosswalks();
        drawTrafficLights();
        drawVehicles();
        drawStats();
    }

    // =========================
    // 🌤️ FUNDO
    // =========================
    private void drawBackground() {

        // chão
        gc.setFill(Color.rgb(200, 220, 200));
        gc.fillRect(0, 170, 800, 230);
    }

    // =========================
    // 🛣️ ESTRADA
    // =========================
    private void drawRoad() {

        // fundo
        gc.setFill(Color.rgb(60, 120, 60));
        gc.fillRect(0, 0, 800, 400);

        // estrada vertical
        gc.setFill(Color.rgb(150, 150, 150));
        gc.fillRect(360, 0, 80, 400);

        // estrada horizontal
        gc.fillRect(0, 160, 800, 80);

        // faixas centrais
        gc.setFill(Color.WHITE);

        // vertical
        for (int y = 10; y < 400; y += 40) {
            gc.fillRect(398, y, 4, 20);
        }

        // horizontal
        for (int x = 10; x < 800; x += 40) {
            gc.fillRect(x, 198, 20, 4);
        }
    }

    /* ================= CROSSWALKS ================= */

    private void drawCrosswalks() {
        gc.setFill(Color.WHITE);

        // horizontal crosswalks
        for (int i = 0; i < 5; i++) {
            gc.fillRect(350 + i * 15, 155, 10, 30);
            gc.fillRect(350 + i * 15, 215, 10, 30);
        }

        // vertical crosswalks
        for (int i = 0; i < 5; i++) {
            gc.fillRect(355, 180 + i * 15, 30, 10);
            gc.fillRect(415, 180 + i * 15, 30, 10);
        }
    }

    // =========================
    // 🚗 VEÍCULOS
    // =========================
    private void drawVehicles() {

        long time = System.currentTimeMillis();
        boolean sirenOn = (time / 300) % 2 == 0; // pisca a cada 300ms

        for (Vehicle v : simulation.getRoad().getVehicles()) {

            double x = v.getX();
            double y = v.getY();

            switch (v.getDirection()) {
                case EAST, WEST -> y = 192;
                case NORTH -> x = 398;
                case SOUTH -> x = 378;
            }



            // sombra
            gc.setFill(Color.rgb(0, 0, 0, 0.25));
            gc.fillRoundRect(x + 3, y + 4, 28, 18, 8, 8);

            // carro base
            if (v instanceof EmergencyVehicle) {
                gc.setFill(Color.DARKRED);
            } else {
                gc.setFill(Color.DODGERBLUE);
            }
            gc.fillRoundRect(x, y, 28, 18, 8, 8);

            // vidro
            gc.setFill(Color.rgb(200, 230, 255));
            gc.fillRoundRect(x + 5, y + 3, 12, 6, 4, 4);

            // sirene 🚨
            if (v instanceof EmergencyVehicle) {
                if (sirenOn) {
                    gc.setFill(Color.RED);
                } else {
                    gc.setFill(Color.BLUE);
                }
                gc.fillRect(x + 10, y - 4, 8, 4);
            }
        }
    }

    // =========================
    // 🚦 SEMÁFOROS
    // =========================
    private void drawTrafficLights() {

        for (Intersection i : simulation.getIntersections()) {

            double x = i.getPosition();
            double y = 90;

            // poste
            gc.setFill(Color.DARKGRAY);
            gc.fillRect(x + 12, y + 45, 6, 45);

            // caixa
            gc.setFill(Color.rgb(50, 50, 50));
            gc.fillRoundRect(x, y, 30, 45, 8, 8);

            // luz
            String state = i.getTrafficLight().getStateName();
            Color color;

            switch (state) {
                case "GREEN" -> color = Color.LIMEGREEN;
                case "YELLOW" -> color = Color.GOLD;
                default -> color = Color.RED;
            }

            gc.setFill(color);
            gc.fillOval(x + 7, y + 8, 16, 16);
        }
    }

    // =========================
    // 📊 HUD
    // =========================
    private void drawStats() {
        gc.setFill(Color.BLACK);
        gc.fillText(
                "Waiting cars: " + simulation.countWaitingCars(),
                10, 20
        );
    }
}
