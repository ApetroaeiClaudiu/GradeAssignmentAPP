package repository;
import domain.Intrebare;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class IntrebareFileRepository extends InMemoryProiectRepository<Integer, Intrebare> {
    private String fileName;
    public IntrebareFileRepository(String fileName) {
        this.fileName = fileName;
        loadData();
    }

    /**
     * method that reads the input from a .txt file
     * @throws IOException if was not able to read the file
     * @throws IllegalArgumentException if the entity is null
     */
    private void loadData() {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            lines.forEach(linie -> {
                String[] l = linie.split(";");
                //String[] variante = l[4].split(",");
                String[] variante = {l[4],l[5],l[6]};
                Intrebare intrebare = new Intrebare(Integer.parseInt(l[0]),l[1],l[2],Integer.parseInt(l[3]),variante);
                super.save(intrebare);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public Intrebare save(Intrebare entity){
        Intrebare ent = super.save(entity);
        clean();
        findAll().forEach(x->writeToFile(x));
        return ent;
    }

    @Override
    public Intrebare delete(Integer id ) {
        Intrebare ent = super.delete(id);
        clean();
        findAll().forEach(x->writeToFile(x));
        return ent;
    }

    @Override
    public Intrebare update(Intrebare entity) {
        Intrebare ent = super.update(entity);
        clean();
        findAll().forEach(x->writeToFile(x));
        return ent;
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

    private void writeToFile(Intrebare intrebare) {
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND);
        ) {
            bufferedWriter.write(intrebare.getNrintrebare()+ ";"+intrebare.getDescriere()+";"+intrebare.getRaspunsCorect()+";"+intrebare.getPunctaj()+";"+intrebare.getVariante()[0]+";"+intrebare.getVariante()[1]+";"+intrebare.getVariante()[2]);
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
