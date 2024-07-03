package plane.application;

import java.util.Date;

public class makeReservation implements Runnable {
    private Plane plane;
    private int seatNumber;

    public makeReservation(Plane plane, int seatNumber) {
        this.plane = plane;
        this.seatNumber = seatNumber;
    }

    @Override
    public void run() {
        System.out.println("Started Writer-Reserve Thread : " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (plane.reserveSeat(seatNumber)) {
            Date reservationTime = plane.getReservationTime(seatNumber);
            System.out.println("Thread " + Thread.currentThread().getName() + ": Reserved seat " + seatNumber + " at " + reservationTime.toString());
        } else {
            System.out.println("Thread " + Thread.currentThread().getName() + ": Seat " + seatNumber + " is already reserved.");
        }
    }
}
