package ProjSrc;

import geom.Point;

public class City 
{
	/**
	 * Class created: 14.12.17 10:30 pm
	 * Next modification: 16.12.17 10:00 pm
	 * 		addded getters-setters
	 */
	 private String cityName;
	 private String countryName;
	 private Point cityCoord;
	 private float cityLat;
	 private float cityLon;
	 private float cityArea;
	 private int cityPop;
	 private float cityPopDen;
	 private float cityForResPer;
	 private float cityGDP;
	 
	 //Setters
	 public void setCityName(String varCityName)
	 {
		 cityName = varCityName;
	 }	 
	 public void setCountryName(String varCountryName)
	 {
		 countryName = varCountryName;
	 }
	 public void setCityCoord(String varCityCoord)
	 {
		 cityCoord = new Point();
		 String[] splitCoord = varCityCoord.split(" ");
		 cityCoord.setStrokeWidth(1/2);
		 cityCoord.setX(Double.parseDouble(splitCoord[1]));
		 cityCoord.setY(Double.parseDouble(splitCoord[0]));
	 }
	 public void setCityArea(float varCityArea)
	 {
		 cityArea = varCityArea;
	 }
	 public void setCityPop(int varCityPop)
	 {
		 cityPop = varCityPop;
	 }
	 public void setCityPopDen(float varCityPopDen)
	 {
		 cityPopDen = varCityPopDen;
	 }	 
	 public void setCityForResPer(float varCityForResPer)
	 {
		 cityForResPer = varCityForResPer;
	 }	 
	 public void setCityGDP(float varCityGDP)
	 {
		 cityGDP = varCityGDP;
	 }
	 
	 //Getters
	 public String getCityName()
	 {
		 return cityName;
	 }
	 public String getCountryName()
	 {
		 return countryName;
	 }
	 public Point getCityCoord()
	 {
		 return cityCoord;
	 }
	 
	 public float getCityArea()
	 {
		 return cityArea;
	 }
	 public int getCityPop()
	 {
		 return cityPop;
	 }
	 public float getCityPopDen()
	 {
		 return cityPopDen;
	 }
	 public float getCityForResPer()
	 {
		 return cityForResPer;
	 }
	 public float getCityGDP()
	 {
		 return cityGDP;
	 }


}
