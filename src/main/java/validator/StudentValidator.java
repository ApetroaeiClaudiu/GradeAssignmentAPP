package validator;

import domain.Student;

public class StudentValidator implements Validator<Student>{
    @Override
    /**
     * validates the student entity
     * throws ValidationException if the fields are null or empty
     * or if the ID  is not made from the first letters of the surname and name
     */
    public void validate(Student entity) throws ValidationException {
        String errors ="";
        if (entity.getGroup() <= 0 )
            errors = errors + "Invalid Group \n";
        if (entity.getSurname() == null || entity.getSurname().equals(""))
            errors = errors + "Invalid Surname \n";
        if (entity.getName()==null || entity.getName().equals(""))
            errors = errors + "Invalid Name \n";
        if (entity.getEmail()==null || entity.getEmail().equals(""))
            errors = errors + "Invalid Email \n";
        if (entity.getProfessorLab()==null || entity.getProfessorLab().equals(""))
            errors = errors + "Invalid professor name \n";
        if(entity.getId().length() != 8) {
            errors = errors + "Invalid ID length \n";
        }
        else{
            String a = entity.getSurname().toLowerCase();
            String b= entity.getName().toLowerCase();
            String id = entity.getId();
            if((a.charAt(0) != id.charAt(0)) || (b.charAt(0) != id.charAt(1)))
                errors = errors + "Invalid ID letters\n";
            if(!Character.isDigit(id.charAt(4)) || !Character.isDigit(id.charAt(5)) || !Character.isDigit(id.charAt(6)) || !Character.isDigit(id.charAt(7)))
                errors = errors + "Invalid ID numbers\n";
        }
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }
}

