package com.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Note;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView xml;
	private TextView xml1;
	private TextView xml2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		xmlread_top10();
		xmlread();
		xmlinput();
	}

	

	private void xmlread_top10() {
		xml1 = (TextView) findViewById(R.id.xml1);
		try {

			String path = "http://wcf.open.cnblogs.com/blog/sitehome/recent/10";
			URL url = new URL(path);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setRequestMethod("GET");
			System.out.println(conn.getResponseCode());
			if (200 == conn.getResponseCode()) {
				DocumentBuilderFactory builderFactory = DocumentBuilderFactory
						.newInstance();
				DocumentBuilder builder = builderFactory.newDocumentBuilder();
				InputStream instream = conn.getInputStream();
				// lst = parseXML(instream);
				Document document = builder.parse(instream);
				Element element = document.getDocumentElement();
				NodeList list = element.getElementsByTagName("");
				
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void xmlread() {
		xml = (TextView) findViewById(R.id.xml);
		try {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document docment = builder.parse(getAssets().open("languages.xml"));
			Element element = docment.getDocumentElement();
			NodeList list = element.getElementsByTagName("lan");
			for (int i = 0; i < list.getLength(); i++) {
				Element lan = (Element) list.item(i);
				xml.append(lan.getAttribute("id") + "\n");
				xml.append(lan.getElementsByTagName("name").item(0)
						.getTextContent()
						+ "\n");
				xml.append(lan.getElementsByTagName("ide").item(0)
						.getTextContent()
						+ "\n");

			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void xmlinput() {
		xml2 = (TextView) findViewById(R.id.xml2);
		try {

			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document newxml = builder.newDocument();
			Element languages = newxml.createElement("Languages");
			languages.setAttribute("cat", "it");
			// 第一个
			Element lan1 = newxml.createElement("lan");
			lan1.setAttribute("id", "1");

			Element name1 = newxml.createElement("name");
			name1.setTextContent("Java");
			Element ide1 = newxml.createElement("ide");
			ide1.setTextContent("Eclipse");

			lan1.appendChild(name1);
			lan1.appendChild(ide1);
			languages.appendChild(lan1);
			// 第二个
			Element lan2 = newxml.createElement("lan");
			lan2.setAttribute("id", "2");

			Element name2 = newxml.createElement("name");
			name2.setTextContent("Swift");
			Element ide2 = newxml.createElement("ide");
			ide2.setTextContent("Xcode");

			lan2.appendChild(name2);
			lan2.appendChild(ide2);
			languages.appendChild(lan2);
			// 第三个
			Element lan3 = newxml.createElement("lan");
			lan3.setAttribute("id", "3");

			Element name3 = newxml.createElement("name");
			name3.setTextContent("C#");
			Element ide3 = newxml.createElement("ide");
			ide3.setTextContent("Visual Studio");

			lan3.appendChild(name3);
			lan3.appendChild(ide3);
			languages.appendChild(lan3);

			newxml.appendChild(languages);

			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("encoding", "utf-8");
			StringWriter sw = new StringWriter();
			transformer.transform(new DOMSource(newxml), new StreamResult(sw));
			xml2.setText(sw.toString());

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
