# Online_Quiz_Java_Application

Design and implement an Online Quiz Application that allows administrators to manage questions, trainers to create tests based on specified criteria, and students to take the tests and receive their results. The application should support various functionalities for different user roles. Admin, Trainer, and Student

Functional Requirements:

1. Admin Functionalities:

• Add Questions: Admin can add multiple-choice questions (MCQs) with a specified complexity level (Easy, Medium, Hard) and correct answers

• Delete Questions: Admin can delete a question by specifying the question number.

View Questions: Admin can view all questions or a specific question by its number.

2. Trainer Functionalities:

• Setup Tests: Trainer can set up tests from the pool of questions created by the admin. The criteria for setting up tests include:

Random selection of questions for each test.

• Specification of the total number of questions.

• Specification of the number of questions under each complexity level (Easy, Medium, Hard)

Exclusion of questions used in the last 'n' tests

• View Results: Trainer can view the results of all students who have taken their tests, including each student's total marks.

3. Student Functionalities:

Take Tests: Students can take tests that are automatically generated based on the criteria set by the trainer.

• View Results: After completing a test, students can view their total marks.
