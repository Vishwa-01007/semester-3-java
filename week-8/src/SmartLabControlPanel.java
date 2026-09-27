import java.util.ArrayList;
import java.util.List;

// ========================================
// Capability Interface
// ========================================
interface Capability {

    String getName();

    boolean apply(int value);

    String getStatus();
}

// ========================================
// Power Capability
// ========================================
class PowerCapability implements Capability {

    private boolean on = false;

    @Override
    public String getName() {
        return "Power";
    }

    @Override
    public boolean apply(int value) {

        if (value != 0 && value != 1) {
            return false;
        }

        on = value == 1;
        return true;
    }

    @Override
    public String getStatus() {
        return on ? "ON" : "OFF";
    }
}

// ========================================
// Brightness Capability
// Valid range: 0–100
// ========================================
class BrightnessCapability implements Capability {

    private int brightness = 0;

    @Override
    public String getName() {
        return "Brightness";
    }

    @Override
    public boolean apply(int value) {

        if (value < 0 || value > 100) {
            return false;
        }

        brightness = value;
        return true;
    }

    @Override
    public String getStatus() {
        return brightness + "%";
    }
}

// ========================================
// Temperature Capability
// Valid range: 16–30
// ========================================
class TemperatureCapability implements Capability {

    private int temperature = 16;

    @Override
    public String getName() {
        return "Temperature";
    }

    @Override
    public boolean apply(int value) {

        if (value < 16 || value > 30) {
            return false;
        }

        temperature = value;
        return true;
    }

    @Override
    public String getStatus() {
        return temperature + "°C";
    }
}

// ========================================
// Device
// ========================================
class Device {

    private String name;

    private List<Capability> capabilities;

    public Device(String name) {

        this.name = name;
        this.capabilities = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // ====================================
    // Add Capability
    // ====================================
    public void addCapability(
            Capability capability) {

        if (capability == null) {
            return;
        }

        if (hasCapability(capability.getName())) {
            return;
        }

        capabilities.add(capability);
    }

    // ====================================
    // Check Capability
    // ====================================
    public boolean hasCapability(
            String capabilityName) {

        for (Capability capability : capabilities) {

            if (capability.getName()
                    .equalsIgnoreCase(capabilityName)) {

                return true;
            }
        }

        return false;
    }

    // ====================================
    // Get Capability
    // ====================================
    private Capability getCapability(
            String capabilityName) {

        for (Capability capability : capabilities) {

            if (capability.getName()
                    .equalsIgnoreCase(capabilityName)) {

                return capability;
            }
        }

        return null;
    }

    // ====================================
    // Apply Capability
    // ====================================
    public boolean applyCapability(
            String capabilityName,
            int value) {

        Capability capability =
                getCapability(capabilityName);

        if (capability == null) {
            return false;
        }

        return capability.apply(value);
    }

    // ====================================
    // Get Status
    // ====================================
    public String getCapabilityStatus(
            String capabilityName) {

        Capability capability =
                getCapability(capabilityName);

        if (capability == null) {
            return "Not supported";
        }

        return capability.getStatus();
    }
}

// ========================================
// Scene Step
// ========================================
class SceneStep {

    private String capabilityName;
    private int value;

    public SceneStep(
            String capabilityName,
            int value) {

        this.capabilityName = capabilityName;
        this.value = value;
    }

    public int execute(Device device) {

        if (!device.hasCapability(capabilityName)) {
            return 0;
        }

        boolean success =
                device.applyCapability(
                        capabilityName,
                        value
                );

        if (!success) {
            return 0;
        }

        System.out.println(
                device.getName()
                        + ": "
                        + getDescription()
        );

        return 1;
    }

    private String getDescription() {

        if (capabilityName.equalsIgnoreCase("Power")) {

            return value == 1
                    ? "ON"
                    : "OFF";
        }

        if (capabilityName.equalsIgnoreCase(
                "Brightness")) {

            return "brightness set to "
                    + value + "%";
        }

        if (capabilityName.equalsIgnoreCase(
                "Temperature")) {

            return "temperature set to "
                    + value + "°C";
        }

        return capabilityName
                + " set to "
                + value;
    }

    public String getCapabilityName() {
        return capabilityName;
    }
}

// ========================================
// Scene
// ========================================
class Scene {

    private String name;

    private List<SceneStep> steps;

    public Scene(String name) {

        this.name = name;
        this.steps = new ArrayList<>();
    }

    public void addStep(SceneStep step) {

        if (step != null) {
            steps.add(step);
        }
    }

    public void execute(List<Device> devices) {

        System.out.println(
                "Scene '" + name + "' started."
        );

        int actionsApplied = 0;

        for (SceneStep step : steps) {

            for (Device device : devices) {

                actionsApplied +=
                        step.execute(device);
            }
        }

        System.out.println(
                "Scene '" + name
                        + "' completed: "
                        + actionsApplied
                        + " actions applied."
        );
    }
}

// ========================================
// Main
// ========================================
public class SmartLabControlPanel {

    public static void main(String[] args) {

        // =================================
        // Create Devices
        // =================================

        Device labAC =
                new Device("Lab AC");

        labAC.addCapability(
                new PowerCapability()
        );

        labAC.addCapability(
                new TemperatureCapability()
        );


        Device ceilingLights =
                new Device("Ceiling Lights");

        ceilingLights.addCapability(
                new PowerCapability()
        );

        ceilingLights.addCapability(
                new BrightnessCapability()
        );


        Device projector =
                new Device("Projector");

        projector.addCapability(
                new PowerCapability()
        );


        // =================================
        // Device List
        // =================================

        List<Device> devices =
                List.of(
                        labAC,
                        ceilingLights,
                        projector
                );


        // =================================
        // Lecture Mode Scene
        // =================================

        Scene lectureMode =
                new Scene("Lecture Mode");

        lectureMode.addStep(
                new SceneStep(
                        "Power",
                        1
                )
        );

        lectureMode.addStep(
                new SceneStep(
                        "Brightness",
                        40
                )
        );

        lectureMode.addStep(
                new SceneStep(
                        "Temperature",
                        24
                )
        );


        // =================================
        // Run Scene
        // =================================

        lectureMode.execute(devices);


        // =================================
        // Invalid Temperature
        // =================================

        System.out.println();

        boolean temperatureResult =
                labAC.applyCapability(
                        "Temperature",
                        12
                );

        if (!temperatureResult) {

            System.out.println(
                    "Rejected: Lab AC temperature "
                            + "must be between 16°C and 30°C."
            );
        }


        // =================================
        // Runtime Capability Addition
        // =================================

        projector.addCapability(
                new BrightnessCapability()
        );

        System.out.println(
                "Projector: Brightness capability added."
        );


        // =================================
        // Set Brightness
        // =================================

        boolean brightnessResult =
                projector.applyCapability(
                        "Brightness",
                        70
                );

        if (brightnessResult) {

            System.out.println(
                    "Projector: brightness set to 70%."
            );
        }
    }
}