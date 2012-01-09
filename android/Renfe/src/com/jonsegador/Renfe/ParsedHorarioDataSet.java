/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 *
 * Jon Segador <jonseg@gmail.com>
 *
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.jonsegador.Renfe;

public class ParsedHorarioDataSet {
	private String hsalida = null;
    private String hllegada = null;
    private String htiempo = null;
    private String hlineasalida = null;
    private String htransbordo = null;
    private String htransbordolinea = null;

    public String getHoraSalida() {
      return hsalida;
    }
    public void setHoraSalida(String hsalida) {
     this.hsalida = hsalida;
    }    
    
    public String getHoraLlegada() {
     return hllegada;
    }
    public void setHoraLlegada(String hllegada) {
     this.hllegada = hllegada;
    }
    
    public String getTiempo() {
     return htiempo;
    }
    public void setTiempo(String htiempo) {
     this.htiempo = htiempo;
    }
       
    public String getLineaSalida() {
     return hlineasalida;
    }
    public void setLineaSalida(String hlineasalida) {
     this.hlineasalida = hlineasalida;
    }    
    
    public String getTransbordo() {
     return htransbordo;
    }
    public void setTransbordo(String htransbordo) {
     this.htransbordo = htransbordo;
    }      
    
    public String getTransbordoLinea() {
     return htransbordolinea;
    }
    public void setTransbordoLinea(String htransbordolinea) {
     this.htransbordolinea = htransbordolinea;
    }     
    
    public String toString(){
         return "hsalida = " + this.hsalida + "\n" + "hllegada = " + this.hllegada;
                   
    }
}
