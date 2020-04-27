package validator;

import domain.Grade;

public class GradeValidator implements Validator<Grade>{
    @Override
    /**
     * validates the grade entity
     * throws ValidationException if the description field is empty
     * or if the startWeek and deadlineWeek values are not between 1 and 14
     * or do not respect the deadlineWeek <= startWeek condition
     */
    public void validate(Grade entity) throws ValidationException {
        String errors = "";
        if(entity.getValue()<1 || entity.getValue()>10)
            errors = errors + "Invaid value for the grade \n";
        if(entity.getId().getIDStudent().equals(" ") || entity.getId().getIDHomework()==0)
            errors = errors + "Invalid ID \n";
        if(entity.getProfessor()== null || entity.getProfessor().equals(""))
            errors = errors + "Invalid Professor name \n";
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }
}

