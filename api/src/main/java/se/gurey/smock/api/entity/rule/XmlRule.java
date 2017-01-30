package se.gurey.smock.api.entity.rule;

import java.io.File;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jaxen.JaxenException;
import org.jaxen.dom.DOMXPath;
import org.xml.sax.InputSource;

import spark.Request;
import spark.Response;

public class XmlRule extends EndpointRule {
    
    public XmlRule(String response, HashMap<String, String> conditions, boolean isDefault) {
        super(response, conditions, isDefault, RuleType.XML);
    }

    @Override
    public boolean applyRule(Request req, Response res) {
        Set<Entry<String, String>> entries = getConditions().entrySet();
        for (Entry<String, String> entry : entries) {
            SAXReader sax = new SAXReader();
            try {
                Document doc = sax.read(new InputSource(new StringReader(req.body())));
                List<Node> nodes = doc.selectNodes(entry.getKey());
                if(nodes.size() > 0) {
                    String nodeValue = nodes.get(0).getText();
                    if(nodeValue.equals(entry.getValue())) {
                        continue;
                    }
                }
                return false;
            } catch (DocumentException e) {
                return false;
            }
        }
        return true;
    }

}
