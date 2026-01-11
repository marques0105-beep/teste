package model;

public class Vehicle {

    private double position;   // posição ao longo da estrada
    private double speed;      // unidades por segundo
    private boolean stopped;

    public Vehicle(double speed) {
        this.speed = speed;
        this.position = 0;
        this.stopped = false;
    }

    public void update(double dt) {
        if (!stopped) {
            position += speed * dt;
        }
    }

    public void stop() {
        stopped = true;
    }

    public void go() {
        stopped = false;
    }

    public double getPosition() {
        return position;
    }
}

