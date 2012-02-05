/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 *
 * Jon Segador <jonseg@gmail.com>
 *
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.jonsegador.Renfe;

import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends Activity{
	
	private Button create_btn;
	private Spinner nucleo;
	private Spinner station1;
	private Spinner station2;
	
	private Spinner day;
	private Spinner month;
	private Spinner year;	
	
	private SharedPreferences mPreferences;
	
	private int nucleo_id = 0;
	private int station1_id = 0;
	private int station2_id = 0;
	
	private int nucleo_id_to_set = 0;
	private int station1_id_to_set = 0;
	private int station2_id_to_set = 0;	
	
	boolean can_change = false;

	
	// Seguramente esto vaya mucho mejor en un fichero a parte
	
	private String [][] actual_station = { 
        	{ "0", "Selecciona primero un núcleo" }
	};	
	
    private String[][] nucleos = { 
           	{ "0", "Seleccionar núcleo" },
        	{ "20", "Asturias" },
    	    { "50", "Barcelona" },
    	    { "60", "Bilbao" },
    	    { "31", "Cádiz" },
    	    { "10", "Madrid" },
    	    { "32", "Málaga" },
    	    { "41", "Murcia" },
    	    { "62", "Santander" },
    	    { "61", "San Sebastián" },
    	    { "30", "Sevilla" },
    	    { "40", "Valencia" },
    	    { "70", "Zaragoza" }
    	};	

        private String[][] empty_nucleo = { 
        	{ "0", "Selecciona primero un núcleo" }
    	};	  
        
        private String[][] station20 = {
    		{ "15205", "Ablaña" },
    		{ "16403", "Avilés" },
    		{ "16006", "Barros" },
    		{ "15401", "Calzada Asturias" },
    		{ "15120", "Campomanes" },
    		{ "16302", "Cancienes" },
    		{ "16010", "Ciaño" },
    		{ "15210", "El Caleyo" },
    		{ "16011", "El Entrego" },
    		{ "16301", "Ferroñes" },
    		{ "15410", "Gijón (Sanz Crespo)" },
    		{ "15121", "La Cobertoria" },
    		{ "15217", "La Corredoria" },
    		{ "16008", "La Felguera" },
    		{ "15119", "La Frecha" },
    		{ "15206", "La Pereda-Riosa" },
    		{ "16402", "La Rocica" },
    		{ "15209", "Las Segadas" },
    		{ "15218", "Llamaquique" },
    		{ "16406", "Los Campos" },
    		{ "15300", "Lugo de Llanera" },
    		{ "15212", "Lugones" },
    		{ "15203", "Mieres-Puente" },
    		{ "15303", "Moteana" },
    		{ "16400", "Nubledo" },
    		{ "15207", "Olloniego" },
    		{ "15211", "Oviedo" },
    		{ "16005", "Peña Rubia" },
    		{ "15122", "Pola de Lena" },
    		{ "15118", "Puente L.Fierros" },
    		{ "16009", "Sama" },
    		{ "16405", "San Juan de Nieva" },
    		{ "16001", "Santa Eulalia M." },
    		{ "15202", "Santullano" },
    		{ "15302", "Serin" },
    		{ "15208", "Soto de Rey" },
    		{ "16002", "Tudela-Veguin" },
    		{ "15200", "Ujo" },
    		{ "15400", "Veriña" },
    		{ "15301", "Villabona Astur" },
    		{ "15305", "Villabona-Tabladiell" },
    		{ "16401", "Villalegre" },
    		{ "15123", "Villallana" }
        };    
        
        private String[][] station50 = {
    		{ "72400", "Aeroport" },
    		{ "79600", "Arenys de Mar" },
    		{ "79404", "Badalona" },
    		{ "77106", "Balenya-Els Hostalets" },
    		{ "77107", "Balenya-Tona-Seva" },
    		{ "78705", "Barbera del Valles" },
    		{ "78804", "Barcelona Arc de Triomf" },
    		{ "79009", "Barcelona El Clot-Arago" },
    		{ "78806", "Barcelona-La Sagrera-Meridiana" },   		
    		{ "71802", "Barcelona Passeig de Gracia" },
    		{ "78805", "Barcelona Placa de Catalunya" },
    		{ "78802", "Barcelona Sant Andreu Arenal" },
    		{ "79004", "Barcelona Sant Andreu Comtal" },
    		{ "71801", "Barcelona Sants" },
    		{ "79400", "Barcelona-Estaçio França" },
    		{ "71708", "Bellvitge" },
    		{ "79606", "Blanes" },
    		{ "77112", "Borgonya" },
    		{ "79412", "Cabrera de Mar-Vilassar de Mar" },
    		{ "71601", "Calafell" },
    		{ "79502", "Caldes D'Estrac" },
    		{ "79603", "Calella" },
    		{ "77301", "Campdevanol" },
    		{ "79601", "Canet de Mar" },
    		{ "79101", "Cardedeu" },
    		{ "78605", "Castellbell-Monistrol de M." },
    		{ "72210", "Castellbisbal" },
    		{ "71705", "Castelldefels" },
    		{ "77105", "Centelles" },
    		{ "78706", "Cerdanyola del Valles" },
    		{ "72503", "Cerdanyola-Universitat" },
    		{ "72303", "Cornella" },
    		{ "71604", "Cubelles" },
    		{ "71603", "Cunit" },
    		{ "79407", "El Masnou" },
    		{ "72211", "El Papiol" },
    		{ "71707", "El Prat de Llobregat" },
    		{ "72201", "El Vendrell" },
    		{ "72203", "Els Monjos" },
    		{ "77103", "Figaro" },
    		{ "71703", "Garraf" },
    		{ "71706", "Gava" },
    		{ "72208", "Gelida" },
    		{ "79100", "Granollers Centre" },
    		{ "77006", "Granollers-Canovelles" },
    		{ "79105", "Gualba" },
    		{ "79107", "Hostalric" },
    		{ "72202", "L'Arboc" },
    		{ "72305", "L'Hospitalet de Llobregat" },
    		{ "77114", "La Farga Bebié" },
    		{ "77102", "La Garriga" },
    		{ "72205", "La Granada" },
    		{ "79011", "La Llagosta" },
    		{ "77306", "La Molina" },
    		{ "77310", "La Tour de Carol" },
    		{ "72206", "Lavern-Subirats" },
    		{ "77100", "Les Franqueses del Valles" },
    		{ "79109", "Les Franqueses-Granollers Nord" },
    		{ "79102", "Llinars del Valles" },
    		{ "79200", "Macanet-Massanes" },
    		{ "79605", "Malgrat de Mar" },
    		{ "77110", "Manlleu" },
    		{ "78600", "Manresa" },
    		{ "72209", "Martorell" },
    		{ "79500", "Mataro" },
    		{ "72300", "Molins de Rei" },
    		{ "79006", "Mollet-Sant Fost" },
    		{ "77004", "Mollet-Santa Rosa" },
    		{ "78708", "Moncada I Reixac-Manresa" },
    		{ "78707", "Moncada I Reixac-Sta. Maria" },
    		{ "78800", "Montcada Bifucarcio" },
    		{ "79005", "Montcada I Reixac" },
    		{ "77002", "Montcada-Ripollet" },
    		{ "79405", "Montgat" },
    		{ "79406", "Montgat Nord" },
    		{ "79007", "Montmelo" },
    		{ "79408", "Ocata" },
    		{ "79103", "Palautordera" },
    		{ "77005", "Parets del Valles" },
    		{ "79604", "Pineda de Mar" },
    		{ "77304", "Planoles" },
    		{ "71704", "Platja Castelldefels" },
    		{ "79409", "Premia de Mar" },
    		{ "77309", "Puigcerda" },
    		{ "77303", "Ribes Freser" },
    		{ "79106", "Riells I Viabrea-Breda" },
    		{ "77200", "Ripoll" },
    		{ "72501", "Rubi" },
    		{ "78704", "Sabadell Centre" },
    		{ "78709", "Sabadell Nord" },
    		{ "78703", "Sabadell Sud" },
    		{ "79403", "Sant Adria de Besos" },
    		{ "79501", "Sant Andreu de Llavaneres" },
    		{ "79104", "Sant Celoni" },
    		{ "72502", "Sant Cugat del Valles" },
    		{ "72301", "Sant Feliu de Llobregat" },
    		{ "72302", "Sant Joan Despi" },
    		{ "77104", "Sant Marti de Centelles" },
    		{ "78610", "Sant Miquel de Gonteres" },
    		{ "79602", "Sant Pol de Mar" },
    		{ "77113", "Sant Quirze Besora" },
    		{ "72207", "Sant Sadurni D'Anoia" },
    		{ "71600", "Sant Vicenc de Calders" },
    		{ "78604", "Sant Vicenc de Castellet" },
    		{ "77003", "Santa Perpetua de Mogoda" },
    		{ "79608", "Santa Susanna" },
    		{ "71602", "Segur de Calafell" },
    		{ "71701", "Sitges" },
    		{ "78700", "Terrassa" },
    		{ "78710", "Terrassa Est" },
    		{ "79607", "Tordera" },
    		{ "77111", "Torello" },
    		{ "78801", "Torre del Baro" },
    		{ "77305", "Toses" },
    		{ "77307", "Urxt Alp" },
    		{ "78606", "Vacarisses" },
    		{ "78607", "Vacarisses-Torreblanca" },
    		{ "77109", "Vic" },
    		{ "71709", "Viladecans" },
    		{ "78609", "Viladecavalls" },
    		{ "72204", "Vilafranca del Penedes" },
    		{ "71700", "Vilanova I La Geltru" },
    		{ "79410", "Vilassar de Mar" }
    	};    

        private String[][] station60 = {
    		{ "13118", "Abaroa-San Miguel" },
    		{ "13206", "Ametzola" },
    		{ "13101", "Amurrio" },
    		{ "13117", "Arakaldo" },
    		{ "13108", "Arbide" },
    		{ "13107", "Areta" },
    		{ "13109", "Arrankudiaga" },
    		{ "13111", "Arrigorriaga" },
    		{ "13207", "Autonomía" },
    		{ "13115", "Bakiola" },
    		{ "13112", "Basauri" },
    		{ "13113", "Bidebieta-Basauri" },
    		{ "13200", "Bilbao-Abando" },
    		{ "13400", "Desertu-Barakaldo" },
    		{ "13501", "Galindo" },
    		{ "13508", "Gallarta" },
    		{ "13116", "Iñarratxu" },
    		{ "13402", "La Iberia" },
    		{ "13119", "La Peña" },
    		{ "13106", "Llodio" },
    		{ "13103", "Luiaondo" },
    		{ "13305", "Lutxana-Barakaldo" },
    		{ "13120", "Miribilla" },
    		{ "13506", "Muskiz" },
    		{ "13303", "Olabeaga" },
    		{ "13114", "Ollargan" },
    		{ "13100", "Orduña" },
    		{ "13504", "Ortuella" },
    		{ "13404", "Peñota" },
    		{ "13403", "Portugalete" },
    		{ "13505", "Putxeta" },
    		{ "13509", "Sagrada Familia" },
    		{ "13102", "Salbio" },
    		{ "13208", "San Mamés" },
    		{ "13104", "Santa Cruz de Llodio" },
    		{ "13405", "Santurtzi" },
    		{ "13401", "Sestao" },
    		{ "13502", "Trapaga" },
    		{ "13110", "Ugao-Miraballes" },
    		{ "13507", "Urioste" },
    		{ "13503", "Valle Trapaga-Trapag" },
    		{ "13205", "Zabalburu" },
    		{ "13304", "Zorrotza" }
    	};

    	private String[][] station31 = {
    		{ "51406", "Bahia Sur" },
    		{ "51405", "Cádiz" },
    		{ "51407", "Cortadura" },
    		{ "51400", "El Puerto de Santa Maria" },
    		{ "51409", "Estadio" },
    		{ "51300", "Jerez de la Frontera" },
    		{ "51415", "Las Aletas" },
    		{ "51401", "Puerto Real" },
    		{ "51402", "San Fernando" },
    		{ "51414", "San Severiano" },
    		{ "51404", "Segunda Aguada" },
    		{ "51416", "Universidad" },
    		{ "51417", "Valdelagrana" }
    	};
    		
    	private String[][] station10 = {
    		{ "98305", "Aeropuerto T4"},
    		{ "70103", "Alcala de Henares" },
    		{ "70107", "Alcala de Henares Universidad" },
    		{ "19003", "Alcobendas" },
    		{ "35605", "Alcorcón" },
    		{ "12002", "Alpedrete" },
    		{ "35600", "Aluche" },
    		{ "60200", "Aranjuez" },
    		{ "10001", "Aravaca" },
    		{ "70002", "Asamblea de Madrid-Entrevias" },
    		{ "18000", "Atocha" },
    		{ "10400", "Avila" },
    		{ "70105", "Azuqueca" },
    		{ "12017", "Camorritos" },
    		{ "17009", "Cantoblanco Universidad" },
    		{ "12006", "Cercedilla" },
    		{ "12015", "Cercedilla Pueblo" },
    		{ "17000", "Chamartín" },
    		{ "60105", "Ciempozuelos" },
    		{ "12004", "Collado Mediano" },
    		{ "17005", "Colmenar Viejo" },
    		{ "70108", "Coslada" },
    		{ "12023", "Cotos" },
    		{ "35603", "Cuatro Vientos" },
    		{ "18004", "Delicias" },
    		{ "35702", "Doce de Octubre" },
    		{ "12021", "Dos Castillas" },
    		{ "10010", "El Barrial-C.Com.Pozuelo" },
    		{ "60109", "El Casar" },
    		{ "10203", "El Escorial" },
    		{ "17003", "El Goloso" },
    		{ "10209", "El Pimpollar" },
    		{ "70003", "El Pozo" },
    		{ "97103", "El Tejar" },
    		{ "35609", "Embajadores" },
    		{ "35601", "Fanjul" },
    		{ "17001", "Fuencarral" },
    		{ "35002", "Fuenlabrada" },
    		{ "98003", "Fuente de la mora" },
    		{ "10104", "Galapagar-La Navata" },
    		{ "37002", "Getafe Centro" },
    		{ "60102", "Getafe Industrial" },
    		{ "37011", "Getafe-Sector Tres" },
    		{ "70200", "Guadalajara" },
    		{ "12008", "Gudillos" },
    		{ "10302", "Guimorcondo" },
    		{ "10300", "Herradon - La Caída" },
    		{ "35012", "Humanes" },
    		{ "70111", "La Garena" },
    		{ "35010", "La Serna" },
    		{ "35608", "Laguna" },
    		{ "35602", "Las Aguilas" },
    		{ "12016", "Las Heras y Los Castaños" },
    		{ "37010", "Las Margaritas Universidad" },
    		{ "10101", "Las Matas" },
    		{ "10207", "Las Navas del marqués" },
    		{ "35610", "Las Retamas" },
    		{ "10005", "Las Rozas" },
    		{ "10202", "Las Zorreras" },
    		{ "35001", "Leganés" },
    		{ "12011", "Los Ángeles de San Rafael" },
    		{ "12005", "Los Molinos" },
    		{ "12001", "Los Negrales" },
    		{ "10007", "Majadahonda" },
    		{ "70104", "Meco" },
    		{ "18003", "Mendez Alvaro" },
    		{ "35606", "Móstoles" },
    		{ "35607", "Móstoles El Soto" },
    		{ "10301", "Navalgrande" },
    		{ "10208", "Navalperal" },
    		{ "12014", "Navas de Riofrio - La Losa" },
    		{ "18002", "Nuevos Ministerios" },
    		{ "35703", "Orcasitas" },
    		{ "12013", "Ortigosa del Monte" },
    		{ "12012", "Otero - Herreros" },	
    		{ "37012", "Parla" },
    		{ "60115", "Parque de Ocio" },
    		{ "35011", "Parque Polvoranca" },
    		{ "10100", "Pinar de Las Rozas" },
    		{ "60103", "Pinto" },
    		{ "18005", "Pirámides" },
    		{ "97100", "Pitis" },
    		{ "10002", "Pozuelo" },
    		{ "10000", "Principe Pio" },
    		{ "35704", "Puente Alcocer" },
    		{ "12020", "Puerto de Navacerrada" },
    		{ "97201", "Ramón y Cajal" },
    		{ "18001", "Recoletos" },
    		{ "10205", "Robledo de Chavela" },
    		{ "60107", "San Critobal de Los Angeles" },
    		{ "60101", "San Cristobal Industrial" },
    		{ "70101", "San Fernando" },
    		{ "35604", "San José de Valderas" },
    		{ "19003", "San Sebastián de los Reyes" },
    		{ "60116", "San Martín de la Vega" },
    		{ "12009", "San Rafael" },
    		{ "10201", "San Yago" },
    		{ "70109", "Santa Eugenia" },
    		{ "10206", "Santa María de la Alameda" },
    		{ "12100", "Segovia" },
    		{ "12018", "Siete Picos" },
    		{ "18101", "Sol" },
    		{ "12007", "Tablada" },
    		{ "70102", "Torrejón de Ardoz" },
    		{ "10103", "Torrelodones" },
    		{ "17004", "Tres Cantos" },
    		{ "19001", "Universidad P.Comillas" },
    		{ "19002", "Valdelasfuentes" },
    		{ "60104", "Valdemoro" },
    		{ "70001", "Vallecas" },
    		{ "12022", "Vaquerizas" },
    		{ "70100", "Vicalvaro" },
    		{ "10200", "Villalba" },
    		{ "37001", "Villaverde-Alto" },
    		{ "60100", "Villaverde-Bajo" },
    		{ "10204", "Zarzalejo" },
    		{ "35009", "Zarzaquemada" }
    	};

    	private String[][] station32 = {
    		{ "54505", "Aeropuerto" },
    		{ "54407", "Aljaima" },
    		{ "54405", "Alora" },
    		{ "54511", "Benalmadena(A.Miel)" },
    		{ "54410", "Campanillas" },
    		{ "54408", "Cartama" },
    		{ "54513", "Carvajal" },
    		{ "54510", "El Pinillo" },
    		{ "54516", "Fuengirola" },
    		{ "54503", "Guadalhorce" },
    		{ "54508", "La Colina" },
    		{ "54518", "Los Alamos" },
    		{ "54515", "Los Boliches" },
    		{ "54412", "Los Prados(Alora)" },
    		{ "54500", "Malaga Maria Zambrano" },
    		{ "54517", "Malaga - Centro Alameda" },
    		{ "54519", "Montemar Alto" },
    		{ "54406", "Pizarra" },
    		{ "54520", "Plaza Mayor" },
    		{ "54506", "San Julian" },
    		{ "54504", "Terminal de Carga" },
    		{ "54514", "Torreblanca" },
    		{ "54509", "Torremolinos" },
    		{ "54512", "Torremuelle" },
    		{ "54501", "Victoria Kent" }
    	};

    	private String[][] station41 = {
    		{ "07004", "Aguilas" },
    		{ "07007", "Aguilas El Labradorcico" },
    		{ "62100", "Albatera-Catral" },
    		{ "06008", "Alcantarilla-Los Romanos" },
    		{ "06002", "Alhama de Murcia" },
    		{ "60911", "Alicante/Alacant.T" },
    		{ "06100", "Almendricos" },
    		{ "62001", "Beniel" },
    		{ "62003", "Callosa de Segura" },
    		{ "62101", "Crevillente" },
    		{ "62102", "Elche Carrus/Elx Carrus" },
    		{ "62103", "Elche Parque/Elx Parc" },
    		{ "62108", "Elche-Mcias." },
    		{ "07003", "Jaravia" },
    		{ "06004", "La Hoya" },
    		{ "06001", "Librilla" },
    		{ "06005", "Lorca San Diego" },
    		{ "06006", "Lorca-Sutullena" },
    		{ "61200", "Murcia del Carmen" },
    		{ "62002", "Orihuela" },
    		{ "06007", "Puerto Lumbreras" },
    		{ "07001", "Pulpi" },
    		{ "62109", "Sant Gabriel" },
    		{ "60913", "Sant Vicent Centre, Apdro" },
    		{ "62104", "Torrellano" },
    		{ "06003", "Totana" },
    		{ "60914", "Universidad de Alicante" }
    	};

    	private String[][] station62 = {
    		{ "14235", "Arenas de Iguña" },
    		{ "14206", "Barcena" },
    		{ "14219", "Boo" },
    		{ "14218", "Guarnizo" },
    		{ "14203", "Lantueno-Santiur." },
    		{ "14211", "Las Caldas de Be." },
    		{ "14209", "Las Fraguas" },
    		{ "14232", "Lombera" },
    		{ "14210", "Los Corrales de Buelna" },
    		{ "14220", "Maliaño" },
    		{ "14207", "Molledo-Portolin" },
    		{ "14221", "Muriedas" },
    		{ "14237", "Muriedas-Bahia" },
    		{ "14231", "Nueva Montaña" },
    		{ "14217", "Parbayon" },
    		{ "14204", "Pesquera" },
    		{ "14234", "Pujayo" },
    		{ "14202", "Reinosa" },
    		{ "14216", "Renedo" },
    		{ "14233", "Rio Ebro" },
    		{ "14208", "Santa Cruz Iguña" },
    		{ "14223", "Santander" },
    		{ "14214", "Sierrapando" },
    		{ "14213", "Torrelavega" },
    		{ "14230", "Valdecilla" },
    		{ "14212", "Viernoles" },
    		{ "14236", "Vioño" },
    		{ "14215", "Zurita" }
    	};

    	private String[][] station61 = {
    		{ "11409", "Alegia de Oria" },
    		{ "11505", "Andoain" },
    		{ "11504", "Andoain Centro" },
    		{ "11502", "Anoeta" },
    		{ "11513", "Ategorrieta" },
    		{ "11404", "Beasain" },
    		{ "11503", "Billabona-Zizurkil" },
    		{ "11305", "Brinkola" },
    		{ "11511", "Donostia-San Sebastián" },
    		{ "11512", "Gros" },
    		{ "11508", "Hernani" },
    		{ "11507", "Hernani Centro" },
    		{ "11514", "Herrara" },
    		{ "11408", "Ikaztegieta" },
    		{ "11600", "Irun" },
    		{ "11406", "Itsasondo" },
    		{ "11306", "Legazpi" },
    		{ "11407", "Legorreta" },
    		{ "11516", "Lezo-Renteria" },
    		{ "11510", "Loiola" },
    		{ "11509", "Martutene" },
    		{ "11405", "Ordizia" },
    		{ "11402", "Ormaiztegui" },
    		{ "11515", "Pasaia" },
    		{ "11500", "Tolosa" },
    		{ "11501", "Tolosa Centro" },
    		{ "11506", "Urnieta" },
    		{ "11518", "Ventas" },
    		{ "11400", "Zumarraga" }
    	};

    	private String[][] station30 = {
    		{ "40121", "Alcolea del Rio" },
    		{ "40118", "Arenillas" },
    		{ "51111", "Bellavista" },
    		{ "43005", "Benacazón" },
    		{ "50702", "Brenes" },
    		{ "43002", "Camas" },
    		{ "51112", "Cantaelgallo" },
    		{ "50701", "Cantillana" },
    		{ "51050", "Cartuja" },
    		{ "40113", "Cazalla-Constantina" },
    		{ "51104", "Don Rodrigo" },
    		{ "51103", "Dos Hermanas" },
    		{ "50704", "El Cáñamo" },
    		{ "40114", "Fábrica de Pedroso" },
    		{ "50602", "Guadajoz" },
    		{ "50703", "La Rinconada" },
    		{ "51101", "La Salud" },
    		{ "51202", "Las Cabezas de San Juan" },
    		{ "51203", "Lebrija" },
    		{ "50600", "Lora del Río" },
    		{ "50700", "Los Rosales" },
    		{ "51010", "Padre Pio Palmete" },
    		{ "51009", "Palacio de Congresos" },
    		{ "40115", "Pedroso" },
    		{ "43027", "Salteras" },
    		{ "51100", "San Bernardo" },
    		{ "43004", "Sanlucar la Mayor" },
    		{ "51003", "Santa Justa" },
    		{ "40122", "Tocina" },
    		{ "51200", "Utrera" },
    		{ "43026", "Valencina - Santiponce" },
    		{ "43003", "Villanueva del Ariscal y Olivares" },
    		{ "40119", "Villanueva del Río y Minas" },
    		{ "51110", "Virgen del Rocio" }
    	};

    	private String[][] station40 = {
    		{ "65005", "Albuixech" },
    		{ "66211", "Aldaia" },
    		{ "64203", "Alfafar-Benetusser" },
    		{ "64105", "Algemesi" },
    		{ "67216", "Algimia-Ciudad" },
    		{ "65209", "Almassora" },
    		{ "65202", "Almenara" },
    		{ "64104", "Alzira" },
    		{ "64107", "Benifaio-Almussafess" },
    		{ "66206", "Bunol" },
    		{ "65207", "Burriana" },
    		{ "64103", "Carcaixent" },
    		{ "65300", "Castello" },
    		{ "64201", "Catarroja" },
    		{ "67211", "Caudiel" },
    		{ "66208", "Cheste" },
    		{ "65204", "Chilches" },
    		{ "66207", "Chiva" },
    		{ "66210", "Circto. Ricardo Tormo" },
    		{ "69104", "Cullera" },
    		{ "65007", "El Puig" },
    		{ "69101", "El Romani" },
    		{ "67221", "Estivella-Albalat" },
    		{ "69110", "Gandia" },
    		{ "67223", "Gilet" },
    		{ "67212", "Jerica-Viver" },
    		{ "64006", "L'Alcudia Crespins" },
    		{ "64007", "L'Enova - Manuel" },
    		{ "65203", "La Llosa" },
    		{ "64102", "La Pobla Llarga" },
    		{ "65201", "Les Valls" },
    		{ "66209", "Loriguilla-Reva" },
    		{ "65006", "Massalfarssar" },
    		{ "64202", "Massanassa" },
    		{ "64003", "Moixent" },
    		{ "65205", "Moncofar" },
    		{ "64005", "Montesa" },
    		{ "67213", "Navajas" },
    		{ "65206", "Nules-Villavieja" },
    		{ "69111", "Platja I Grau Gandia" },
    		{ "65008", "Puzol" },
    		{ "66203", "Rebollar" },
    		{ "66202", "Requena" },
    		{ "65001", "Roca-Cuper" },
    		{ "66201", "S Antonio de R" },
    		{ "65200", "Sagunt" },
    		{ "67214", "Segorbe-Arrabal" },
    		{ "67215", "Segorbe-Ciudad" },
    		{ "66204", "Siete Aguas" },
    		{ "64200", "Silla" },
    		{ "69102", "Sollana" },
    		{ "67217", "Soneja" },
    		{ "69103", "Sueca" },
    		{ "69105", "Tavernes Valldigna" },
    		{ "66200", "Utiel" },
    		{ "65003", "Valencia-Cabanyal" },
    		{ "65000", "Valencia-E. del Nord" },
    		{ "65002", "Valencia-Fuente S.L." },
    		{ "66212", "Valencia-Sant Isidre" },
    		{ "64004", "Vallada" },
    		{ "66205", "Venta M. Siete A" },
    		{ "65208", "Vila-Real" },
    		{ "64100", "Xativa" },
    		{ "69107", "Xeraco" },
    		{ "66214", "Xirivella-Alqueries" },
    		{ "69200", "Xirivella-L'Alter" }
    	};
        
        private String[][] station70 = {
    		{ "70800", "Casetas" },
    		{ "71100", "Miraflores" },
    		{ "70801", "Utebo" },
    		{ "04040", "Zaragoza-Delicias" },
    		{ "70806", "Zaragoza-Portillo" }
        };    
        
        private String[][] days = {
        		{ "01", "1" },
        		{ "02", "2" },
        		{ "03", "3" },
        		{ "04", "4" },
        		{ "05", "5" },
        		{ "06", "6" },
        		{ "07", "7" },
        		{ "08", "8" },
        		{ "09", "9" },
        		{ "10", "10" },
        		{ "11", "11" },
        		{ "12", "12" },
        		{ "13", "13" },
        		{ "14", "14" },
        		{ "15", "15" },
        		{ "16", "16" },
        		{ "17", "17" },
        		{ "18", "18" },
        		{ "19", "19" },
        		{ "20", "20" },
        		{ "21", "21" },
        		{ "22", "22" },
        		{ "23", "23" },
        		{ "24", "24" },
        		{ "25", "25" },
        		{ "26", "26" },
        		{ "27", "27" },
        		{ "28", "28" },
        		{ "29", "29" },
        		{ "30", "30" },
        		{ "31", "31" }
            };      
        
        private String[][] months = {
        		{ "01", "Enero" },
        		{ "02", "Febrero" },
        		{ "03", "Marzo" },
        		{ "04", "Abril" },
        		{ "05", "Mayo" },
        		{ "06", "Junio" },
        		{ "07", "Julio" },
        		{ "08", "Agosto" },
        		{ "09", "Septiembre" },
        		{ "10", "Octubre" },
        		{ "11", "Noviembre" },
        		{ "12", "Diciembre" }
            };      
        
        private String[][] years = {
        		{ "2012", "2012" },
        		{ "2013", "2013" },
        		{ "2014", "2014" },
        		{ "2015", "2015" }
            };
        

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		mPreferences = getSharedPreferences("Renfe", MODE_PRIVATE);
		
		nucleo = (Spinner) this.findViewById(R.id.spinner1);
		station1 = (Spinner) this.findViewById(R.id.spinner2);
		station2 = (Spinner) this.findViewById(R.id.spinner3);
		
		day = (Spinner) this.findViewById(R.id.spinner_day);
		month = (Spinner) this.findViewById(R.id.spinner_month);
		year = (Spinner) this.findViewById(R.id.spinner_year);
		
		
		
        Calendar c = Calendar.getInstance();

        String current_day = Integer.toString(c.get(Calendar.DATE));
        String current_month = Integer.toString(c.get(Calendar.MONTH));
        String current_year2 = Integer.toString(c.get(Calendar.YEAR));	
        
        String current_year = "0";
        
        if(current_year2.equals("2012")){
        	current_year = "0";
        }
        else if(current_year2.equals("2013")){
        	current_year = "1";
        }
        else if(current_year2.equals("2014")){
        	current_year = "2";
        }
        else if(current_year2.equals("2015")){
        	current_year = "2";
        }
        else{
        	current_year = "0";
        }
        
		
		ArrayAdapter<CharSequence> day_adapter = new ArrayAdapter<CharSequence>(
			getApplicationContext(), android.R.layout.simple_spinner_item);
 
        for (int i = 0; i < days.length; i++)
        	day_adapter.add(days[i][1]);
        
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        day.setAdapter(day_adapter);	
        
		ArrayAdapter<CharSequence> month_adapter = new ArrayAdapter<CharSequence>(
			getApplicationContext(), android.R.layout.simple_spinner_item);
 
        for (int i = 0; i < months.length; i++)
        	month_adapter.add(months[i][1]);
        
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(month_adapter);	
        
		ArrayAdapter<CharSequence> year_adapter = new ArrayAdapter<CharSequence>(
			getApplicationContext(), android.R.layout.simple_spinner_item);
 
        for (int i = 0; i < years.length; i++)
        	year_adapter.add(years[i][1]);
        
        year_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(year_adapter);	        
	        
	    
        day.setSelection(Integer.parseInt(current_day)-1);
        month.setSelection(Integer.parseInt(current_month));
        year.setSelection(Integer.parseInt(current_year));
		
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
            this, android.R.layout.simple_spinner_item);
 
        for (int i = 0; i < nucleos.length; i++)
        	adapter.add(nucleos[i][1]);
 
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nucleo.setAdapter(adapter);		
		
        nucleo.setOnItemSelectedListener(nucleoListener);
		
		create_btn = (Button) findViewById(R.id.btn_view);
		create_btn.setOnClickListener(new OnClickListener() {
              public void onClick(View v) {
            	  
            	  // Errores
            	  if(nucleo_id == 0){
            		  Toast.makeText(getApplicationContext(), "Los 3 campos son obligatorios", Toast.LENGTH_SHORT).show();
            	  }
            	  else if(station1_id == station2_id){
            		  Toast.makeText(getApplicationContext(), "Las estaciones de origen y destino no pueden ser iguales", Toast.LENGTH_SHORT).show();
            	  }
            	  else{
            	        Intent intent = new Intent(getApplicationContext(), HorariosActivity.class);
            	        intent.putExtra("nucleo_id", nucleo_id);
            	        intent.putExtra("station1_id", station1_id);
            	        intent.putExtra("station2_id", station2_id);

        		    	int pos1 = station1.getSelectedItemPosition();
        		        String station1_name = actual_station[pos1][1];	     
        		        
        		    	int pos2 = station2.getSelectedItemPosition();
        		        String station2_name = actual_station[pos2][1];	       

        		    	int cday = day.getSelectedItemPosition();
        		        String day_key = days[cday][0];	     
        		    	int cmonth = month.getSelectedItemPosition();
        		        String month_key = months[cmonth][0];	     
        		    	int cyear = year.getSelectedItemPosition();
        		        String year_key = years[cyear][0];	 
        		        
        		        intent.putExtra("day", day_key);
            	        intent.putExtra("month", month_key);
            	        intent.putExtra("year", year_key);

        		        intent.putExtra("station1_name", station1_name);
            	        intent.putExtra("station2_name", station2_name);
            	        
                        SharedPreferences.Editor editor=mPreferences.edit();
                        editor.putInt("nucleo", nucleo_id_to_set);
                        editor.putInt("station1", station1_id_to_set);
                        editor.putInt("station2", station2_id_to_set);
                        editor.commit();
            	        
            	        startActivity(intent);
            		  
            	  }
              }
        });
		
		ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
	            this, android.R.layout.simple_spinner_item);
	 
        for (int i = 0; i < empty_nucleo.length; i++)
        	adapter2.add(empty_nucleo[i][1]);
 
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        station1.setAdapter(adapter2);		
        station2.setAdapter(adapter2);
        
        station1.setOnItemSelectedListener(selectListener2);
        station2.setOnItemSelectedListener(selectListener3);
        
        boolean nucleo_set = mPreferences.contains("nucleo");
        boolean station1_set = mPreferences.contains("station1");
        boolean station2_set = mPreferences.contains("station2");
        
        if(nucleo_set && station1_set && station2_set){
        	can_change = true;
        	nucleo.setSelection(mPreferences.getInt("nucleo", 0));
        }        
        
			
	}
	
	
	
	private OnItemSelectedListener nucleoListener = new OnItemSelectedListener() {
	    public void onItemSelected(AdapterView parent, View v, int position, long id) {

	    	if(nucleo.getSelectedItemPosition() > 0){
	    	
		    	int pos = nucleo.getSelectedItemPosition();
		    	nucleo_id_to_set = pos;
		        nucleo_id = Integer.parseInt(nucleos[pos][0]);
		    	
		    	switch(pos){
			    	case 1:
			    		actual_station = station20;
			    		break;
			    	case 2:
			    		actual_station = station50;
			    		break;
			    	case 3:
			    		actual_station = station60;
			    		break;
			    	case 4:
			    		actual_station = station31;
			    		break;
			    	case 5:
			    		actual_station = station10;
			    		break;
			    	case 6:
			    		actual_station = station32;
			    		break;
			    	case 7:
			    		actual_station = station41;
			    		break;
			    	case 8:
			    		actual_station = station62;
			    		break;
			    	case 9:
			    		actual_station = station61;
			    		break;
			    	case 10:
			    		actual_station = station30;
			    		break;			    		
			    	case 11:
			    		actual_station = station40;
			    		break;			    		
			    	case 12:
			    		actual_station = station70;
			    		break;			    		
			    	default:
			    		actual_station = empty_nucleo;
			    		break;
		    	}
		    	
	    		ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
	    			getApplicationContext(), android.R.layout.simple_spinner_item);
		 
		        for (int i = 0; i < actual_station.length; i++)
		        	adapter2.add(actual_station[i][1]);
		        
		        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        station1.setAdapter(adapter2);
		        
		        station2.setAdapter(adapter2);
		        
		        station1_id = Integer.parseInt(actual_station[0][0]);	 
		        station2_id = Integer.parseInt(actual_station[0][0]);
		        
		        
	    	}
	    	else if(nucleo.getSelectedItemPosition() == 0){
	    	
	    		station1_id = 0;
	    		station2_id = 0;
	    		
	    		ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
	    			getApplicationContext(), android.R.layout.simple_spinner_item);
		 
		        for (int i = 0; i < empty_nucleo.length; i++)
		        	adapter2.add(empty_nucleo[i][1]);
		        
		        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		        station1.setAdapter(adapter2);
		        station2.setAdapter(adapter2);
	    		
	    	}
		        
	    }
	    public void onNothingSelected(AdapterView arg0) {}
    };	
    
	private OnItemSelectedListener selectListener2 = new OnItemSelectedListener() {
	    public void onItemSelected(AdapterView parent, View v, int position, long id) {

	    	if(can_change)
	    	{
	    		station1.setSelection(mPreferences.getInt("station1", 0));
	    	}
	    	
	    	if(station1.getSelectedItemPosition() >= 0){
		    	int pos = station1.getSelectedItemPosition();
		    	station1_id_to_set = pos;
		        station1_id = Integer.parseInt(actual_station[pos][0]);
	    	}
	    }
	    public void onNothingSelected(AdapterView arg0) {}
    };	    
	
	private OnItemSelectedListener selectListener3 = new OnItemSelectedListener() {
	    public void onItemSelected(AdapterView parent, View v, int position, long id) {

	    	if(can_change)
	    	{
	    		station2.setSelection(mPreferences.getInt("station2", 0));
	    		can_change = false;
	    	}
	    	
	    	if(station2.getSelectedItemPosition() >= 0){
		    	int pos = station2.getSelectedItemPosition();
		    	station2_id_to_set = pos;
		        station2_id = Integer.parseInt(actual_station[pos][0]);	    
	    	}
	    }
	    public void onNothingSelected(AdapterView arg0) {}
    };	    
    
}
