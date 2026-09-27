public class PatientProfile {

    private String patientId;
    private String name;
    private boolean discharged;

    // Write-only property
    private String lockerPin;

    public PatientProfile() {
        this(null, null);
    }

    public PatientProfile(String name) {
        this(null, name);
    }

    public PatientProfile(String patientId, String name) {
        this.patientId = patientId;
        this.name = name;
        this.discharged = false;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String id) {

        // Write once only
        if (this.patientId == null) {
            this.patientId = id;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDischarged() {
        return discharged;
    }

    public void setDischarged(boolean discharged) {
        this.discharged = discharged;
    }

    // Write-only: no getter
    public void setLockerPin(String pin) {

        if (pin != null && pin.matches("\\d{4,6}")) {

            // Simple deterministic one-way transformation
            this.lockerPin = Integer.toHexString(pin.hashCode());
        }
    }

    public static void main(String[] args) {

        PatientProfile p =
                new PatientProfile("Arjun Iyer");

        System.out.println(
                "Patient ID: " + p.getPatientId());

        p.setPatientId("MT2026-0142");
        p.setPatientId("HACKED-0000");

        System.out.println(
                "Patient ID: " + p.getPatientId());

        p.setLockerPin("1234");

        p.setDischarged(true);

        System.out.println(
                "Discharged: " + p.isDischarged());
    }
}