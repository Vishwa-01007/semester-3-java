import java.util.ArrayList;
import java.util.List;

public class PatientVitals {

    private List<Double> readings;

    public PatientVitals(double[] initialReadings) {

        readings = new ArrayList<>();

        if (initialReadings != null) {
            for (double reading : initialReadings) {
                recordReading(reading);
            }
        }
    }

    public void recordReading(double reading) {

        if (reading <= 0 || reading > 45) {
            return;
        }

        if (readings.size() < 500) {
            readings.add(reading);
        }
    }

    public double getAverage() {

        if (readings.isEmpty()) {
            return 0.0;
        }

        double sum = 0;

        for (double reading : readings) {
            sum += reading;
        }

        return sum / readings.size();
    }

    public double[] getAllReadings() {

        double[] copy = new double[readings.size()];

        for (int i = 0; i < readings.size(); i++) {
            copy[i] = readings.get(i);
        }

        return copy;
    }

    public static void main(String[] args) {

        PatientVitals v =
                new PatientVitals(
                        new double[]{36.5, -2, 37.1});

        double[] values = v.getAllReadings();

        for (double value : values) {
            System.out.print(value + " ");
        }

        System.out.println();

        System.out.println("Average: " + v.getAverage());

        values[0] = 999;

        System.out.println(
                "After changing copy: "
                        + v.getAllReadings()[0]);
    }
}