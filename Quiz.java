package Quiz;

import java.util.*;

public class Quiz {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Admin admin = new Admin();
        initializeQuestions(admin);

        boolean loop = true;
        while (loop) {
            System.out.println("Enter your role:\n1 Admin\n2 Trainer\n3 Student\n4 Exit");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    handleAdminTasks(sc, admin);
                    break;
                case 2:
                    handleTrainerTasks(sc, admin);
                    break;
                case 3:
                    handleStudentTasks(sc, admin);
                    break;
                case 4:
                    loop = false;
                    continue;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (choice != 4) {
                System.out.println("Do you want to switch role or exit? Yes to continue, No to exit");
                String choi = sc.next();
                loop = choi.equalsIgnoreCase("Yes");
            }
        }
        sc.close();
    }

    private static void handleAdminTasks(Scanner sc, Admin admin) {
        boolean adminLoop = true;
        while (adminLoop) {
            System.out.println("Admin Tasks:\n1 Add Question\n2 Delete Question\n3 View Questions\n4 Exit");
            int adminChoice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (adminChoice) {
                case 1:
                    System.out.println("Enter question ID:");
                    int id = sc.nextInt();
                    sc.nextLine(); // Consume newline
                    System.out.println("Enter question text:");
                    String text = sc.nextLine();
                    System.out.println("Enter options separated by commas:");
                    String[] options = sc.nextLine().split(",");
                    System.out.println("Enter correct answer:");
                    String correctAnswer = sc.nextLine();
                    System.out.println("Enter complexity level (Easy, Medium, Hard):");
                    String complexity = sc.nextLine();

                    Question newQuestion = new Question(id, text, options, correctAnswer, complexity);
                    admin.addQuestion(newQuestion);
                    break;
                case 2:
                    System.out.println("Enter question ID to delete:");
                    int deleteId = sc.nextInt();
                    admin.deleteQuestion(deleteId);
                    break;
                case 3:
                    System.out.println("All Questions:");
                    for (Question q : admin.viewAllQuestions()) {
                        System.out.println("ID: " + q.getId() + ", Question: " + q.getText());
                    }
                    break;
                case 4:
                    adminLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (adminChoice != 4) {
                System.out.println("Do you want to perform another admin task? Yes or No");
                String choi = sc.next();
                adminLoop = choi.equalsIgnoreCase("Yes");
            }
        }
    }

    private static void handleTrainerTasks(Scanner sc, Admin admin) {
        boolean trainerLoop = true;
        Trainer trainer = new Trainer();
        while (trainerLoop) {
            System.out.println("Trainer Tasks:\n1 Set Up Test\n2 View Results\n3 Exit");
            int trainerChoice = sc.nextInt();

            switch (trainerChoice) {
                case 1:
                    System.out.println("Enter total number of questions:");
                    int totalQuestions = sc.nextInt();
                    System.out.println("Enter number of Easy questions:");
                    int easy = sc.nextInt();
                    System.out.println("Enter number of Medium questions:");
                    int medium = sc.nextInt();
                    System.out.println("Enter number of Hard questions:");
                    int hard = sc.nextInt();
                    System.out.println("Enter the number of past tests to exclude questions from:");
                    int excludeTests = sc.nextInt();

                    Test test = trainer.setupTest(admin, totalQuestions, easy, medium, hard, excludeTests);
                    System.out.println("\nTest Questions:");
                    for (Question q : test.getQuestions()) {
                        System.out.println("Question: " + q.getText());
                    }
                    trainer.addTest(test);  // Save the test for students to take
                    break;
                case 2:
                    System.out.println("\nTrainer View of Student Results:");
                    for (StudentResult result : trainer.viewResults()) {
                        System.out.println("Student: " + result.getStudent().getName() + ", Score: " + result.getScore());
                    }
                    break;
                case 3:
                    trainerLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (trainerChoice != 3) {
                System.out.println("Do you want to perform another trainer task? Yes or No");
                String choi = sc.next();
                trainerLoop = choi.equalsIgnoreCase("Yes");
            }
        }
    }

    private static void handleStudentTasks(Scanner sc, Admin admin) {
        boolean studentLoop = true;
        Student student = new Student("John Doe"); // In a real application, we'd get the student's name from input

        while (studentLoop) {
            System.out.println("Student Tasks:\n1 Take Test\n2 View Results\n3 Exit");
            int studentChoice = sc.nextInt();

            switch (studentChoice) {
                case 1:
                    System.out.println("Getting ready to take the test...");
                    Test test = Trainer.getLatestTest(); // Get the latest test set up by a trainer
                    if (test == null) {
                        System.out.println("No test available. Please try again later.");
                        break;
                    }

                    // Student takes the test
                    System.out.println("\nStarting Test:");
                    for (Question q : test.getQuestions()) {
                        System.out.println("Question: " + q.getText());
                        for (String option : q.getOptions()) {
                            System.out.println(option);
                        }
                        System.out.println("Enter your answer:");
                        String answer = sc.next();
                        if (answer.equalsIgnoreCase(q.getCorrectAnswer())) {
                            System.out.println("Correct!");
                        } else {
                            System.out.println("Incorrect! Correct answer is: " + q.getCorrectAnswer());
                        }
                    }

                    student.takeTest(test);

                    System.out.println("Test Completed. Your Score: ");
                    for (StudentResult result : student.viewResults()) {
                        System.out.println("Score: " + result.getScore());
                    }
                    break;
                case 2:
                    System.out.println("\nStudent Results:");
                    for (StudentResult result : student.viewResults()) {
                        System.out.println("Score: " + result.getScore());
                    }
                    break;
                case 3:
                    studentLoop = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (studentChoice != 3) {
                System.out.println("Do you want to perform another student task? Yes or No");
                String choi = sc.next();
                studentLoop = choi.equalsIgnoreCase("Yes");
            }
        }
    }

    private static void initializeQuestions(Admin admin) {
        Question q1 = new Question(1, "What is the capital of Germany?", new String[]{"A) Berlin", "B) Madrid", "C) Paris", "D) Rome"}, "A) Berlin", "Easy");
        Question q2 = new Question(2, "What is 2 + 2?", new String[]{"A) 3", "B) 4", "C) 5", "D) 6"}, "B) 4", "Easy");
        Question q3 = new Question(3, "Who designed the current 'National Flag of India'?", new String[]{"A) Sachindra Prasad Bose", "B) Sukumar Mitra", "C) Pingali Venkayya", "D) Sarojini Naidu"}, "C) Pingali Venkayya", "Medium");

        admin.addQuestion(q1);
        admin.addQuestion(q2);
        admin.addQuestion(q3);
    }
}

class Question {
    private int id;
    private String text;
    private String[] options;
    private String correctAnswer;
    private String complexity;

    public Question(int id, String text, String[] options, String correctAnswer, String complexity) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.complexity = complexity;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getComplexity() {
        return complexity;
    }
}

class Admin {
    private List<Question> questions;

    public Admin() {
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public void deleteQuestion(int id) {
        questions.removeIf(q -> q.getId() == id);
    }

    public List<Question> viewAllQuestions() {
        return questions;
    }
}

class Trainer {
    private static List<Test> tests = new ArrayList<>();
    private List<StudentResult> results = new ArrayList<>();

    public Test setupTest(Admin admin, int totalQuestions, int easy, int medium, int hard, int excludeTests) {
        List<Question> easyQuestions = new ArrayList<>();
        List<Question> mediumQuestions = new ArrayList<>();
        List<Question> hardQuestions = new ArrayList<>();
        List<Question> selectedQuestions = new ArrayList<>();

        Set<Integer> excludedQuestionIds = new HashSet<>();
        int start = Math.max(0, tests.size() - excludeTests);
        for (int i = start; i < tests.size(); i++) {
            for (Question q : tests.get(i).getQuestions()) {
                excludedQuestionIds.add(q.getId());
            }
        }

        for (Question q : admin.viewAllQuestions()) {
            if (excludedQuestionIds.contains(q.getId())) {
                continue;
            }
            switch (q.getComplexity().toLowerCase()) {
                case "easy":
                    easyQuestions.add(q);
                    break;
                case "medium":
                    mediumQuestions.add(q);
                    break;
                case "hard":
                    hardQuestions.add(q);
                    break;
            }
        }

        Random rand = new Random();
        for (int i = 0; i < easy && !easyQuestions.isEmpty(); i++) {
            selectedQuestions.add(easyQuestions.remove(rand.nextInt(easyQuestions.size())));
        }
        for (int i = 0; i < medium && !mediumQuestions.isEmpty(); i++) {
            selectedQuestions.add(mediumQuestions.remove(rand.nextInt(mediumQuestions.size())));
        }
        for (int i = 0; i < hard && !hardQuestions.isEmpty(); i++) {
            selectedQuestions.add(hardQuestions.remove(rand.nextInt(hardQuestions.size())));
        }

        return new Test(selectedQuestions);
    }

    public void addTest(Test test) {
        tests.add(test);
    }

    public List<StudentResult> viewResults() {
        return results;
    }

    public static Test getLatestTest() {
        if (tests.isEmpty()) return null;
        return tests.get(tests.size() - 1);
    }
}

class Student {
    private String name;
    private List<StudentResult> results;

    public Student(String name) {
        this.name = name;
        this.results = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void takeTest(Test test) {
        int score = 0;
        for (Question q : test.getQuestions()) {
            if (q.getCorrectAnswer().equalsIgnoreCase(q.getCorrectAnswer())) {
                score++;
            }
        }
        results.add(new StudentResult(this, score));
    }

    public List<StudentResult> viewResults() {
        return results;
    }
}

class Test {
    private List<Question> questions;

    public Test(List<Question> questions) {
        this.questions = questions;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

class StudentResult {
    private Student student;
    private int score;

    public StudentResult(Student student, int score) {
        this.student = student;
        this.score = score;
    }

    public Student getStudent() {
        return student;
    }

    public int getScore() {
        return score;
    }
}
