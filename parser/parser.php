<?php

/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 *
 * Jon Segador <jonseg@gmail.com>
 *
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */


// Forzamos un user_agent "normal"
ini_set('user_agent', 'Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.9) Gecko/20071025 Firefox/2.0.0.9');

require 'curl.php';
$curl = new Curl;

$nucleo = $_GET['nucleo_id'];
$o = $_GET['station1_id'];
$d = $_GET['station2_id'];

if((isset($_GET['day']) && isset($_GET['month']) && isset($_GET['year'])) && 
	($_GET['day'] != "" && $_GET['month'] != "" && $_GET['year'] != "")){

        $day = $_GET['day'];
        $month = $_GET['month'];
        $year = $_GET['year'];

        $fecha = $year.$month.$day;

}
else{
        $day = date('d', time());
        $month = date('m', time());
        $year = date('Y', time());

        $fecha = $year.$month.$day;
}

$response = $curl->post('http://horarios.renfe.com/cer/hjcer310.jsp',
        array(
                'nucleo' => $nucleo, // Nucleo
                'i' => 's',
                'I' => 's',
                'cp' => 'NO',
                'CP' => 'NO',
                'TXTInfo'=> '',
                'o'=> $o, // Estacion origen
                'd'=> $d, // Estacion destino
                'df'=> $fecha, // Fecha, formato 20091025 -> 25/10/2009
                'ho'=> '00', // Intervalo de horas
                'hd'=> '26'
        )
);

$url_content = $response->body;

$contenido_url = substr($url_content, strpos($url_content, '<table border="0" width="100%" cellpadding="1" cellspacing="1" align="center">') + strlen('<table border="0" width="100%" cellpadding="1" cellspacing="1" align="center">') );
$contenido_url = substr($contenido_url, 0, strpos($contenido_url, '</table>') );

$contenido_url = substr($contenido_url, strpos($contenido_url, '<td align=center valign="middle" colspan="5">') + strlen('<td align=center valign="middle" colspan="5">'));

$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
$val = substr($val, 0, strpos($val, '</tr>'));

$count = substr_count($val, '<td');

echo "<HorarioList>"."\n\n";

if($count == 5){ // Sin transbordo

	/*
	CABECERA DE LA TABLA EN LA WEB DE RENFE:
	- Hora de Salida
	- Hora de Llegada
	- Línea
	- Tiempo
	*/

	$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
	$val = substr($val, 0, strpos($val, '</tr>'));
	
	$i=0;
	while($val != ""){
		
		if($i==0){
			$contenido_url = substr($contenido_url, strpos($contenido_url, '</tr>') + strlen('</tr>') );
			$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
			$val = substr($val, 0, strpos($val, '</tr>'));
			$i++;
			continue;
		}
		
		
		if(preg_match("/Volver/", $val)){
			break;
		}
		
		echo "<Horario>"."\n";
		
		// Extraemos la hora de salida
		$hs = substr($val, strpos($val, '<td class=color1 align=center>') + strlen('<td class=color1 align=center>'));
		$hs = substr($hs, 0, strpos($hs, '</td>'));
		$hs = strip_tags($hs);
		$hs = trim($hs);		

		// Extraemos la hora de llegada
		$hl = substr($val, strpos($val, '<td class=color2 align=center>') + strlen('<td class=color2 align=center>'));
		$hl = substr($hl, 0, strpos($hl, '</td>'));
		$hl = strip_tags($hl);
		$hl = trim($hl);
		
		// Extraemos la línea de salida
		$li = substr($val, strpos($val, "<td class='color3' >") + strlen("<td class='color3' >"));
		$li = substr($li, 0, strpos($li, '</td>'));
		$li = strip_tags($li);
		$li = trim($li);

		// Extraemos el tiempo de viaje
		$ti = substr($val, strpos($val, "<td class=color1 align='center'>") + strlen("<td class=color1 align='center'>"));
		$ti = substr($ti, 0, strpos($ti, '</td>'));
		$ti = strip_tags($ti);
		$ti = trim($ti);
		
		echo '<salida value="'.$hs.'" />'."\n";
		echo '<llegada value="'.$hl.'" />'."\n";
		echo '<tiempo value="'.$ti.'" />'."\n";
		echo '<lineasalida value="'.$li.'" />'."\n";
		echo '<transbordo value="" />'."\n";
		echo '<transbordolinea value="" />'."\n";
		
		$contenido_url = substr($contenido_url, strpos($contenido_url, '</tr>') + strlen('</tr>') );
	
		$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
		$val = substr($val, 0, strpos($val, '</tr>'));	
		$i++;	
		
		echo "</Horario>"."\n\n";	
		
	}
}
elseif($count == 6){ // Con transbordo

	/*
	CABECERA DE LA TABLA EN LA WEB DE RENFE:
	- Línea
	- Hora de salida en origen
	- Transbordo en
	  - Estacion
	  - Llegada
	  - Salida
	- Hora de Llegada en destino
	- Línea
	- Tiempo de viaje
	*/

	$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
	$val = substr($val, 0, strpos($val, '</tr>'));
	
	// Extraemos la estación de transbordo
	$trans = substr($contenido_url, strpos($contenido_url, "Transbordo en") + strlen("Transbordo en"));
	$trans = substr($trans, strpos($trans, "<td class='cabe' colspan=2 align=center>") + strlen("<td class='cabe' colspan=2 align=center>"));
	$trans = substr($trans, 0, strpos($trans, '</td>'));
	$trans = strip_tags($trans);
	$trans = trim($trans);	
	$trans = utf8_encode($trans);

	$contenido_url = substr($contenido_url, strpos($contenido_url, "<td class='esta' align=center>Salida</td>") + strlen("<td class='esta' align=center>Salida</td>"));

	$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
	$val = substr($val, 0, strpos($val, '</tr>'));
	
	while($val != ""){
		
		if(preg_match("/javascript/", $val)){
			break;
		}
		
		$val = strip_tags($val);
		
		$val_array = explode("\n", $val);
		
		$ol = trim($val_array[1]); // Línea de salida
		$hl = trim($val_array[2]); // Hora de salida
		$hd = isset($val_array[9])?trim($val_array[9]):""; // Hora de llegada
		$dl = isset($val_array[10])?trim($val_array[10]):""; // Línea de transbordo
		$ti = isset($val_array[11])?trim($val_array[11]):""; // Tiempo de viaje

		
		if($hl == "" || $hd == ""){
			$contenido_url = substr($contenido_url, strpos($contenido_url, '</tr>') + strlen('</tr>') );
		
			$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
			$val = substr($val, 0, strpos($val, '</tr>'));
			continue;				
		}
		
		echo "<Horario>"."\n";
		echo '<salida value="'.$hl.'" />'."\n";
		echo '<llegada value="'.$hd.'" />'."\n";
		echo '<tiempo value="'.$ti.'" />'."\n";
		echo '<lineasalida value="'.$ol.'" />'."\n";
		echo '<transbordo value="'.$trans.'" />'."\n";
		echo '<transbordolinea value="'.$dl.'" />'."\n";
		
		$contenido_url = substr($contenido_url, strpos($contenido_url, '</tr>') + strlen('</tr>') );
	
		$val = substr($contenido_url, strpos($contenido_url, '<tr>') + strlen('<tr>'));
		$val = substr($val, 0, strpos($val, '</tr>'));	
		
		echo "</Horario>"."\n\n";
		
	}	
	
}

echo "</HorarioList>";

die();

?>
