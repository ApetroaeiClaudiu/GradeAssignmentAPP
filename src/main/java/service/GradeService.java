package service;

import domain.*;
import repository.InMemoryRepository;
import utils.event.ChangeEventType;
import utils.event.GradeChangeEvent;
import utils.event.GradeStatusEvent;
import utils.event.StudentChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;
import validator.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GradeService implements Observable<GradeChangeEvent> {
    private InMemoryRepository<GradePair, Grade> gradeRepo;
    private InMemoryRepository<String, Student> studentRepo;
    private InMemoryRepository<Integer, Homework> homeworkRepo;
    private UniversityYearStructure year;

    public GradeService(InMemoryRepository<GradePair, Grade> gradeRepo, InMemoryRepository<String, Student> studentRepo, InMemoryRepository<Integer, Homework> homeworkRepo, UniversityYearStructure year) {
        this.gradeRepo = gradeRepo;
        this.studentRepo = studentRepo;
        this.homeworkRepo = homeworkRepo;
        this.year = year;
    }

    /**
     *
     * @param IDHomework - the Homework ID
     * @param value - the grade value
     * @return the value of the grade after decreasing its value based on how many weeks have passed after the deadline
     */
    public float getLateValue(int IDHomework, float value,int saptPredarii){
        int deadlineWeeekTema = homeworkRepo.findOne(IDHomework).getDeadlineWeek();
        if (deadlineWeeekTema < saptPredarii) {
            int diferenta = saptPredarii - deadlineWeeekTema;
            if (diferenta == 1) {
                value = value - 1;
                System.out.println("The maximum grade is 9 !");
            }
            if (diferenta == 2) {
                value = value - 2;
                System.out.println("The maximum grade is 8 !");
            }
            if(diferenta >=3){
                value = 1;
                System.out.println("The given grade is 1 (late laboratory !!!)");
            }
        } else {
            System.out.println("The maximum grade is 10 !");
        }
        return value;
    }

    /**
     *
     * @param IDStudent - the Student ID
     * @param grade - the grade entity
     * @param deadlineWeek - the deadline week of the homework
     * @param deliveryWeek - the delivery week
     * @param feedback - the grade feedback
     * creates a file for every student that receives a grade ( only one per student,
     * and adds the info of additional grades)
     * writes in a student file the infos about his grade
     */

    private void writeStudentToFile(String IDStudent, Grade grade, int deadlineWeek, int deliveryWeek, String feedback){
        File file =  new File("data/Grades/"+IDStudent+".txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Path path = Paths.get("data/Grades/"+IDStudent+".txt");
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        ) {
            bufferedWriter.write("Homework : " + grade.getId().getIDHomework());
            bufferedWriter.newLine();
            bufferedWriter.write("Grade : " + grade.getValue());
            bufferedWriter.newLine();
            bufferedWriter.write("Delivered on : " + deliveryWeek);
            bufferedWriter.newLine();
            bufferedWriter.write("Deadline : " + deadlineWeek);
            bufferedWriter.newLine();
            bufferedWriter.write("Feedback : " + feedback);
            bufferedWriter.newLine();
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param IDStudent - the ID of the student that is receiving the grade
     * @param IDHomework - the ID of the homework at which the student is receiving the grade
     * @param deliveryWeek  - the number of the delivery week
     * @param professor - the professor that gives the grade
     * @param value - the value of the grade
     * @param feedback - the professor's feedback for the student's homework
     * @throws ValidationException - if the entity is not valid
     */
    public Grade add(String IDStudent, int IDHomework, int deliveryWeek,String professor, float value,String feedback) throws ValidationException {
        int deadlineWeek = homeworkRepo.findOne(IDHomework).getDeadlineWeek();
        Grade grade = new Grade(value, professor, deliveryWeek,feedback);
        GradePair id = new GradePair(IDStudent,IDHomework);
        grade.setId(id);
        Grade savedGrade = gradeRepo.save(grade);
        if(savedGrade==null) {
            writeStudentToFile(IDStudent, grade, deadlineWeek, deliveryWeek, feedback);
            notifyObservers(new GradeChangeEvent(ChangeEventType.ADD, savedGrade));
        }
        return savedGrade;
    }

    public Grade delete(GradePair id){
        Grade deletedGrade = gradeRepo.delete(id);
//        if(deletedGrade != null){
//            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE,deletedGrade));
//        }
        return deletedGrade;
    }
    /**
     *
     * @param IDHomework - int , the ID of the homework by which we want to filter the Students
     * @return - an Iterable object containing the Students that delivered a certain homework - that with the given ID
     */
    public Iterable<Student> groupByDeliveredHomework(int IDHomework){
        return StreamSupport.stream(gradeRepo.findAll().spliterator(), false)
                .filter(x-> x.getId().getIDHomework() == IDHomework)
                .map(x->studentRepo.findOne(x.getId().getIDStudent()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param IDHomework - int , the ID of the homework by which we want to filter the Grades
     * @param Professor - String , the name of the professor by which we want to filter the Grades
     * @return - an Iterable object containing the Grades received by the students that delivered a certain homework -
     * - that with the given ID at a certain professor - that with the given name
     */
    public Iterable<Student> groupByDeliveredHomeworkAndProfessor(int IDHomework, String Professor){
        Predicate<Grade> filterByHomework = x->x.getId().getIDHomework()== IDHomework;
        Predicate<Grade> filterByProfessor = x->x.getProfessor().equals(Professor);
        Predicate<Grade> filter = filterByHomework.and(filterByProfessor);
        return StreamSupport.stream(gradeRepo.findAll().spliterator(), false)
                .filter(filter)
                .map(x->studentRepo.findOne(x.getId().getIDStudent()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param IDHomework - int , the ID of the homework by which we want to filter the Grades
     * @param deliveryWeek - int , the delivery week of the homework by which we want to filter the Grades
     * @return - and Iterable object containing the Grades received by the students that delivered a certain homework -
     * - that with the given ID in a certain week - that with the delivery week number
     */
    public Iterable<Grade> groupByHomeworkAndDeliveryWeek(int IDHomework, int deliveryWeek){
        Predicate<Grade> filterByHomework = x->x.getId().getIDHomework() == IDHomework;
        Predicate<Grade> filterByDeliveryWeek = x->x.getDeliveryWeek() == deliveryWeek;
        Predicate<Grade> filter = filterByHomework.and(filterByDeliveryWeek);
        return StreamSupport.stream(gradeRepo.findAll().spliterator(), false)
                .filter(filter)
                .collect(Collectors.toList());
    }
    /**
     *
     * @return an Iterable collection of all the grades
     */
    public Iterable<Grade> findAll(){
        return gradeRepo.findAll();
    }

    public Grade findById(GradePair id){
        return gradeRepo.findOne(id);
    }

    private List<Observer<GradeChangeEvent>> observers=new ArrayList<>();

    public void addObserver(Observer<GradeChangeEvent> event) {
        observers.add(event);

    }

    public void removeObserver(Observer<GradeChangeEvent> event) {
        observers.remove(event);
    }

    public void notifyObservers(GradeChangeEvent event) {
        observers.stream().forEach(x->x.update(event));
    }
}

