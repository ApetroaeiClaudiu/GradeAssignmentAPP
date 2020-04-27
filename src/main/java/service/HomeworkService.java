package service;

import domain.Homework;
import domain.Student;
import domain.UniversityYearStructure;
import javafx.collections.ObservableList;
import repository.InMemoryRepository;
import utils.event.ChangeEventType;
import utils.event.HomeworkChangeEvent;
import utils.event.StudentChangeEvent;
import utils.observer.Observable;
import utils.observer.Observer;
import validator.ValidationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeworkService implements Observable<HomeworkChangeEvent> {
    private InMemoryRepository<Integer, Homework> repo;
    private UniversityYearStructure year;

    public HomeworkService(InMemoryRepository<Integer, Homework> repo, UniversityYearStructure year) {
        this.repo = repo;
        this.year = year;
    }

    /**
     * the homework fields required to add a homework
     * the id field will be created as a strinf formed by the startweek and the description
     * startWeek - the homework's startWeek, it has values between 1 and 14 and it cannot be higher that the deadlineWeek
     * it is calculated automatically , using the university year structure
     *
     * @param deadlineWeek - the homework's deadlineWeek, it has values between 1 and 14 and it annot be lower that the startWeek
     * @param description  - the homework's description , it must not be null or empty
     * @throws ValidationException      if the entity is not valid
     * @throws IllegalArgumentException if the entity is null
     */
    public Homework add(int ID, int deadlineWeek, String description) throws ValidationException {
        /**
         * automatically generates the number of the current week
         */
        LocalDate today = LocalDate.now();
        int startWeek = year.getWeek(today);
        Homework homework = new Homework(startWeek, deadlineWeek, description);
        homework.setId(ID);
        Homework savedHomework = repo.save(homework);
        if(savedHomework == null){
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.ADD,savedHomework));
        }
        return savedHomework;
    }

    /**
     * @param id - the id of the homework , string
     * @throws IllegalArgumentException if the entity with the given id is null
     */
    public Homework delete(int id) {
       Homework deletedHomework = repo.delete(id);
        if(deletedHomework != null){
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.DELETE,deletedHomework));
        }
        return deletedHomework;
    }

    /**
     * the fields required to update a homework
     *
     * @param id           - also requires the id of the homework that needs to be updated,string
     * @param deadlineWeek - the homework's deadlineWeek, it has values between 1 and 14 and it annot be lower that the startWeek
     * @param description  - the homework's description , it must not be null or empty
     * @throws ValidationException      if the entity is not valid
     * @throws IllegalArgumentException if the entity is null
     */
    public Homework update(int id, int deadlineWeek, String description) throws ValidationException {
        LocalDate today = LocalDate.now();
        int nrWeek = year.getWeek(today);
        /**
         * generates the number of the current week
         * if the new deadlineWeek number is less than the current week
         * deadlineWeek takes the -1 value so it will be invalid
         */
        if (nrWeek > deadlineWeek)
            deadlineWeek = -1;
        Homework homework = repo.findOne(id);
        Homework newHomework = new Homework(homework.getStartWeek(), deadlineWeek, description);
        newHomework.setId(id);

        Homework oldHomework = repo.findOne(id);
        if(oldHomework != null){
            Homework updatedHomework = repo.update(newHomework);
            notifyObservers(new HomeworkChangeEvent(ChangeEventType.UPDATE,newHomework,oldHomework));
            return updatedHomework;
        }
        return oldHomework;
    }

    /**
     * @return an Iterable collection of all the homeworks
     */
    public Iterable<Homework> findAll() {
        return repo.findAll();
    }

    public Homework findById(int id) {
        return repo.findOne(id);
    }

    private List<Observer<HomeworkChangeEvent>> observers = new ArrayList<>();

    public void addObserver(Observer<HomeworkChangeEvent> event) {
        observers.add(event);

    }

    public void removeObserver(Observer<HomeworkChangeEvent> event) {
        observers.remove(event);
    }

    public void notifyObservers(HomeworkChangeEvent event) {
        observers.stream().forEach(x -> x.update(event));
    }
}

