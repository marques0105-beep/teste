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

        drawBackground();
        drawRoad();
        drawTrafficLights();
        drawVehicles();
        drawStats();
    }

    // =========================
    // 🌤️ FUNDO
    // =========================
    private void drawBackground() {
        // céu
        gc.setFill(Color.rgb(220, 235, 250));
        gc.fillRect(0, 0, 800, 170);

        // chão
        gc.setFill(Color.rgb(200, 220, 200));
        gc.fillRect(0, 170, 800, 230);
    }

    // =========================
    // 🛣️ ESTRADA
    // =========================
    private void drawRoad() {

        gc.setFill(Color.rgb(160, 160, 160));
        gc.fillRoundRect(0, 180, 800, 60, 30, 30);

        gc.setStroke(Color.rgb(110, 110, 110));
        gc.strokeRoundRect(0, 180, 800, 60, 30, 30);

        // faixa central
        gc.setFill(Color.WHITE);
        for (int x = 20; x < 800; x += 40) {
            gc.fillRect(x, 208, 20, 4);
        }
    }

    // =========================
    // 🚗 VEÍCULOS
    // =========================
    private void drawVehicles() {

        long time = System.currentTimeMillis();
        boolean sirenOn = (time / 300) % 2 == 0; // pisca a cada 300ms

        for (Vehicle v : simulation.getRoad().getVehicles()) {

            double x = v.getPosition();
            double y = 192;

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
