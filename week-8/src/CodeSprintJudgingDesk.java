import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// =========================
// Scoring Rule Interface
// =========================
interface TrackScoringRule {
    double calculateScore(double idea, double execution, double presentation);

    String getTrackName();
}

// =========================
// Innovation Track
// Idea 50%, Execution 30%, Presentation 20%
// =========================
class InnovationScoringRule implements TrackScoringRule {

    @Override
    public double calculateScore(double idea, double execution, double presentation) {
        return (idea * 0.50) +
                (execution * 0.30) +
                (presentation * 0.20);
    }

    @Override
    public String getTrackName() {
        return "Innovation";
    }
}

// =========================
// Open Track
// Simple Average
// =========================
class OpenScoringRule implements TrackScoringRule {

    @Override
    public double calculateScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }

    @Override
    public String getTrackName() {
        return "Open";
    }
}

// =========================
// Student
// =========================
class Student {

    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// =========================
// Score
// =========================
class Score {

    private double idea;
    private double execution;
    private double presentation;

    public Score(double idea, double execution, double presentation) {

        if (!isValidRating(idea) ||
                !isValidRating(execution) ||
                !isValidRating(presentation)) {

            throw new IllegalArgumentException(
                    "Ratings must be between 0 and 10"
            );
        }

        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    private boolean isValidRating(double rating) {
        return rating >= 0 && rating <= 10;
    }

    public double calculateFinalScore(TrackScoringRule rule) {
        return rule.calculateScore(
                idea,
                execution,
                presentation
        );
    }

    public double getIdea() {
        return idea;
    }

    public void setIdea(double idea) {
        this.idea = idea;
    }
}

// =========================
// Project
// =========================
class Project {

    private String projectName;
    private Score score;

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public boolean hasScore() {
        return score != null;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}

// =========================
// Team
// =========================
class Team {

    private String teamName;
    private List<Student> members;
    private TrackScoringRule scoringRule;
    private Project project;

    public Team(
            String teamName,
            List<Student> members,
            TrackScoringRule scoringRule
    ) {

        if (members.size() < 2 || members.size() > 4) {
            throw new IllegalArgumentException(
                    "A team must have 2 to 4 members."
            );
        }

        this.teamName = teamName;
        this.members = new ArrayList<>(members);
        this.scoringRule = scoringRule;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Student> getMembers() {
        return members;
    }

    public TrackScoringRule getScoringRule() {
        return scoringRule;
    }

    public boolean hasProject() {
        return project != null;
    }

    public void submitProject(Project project) {

        if (this.project != null) {
            throw new IllegalStateException(
                    "A team can submit only one project."
            );
        }

        this.project = project;
    }

    public Project getProject() {
        return project;
    }
}

// =========================
// Judge
// =========================
class Judge {

    private String name;

    public Judge(String name) {
        this.name = name;
    }

    public void scoreProject(
            Hackathon hackathon,
            Project project,
            double idea,
            double execution,
            double presentation
    ) {

        hackathon.recordScore(
                project,
                idea,
                execution,
                presentation
        );
    }
}

// =========================
// Hackathon
// =========================
class Hackathon {

    enum State {
        OPEN,
        JUDGING,
        PUBLISHED
    }

    private String name;
    private State state;

    private List<Team> teams;
    private Set<String> registeredStudentNames;

    public Hackathon(String name) {
        this.name = name;
        this.state = State.OPEN;
        this.teams = new ArrayList<>();
        this.registeredStudentNames = new HashSet<>();
    }

    // =========================
    // Team Registration
    // =========================
    public void registerTeam(Team team) {

        if (state != State.OPEN) {
            System.out.println(
                    "Registration failed: Registration is closed."
            );
            return;
        }

        if (team.getMembers().size() < 2 ||
                team.getMembers().size() > 4) {

            System.out.println(
                    "Registration failed: A team must have 2 to 4 members."
            );
            return;
        }

        // Check one-team-per-student rule
        for (Student student : team.getMembers()) {

            if (registeredStudentNames.contains(student.getName())) {

                System.out.println(
                        "Registration failed: Student "
                                + student.getName()
                                + " already belongs to another team."
                );

                return;
            }
        }

        teams.add(team);

        for (Student student : team.getMembers()) {
            registeredStudentNames.add(student.getName());
        }

        System.out.println(
                "Team " + team.getTeamName()
                        + " registered ("
                        + team.getMembers().size()
                        + " members, "
                        + team.getScoringRule().getTrackName()
                        + " track)."
        );
    }

