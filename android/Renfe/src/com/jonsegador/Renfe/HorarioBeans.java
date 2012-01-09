/*
 * Este fichero es parte de la aplicación Cercanías Renfe para Android
 *
 * Jon Segador <jonseg@gmail.com>
 *
 * Podrás encontrar información sobre la licencia en el archivo LICENSE
 * https://github.com/jonseg/cercanias-renfe-android
 */

package com.jonsegador.Renfe;

public class HorarioBeans implements Comparable<HorarioBeans>{ 

	private String hSalida = "";
	private String hLlegada = "";
	private String hTiempo = "";
	private String hLineaSalida = "";
	private String hTransbordo = "";
	private String hTransbordoLinea = "";
	
    private boolean mSelectable = true;

    public HorarioBeans(String hSalida, String hLlegada) {
    	this.hSalida = hSalida;
    	this.hLlegada = hLlegada;
    }
    
   public boolean isSelectable() {
        return mSelectable;
   }
    
   public void setSelectable(boolean selectable) {
        mSelectable = selectable;
   }
    
   public String getHoraSalida() {
       return hSalida;
  }
  
  public void setHoraSalida(String text) {
	  hSalida = text;
  }   
   
   public String getHoraLlegada() {
        return hLlegada;
   }
   
   public void setHoraLlegada(String text) {
	   hLlegada = text;
   }
   
   public String getTiempo() {
       return hTiempo;
   }
  
   public void setTiempo(String text) {
	   hTiempo = text;
   }
  
   public String getLineaSalida() {
      return hLineaSalida;
   }
 
   public void setLineaSalida(String text) {
	   hLineaSalida = text;
   }  
   
   public String getTransbordo() {
      return hTransbordo;
   }
 
   public void setTransbordo(String text) {
	   hTransbordo = text;
   }    
   
   public String getTransbordoLinea() {
      return hTransbordoLinea;
   }
 
   public void setTransbordoLinea(String text) {
	   hTransbordoLinea = text;
   }     
   
   public int compareTo(HorarioBeans other) {
        if(this.hSalida != null && this.hLlegada != null)
             return this.hSalida.compareTo(other.getHoraSalida());
        else
             throw new IllegalArgumentException();
   }
   
}
