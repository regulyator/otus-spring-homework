package ru.otus.icdimporter.tasklets;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.otus.icdimporter.model.IcdEntry;
import ru.otus.icdimporter.model.Tree;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.FileInputStream;

public class IcdXmlSourceReader implements Tasklet, StepExecutionListener {
    private final XPath xPath = XPathFactory.newInstance().newXPath();
    private final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
    private Tree<IcdEntry> icdEntryTree;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
        String sourcePath = (String) chunkContext.getStepContext().getJobParameters().get("source");
        try (FileInputStream sourceFileInputStream = new FileInputStream(sourcePath)) {

            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDocument = builder.parse(sourceFileInputStream);

            String expression = "/book/entries/entry[not(descendant::ID_PARENT)]";
            NodeList rootNodes = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
            fillIcdTree(xmlDocument, rootNodes);

        } catch (Exception ex){
            return RepeatStatus.FINISHED;
        }
        return RepeatStatus.FINISHED;
    }

    private void fillIcdTree(Document xmlDocument, NodeList rootNodes) throws XPathExpressionException {
        for (int i = 0; i < rootNodes.getLength(); i++) {
            if (rootNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) rootNodes.item(i);
                if (el.getNodeName().equals("entry")) {
                    String id = el.getElementsByTagName("ID").item(0).getTextContent();
                    String recCode = el.getElementsByTagName("REC_CODE").item(0).getTextContent();
                    String mkbName = el.getElementsByTagName("MKB_NAME").item(0).getTextContent();
                    String actual = el.getElementsByTagName("ACTUAL").item(0).getTextContent();
                    Tree<IcdEntry> parent = icdEntryTree.addChild(IcdEntry.builder()
                            .id(Long.valueOf(id))
                            .recCode(recCode)
                            .mbkName(mkbName)
                            .actual(Integer.valueOf(actual))
                            .build());
                    String expression1 = "/book/entries/entry[./ID_PARENT[.='"+id+"']]";
                    NodeList nodeList1 = (NodeList) xPath.compile(expression1).evaluate(xmlDocument, XPathConstants.NODESET);

                }
            }
        }
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        icdEntryTree = new Tree<>(IcdEntry.builder().build());

    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        stepExecution
                .getJobExecution()
                .getExecutionContext()
                .put("icdEntryTree", icdEntryTree);
        return ExitStatus.COMPLETED;
    }
}
