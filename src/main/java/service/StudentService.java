package service;


import domain.Student;
import repository.InMemoryRepository;
import utils.event.ChangeEventType;
import utils.event.StudentChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;
import validator.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentService implements Observable<StudentChangeEvent> {
    private InMemoryRepository<String, Student> repo ;

    public StudentService(InMemoryRepository<String, Student> repo) {
        this.repo = repo;
    }

    /**
     *
     * the student fields required to add a student
     * the id field will be created as a string formed by name and surname
     * @param group - the group of the student , group must not be 0 or negative
     * @param surname - the surname of the student ,surname must not be null or empty
     * @param name - the name of the student , name must not be null or empty
     * @param email - the email of the student , email must not be null or empty
     * @param professorLab - the lab coordinator's name , his name must not be null or empty
     * @throws ValidationException if the entity is not valid
     * @throws IllegalArgumentException if the entity is null
     */
    public Student add(String ID,int group,String surname,String name,String email,String professorLab) throws ValidationException {
        Student student = new Student(ID,group, surname, name, email, professorLab);
        Student savedStudent = repo.save(student);
        if(savedStudent == null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD,savedStudent));
        }
        return savedStudent;
    }

    /**
     *
     * @param id - the id of the student , string
     * @throws IllegalArgumentException if the entity with the given id is null
     */
    public Student delete(String id){
        Student deletedStudent = repo.delete(id);
        if(deletedStudent != null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE,deletedStudent));
        }
        return deletedStudent;
    }

    /**
     *
     * the student fields required to update a student
     * @param id - requires the id of the student that needs to be updated, string
     * @param group - the group of the student , group must not be 0 or negative
     * @param surname - the surname of the student ,surname must not be null or empty
     * @param name - the name of the student , name must not be null or empty
     * @param email - the email of the student , email must not be null or empty
     * @param professorLab - the lab coordinator's name , his name must not be null or empty
     * @throws ValidationException if the entity with the given id is not valid
     * @throws  IllegalArgumentException if the entity given is null
     */
    public Student update(String id,int group,String surname,String name,String email,String professorLab)  throws  ValidationException{
        Student newStudent = new Student(id,group, surname, name, email, professorLab);
        Student oldStudent = repo.findOne(id);
        if(oldStudent != null){
            Student updatedStudent = repo.update(newStudent);
            notifyObservers(new StudentChangeEvent(ChangeEventType.UPDATE,newStudent,oldStudent));
            return updatedStudent;
        }
        return oldStudent;
    }
    /**
     *
     * @param groupNumber - int , the number of the group by which we want to filter the Students
     * @return - an Iterable object of Students from a certain group - the given group number
     */
    public Iterable<Student> filterGroup(int groupNumber){
        return StreamSupport.stream(repo.findAll().spliterator(), false)
                .filter(x->x.getGroup()==groupNumber)
                .collect(Collectors.toList());
    }
    /**
     *
     * @return an Iterable collection of all the students
     */
    public Iterable<Student> findAll(){
        return repo.findAll();
    }

    public Student findById(String id){
        return repo.findOne(id);
    }

    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();

    public void addObserver(Observer<StudentChangeEvent> event) {
        observers.add(event);

    }

    public void removeObserver(Observer<StudentChangeEvent> event) {
        observers.remove(event);
    }

    public void notifyObservers(StudentChangeEvent event) {
        observers.stream().forEach(x->x.update(event));
    }
}
