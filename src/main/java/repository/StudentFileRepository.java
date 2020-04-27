package repository;
import domain.Student;
import validator.ValidationException;
import validator.Validator;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class StudentFileRepository extends InMemoryRepository<String, Student> {
    private String fileName;
    public StudentFileRepository(Validator validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    /**
     * method that reads the input from a .txt file
     * @throws IOException if was not able to read the file
     * @throws ValidationException if the entity is not valid
     * @throws IllegalArgumentException if the entity is null
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(linie -> {
                String[] l = linie.split(";");
                Student s = new Student(l[0],Integer.parseInt(l[1]), l[2], l[3], l[4], l[5]);
                super.save(s);
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ValidationException ex) {
            System.out.println(ex.getMessage());
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        Student ent = super.save(entity);
        clean();
        findAll().forEach(x->writeToFile(x));
        return ent;
    }

    @Override
    public Student delete(String s) {
        Student st = super.delete(s);
        clean();
        findAll().forEach(x->writeToFile(x));
        return st;
    }

    @Override
    public Student update(Student entity) {
        Student st = super.update(entity);
        clean();
        findAll().forEach(x->writeToFile(x));
        return st;
    }

    private void clean(){
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        writer.print("");
        writer.close();
    }

    private void writeToFile(Student student) {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        ) {
            bufferedWriter.write(student.getId() + ";" + student.getGroup() + ";" + student.getSurname() + ";" + student.getName() + ";" + student.getEmail() + ";" + student.getProfessorLab());
            bufferedWriter.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//        try (PrintWriter printWriter = new PrintWriter(fileName);
//        ) {
//            printWriter.println(student.getId() + ";" + student.getGroup() + ";" + student.getSurname() + ";" + student.getName() + ";" + student.getEmail() + ";" + student.getProfessorLab());
//            printWriter.println();
//            printWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
}
