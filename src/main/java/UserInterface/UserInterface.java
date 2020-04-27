//package UserInterface;
//
//import service.GradeService;
//import service.HomeworkService;
//import service.StudentService;
//import validator.ValidationException;
//
//import java.util.Scanner;
//
//public class UserInterface {
//    private StudentService studentService;
//    private HomeworkService homeworkService;
//    private GradeService gradeService;
//
//    public UserInterface(StudentService studentService, HomeworkService homeworkService,GradeService gradeService) {
//        this.studentService = studentService;
//        this.homeworkService = homeworkService;
//        this.gradeService = gradeService;
//    }
//
//    private String input(String prompt) {
//        Scanner input = new Scanner(System.in);
//        System.out.print(prompt);
//        return input.nextLine();
//    }
//
//    private void printCommands() {
//        System.out.println("1. Add ");
//        System.out.println("2. Delete");
//        System.out.println("3. Update");
//        System.out.println("4. Show lists");
//        System.out.println("5. Filters");
//        System.out.println("0. Exit");
//    }
//
//
//
//
//    private void addStudent() {
//        try{
//            String ID = input("Student ID = ");
//            int group = Integer.parseInt(input("Student's group = "));
//            String surname = input("Student's surname = ");
//            String name = input("Student's name = ");
//            String email = input("Student's email = ");
//            String professorLab = input("Student's lab coordinator's name = ");
//            studentService.add(ID,group,surname,name,email,professorLab);
//        } catch (ValidationException | IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());       }
//    }
//    private void addTema() {
//        try {
//            int ID = Integer.parseInt(input("Homework ID (Homework number) = "));
//            int deadlineWeek = Integer.parseInt(input("Deadline Week of homework = "));
//            String description = input("Homework description = ");
//            homeworkService.add(ID,deadlineWeek,description);
//        } catch (ValidationException | IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//    private void addGrade() {
//        try{
//            String IDStudent = input("Student ID = ");
//            int IDHomework = Integer.parseInt(input("Homework ID = "));
//            int deliveryWeek = Integer.parseInt(input("Delivery week number = "));
//            String professorLab = input("Student's lab coordinator's name = ");
//            float value = Float.parseFloat(input("Grade value = "));
//            String feedback = input("The grade feedback = ");
//            String specialCase = input("Is there any special case for being late on the homework ? (yes/no) = ");
//            int howMany=0;
//            if(specialCase.equals("yes")) {
//                String does = input("does the student have a motivation ? Maximum 2 weeks (yes/no) = ");
//                if(does.equals("yes")) {
//                    howMany = Integer.parseInt(input("For how many weeks ? = "));
//                }
//                String does2= input("Did the professor forgot to add the grade at the needed time ? (yes/no) = ");
//                if(does2.equals("yes")){
//                    deliveryWeek = Integer.parseInt(input("What was the delivery week ? = "));
//                }
//                gradeService.add(IDStudent,IDHomework,deliveryWeek,professorLab,value,feedback,howMany);
//            }
//            else{
//                gradeService.add(IDStudent,IDHomework,deliveryWeek,professorLab,value,feedback,howMany);
//            }
//        } catch (ValidationException | IllegalArgumentException ex) {
//            System.out.println(ex.getMessage());       }
//    }
//    private void deleteStudent() {
//        String id = input("Student ID  = ");
//        studentService.delete(id);
//    }
//    private void deleteTema() {
//        int id = Integer.parseInt(input("Homework ID  = "));
//        homeworkService.delete(id);
//    }
//    private void updateStudent() {
//        try {
//            String id = input("The ID of the student that needs to be updated = ");
//            int group = Integer.parseInt(input("Student's new group = "));
//            String surname = input("Student's new surname = ");
//            String name = input("Student's new name = ");
//            String email = input("Student's new email = ");
//            String professorLab = input("Student's new lab coordinator = ");
//            studentService.update(id,group,surname,name, email, professorLab);
//        } catch (ValidationException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//    private void updateTema() {
//        try {
//            int id = Integer.parseInt(input("The ID of the homework tht needs to be updated = "));
//            int deadlineWeek = Integer.parseInt(input("Homework's new deadline Week = "));
//            String description = input("Homework's new description = ");
//            homeworkService.update(id,deadlineWeek,description);
//        } catch (ValidationException ex) {
//            System.out.println(ex.getMessage());
//        }
//    }
//
//    private void filterByGroup(){
//        int groupNumber = Integer.parseInt(input("Give the group number: "));
//        studentService.filterGroup(groupNumber).forEach(System.out::println);
//    }
//    private void filterByHomework(){
//        int IDHomework = Integer.parseInt(input("Give the homework ID: "));
//        gradeService.groupByDeliveredHomework(IDHomework).forEach(System.out::println);
//    }
//    private void filterByHomeworkAndProfessor(){
//        int IDHomework = Integer.parseInt(input("Give the homework ID: "));
//        String professor = input("Give the name of the professor: ");
//        gradeService.groupByDeliveredHomeworkAndProfessor(IDHomework,professor).forEach(System.out::println);
//    }
//    private void filterByHomeworkAndDeliveryWeek(){
//        int IDHomework = Integer.parseInt(input("Give the homework ID: "));
//        int deliveryWeek = Integer.parseInt(input("Give the delivery week number: "));
//        gradeService.groupByHomeworkAndDeliveryWeek(IDHomework,deliveryWeek).forEach(System.out::println);
//    }
//
//    private void listStudent() {
//        studentService.findAll().forEach(System.out::println);
//    }
//    private void listTema() { homeworkService.findAll().forEach(System.out::println); }
//    private void listGrade() {
//        gradeService.findAll().forEach(System.out::println);
//    }
//
//}
