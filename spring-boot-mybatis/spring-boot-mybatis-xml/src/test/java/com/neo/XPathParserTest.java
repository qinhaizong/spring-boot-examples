package com.neo;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.session.Configuration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

public class XPathParserTest {

    @Test
    public void testConfigurationParser() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        String resource = "xsd/mybatis-config.xml";
        XPathParser parser = new XPathParser(getDocument(resource), true, new Properties());
        Constructor<XMLConfigBuilder> constructor = XMLConfigBuilder.class.getDeclaredConstructor(XPathParser.class, String.class, Properties.class);
        constructor.setAccessible(true);
        XMLConfigBuilder xmlConfigBuilder = constructor.newInstance(parser, "div", new Properties());
        Configuration configuration = xmlConfigBuilder.parse();
        Assert.assertNotNull(configuration);
    }


    @Test
    public void testMapperParser() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException, SAXException, ParserConfigurationException {
        String resource = "xsd/UserMapper.xml";
        XPathParser parser = new XPathParser(getDocument(resource), true, new Properties());
        Constructor<XMLMapperBuilder> constructor = XMLMapperBuilder.class.getDeclaredConstructor(XPathParser.class, Configuration.class, String.class, Map.class);
        constructor.setAccessible(true);
        Configuration configuration = new Configuration();
        XMLMapperBuilder builder = constructor.newInstance(parser, configuration, resource, configuration.getSqlFragments());
        builder.parse();
        Assert.assertNotNull(configuration.getSqlFragments());
    }

    private Document getDocument(String resource) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(true);
        factory.setIgnoringComments(true);
        factory.setIgnoringElementContentWhitespace(true);
        factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", "http://www.w3.org/2001/XMLSchema");
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) throws SAXException {

            }

            @Override
            public void error(SAXParseException exception) throws SAXException {

            }

            @Override
            public void fatalError(SAXParseException exception) throws SAXException {

            }
        });
        return builder.parse(new ClassPathResource(resource).getInputStream());
    }
}
