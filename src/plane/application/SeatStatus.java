package plane.application;

public class SeatStatus implements Runnable {
    private Plane plane;

    public SeatStatus(Plane plane) {
        this.plane = plane;
    }

    @Override
    public void run() {
        System.out.println("Started Reader Thread : " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate delay
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        plane.printSeatStatus();
    }
}
