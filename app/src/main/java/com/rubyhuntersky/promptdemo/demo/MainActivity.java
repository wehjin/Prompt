package com.rubyhuntersky.promptdemo.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.rubyhuntersky.promptdemo.prompt.basic.ColorPrompt;
import com.rubyhuntersky.promptdemo.prompt.core.Dimension;
import com.rubyhuntersky.promptdemo.prompt.core.Palette;
import com.rubyhuntersky.promptdemo.prompt.core.Presentation;
import com.rubyhuntersky.promptdemo.prompt.core.Prompt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class MainActivity extends AudienceActivity {

    private Prompt<?, ?> prompt;
    private Presentation presentation;
    private final Palette palette = new MainPalette();

    @Override
    protected Palette getPalette() {
        return palette;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.prompt = ColorPrompt.PRIMARY.inset(Dimension.READABLE.multiply(2));

        try {
            final Document document = getDocument();
            Log.d(MainActivity.class.getSimpleName(), "Prompt: " + getStringFromDocument(document));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private Document getDocument() throws ParserConfigurationException {
        final Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        final Element promptElement = document.createElement("Activity");
        for (Element element : prompt.toElements(document)) {
            promptElement.appendChild(element);
        }
        document.appendChild(promptElement);
        return document;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presentation = prompt.present(this, null);
    }

    @Override
    protected void onStop() {
        presentation.end();
        super.onStop();
    }

    public String getStringFromDocument(Document doc) {
        try {
            DOMSource domSource = new DOMSource(doc);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.transform(domSource, result);
            return writer.toString();
        } catch (TransformerException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
