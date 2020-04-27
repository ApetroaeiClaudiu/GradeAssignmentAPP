package repository;

import domain.Student;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validator.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class StudentXMLFileRepository extends InMemoryRepository<String, Student> {
    private String fileName;

    public StudentXMLFileRepository(Validator validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    /**
     * method that loads the student data from the XML file with DOM parser in memory
     */
    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node studentElement = children.item(i);
                if (studentElement instanceof Element) {
                    Student student = createStudentFromElement((Element) studentElement);
                    super.save(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method that writes the student data from memory into the XML file with DOM parser
     */
    public void writeToFile() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("students");
            document.appendChild(root);
            super.findAll().forEach(s -> {
                Element e = createElementfromStudent(document, s);
                root.appendChild(e);
            });

            //write Document to file
            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();

            transformer.setOutputProperty(OutputKeys.INDENT,"yes");
            Source source=new DOMSource(document);

            transformer.transform(source,
                    new StreamResult(fileName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param document - the document in which we will create the element to be written in the XML file
     * @param student - the student used to write its attributes in the XML file
     * @return an Element entity used in the WriteToFile method to write the student's attributes in the XML file
     */
    private Element createElementfromStudent(Document document, Student student) {
        Element element = document.createElement("student");
        element.setAttribute("ID", student.getId());

        Element id = document.createElement("ID");
        id.setTextContent(student.getId());
        element.appendChild(id);

        Element group = document.createElement("Group");
        group.setTextContent(((Integer) student.getGroup()).toString());
        element.appendChild(group);

        Element surname = document.createElement("Surname");
        surname.setTextContent(student.getSurname());
        element.appendChild(surname);

        Element name = document.createElement("Name");
        name.setTextContent(student.getName());
        element.appendChild(name);

        Element email = document.createElement("Email");
        email.setTextContent(student.getEmail());
        element.appendChild(email);

        Element professor = document.createElement("ProfessorLab");
        professor.setTextContent(student.getProfessorLab());
        element.appendChild(professor);

        return element;
    }

    @Override
    public Student save(Student entity) {
        Student student = super.save(entity);
        if (student == null) {
            writeToFile();
        }
        return student;
    }

    @Override
    public Student delete(String s) {
        Student student = super.delete(s);
        if(student != null){
            writeToFile();
        }
        return student;
    }

    @Override
    public Student update(Student entity) {
        Student student = super.update(entity);
        if(student == null ){
            writeToFile();
        }
        return student;
    }

    /**
     *
     * @param studentElement - an Element Entity used to get the values of the Student Element from the XML file to create a
     *                       Student Entity
     * @return a Student Entity used to load in memory the Student in the LoadData method
     */
    private Student createStudentFromElement(Element studentElement) {
        String id = studentElement.getAttribute("ID");
        //NodeList nods = studentElement.getChildNodes();
        String ID = studentElement.getElementsByTagName("ID")
                .item(0)
                .getTextContent();

        String group = studentElement.getElementsByTagName("Group")
                .item(0)
                .getTextContent();

        String surname = studentElement.getElementsByTagName("Surname")
                .item(0)
                .getTextContent();

        String name = studentElement.getElementsByTagName("Name")
                .item(0)
                .getTextContent();

        String email = studentElement.getElementsByTagName("Email")
                .item(0)
                .getTextContent();

        String professor = studentElement.getElementsByTagName("ProfessorLab")
                .item(0)
                .getTextContent();

        Student s= new Student(ID,Integer.parseInt(group),surname,name,email,professor);
        return s;
    }
}


