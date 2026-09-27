public class F3ParkingAllocation {

    static class ParkingSlot {
        private String slotNo;
        private int capacity;
        private int occupiedCount;

        ParkingSlot(String slotNo, int capacity, int occupiedCount) {
            this.slotNo = slotNo;
            this.capacity = capacity;
            this.occupiedCount = occupiedCount;
        }

        void allot(String vehicleNo) {
            if (occupiedCount < capacity) {
                occupiedCount++;

                System.out.println(
                        vehicleNo +
                                " allotted to slot " +
                                slotNo
                );
            }
        }

        String getSlotNo() {
            return slotNo;
        }

        int getOccupiedCount() {
            return occupiedCount;
        }

        int getCapacity() {
            return capacity;
        }
    }

    static ParkingSlot findAvailableSlot(ParkingSlot[] slots) {

        for (ParkingSlot slot : slots) {

            if (slot.getOccupiedCount() < slot.getCapacity()) {
                return slot;
            }
        }

        return null;
    }

    /*
     * The array contains references to ParkingSlot objects.
     * Passing the array does not create copies of those objects.
     * Therefore, changes made through a slot reference affect
     * the original ParkingSlot object.
     */
    static void safeAllot(
            ParkingSlot[] slots,
            String vehicleNo) {

        ParkingSlot availableSlot =
                findAvailableSlot(slots);

        if (availableSlot == null) {

            System.out.println(
                    "No slots available for " + vehicleNo
            );

        } else {

            availableSlot.allot(vehicleNo);
        }
    }

    public static void main(String[] args) {

        ParkingSlot[] slots1 = {
                new ParkingSlot("A1", 4, 3),
                new ParkingSlot("A2", 5, 5)
        };

        System.out.println("First allocation:");

        safeAllot(slots1, "TN09AB1234");

        ParkingSlot[] slots2 = {
                new ParkingSlot("A1", 4, 4),
                new ParkingSlot("A2", 5, 5)
        };

        System.out.println("\nSecond allocation:");

        safeAllot(slots2, "TN09AB1234");
    }
}