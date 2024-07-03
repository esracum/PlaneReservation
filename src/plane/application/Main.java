package plane.application;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Plane plane = new Plane();
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        boolean running = true;

        while (running) {
            System.out.println("\n=== Ticket Booking System ===");
            System.out.println("1. Koltuk Rezervasyonu");
            System.out.println("2. Koltuk İptali");
            System.out.println("3. Koltuk Bilgisi");
            System.out.println("4. Çoklu Test (Multiple Writers/Readers)");
            System.out.println("5. Senkronize Olmayan Rezervasyon Testi");  
            System.out.println("6. Çıkış");
            System.out.print("Seçiminizi yapın (1-6): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Rezervasyon için koltuk numarasını girin: ");
                    int seatToReserve = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    Thread reserveThread = new Thread(new makeReservation(plane, seatToReserve));
                    reserveThread.start();
                    try {
                        reserveThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 2:
                    System.out.print("İptal için koltuk numarasını girin: ");
                    int seatToCancel = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    Thread cancelThread = new Thread(new cancelReservation(plane, seatToCancel));
                    cancelThread.start();
                    try {
                        cancelThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    Thread statusThread = new Thread(new SeatStatus(plane));
                    statusThread.start();
                    try {
                        statusThread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;

                case 4:
                    // Çoklu yazar ve okuyucu için test
                    System.out.println("Çoklu yazar ve okuyucu test ediliyor...");

                    int seat1 = random.nextInt(5) + 1;
                    int seat2 = random.nextInt(5) + 1;
                    int seat3 = random.nextInt(5) + 1;

                    Thread writer1 = new Thread(new makeReservation(plane, seat1), "Writer1");
                    Thread writer2 = new Thread(new makeReservation(plane, seat2), "Writer2");
                    Thread writer3 = new Thread(new makeReservation(plane, seat3), "Writer3");

                    Thread reader1 = new Thread(new SeatStatus(plane), "Reader1");
                    Thread reader2 = new Thread(new SeatStatus(plane), "Reader2");
                    Thread reader3 = new Thread(new SeatStatus(plane), "Reader3");

                    System.out.println("Writer1 attempting to reserve Koltuk Numarası: " + seat1);
                    System.out.println("Writer2 attempting to reserve Koltuk Numarası: " + seat2);
                    System.out.println("Writer3 attempting to reserve Koltuk Numarası: " + seat3);

                    writer1.start();
                    writer2.start();
                    writer3.start();
                    reader1.start();
                    reader2.start();
                    reader3.start();

                    try {
                        writer1.join();
                        writer2.join();
                        writer3.join();
                        reader1.join();
                        reader2.join();
                        reader3.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Çoklu yazar ve okuyucu testi tamamlandı.");
                    break;

                case 5:
                    // Senkronize Olmayan Rezervasyon Testi
                    System.out.println("Senkronize Olmayan Rezervasyon Testi ediliyor...");
                    
                    Thread unsyncedReserve1 = new Thread(new UnsyncedSeatReserve(plane, 1), "Thread1");
                    Thread unsyncedReserve2 = new Thread(new UnsyncedSeatReserve(plane, 2), "Thread2");
                    Thread unsyncedReserve3 = new Thread(new UnsyncedSeatReserve(plane, 1), "Thread3");
                    Thread unsyncedReserve4 = new Thread(new UnsyncedSeatReserve(plane, 3), "Thread4");
                    Thread unsyncedReserve5 = new Thread(new UnsyncedSeatReserve(plane, 3), "Thread5");
                    

                    unsyncedReserve1.start();
                    unsyncedReserve2.start();
                    unsyncedReserve3.start();
                    unsyncedReserve4.start();
                    unsyncedReserve5.start();

				    System.out.println("join olması gereken yerde bu print yazısı var.");

                    System.out.println("Senkronize Olmayan Rezervasyon Testi tamamlandı.");
                    break;

                case 6:
                    System.out.println("Çıkılıyor...");
                    running = false;
                    break;

                default:
                    System.out.println("Geçersiz seçim! Lütfen 1-6 arasında bir seçim yapın.");
                    break;
            }
        }

        scanner.close();
    }
}
