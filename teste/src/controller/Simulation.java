package controller;

import model.*;
import util.Metrics;
import model.EmergencyVehicle;


import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private Road road;
    private List<Intersection> intersections;
    private AdaptiveCycle adaptiveStrategy;
    private Metrics metrics;

    public Simulation() {

        adaptiveStrategy = new AdaptiveCycle();
        metrics = new Metrics();

        road = new Road(800);
        intersections = new ArrayList<>();

        intersections.add(new Intersection(
                new TrafficLight(adaptiveStrategy), 300
        ));
        intersections.add(new Intersection(
                new TrafficLight(adaptiveStrategy), 600
        ));

        road.addVehicle(new Vehicle(50));
        road.addVehicle(new EmergencyVehicle(0));

    }

    public void update(double dt) {

        adaptiveStrategy.setWaitingCars(countWaitingCars());

        for (Intersection i : intersections) {
            i.update(dt);
        }

        for (Vehicle v : road.getVehicles()) {

            // Veículo de emergência: nunca para
            if (v instanceof EmergencyVehicle) {
                v.go();
                continue;
            }

            boolean mustStop = false;

            for (Intersection i : intersections) {
                boolean nearIntersection =
                        v.getPosition() >= i.getPosition() - 40 &&
                                v.getPosition() <= i.getPosition();

                if (!i.getTrafficLight().isGreen() && nearIntersection) {
                    mustStop = true;
                }
            }

            if (mustStop) v.stop();
            else v.go();
        }

        road.update(dt);
    }


    public int countWaitingCars() {
        int count = 0;
        for (Vehicle v : road.getVehicles()) {
            for (Intersection i : intersections) {
                if (v.getPosition() >= i.getPosition() - 40 &&
                        v.getPosition() <= i.getPosition()) {
                    count++;
                }
            }
        }
        return count;
    }

    // 🔄 RESET
    public void reset() {

        road = new Road(800);
        intersections.clear();

        intersections.add(new Intersection(
                new TrafficLight(adaptiveStrategy), 300
        ));
        intersections.add(new Intersection(
                new TrafficLight(adaptiveStrategy), 600
        ));

        road.addVehicle(new Vehicle(50));
        road.addVehicle(new EmergencyVehicle(0));
    }


    // ===== GETTERS =====
    public Road getRoad() {
        return road;
    }

    public List<Intersection> getIntersections() {
        return intersections;
    }

    public Metrics getMetrics() {
        return metrics;
    }
}
