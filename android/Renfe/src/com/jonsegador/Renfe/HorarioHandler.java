/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 *
 * Jon Segador <jonseg@gmail.com>
 *
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.jonsegador.Renfe;

import java.util.Vector;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class HorarioHandler extends DefaultHandler{

     public HorarioHandler() {
		super();
		this.MyParsedExampleDataSets = new Vector<ParsedHorarioDataSet>();
	}

     private boolean in_hsalida = false;
	 private boolean in_hllegada = false;
	 private boolean in_htiempo = false;
	 private boolean in_hlineasalida = false;
	 private boolean in_htransbordo = false;
	 private boolean in_htransbordolinea = false;
     
     private ParsedHorarioDataSet DataSet;
     private Vector<ParsedHorarioDataSet> MyParsedExampleDataSets; 
     
     public Vector<ParsedHorarioDataSet> getParsedExampleDataSets() {
         return this.MyParsedExampleDataSets;
     }      

     @Override
     public void startDocument() throws SAXException {

     }

     @Override
     public void endDocument() throws SAXException {

     }

     
	@Override
	public void startElement(String namespaceURI, String localName,
   		 String qName, org.xml.sax.Attributes atts) throws SAXException {
          if (localName.equals("salida")) {
              this.in_hsalida = true;
              DataSet.setHoraSalida(atts.getValue("value"));
          }else if (localName.equals("llegada")) {
               this.in_hllegada = true;
               DataSet.setHoraLlegada(atts.getValue("value"));
          }else if (localName.equals("tiempo")) {
              this.in_htiempo = true;
              DataSet.setTiempo(atts.getValue("value"));
          }else if (localName.equals("lineasalida")) {
              this.in_hlineasalida = true;
              DataSet.setLineaSalida(atts.getValue("value"));     
          }else if (localName.equals("transbordo")) {
              this.in_htransbordo = true;
              DataSet.setTransbordo(atts.getValue("value"));        
          }else if (localName.equals("transbordolinea")) {
              this.in_htransbordolinea = true;
              DataSet.setTransbordoLinea(atts.getValue("value"));                
          }else if (localName.equals("Horario")) {
        	  DataSet = new ParsedHorarioDataSet(); 
          }
     }
     
     @Override
     public void endElement(String namespaceURI, String localName, String qName)
               throws SAXException {
        if (localName.equals("salida")) {
             this.in_hsalida = false;
        }else if (localName.equals("llegada")) {
             this.in_hllegada = false;
        }else if (localName.equals("tiempo")) {
            this.in_htiempo = false;
        }else if (localName.equals("lineasalida")) {
            this.in_hlineasalida = false;     
        }else if (localName.equals("transbordo")) {
            this.in_htransbordo = false;
        }else if (localName.equals("transbordolinea")) {
            this.in_htransbordolinea = false;
        }else if (localName.equals("Horario")) {
        	MyParsedExampleDataSets.add(DataSet);
        }
     }
     
    @Override
    public void characters(char ch[], int start, int length) {
    	
    }

}
