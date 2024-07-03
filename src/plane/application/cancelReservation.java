package plane.application;

public class cancelReservation implements Runnable {
    private Plane plane;
    private int seatNumber;

    public cancelReservation(Plane plane, int seatNumber) {
        this.plane = plane;
        this.seatNumber = seatNumber;
    }

    @Override
    public void run() {
        System.out.println("Started Writer-Cancel Thread : " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        plane.cancelSeat(seatNumber);
        System.out.println("Thread " + Thread.currentThread().getName() + ": Canceled seat " + seatNumber);
    }
}
