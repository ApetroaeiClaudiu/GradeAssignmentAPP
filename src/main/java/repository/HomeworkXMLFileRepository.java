package repository;

import domain.Homework;
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

public class HomeworkXMLFileRepository extends InMemoryRepository<Integer, Homework> {
    private String fileName;

    public HomeworkXMLFileRepository(Validator validator, String fileName) {
        super(validator);
        this.fileName = fileName;
        loadData();
    }
    /**
     * method that loads the homework data from the XML file with DOM parser in memory
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
                Node homeworkElement = children.item(i);
                if (homeworkElement instanceof Element) {
                    Homework homework = createHomeworkFromElement((Element) homeworkElement);
                    super.save(homework);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * method that writes the homework data from memory into the XML file with DOM parser
     */
    public void writeToFile() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root = document.createElement("homework");
            document.appendChild(root);
            super.findAll().forEach(s -> {
                Element e = createElementfromHomework(document, s);
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
     * @param homework - the homework used to write its attributes in the XML file
     * @return an Element entity used in the WriteToFile method to write the homework's attributes in the XML file
     */
    private Element createElementfromHomework(Document document, Homework homework) {
        Element element = document.createElement("homework");

        element.setAttribute("ID", homework.getId().toString());

        Element startWeek = document.createElement("StartWeek");
        startWeek.setTextContent(((Integer) homework.getStartWeek()).toString());
        element.appendChild(startWeek);

        Element deadlineWeek = document.createElement("DeadlineWeek");
        deadlineWeek.setTextContent(((Integer) homework.getDeadlineWeek()).toString());
        element.appendChild(deadlineWeek);

        Element desc = document.createElement("Description");
        desc.setTextContent(homework.getDescription());
        element.appendChild(desc);

        return element;
    }

    @Override
    public Homework save(Homework entity) {
        Homework homework = super.save(entity);
        if (homework == null) {
            writeToFile();
        }
        return homework;
    }

    @Override
    public Homework delete(Integer integer) {
        Homework homework = super.delete(integer);
        if(homework != null){
            writeToFile();
        }
        return homework;
    }

    @Override
    public Homework update(Homework entity) {
        Homework homework = super.update(entity);
        if(homework == null){
            writeToFile();
        }
        return homework;
    }
    /**
     *
     * @param homeworkElement - an Element Entity used to get the values of the Homework Element from the XML file to create a
     *                       Homework Entity
     * @return a Homework Entity used to load in memory the Homework in the LoadData method
     */
    private Homework createHomeworkFromElement(Element homeworkElement) {
        String id = homeworkElement.getAttribute("ID");
        //NodeList nods = studentElement.getChildNodes();
        String StartWeek = homeworkElement.getElementsByTagName("StartWeek")
                .item(0)
                .getTextContent();

        String DeadlineWeek = homeworkElement.getElementsByTagName("DeadlineWeek")
                .item(0)
                .getTextContent();

        String description = homeworkElement.getElementsByTagName("Description")
                .item(0)
                .getTextContent();

        Homework homework = new Homework(Integer.parseInt(StartWeek),Integer.parseInt(DeadlineWeek),description);
        homework.setId(Integer.parseInt(id));
        return homework;
    }
}

