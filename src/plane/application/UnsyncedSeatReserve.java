package plane.application;

public class UnsyncedSeatReserve implements Runnable {
    private Plane plane;
    private int seatNumber;

    public UnsyncedSeatReserve(Plane plane, int seatNumber) {
        this.plane = plane;
        this.seatNumber = seatNumber;
    }

    @Override
    public void run() {
        // Hangi thread'in hangi koltuğu rezerve etmeye çalıştığını yazdır
        System.out.println(Thread.currentThread().getName() + " Koltuk Numarası " + seatNumber + " için rezervasyon yapıyor.");

        // Senkronize edilmemiş rezervasyon
        plane.reserveSeat(seatNumber);

        // Rezervasyon sonrası durum
        System.out.println(Thread.currentThread().getName() + " Koltuk Numarası " + seatNumber + " için rezervasyon yaptı.");
    }
}
