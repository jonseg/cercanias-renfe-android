/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 *
 * Jon Segador <jonseg@gmail.com>
 *
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.jonsegador.Renfe;

import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class HorariosActivity extends Activity{
	
    Thread t;
    ProgressDialog dialog;	
	
	TextView info_transbordo;
	String fulldate;
	
	TableLayout tl;
	Vector<ParsedHorarioDataSet> test;
	
    String final_dia;
    String final_mes;
    String annio;	
    
    // URL completa al script que parsea la web de renfe para obtener los horarios
    String parser_url = "http://mipaginaweb.com/renfe/parser.php";
    
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.horario);

		tl = (TableLayout)findViewById(R.id.myTableLayout);			
		
        final int nucleo_id = getIntent().getIntExtra("nucleo_id", 0);
        final int station1_id = getIntent().getIntExtra("station1_id", 0);
        final int station2_id = getIntent().getIntExtra("station2_id", 0);
        
        final String station1_label = getIntent().getStringExtra("station1_name");
        final String station2_label = getIntent().getStringExtra("station2_name");
        
        TextView info_stations = (TextView) findViewById(R.id.info_stations);
        TextView info_date = (TextView) findViewById(R.id.info_date);
        info_transbordo = (TextView) findViewById(R.id.info_transbordo);
        
        final_dia = getIntent().getStringExtra("day");
        final_mes = getIntent().getStringExtra("month");
        annio = getIntent().getStringExtra("year");

		Button change_btn = (Button) findViewById(R.id.btn_change);
		change_btn.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {

            	Intent intent = new Intent(getApplicationContext(), HorariosActivity.class);
    	        intent.putExtra("nucleo_id", nucleo_id);
    	        intent.putExtra("station1_id", station2_id);
    	        intent.putExtra("station2_id", station1_id);

		        intent.putExtra("station1_name", station2_label);
    	        intent.putExtra("station2_name", station1_label);
    	        
		        intent.putExtra("day", final_dia);
    	        intent.putExtra("month", final_mes);
    	        intent.putExtra("year", annio);    	        
    	        
    	        startActivity(intent);
            	  
              }
        });        
        
        info_stations.setText(station1_label + " - " + station2_label);
        info_date.setText(final_dia + "/" + final_mes + "/" + annio);
        
        fulldate = annio + final_mes + final_dia;
        
        showDialog(0);
        t=new Thread() {
              public void run() {
            	  loadSchedule(nucleo_id, station1_id, station2_id, fulldate);
              }
        };        
        t.start();        
        
	}
	
	
	public void loadSchedule(int nucleo_id, int station1_id, int station2_id, String fulldate) {
		
		
        try {
        	
          URL url = new URL(parser_url + "?nucleo_id=" + nucleo_id + "&station1_id=" + station1_id + "&station2_id=" + station2_id + "&day=" + final_dia+ "&month=" + final_mes+ "&year=" + annio);

          SAXParserFactory spf = SAXParserFactory.newInstance();
          SAXParser sp = spf.newSAXParser();

          XMLReader xr = sp.getXMLReader();
          HorarioHandler myExampleHandler = new HorarioHandler();
          xr.setContentHandler(myExampleHandler);
          
          xr.parse(new InputSource(url.openStream()));
          
          test = myExampleHandler.getParsedExampleDataSets();
          
          Message myMessage=new Message();
          myMessage.obj="SUCCESS";
          handler.sendMessage(myMessage);	
          
     } catch (Exception e) {
          //Log.e("Renfe", "Error", e);
     }        
     
	}
	
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	String loginmsg=(String)msg.obj;
        	if(loginmsg.equals("SUCCESS")) {
        		
                int i=0;
                
                for(ParsedHorarioDataSet v:test)
                {
                	
            		TableRow tr = new TableRow(HorariosActivity.this);

            		if(i%2!=0)
            			tr.setBackgroundColor((Color.argb(0xFF, 0xD9, 0xE2, 0xF3)));
            		
            		TextView a = new TextView(HorariosActivity.this);
            		a.setText(v.getHoraSalida());
            		a.setTextSize(15);
            		a.setTextColor((Color.argb(0xFF, 0, 0, 0)));
            		a.setPadding(3, 3, 5, 3);
            		a.setGravity(Gravity.CENTER_HORIZONTAL);

            		TextView b = new TextView(HorariosActivity.this);
            		b.setText(v.getHoraLlegada());
            		b.setTextSize(15);
            		b.setTextColor((Color.argb(0xFF, 0, 0, 0)));
            		b.setPadding(3, 3, 3, 3);
            		b.setGravity(Gravity.CENTER_HORIZONTAL);
            		
            		TextView d = new TextView(HorariosActivity.this);
            		d.setText(v.getTiempo());
            		d.setTextSize(15);
            		d.setTextColor((Color.argb(0xFF, 0, 0, 0)));
            		d.setPadding(3, 3, 3, 3);
            		d.setGravity(Gravity.CENTER_HORIZONTAL);        		

            		TextView e = new TextView(HorariosActivity.this);
            		e.setText(v.getLineaSalida());
            		e.setTextSize(15);
            		e.setTextColor((Color.argb(0xFF, 0, 0, 0)));
            		e.setPadding(3, 3, 3, 3);
            		e.setGravity(Gravity.CENTER_HORIZONTAL);        
            		
            		
            		if(!v.getTransbordo().equals("")){
            			
            			char letra;
            			String texto="",nuevoTexto="", resto="";
            			//Borrando los espacios en blanco
            			texto=v.getTransbordo().toLowerCase();
            			//cogiendo un string a partir de la segunda letra
            			resto=texto.substring(1);
            			//cogiendo la primera letra en un char
            			letra=texto.charAt(0);
            			//poniendo dicha letra en mayusculas
            			letra = Character.toUpperCase(letra);
            			//concatenando todo
            			nuevoTexto=letra+resto + " (Línea " + v.getTransbordoLinea() + ")";        			
            			
            			info_transbordo.setVisibility(0);
      	        		info_transbordo.setText("Transbordo en:\n" + nuevoTexto);
            		}
            		else{
            			info_transbordo.setHeight(1);
            		}
            		
            		tr.addView(a);
            		tr.addView(b);
            		tr.addView(d);
            		tr.addView(e);
            		
            		tl.addView(tr);  
            		
            		i++;
                	
                }
                
                // No siempre es culpa de mi aplicacion
                if(i==0){
                	Toast.makeText(getApplicationContext(), "La web de Renfe no responde", Toast.LENGTH_SHORT).show();
                }
        		
        		
              removeDialog(0);
        	}
        }
    };	
	
    @Override
    protected Dialog onCreateDialog(int id) {
          switch (id) {
                case 0: {
                      dialog = new ProgressDialog(this);
                      dialog.setMessage("Cargando...");
                      dialog.setIndeterminate(true);
                      dialog.setCancelable(true);
                      return dialog;
                }
          }
          return null;
    }   	
	
	
}
