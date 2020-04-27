package validator;
import domain.Homework;

public class HomeworkValidator implements Validator<Homework>{
    @Override
    /**
     * validates the homework entity
     * throws ValidationException if the description field is empty
     * or if the startWeek and deadlineWeek values are not between 1 and 14
     * or do not respect the deadlineWeek <= startWeek condition
     */
    public void validate(Homework entity) throws ValidationException {
        String errors = "";
        if(entity.getStartWeek()<1 || entity.getStartWeek()>14)
            errors = errors + "Invaid Start Week argument \n";
        if(entity.getDeadlineWeek()<1 || entity.getDeadlineWeek()>14)
            errors = errors + "Invalid Deadline Week argument \n";
        if(entity.getDeadlineWeek()<=entity.getStartWeek())
            errors = errors + "Invalid Deadline Week argument, less than Start Week \n";
        if(entity.getDescription() == null || entity.getDescription().equals(""))
            errors = errors + "Invalid input for entity (description cannot be null)\n";
        if(entity.getId()<1 || entity.getId()>100)
            errors = errors + "Invalid homework ID\n";
        if(!errors.equals(""))
            throw new ValidationException(errors);
    }
}

