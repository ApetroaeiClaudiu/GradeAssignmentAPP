package service;

import domain.Intrebare;
import repository.InMemoryProiectRepository;
import repository.InMemoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class IntrebareService  {
    private InMemoryProiectRepository<Integer, Intrebare> repo ;

    public IntrebareService(InMemoryProiectRepository<Integer,Intrebare> repo) {
        this.repo = repo;
    }

    /**
     *
     * @throws IllegalArgumentException if the entity is null
     */
    public Intrebare add(int nrIntrebare,String descriere,String raspunsCorect,int punctaj,String[] variante){
        Intrebare intrebare= new Intrebare(nrIntrebare,descriere,raspunsCorect,punctaj,variante);
        Intrebare saved = repo.save(intrebare);
//        if(saved == null){
//            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD,savedStudent));
//        }
        return saved;
    }

    /**
     *
     * @param id - the id of the student , string
     * @throws IllegalArgumentException if the entity with the given id is null
     */
    public Intrebare delete(int id){
        Intrebare deleted = repo.delete(id);
//        if(deletedStudent != null){
//            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE,deletedStudent));
//        }
        return deleted;
    }



    public Iterable<Intrebare> findAll(){
        return repo.findAll();
    }

    public Intrebare findById(int id){
        return repo.findOne(id);
    }

//    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();
//
//    public void addObserver(Observer<StudentChangeEvent> event) {
//        observers.add(event);
//
//    }
//
//    public void removeObserver(Observer<StudentChangeEvent> event) {
//        observers.remove(event);
//    }
//
//    public void notifyObservers(StudentChangeEvent event) {
//        observers.stream().forEach(x->x.update(event));
//    }
}
