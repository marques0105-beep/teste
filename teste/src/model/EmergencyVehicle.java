package model;

public class EmergencyVehicle extends Vehicle {

    public EmergencyVehicle(double position) {
        super(position);
        this.setSpeed(150); // mais rápido
    }

    @Override
    public void stop() {
        // Veículo de emergência ignora stop
    }
}
