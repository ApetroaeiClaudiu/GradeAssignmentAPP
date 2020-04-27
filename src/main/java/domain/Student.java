package domain;

public class Student extends Entity<String> {
    private int group;
    private String surname, name, email, professorLab;

    /**
     * @param group        - the group of the student , group must not be 0 or negative
     * @param surname      - the surname of the student ,surname must not be null or empty
     * @param name         - the name of the student , name must not be null or empty
     * @param email        - the email of the student , email must not be null or empty
     * @param professorLab - the lab coordinator's name , his name must not be null or empty
     */
    public Student(String id,int group, String surname, String name, String email, String professorLab) {
        this.group = group;
        this.surname = surname;
        this.name = name;
        this.email = email;
        this.professorLab = professorLab;
        setId(id);
    }

    public int getGroup() {
        return group;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getProfessorLab() {
        return professorLab;
    }

    @Override
    public String toString() {
        return "Student{"+
                "ID= " + getId()+
                ", group=" + group +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", professorLab='" + professorLab + '\'' +
                '}';
    }
}
