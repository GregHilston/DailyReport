package com.greghilston.dailyreport;

import android.annotation.TargetApi;
import android.os.Build;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// For XML File creation
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Responsible for creating human readable documents
 * Source: http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 */
public class DocumentMaster {
    private static DocumentMaster instance = new DocumentMaster();

    public static DocumentMaster getInstance() {
        return instance;
    }

    /**
     * Creates an XML file based on the report
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public void createXml(Report r) {
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            // Account element
            Document doc = docBuilder.newDocument();
            Element accountElement = doc.createElement("Account");
            doc.appendChild(accountElement);

            // Name element
            Element name = doc.createElement("Name");
            name.appendChild(doc.createTextNode(r.getProject().getAccount().getName()));
            accountElement.appendChild(name);

            // Company element
            Element company = doc.createElement("Company");
            company.appendChild(doc.createTextNode(r.getProject().getAccount().getCompany()));
            accountElement.appendChild(company);

            // Project element
            Element project = doc.createElement("Project");
            accountElement.appendChild(project);

            // set attribute to staff element
            Attr attr = doc.createAttribute("Name");
            attr.setValue(r.getProject().getProjectName());
            project.setAttributeNode(attr);

            // Date element
            Element date = doc.createElement("Date");
            date.appendChild(doc.createTextNode(r.getDate()));
            project.appendChild(date);

            // Headcount element
            Element headCount = doc.createElement("Headcount");
            headCount.appendChild(doc.createTextNode(String.valueOf(r.getHeadCount())));
            project.appendChild(headCount);

            // Companies elements
            for(int i = 0; i < r.getCompanies().size(); i++) {
                String sanitizedOtherCompanyName = r.getCompanies().get(i).getName().replace(" ", "_"); // XML doesn't allow spaces

                Element otherCompany = doc.createElement("Company");
                project.appendChild(otherCompany);

                // Company Name element
                Element otherCompanyName = doc.createElement("Name");
                otherCompanyName.appendChild(doc.createTextNode(sanitizedOtherCompanyName));
                otherCompany.appendChild(otherCompanyName);

                // Quantity element
                Element quantity = doc.createElement("Quantity");
                quantity.appendChild(doc.createTextNode(String.valueOf(r.getCompanies().get(i).getQuantity())));
                otherCompany.appendChild(quantity);
            }

            // People elements
            for(int i = 0; i < r.getPeople().size(); i++) {
                String sanitizedPersonName = r.getPeople().get(i).getName().replace(" ", "_"); // XML doesn't allow spaces

                Element person = doc.createElement("Person");
                project.appendChild(person);

                // Person Name element
                Element personName = doc.createElement("Name");
                personName.appendChild(doc.createTextNode(r.getPeople().get(i).getName()));
                person.appendChild(personName);

                // Person Company element
                Element personCompany = doc.createElement("Company");
                personCompany.appendChild(doc.createTextNode(r.getPeople().get(i).getCompany()));
                person.appendChild(personCompany);

                // Person Job Title element
                Element personJobTitle = doc.createElement("Job");
                personJobTitle.appendChild(doc.createTextNode(r.getPeople().get(i).getJobTitle()));
                person.appendChild(personJobTitle);

                // Hour on Site element
                Element pesonHours = doc.createElement("Hours");
                pesonHours.appendChild(doc.createTextNode(String.valueOf(r.getPeople().get(i).getHoursOnSite())));
                person.appendChild(pesonHours);
            }

            // Equipment elements
            for(int i = 0; i < r.getEquipment().size(); i++) {
                String sanitizedEquipmentName = r.getEquipment().get(i).getName().replace(" ", "_"); // XML doesn't allow spaces

                Element equipment = doc.createElement("Equipment");
                project.appendChild(equipment);

                // Equipment Name element
                Element equipmentName = doc.createElement("Name");
                equipmentName.appendChild(doc.createTextNode(r.getEquipment().get(i).getName()));
                equipment.appendChild(equipmentName);

                // Equipment Quantity element
                Element equipmentQuantity = doc.createElement("Quantity");
                equipmentQuantity.appendChild(doc.createTextNode(String.valueOf(r.getEquipment().get(i).getQuantity())));
                equipment.appendChild(equipmentQuantity);
            }

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("app/src/main/java/com/greghilston/dailyreport/Reports/file.xml"));

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File saved!");

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
    }

    /**
     * Creates a CSV based on the XML file
     */
    public void createCsv() {

    }


    /**
     * Creates a PDF based on the XML file
     */
    public void createPdf() {

    }
}
