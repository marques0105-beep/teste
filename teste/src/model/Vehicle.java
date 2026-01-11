package model;
import model.Direction; // se necessário

public class Vehicle {

    protected double position; // ainda usado pelo controller
    protected double x;
    protected double y;
    protected double speed;
    protected boolean stopped;

    // 🔹 NOVO
    protected Direction direction;

    public Vehicle(double speed) {
        this.speed = speed;
        this.position = 0;
        this.x = 0;
        this.y = 0;
        this.direction = Direction.EAST; //
        this.stopped = false;
    }

    public void update(double dt) {
        if (!stopped) {
            switch (direction) {
                case EAST -> x += speed * dt;
                case WEST -> x -= speed * dt;
                case NORTH -> y -= speed * dt;
                case SOUTH -> y += speed * dt;
            }
            position = x; // compatibilidade temporária
        }
    }

    public void stop() {
        stopped = true;
    }

    public void go() {
        stopped = false;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getPosition() {
        return position;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Axis getAxis() {
        return (direction == Direction.NORTH || direction == Direction.SOUTH)
                ? Axis.NORTH_SOUTH
                : Axis.EAST_WEST;
    }


    // 🔹 NOVO
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        this.position = x; // compatibilidade
    }
}

