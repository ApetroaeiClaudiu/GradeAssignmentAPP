package repository;

import domain.Grade;
import domain.GradePair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validator.ValidationException;
import validator.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class GradeXMLFileRepository extends InMemoryRepository<GradePair, Grade> {
    private String fileName;

    public GradeXMLFileRepository(Validator validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }

    /**
     * method that loads the grad data from the XML file with DOM parser in memory
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
                Node gradeElement = children.item(i);
                if (gradeElement instanceof Element) {
                    Grade grade = createGradeFromElement((Element) gradeElement);
                    super.save(grade);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * method that writes the grade data from memory into the XML file with DOM parser
     */
    public void writeToFile() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("grades");
            document.appendChild(root);
            super.findAll().forEach(s -> {
                Element e = createElementfromGrade(document, s);
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
     * @param grade - the grade used to write its attributes in the XML file
     * @return an Element entity used in the WriteToFile method to write the grade's attributes in the XML file
     */
    private Element createElementfromGrade(Document document, Grade grade) {
        Element element = document.createElement("grade");

        element.setAttribute("ID", grade.getId().getIDStudent()+" "+grade.getId().getIDHomework());

        Element IDStudent = document.createElement("IDStudent");
        IDStudent.setTextContent(grade.getId().getIDStudent());
        element.appendChild(IDStudent);

        Element IDHomework = document.createElement("IDHomework");
        IDHomework.setTextContent(((Integer)grade.getId().getIDHomework()).toString());
        element.appendChild(IDHomework);

        Element value = document.createElement("Value");
        value.setTextContent(((Float)grade.getValue()).toString());
        element.appendChild(value);

        Element professor = document.createElement("Professor");
        professor.setTextContent(grade.getProfessor());
        element.appendChild(professor);

        Element delweek = document.createElement("DeliveryWeek");
        delweek.setTextContent(((Integer) grade.getDeliveryWeek()).toString());
        element.appendChild(delweek);

        Element feedback = document.createElement("Feedback");
        feedback.setTextContent(grade.getFeedback());
        element.appendChild(feedback);

        return element;
    }

    @Override
    public Grade save(Grade entity) throws ValidationException {
        Grade gr = super.save(entity);
        if(gr==null){
            writeToFile();
        }
        return gr;
    }

    @Override
    public Grade delete(GradePair gradePair) {
        Grade gr = super.delete(gradePair);
        if(gr != null){
            writeToFile();
        }
        return gr;
    }

    @Override
    public Grade update(Grade entity) {
        Grade gr =super.update(entity);
        if(gr ==null){
            writeToFile();
        }
        return gr;
    }

    /**
     *
     * @param gradeElement - an Element Entity used to get the values of the Grade Element from the XML file to create a
     *                       Student Entity
     * @return a Grade Entity used to load in memory the Grade in the LoadData method
     */
    private Grade createGradeFromElement(Element gradeElement) {
        String id = gradeElement.getAttribute("ID");
        //NodeList nods = studentElement.getChildNodes();

        String IDStudent  = gradeElement.getElementsByTagName("IDStudent")
                .item(0)
                .getTextContent();

        String IDHomework  = gradeElement.getElementsByTagName("IDHomework")
                .item(0)
                .getTextContent();

        String value  = gradeElement.getElementsByTagName("Value")
                .item(0)
                .getTextContent();

        String professor = gradeElement.getElementsByTagName("Professor")
                .item(0)
                .getTextContent();

        String deliveryWeek = gradeElement.getElementsByTagName("DeliveryWeek")
                .item(0)
                .getTextContent();

        String feedback = gradeElement.getElementsByTagName("Feedback")
                .item(0)
                .getTextContent();

        Grade gr = new Grade(Float.parseFloat(value),professor,Integer.parseInt(deliveryWeek),feedback);
        GradePair pr = new GradePair(IDStudent,Integer.parseInt(IDHomework));
        gr.setId(pr);
        return gr;
    }
}


