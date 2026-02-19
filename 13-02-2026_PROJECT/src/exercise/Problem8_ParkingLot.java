package exercise;

public class Problem8_ParkingLot {

    static class Spot {
        String plate;
        long entryTime;

        Spot(String plate) {
            this.plate = plate;
            this.entryTime = System.currentTimeMillis();
        }
    }

    private final Spot[] table = new Spot[500];

    private int hash(String plate) {
        return Math.abs(plate.hashCode()) % table.length;
    }

    public int parkVehicle(String plate) {

        int index = hash(plate);
        int probes = 0;

        while (table[index] != null) {
            index = (index + 1) % table.length;
            probes++;
        }

        table[index] = new Spot(plate);
        System.out.println("Assigned spot " + index + " (" + probes + " probes)");
        return index;
    }

    public void exitVehicle(String plate) {

        int index = hash(plate);

        while (table[index] != null) {
            if (table[index].plate.equals(plate)) {
                long duration = (System.currentTimeMillis() - table[index].entryTime) / 1000;
                table[index] = null;
                System.out.println("Vehicle exited. Duration: " + duration + " seconds");
                return;
            }
            index = (index + 1) % table.length;
        }
    }

    public static void solve() {

        Problem8_ParkingLot lot = new Problem8_ParkingLot();

        lot.parkVehicle("ABC-1234");
        lot.parkVehicle("ABC-1235");
        lot.parkVehicle("XYZ-9999");

        lot.exitVehicle("ABC-1234");
    }
}
