package plane.application;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Plane {

    private static final int TOTALSEATS = 10;
    private final boolean[] seats;
    private final Date[] reservationTimes; // Rezervasyon zamanları için dizi
    private final ReadWriteLock rwLock;
    private final Lock readLock;
    private final Lock writeLock;

    public Plane() {
        this.seats = new boolean[TOTALSEATS];
        this.reservationTimes = new Date[TOTALSEATS]; // Rezervasyon zamanlarını tutmak için
        this.rwLock = new ReentrantReadWriteLock(true);
        this.readLock = rwLock.readLock();
        this.writeLock = rwLock.writeLock();
    }

    public boolean isSeatAvailable(int seatNumber) {
        readLock.lock();
        try {
            if (seatNumber < 1 || seatNumber > TOTALSEATS) {
                throw new IllegalArgumentException("Geçersiz koltuk numarası");
            }
            return !seats[seatNumber - 1];
        } finally {
            readLock.unlock();
        }
    }

    public boolean reserveSeat(int seatNumber) {
        writeLock.lock();
        try {
            if (seatNumber < 1 || seatNumber > TOTALSEATS) {
                throw new IllegalArgumentException("Geçersiz koltuk numarası");
            }
            if (seats[seatNumber - 1]) {
                return false;
            }
            seats[seatNumber - 1] = true;
            reservationTimes[seatNumber - 1] = new Date(); // Rezervasyon zamanını kaydet
            return true;
        } finally {
            writeLock.unlock();
        }
    }

    public void cancelSeat(int seatNumber) {
        writeLock.lock();
        try {
            if (seatNumber < 1 || seatNumber > TOTALSEATS) {
                throw new IllegalArgumentException("Geçersiz koltuk numarası");
            }
            seats[seatNumber - 1] = false;
            reservationTimes[seatNumber - 1] = null; // Rezervasyon zamanını sıfırla
        } finally {
            writeLock.unlock();
        }
    }

    public Date getReservationTime(int seatNumber) {
        readLock.lock();
        try {
            if (seatNumber < 1 || seatNumber > TOTALSEATS) {
                throw new IllegalArgumentException("Geçersiz koltuk numarası");
            }
            return reservationTimes[seatNumber - 1];
        } finally {
            readLock.unlock();
        }
    }

    public void printSeatStatus() {
        readLock.lock();
        try {
        	 System.out.print("Mevcut koltuk durumu: ");
             for (int i = 0; i < TOTALSEATS; i++) {
                 System.out.print((seats[i] ? "[Rezerve]" : "[Boş]") + " ");
             }
             System.out.println("\n");
            
            
        } finally {
            readLock.unlock();
        }
    }
}