    // =========================
    // Project Submission
    // =========================
    public void submitProject(
            Team team,
            Project project
    ) {

        if (state == State.PUBLISHED) {
            System.out.println(
                    "Submission failed: Results already published."
            );
            return;
        }

        if (team.hasProject()) {
            System.out.println(
                    "Submission failed: Team can submit only one project."
            );
            return;
        }

        team.submitProject(project);

        System.out.println(
                "Project '" + project.getProjectName()
                        + "' submitted by "
                        + team.getTeamName() + "."
        );
    }

    // =========================
    // Start Judging
    // =========================
    public void startJudging() {

        if (state == State.OPEN) {
            state = State.JUDGING;
            System.out.println("Judging started.");
        }
    }

    // =========================
    // Record Score
    // =========================
    public void recordScore(
            Project project,
            double idea,
            double execution,
            double presentation
    ) {

        if (state == State.PUBLISHED) {

            System.out.println(
                    "Rescore rejected: Results have already been published."
            );

            return;
        }

        if (state == State.OPEN) {
            state = State.JUDGING;
        }

        if (project.hasScore()) {

            System.out.println(
                    "Rescore rejected: Project already has a score."
            );

            return;
        }

        Score score = new Score(
                idea,
                execution,
                presentation
        );

        project.setScore(score);

        System.out.println(
                "Score recorded for '"
                        + project.getProjectName()
                        + "'."
        );

        // Find team's scoring rule
        Team team = findTeamForProject(project);

        if (team != null) {

            double finalScore =
                    score.calculateFinalScore(
                            team.getScoringRule()
                    );

            System.out.printf(
                    "Final score: %.2f%n",
                    finalScore
            );
        }
    }

    // =========================
    // Find Team
    // =========================
    private Team findTeamForProject(Project project) {

        for (Team team : teams) {

            if (team.getProject() == project) {
                return team;
            }
        }

        return null;
    }

    // =========================
    // Publish Results
    // =========================
    public void publishResults() {

        if (state == State.PUBLISHED) {
            System.out.println(
                    "Results are already published."
            );
            return;
        }

        state = State.PUBLISHED;

        System.out.println(
                "Results published."
        );
    }
}

// =========================
// Main Class
// =========================
public class CodeSprintJudgingDesk {

    public static void main(String[] args) {

        Hackathon hackathon =
                new Hackathon("Code Sprint");

        // -------------------------
        // Students
        // -------------------------
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        // -------------------------
        // Innovation Team
        // -------------------------
        List<Student> byteBustersMembers =
                List.of(asha, ravi, neha);

        Team byteBusters =
                new Team(
                        "ByteBusters",
                        byteBustersMembers,
                        new InnovationScoringRule()
                );

        hackathon.registerTeam(byteBusters);

        // -------------------------
        // Invalid Team
        // -------------------------
        List<Student> soloCoderMembers =
                List.of(kiran);

        Team soloCoder =
                new Team(
                        "SoloCoder",
                        soloCoderMembers,
                        new OpenScoringRule()
                );

        hackathon.registerTeam(soloCoder);

        // -------------------------
        // Project Submission
        // -------------------------
        Project smartAttend =
                new Project("SmartAttend");

        hackathon.submitProject(
                byteBusters,
                smartAttend
        );

        // -------------------------
        // Judge
        // -------------------------
        Judge judge =
                new Judge("Judge 1");

        judge.scoreProject(
                hackathon,
                smartAttend,
                8,
                7,
                9
        );

        // -------------------------
        // Publish Results
        // -------------------------
        hackathon.publishResults();

        // -------------------------
        // Attempt Rescore
        // -------------------------
        judge.scoreProject(
                hackathon,
                smartAttend,
                10,
                7,
                9
        );
    }
}