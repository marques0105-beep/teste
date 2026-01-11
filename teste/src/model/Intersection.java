package model;

public class Intersection {

    private TrafficLight trafficLight;
    private double position; // posição na estrada

    public Intersection(TrafficLight light, double position) {
        this.trafficLight = light;
        this.position = position;
    }

    public void update(double dt) {
        trafficLight.update(dt);
    }

    public TrafficLight getTrafficLight() {
        return trafficLight;
    }

    public double getPosition() {
        return position;
    }
}