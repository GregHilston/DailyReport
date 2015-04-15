package com.greghilston.dailyreport;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

// For XML File creation
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

// For XML File reading


/**
 * Responsible for creating human readable documents
 * Source: http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 *         http://www.mkyong.com/java/how-to-export-data-to-csv-file-java/
 *         http://www.mkyong.com/java/how-to-read-xml-file-in-java-dom-parser/
 */
public class DocumentMaster {
    private static DocumentMaster instance = new DocumentMaster();
    private static File reportsDir = new File("app/src/main/java/com/greghilston/dailyreport/Reports/");

    static {
        createReportFolder();
    }

    public static DocumentMaster getInstance() {
        return instance;
    }

    /**
     * Creates the "Reports" directory if does not already exist
     */
    public static void createReportFolder() {
        // If directory doesn't exist, then create it
        if (reportsDir.exists() || reportsDir.mkdir()) {

            // If file doesn't exist, then create it
            if (!reportsDir.exists()) {
                System.err.println("Warning: Reports folder was missing, creating folder now");

                try {
                    reportsDir.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
    }

    /**
     * Creates a report based on an XML file
     * @param fileName xml file name
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public Report createReportFromXml(String fileName) {
        Report r = new Report();

        try {
            File file = new File("app/src/main/java/com/greghilston/dailyreport/Reports/" + fileName + ".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            int pCount = 0;     // Person count
            int cCount = 0;     // Company count
            int eCount = 0;     // Equipment count
            int oCount = 0;     // Observation count

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("Project");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    r.setCompanyName(element.getElementsByTagName("Company").item(0).getTextContent());
                    r.setProjectName(element.getElementsByTagName("prName").item(0).getTextContent());
                    r.setDate(element.getElementsByTagName("Date").item(0).getTextContent());

                    pCount = Integer.valueOf(element.getElementsByTagName("HeadCount").item(0).getTextContent());
                    cCount = Integer.valueOf(element.getElementsByTagName("CompanyCount").item(0).getTextContent());
                    eCount = Integer.valueOf(element.getElementsByTagName("EquipmentCount").item(0).getTextContent());
                    oCount = Integer.valueOf(element.getElementsByTagName("ObservationsCount").item(0).getTextContent());

                    // People
                    for(int i = 0; i < pCount; i++) {
                        String pName = element.getElementsByTagName("pName").item(0).getTextContent();
                        String job = element.getElementsByTagName("Job").item(0).getTextContent();
                        int hours = Integer.valueOf(element.getElementsByTagName("Hours").item(0).getTextContent());

                        Person p = new Person(pName, job, hours);
                        r.addPerson(p);
                    }

                    // Companies
                    for(int i = 0; i < cCount; i++) {
                        String cName = element.getElementsByTagName("cName").item(i).getTextContent();
                        int cQuantity = Integer.valueOf(element.getElementsByTagName("cQuantity").item(i).getTextContent());

                        Company c = new Company(cName, cQuantity);
                        r.addCompany(c);
                    }

                    // Equipment
                    for(int i = 0; i < eCount; i++) {
                        String eName = element.getElementsByTagName("eName").item(i).getTextContent();
                        int eQuantity = Integer.valueOf(element.getElementsByTagName("eQuantity").item(i).getTextContent());

                        Equipment e = new Equipment(eName, eQuantity);
                        r.addEquipment(e);
                    }

                    // Observations
                    for(int i = 0; i < oCount; i++) {
                        String time = element.getElementsByTagName("Time").item(i).getTextContent();
                        String text = element.getElementsByTagName("Text").item(i).getTextContent();
                        String note = element.getElementsByTagName("Note").item(i).getTextContent();

                        Text o = new Text(time, text, note);
                        r.addObservation(o);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return r;
    }

    /**
     * Creates an XML file based on the report
     * @param r  the report to make the XML file from
     * @param file  the file to write the xml from
     * @return  the file path or NULL if the file creation failed
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public String createXml(Report r, File file) {
        String fileName = r.getFileName();
        String outputFilePath = file + "/" + fileName +  ".xml";

        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            Document doc = docBuilder.newDocument();

            // Project element
            Element project = doc.createElement("Project");
            doc.appendChild(project);

            // Company element
            Element company = doc.createElement("Company");
            company.appendChild(doc.createTextNode(r.getProject().getCompanyName()));
            project.appendChild(company);

            // Project Name element
            Element projectName = doc.createElement("prName");
            projectName.appendChild(doc.createTextNode(r.getProject().getProjectName()));
            project.appendChild(projectName);

            // Date element
            Element date = doc.createElement("Date");
            date.appendChild(doc.createTextNode(r.getDate()));
            project.appendChild(date);

            // Headcount element
            Element headCount = doc.createElement("HeadCount");
            headCount.appendChild(doc.createTextNode(String.valueOf(r.getHeadCount())));
            project.appendChild(headCount);

            // Company count
            Element companyCount = doc.createElement("CompanyCount");
            companyCount.appendChild(doc.createTextNode(String.valueOf(r.getCompanyCount())));
            project.appendChild(companyCount);

            // Equipment count
            Element equipmentCount = doc.createElement("EquipmentCount");
            equipmentCount.appendChild(doc.createTextNode(String.valueOf(r.getEquipmentCount())));
            project.appendChild(equipmentCount);

            // Observations count
            Element observationsCount = doc.createElement("ObservationsCount");
            observationsCount.appendChild(doc.createTextNode(String.valueOf(r.getObservationsCount())));
            project.appendChild(observationsCount);

            // People elements (These people work for the same company as the maanger)
            for(int i = 0; i < r.getPeople().size(); i++) {
                String sanitizedPersonName = r.getPeople().get(i).getName().replace(" ", "_"); // XML doesn't allow spaces

                Element person = doc.createElement("Person");
                project.appendChild(person);

                // Person Name element
                Element personName = doc.createElement("pName");
                personName.appendChild(doc.createTextNode(r.getPeople().get(i).getName()));
                person.appendChild(personName);

                // Person Job Title element
                Element personJobTitle = doc.createElement("Job");
                personJobTitle.appendChild(doc.createTextNode(r.getPeople().get(i).getJobTitle()));
                person.appendChild(personJobTitle);

                // Hour on Site element
                Element pesonHours = doc.createElement("Hours");
                pesonHours.appendChild(doc.createTextNode(String.valueOf(r.getPeople().get(i).getHoursOnSite())));
                person.appendChild(pesonHours);
            }

            // Companies elements
            for(int i = 0; i < r.getCompanies().size(); i++) {
                String sanitizedOtherCompanyName = r.getCompanies().get(i).getName().replace(" ", "_"); // XML doesn't allow spaces

                Element otherCompany = doc.createElement("Company");
                project.appendChild(otherCompany);

                // Company Name element
                Element otherCompanyName = doc.createElement("cName");
                otherCompanyName.appendChild(doc.createTextNode(sanitizedOtherCompanyName));
                otherCompany.appendChild(otherCompanyName);

                // Company Quantity element
                Element quantity = doc.createElement("cQuantity");
                quantity.appendChild(doc.createTextNode(String.valueOf(r.getCompanies().get(i).getQuantity())));
                otherCompany.appendChild(quantity);
            }

            // Equipment elements
            for(int i = 0; i < r.getEquipment().size(); i++) {
                String sanitizedEquipmentName = r.getEquipment().get(i).getName().replace(" ", "_"); // XML doesn't allow spaces

                Element equipment = doc.createElement("Equipment");
                project.appendChild(equipment);

                // Equipment Name element
                Element equipmentName = doc.createElement("eName");
                equipmentName.appendChild(doc.createTextNode(r.getEquipment().get(i).getName()));
                equipment.appendChild(equipmentName);

                // Equipment Quantity element
                Element equipmentQuantity = doc.createElement("eQuantity");
                equipmentQuantity.appendChild(doc.createTextNode(String.valueOf(r.getEquipment().get(i).getQuantity())));
                equipment.appendChild(equipmentQuantity);
            }

            // Observation elements
            Element observations = doc.createElement("Observations");
            project.appendChild(observations);

            // Observation elements
            for(int i = 0; i < r.getObservationsCount(); i++) {
                Observation o = r.observations.get(i);

                // Time element
                Element observationTime = doc.createElement("Time");
                observationTime.appendChild(doc.createTextNode(o.getTime()));
                observations.appendChild(observationTime);

                if(o instanceof Weather) {
                    // Weather element
                    Element weather = doc.createElement("Weather");
                    // TODO: GREHG HERE
                    weather.appendChild(doc.createTextNode(((Weather) o).getCurrently()));
                    weather.appendChild(doc.createTextNode(Float.toString(((Weather) o).getTemperature())));
                    weather.appendChild(doc.createTextNode(Float.toString(((Weather) o).getHumidity())));
                    weather.appendChild(doc.createTextNode(Float.toString(((Weather) o).getPressure())));
                    observations.appendChild(weather);
                }
                else if(o instanceof Text) {
                    // Text element
                    Element text = doc.createElement("Text");
                    text.appendChild(doc.createTextNode(((Text) o).getText()));
                    observations.appendChild(text);
                }
                else if(o instanceof Picture){

                }
                else {
                    System.err.println("Unknown Instance of Observation");
                    System.exit(-1);
                }

                // Note element
                Element note = doc.createElement("Note");
                note.appendChild(doc.createTextNode(o.getNote()));
                observations.appendChild(note);
            }

            // Write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(outputFilePath);

            // Output to console for testing
            //StreamResult result = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("XML File saved: " + outputFilePath);
            // printXml(outputFilePath); // TODO: Remove this after demo
            return outputFilePath;
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return null; // TODO: Handle this
    }

    /**
     * Reads in a XML file and prints it out to stdOut (Useful for debugging)
     *
     * @param fileName  name of csv file
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public void printXml(String fileName) {
        try {
            // File file = new File("app/src/main/java/com/greghilston/dailyreport/Reports/" + fileName + ".xml");
            File file = new File(fileName);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            int pCount = 0;     // Person count
            int cCount = 0;     // Company count
            int eCount = 0;     // Equipment count
            int oCount = 0;     // Observation count

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
            NodeList nList = doc.getElementsByTagName("Project");
            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                System.out.println("\nCurrent Node: " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    System.out.println("\tCompany: " + element.getElementsByTagName("Company").item(0).getTextContent());
                    System.out.println("\tprName: " + element.getElementsByTagName("prName").item(0).getTextContent());
                    System.out.println("\t\tDate: " + element.getElementsByTagName("Date").item(0).getTextContent());

                    pCount = Integer.valueOf(element.getElementsByTagName("HeadCount").item(0).getTextContent());
                    System.out.println("\t\tHeadcount: " + element.getElementsByTagName("HeadCount").item(0).getTextContent());

                    cCount = Integer.valueOf(element.getElementsByTagName("CompanyCount").item(0).getTextContent());
                    System.out.println("\t\tCompanyCount: " + element.getElementsByTagName("CompanyCount").item(0).getTextContent());

                    eCount = Integer.valueOf(element.getElementsByTagName("EquipmentCount").item(0).getTextContent());
                    System.out.println("\t\tEquipmentCount: " + element.getElementsByTagName("EquipmentCount").item(0).getTextContent());

                    oCount = Integer.valueOf(element.getElementsByTagName("ObservationsCount").item(0).getTextContent());
                    System.out.println("\t\tObservationsCount: " + element.getElementsByTagName("ObservationsCount").item(0).getTextContent());

                    System.out.println("\t\tPeople:");
                    for(int i = 0; i < pCount; i++) {
                        System.out.println("\t\t\tpName: " + element.getElementsByTagName("pName").item(i).getTextContent());
                        System.out.println("\t\t\tJob: " + element.getElementsByTagName("Job").item(i).getTextContent()); // - 2 due to how the XML is laid out
                        System.out.println("\t\t\tHours: " + element.getElementsByTagName("Hours").item(i).getTextContent() + "\n"); // - 2 due to how the XML is laid out
                    }


                    System.out.println("\t\tCompanies:");
                    for(int i = 0; i < cCount; i++) {
                        System.out.println("\t\t\tcName: " + element.getElementsByTagName("cName").item(i).getTextContent());
                        System.out.println("\t\t\tcQuantity: " + element.getElementsByTagName("cQuantity").item(i).getTextContent() + "\n");
                    }

                    System.out.println("\t\tEquipment:");
                    for(int i = 0; i < eCount; i++) {
                        System.out.println("\t\t\teName: " + element.getElementsByTagName("eName").item(i).getTextContent());
                        System.out.println("\t\t\teQuantity: " + element.getElementsByTagName("eQuantity").item(i).getTextContent() + "\n");
                    }

                    System.out.println("\t\tObservatons:");
                    for(int i = 0; i < oCount; i++) {
                        System.out.println("\t\t\tTime: " + element.getElementsByTagName("Time").item(i).getTextContent());
                        System.out.println("\t\t\tText: " + element.getElementsByTagName("Text").item(i).getTextContent());
                        System.out.println("\t\t\tNote: " + element.getElementsByTagName("Note").item(i).getTextContent() + "\n");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a CSV based on the XML file
     * @param r  the report to make the CSV file from
     * @param file  the file to write the csv from
     * @return  the file path or NULL if the file creation failed
     */
    @TargetApi(Build.VERSION_CODES.FROYO)
    public String createCsv(Report r, File file) {
        // Commented out, as this creates a CSV from a report, rather than an XML file
        String fileName = r.getFileName();
        String outputFilePath = file + "/" + fileName +  ".csv";

        try
        {
            FileWriter writer = new FileWriter(outputFilePath);

            writer.append(r.getProject().getProjectName());
            writer.append(',');
            writer.append(r.getProject().getCompanyName());
            writer.append("\n\n");

            writer.append(r.getDate());
            writer.append(',');
            writer.append(String.valueOf(r.getHeadCount() + " employees"));
            writer.append(',');
            writer.append(String.valueOf(r.getCompanyCount() + " companies"));
            writer.append(',');
            writer.append(String.valueOf(r.getEquipmentCount() + " equipment"));
            writer.append(',');
            writer.append(String.valueOf(r.getObservationsCount() + " observations"));

            writer.append("\n\nPeople\n");
            for(Person p : r.getPeople()) {
                writer.append(p.getName());
                writer.append(',');
                writer.append(p.getJobTitle());
                writer.append(',');
                writer.append(p.getHoursOnSite() + " hours");
                writer.append('\n');
            }

            writer.append("\nCompanies\n");
            for(Company c : r.getCompanies()) {
                writer.append(c.getName());
                writer.append(',');
                writer.append(c.getQuantity() + "x");
                writer.append('\n');
            }

            writer.append("\nEquipment\n");
            for(Equipment e : r.getEquipment()) {
                writer.append(e.getName());
                writer.append(',');
                writer.append(e.getQuantity() + "x");
                writer.append('\n');
            }

            writer.append("\nObservations\n");
            for(Observation o : r.getObservations()) {
                writer.append(o.getTime());
                writer.append(',');

                // Text
                if(o instanceof Text) {
                    writer.append(((Text) o).getText());
                    writer.append(',');
                }

                writer.append(o.getNote());
                writer.append(',');
                writer.append('\n');
            }

            System.out.println("CSV File saved: " + outputFilePath);

            writer.flush();
            writer.close();

            return outputFilePath;
        } catch(IOException e) {
            e.printStackTrace();
        }

        return null; // TODO: Handle this
    }


    /**
     * Creates a PDF based on the XML file
     */
    public void createPdf(String fileName) {

    }
}
