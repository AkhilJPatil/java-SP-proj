package ProjSrc;

import java.awt.Color;
import java.io.IOException;

import Base.*;
import geom.Label;
import geom.Point;
import geom.Polyline;
import geom.Rectangle;
import geom.Circle;

/*********
 * 
 * Project started!!! 14.12.17 10:00 pm
 * Next Modification 16.12.17 10:00 pm
 * 		Added logic to store the file string into city obj; updated CSV file; display contents of the city objects
 * Modification 18.12.17 10:40 pm
 * 		Added function f_mkBoundingBox to calculate the bounding box
 * 		Added/started with function f_scale to find the scaling factor and then scale the Lat-Long of each city coordinate
 * Modification 19.12.17 6:30 pm
 * 		Took file name as command line argument instead of hard code
 * 		Added offset functions
 *      extended the scaling function
 *      created the translation function and plotted the cities to the frame.
 * Modification 26.12.17 10:30 am
 * 		Tweaked the frame size and numbers related to scaling and fliping to fit all the coordinates in the frame 
 * 		Included the function logic to separate the frame in 2 parts to plot the demographic data graphs 
 * 		Added functions to plot the demographic data onto the frame.
 * 		Created func PlotDemoData1 and PlotDemoData2
 * Modification 29.12.17 11:50 am
 * 		Including func PlotDemoData3			
 * 
 */

public class MainCall 
{
	public static void main(String[] a) throws IOException
	{
		//accessing and reading the CSV input file
		InputStringReader isr = new InputStringReader(); 
		String strBase = isr.readFileAsString(a[0]);		//"E:\1 Agman sem\SP\JavaFinalProject\Submission1\list_of_cities.csv");
		String str[] = strBase.split(",");
		
		//variable declaration
		int fieldCnt = 8, cityIndex = 0, ctCnt = 10;
		City ctArr [] = new City[ctCnt];						//original city details with Lat-Long
		City frameCtArr[] = new City[ctCnt];					//replica of city with frame coordinates
		SimpleFrame frame = new SimpleFrame(1500,800);     //adding variables instead and making width as 1500
		Rectangle rectBBox = new Rectangle();
		double offsetEast, offsetNorth;
		Point bBoxCenter = new Point();
		Label lbl[] = new Label[12];
		Point tmppt = new Point();
		
		//storing the city data in the array of point objects
		for (int i=fieldCnt;i<str.length;i++)
		{
			ctArr[cityIndex] = new City();
			//filling in string values into the city variables
			ctArr[cityIndex].setCountryName(str[i++]);
			ctArr[cityIndex].setCityName(str[i++]);  
			ctArr[cityIndex].setCityCoord(str[i++]);
			ctArr[cityIndex].setCityArea(Float.parseFloat(str[i++]));
			ctArr[cityIndex].setCityPop(Integer.parseInt(str[i++]));
			ctArr[cityIndex].setCityPopDen(Float.parseFloat(str[i++]));
			ctArr[cityIndex].setCityForResPer(Float.parseFloat(str[i++]));
			ctArr[cityIndex].setCityGDP(Float.parseFloat(str[i]));
			
			cityIndex++;
		}
		
		//city array to make the coordinate transactions
		frameCtArr = ctArr;
		
		//To display the values stored to city objects
		for (int i=0;i<cityIndex;i++)
		{
			System.out.print(" " + ctArr[i].getCityName());
			System.out.print(" " + ctArr[i].getCountryName());
			System.out.print(" " + ctArr[i].getCityCoord().getX() + " " + ctArr[i].getCityCoord().getY()); 
			System.out.print(" " + ctArr[i].getCityArea());
			System.out.print(" " + ctArr[i].getCityPop());
			System.out.print(" " + ctArr[i].getCityPopDen());
			System.out.print(" " + ctArr[i].getCityForResPer());
			System.out.println(" " + ctArr[i].getCityGDP());
		}
		
		//call to the function to create bounding box
		rectBBox = f_mkBoundingBox(frameCtArr);
		
		//calls to functions to shift the coordinates such that the actual west most and south most cities are set 0 coordinate, for the plotting frame
		offsetEast = f_offsetEast(frameCtArr);
		offsetNorth = f_offsetNorth(frameCtArr);
		
		//call to function that would scale the city coordinates
		bBoxCenter = f_scale(frameCtArr, rectBBox, offsetEast, offsetNorth);
		
		//call to translation function
		f_translation(frameCtArr, bBoxCenter);
		
		//plotting demographic data 26.12.17
		//plotting a separation line from the points
		f_separateFrame(frameCtArr,frame);
		
		f_plotDemoData1(ctArr,frame);
		
		f_plotDemoData2(ctArr,frame);

		f_plotDemoData3(ctArr,frame);
		
		//plotting of city coordinates on the frame
		for (int i = 0;i<ctArr.length;i++)
		{
			
			frame.addToPlot(ctArr[i].getCityCoord());
			lbl[i] = new Label();
			lbl[i].setText((i+1)+". "+ctArr[i].getCityName());
			lbl[i].setPosition(ctArr[i].getCityCoord());	//actual lbl positions are altered for aesthetics
			lbl[i].setStrokeColor(Color.black);
			frame.addToPlot(lbl[i]);
		}
		
		lbl[10] = new Label();
		lbl[10].setText("(the radii of circles correspond to the area of the city and the saturation of circles proportional to their density)");
		tmppt.setX(250);
		tmppt.setY(750);
		lbl[10].setPosition(tmppt);
		lbl[10].setStrokeColor(Color.black);
		frame.addToPlot(lbl[10]);
		

	}
	public static Rectangle f_mkBoundingBox(City varCtArr[])
	{
		/*
		 * Created on - 18.12.17
		 * Desc - this function forms the bounding box for the given data based on the co-ordinates of cities
		 * I/P - array of cities
		 * 
		 */
		
		Rectangle bBox = new Rectangle();
		Point tmpPoint = new Point();
		int nindex = 0, eindex = 0, sindex = 0, windex = 0;
		double nmost = 1000, emost = 0,  smost = 0, wmost = 1000, tmpDist = 0;

		for (int i=0;i<varCtArr.length;i++)
		{
			if(nmost > varCtArr[i].getCityCoord().getY()){
				nmost = varCtArr[i].getCityCoord().getY();
				nindex = i;
			}
			if(emost < varCtArr[i].getCityCoord().getX()){
				emost = varCtArr[i].getCityCoord().getX();
				eindex = i;
			}
			if(smost < varCtArr[i].getCityCoord().getY()){
				smost = varCtArr[i].getCityCoord().getY();
				sindex = i;
			}
			if(wmost > varCtArr[i].getCityCoord().getX()){
				wmost = varCtArr[i].getCityCoord().getX();
				windex = i;
			}
		}
		System.out.println("The northernmost point is the city "+ varCtArr[nindex].getCityName()+" "+varCtArr[nindex].getCityCoord().getX()+","+varCtArr[nindex].getCityCoord().getY());//+ nindex +" ("+ points[nindex][0] +","+ points[nindex][1]+");");
		System.out.println("The easternmost point is the point "+ varCtArr[eindex].getCityName()+" "+varCtArr[eindex].getCityCoord().getX()+","+varCtArr[eindex].getCityCoord().getY());//+ eindex +" ("+ points[eindex][0] +","+ points[eindex][1]+");");
		System.out.println("The southernmost point is the point "+ varCtArr[sindex].getCityName()+" "+varCtArr[sindex].getCityCoord().getX()+","+varCtArr[sindex].getCityCoord().getY());//+ sindex +" ("+ points[sindex][0] +","+ points[sindex][1]+");");
		System.out.println("The westernmost point is the point "+ varCtArr[windex].getCityName()+" "+varCtArr[windex].getCityCoord().getX()+","+varCtArr[windex].getCityCoord().getY());//+ windex +" ("+ points[windex][0] +","+ points[windex][1] +");");

		//setting top-left point for the bonding box
		tmpPoint.setX(varCtArr[windex].getCityCoord().getX());
		tmpPoint.setY(varCtArr[nindex].getCityCoord().getY());
		System.out.println("Top left point: ("+varCtArr[windex].getCityCoord().getX()+","+varCtArr[nindex].getCityCoord().getY()+")");
		bBox.setTopLPt(tmpPoint);
		
		//set width and height of bonding box
			
		bBox.setWidth(Math.abs(varCtArr[eindex].getCityCoord().getX() - varCtArr[windex].getCityCoord().getX()));
		System.out.println("Width: "+ Math.abs(varCtArr[eindex].getCityCoord().getX() - varCtArr[windex].getCityCoord().getX()));
		
		bBox.setHeight(Math.abs(varCtArr[nindex].getCityCoord().getX() - varCtArr[sindex].getCityCoord().getX()));
		System.out.println("Height: "+ Math.abs(varCtArr[nindex].getCityCoord().getY() - varCtArr[sindex].getCityCoord().getY()));
		
		return bBox;
	}
	
	public static double f_offsetEast(City varCtArr[])
	{
		/* Created on - 19.12.17
		 * Function to shift the coordinates of the city such that all the X coordinates/ longitudes have a non-negative values
		 * i/p - array of cities
		 * o/p - offset value
		 * */
		
		double minLong = 0;
		//find the least longitude value/west-most coordinate
		for (int i = 0;i<varCtArr.length;i++)
		{
			if(minLong > varCtArr[i].getCityCoord().getX())
			{
				minLong = varCtArr[i].getCityCoord().getX();
			}
		}
		System.out.println("minLong - " + minLong);
		
		//adding this minLong to the Long coordinate of every city
		for (int i = 0;i<varCtArr.length;i++)
		{
			varCtArr[i].getCityCoord().setX( varCtArr[i].getCityCoord().getX()-minLong);
			System.out.print(" " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 
		}
		return minLong;
	}

	public static double f_offsetNorth(City varCtArr[])
	{
		/* Created on - 19.12.17
		 * Function to shift the coordinates of the city such that minimum Y coordinate/ latitudes have a 0 value
		 * i/p - array of cities
		 * o/p - offset value
		 * */
		
		double minLat = 90;
		//find the least longitude value
		for (int i = 0;i<varCtArr.length;i++)
		{
			if(minLat > varCtArr[i].getCityCoord().getY())
			{
				minLat = varCtArr[i].getCityCoord().getY();
			}
		}
		System.out.println("minLat - " + minLat);
		
		//adding this minLong to the Long coordinate of every city
		for (int i = 0;i<varCtArr.length;i++)
		{
			varCtArr[i].getCityCoord().setY( varCtArr[i].getCityCoord().getY()-minLat);
			System.out.print(" " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 
		}
		return minLat;
	}

	public static Point f_scale(City varCtArr[], Rectangle varBBox, double varOffsetEast, double varOffsetNorth)
	{
		/* Created on - 18.12.17
		 * Function to scale the coordinates of the cities such that they are proportioned to the frame
		 * i/p - array of cities, bounding box, offsets
		 * o/p - scaled center point of the bounding box
		 * */
		double scale=0, scaleX=0, scaleY=0;
		//Scaling 3.3
		scale = 700 / Math.max(varBBox.getHeight(), varBBox.getWidth());
		System.out.println("Scale - " + scale);
		for(int i=0;i<varCtArr.length;i++)
		{
			varCtArr[i].setCityCoord(varCtArr[i].getCityCoord().getY() * scale + " " + varCtArr[i].getCityCoord().getX() * scale);
			System.out.print(" " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 
			//Setting back to old values
			varCtArr[i].setCityCoord(varCtArr[i].getCityCoord().getY() / scale + " " + varCtArr[i].getCityCoord().getX() / scale);
		}
		
		//Scaling 3.4
		scaleX = 700 / varBBox.getWidth();
		scaleY = 700 / varBBox.getHeight();
		System.out.println("ScaleX - " + scaleX + "...ScaleY - " + scaleY);
		for(int i=0;i<varCtArr.length;i++)
		{
			varCtArr[i].setCityCoord(varCtArr[i].getCityCoord().getY() * scaleY + " " + varCtArr[i].getCityCoord().getX() * scaleX);
			System.out.print(" " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 

		}
		
		//scaling the center of the bounding box
		Point bBoxCenter = new Point();
				
		bBoxCenter = varBBox.centroid();
		System.out.println("BBOX center:" + bBoxCenter.getX()+ " " + bBoxCenter.getY());
		
		bBoxCenter.setX(bBoxCenter.getX() - varOffsetEast );		
		bBoxCenter.setY(bBoxCenter.getY() - varOffsetNorth );
		System.out.println("BBOX center offsetting:" + bBoxCenter.getX()+ " " + bBoxCenter.getY());
		
		bBoxCenter.setX(bBoxCenter.getX() * scaleX);
		bBoxCenter.setY(bBoxCenter.getY() * scaleY);
		
		System.out.println("BBOX center scaling:" + bBoxCenter.getX()+ " " + bBoxCenter.getY());
		
		return bBoxCenter;
	}
	
	public static void f_translation(City varCtArr[], Point varBBoxCenter)
	{
		/*
		 * Created in 19.12.17
		 * Desc - the function here identifies if there is any shift to be made of the scaled coordinated with respect to the bounding box
		 * 			this is done keeping the center points in reference, and because of this the buffer on the edges are honored
		 * I/P - array of cities and center of bounding box
		 * O/P - city coordinates after applying the translations
		 */
		
		Point frameCenter = new Point();
		double xTranslation, yTranslation;
		
		frameCenter.setX(400);
		frameCenter.setY(400);
		
		xTranslation = frameCenter.getX() - varBBoxCenter.getX();
		yTranslation = frameCenter.getY() - varBBoxCenter.getY();
		
		System.out.println("xTranslation: "+ xTranslation + "...yTranslation: "+ yTranslation);
		
		//applying translation to the city coordinates
		for(int i=0;i<varCtArr.length;i++)
		{
			System.out.print("Before translation.. " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 
			varCtArr[i].setCityCoord((varCtArr[i].getCityCoord().getY() + yTranslation ) + " " + (varCtArr[i].getCityCoord().getX() + xTranslation));
			System.out.print("after translation.. " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 
		}
		
		//flipping the map around the horizontal center-line
		for(int i=0;i<varCtArr.length;i++)
		{
			varCtArr[i].setCityCoord((750 - varCtArr[i].getCityCoord().getY()) + " " + varCtArr[i].getCityCoord().getX());//made the value 750 to move cities upwards
			System.out.print(" " + varCtArr[i].getCityName());
			System.out.println(" " + varCtArr[i].getCityCoord().getX() + " " + varCtArr[i].getCityCoord().getY()); 
		}
	}
	
	public static void f_separateFrame(City varCtArr[], SimpleFrame varFrame)
	{
		/*
		 * Created on 26.12.17
		 * Desc - Creates a separation line in between the frame so that 2 separate sections are formed
		 * I/P - City array and frame to plot the line on
		 * O/P - Separation line
		 * 
		 */

		double maxLong = 0;
		//find the max longitude value/east-most coordinate
		for (int i = 0;i<varCtArr.length;i++)
		{
			if(maxLong < varCtArr[i].getCityCoord().getX())
			{
				maxLong = varCtArr[i].getCityCoord().getX();
			}
		}
		System.out.println("minLong - " + maxLong);
		
		//plot seperation line
		Polyline sepLine = new Polyline();
		sepLine.setNumPoints(2);
		Point seppt[] = new Point[2];
		
		seppt[0] = new Point();
		seppt[0].setX(maxLong+70);
		seppt[0].setY(0);

		seppt[1] = new Point();
		seppt[1].setX(maxLong+70);
		seppt[1].setY(800);
		
		sepLine.setPoints(seppt);
		sepLine.setStrokeColor(Color.blue);
		sepLine.setStrokeWidth(2);
		varFrame.addToPlot(sepLine);
		
	}
	
	public static void f_plotDemoData1(City varCtArr[], SimpleFrame varFrame)
	{
		/*
		 * Created on 26.12.17
		 * Desc - Function for plotting GDP graph of each of the given cities along with their values
		 * I/P - City array and frame to plot the buffer on
		 * O/P - Graph showing the GDP for each city
		 */
		
		//Hard-coded graphs
		Label grp1lbl = new Label();
		grp1lbl.setText("GDP Comparison (in Bil. €)");
		grp1lbl.setStrokeWidth(5);
		Point grp1Pt = new Point();
		grp1Pt.setX(920);
		grp1Pt.setY(30);
		grp1lbl.setPosition(grp1Pt);
		
		varFrame.addToPlot(grp1lbl);
		
		//geoms for making a graph of cities GDPs
		
		Polyline grpLn = new Polyline();
		
		grpLn.setNumPoints(2);
		Point grppt[] = new Point[2];
		
		grppt[0] = new Point();
		grppt[0].setX(920);
		grppt[0].setY(40);

		grppt[1] = new Point();
		grppt[1].setX(920);
		grppt[1].setY(240);
		
		grpLn.setPoints(grppt);
		grpLn.setStrokeWidth(2);
		grpLn.setStrokeColor(Color.black);
		varFrame.addToPlot(grpLn);

		Label lbl[] = new Label[varCtArr.length*2];
		Rectangle grp1rect[] = new Rectangle[varCtArr.length];
		Point tmppt = new Point();
		for(int i=0;i<varCtArr.length;i++)
		{
			lbl[i] = new Label();
			lbl[i].setText(varCtArr[i].getCityName());
			tmppt.setX(850);
			tmppt.setY(70+(i*18));
			lbl[i].setPosition(tmppt);	//actual lbl positions are altered for aesthetics
			lbl[i].setStrokeColor(Color.black);
			varFrame.addToPlot(lbl[i]);
			
			grp1rect[i] = new Rectangle();
			tmppt.setX(920);
			tmppt.setY(50+(i*18));
			grp1rect[i].setTopLPt(tmppt);
			grp1rect[i].setHeight(18);
			grp1rect[i].setWidth(varCtArr[i].getCityGDP()*0.66);
			grp1rect[i].setFillColor(Color.DARK_GRAY);
			varFrame.addToPlot(grp1rect[i]);
			
			lbl[i+varCtArr.length] = new Label();
			lbl[i+varCtArr.length].setText(varCtArr[i].getCityGDP()+"");
			tmppt.setX(920+(varCtArr[i].getCityGDP()*0.66));
			tmppt.setY(70+(i*18));
			lbl[i+varCtArr.length].setPosition(tmppt);	//actual lbl positions are altered for aesthetics
			lbl[i+varCtArr.length].setStrokeColor(Color.red);
			varFrame.addToPlot(lbl[i+varCtArr.length]);
			
		}
	}

	public static void f_plotDemoData2(City varCtArr[], SimpleFrame varFrame)
	{
		/*
		 * Created on 26.12.17
		 * Desc - Function to print/plot the graph of populations of each of the cities and also plot the proportions of foreign nationals in the population
		 * I/P - City array and frame to plot the buffer on
		 * O/P - Bar graph showing the total population and the percent of foreign nationals for each city
		 * 
		 */
		Label grp1lbl = new Label();
		grp1lbl.setText("Total City Populations (along with Foreign Population %)");
		grp1lbl.setStrokeWidth(5);
		grp1lbl.setStrokeColor(Color.black);
		Point grp1Pt = new Point();
		grp1Pt.setX(920);
		grp1Pt.setY(280);
		grp1lbl.setPosition(grp1Pt);
		
		varFrame.addToPlot(grp1lbl);
		
		//geoms for making a graph of cities GDPs
		
		Polyline grpLn = new Polyline();
		
		grpLn.setNumPoints(2);
		Point grppt[] = new Point[2];
		
		grppt[0] = new Point();
		grppt[0].setX(920);
		grppt[0].setY(300);

		grppt[1] = new Point();
		grppt[1].setX(920);
		grppt[1].setY(500);
		
		grpLn.setPoints(grppt);
		grpLn.setStrokeWidth(2);
		grpLn.setStrokeColor(Color.black);
		varFrame.addToPlot(grpLn);

		Label lbl[] = new Label[varCtArr.length*2];
		Rectangle grp1rect[] = new Rectangle[varCtArr.length*2];
		Point tmppt = new Point();
		for(int i=0;i<varCtArr.length;i++)
		{
			lbl[i] = new Label();
			lbl[i].setText(varCtArr[i].getCityName());
			tmppt.setX(850);
			tmppt.setY(330+(i*18));
			lbl[i].setPosition(tmppt);	//actual lbl positions are altered for aesthetics
			lbl[i].setStrokeColor(Color.black);
			varFrame.addToPlot(lbl[i]);
			
			grp1rect[i] = new Rectangle();
			tmppt.setX(920);
			tmppt.setY(310+(i*18));
			grp1rect[i].setTopLPt(tmppt);
			grp1rect[i].setHeight(18);
			grp1rect[i].setWidth(varCtArr[i].getCityPop()*0.000060);
			grp1rect[i].setFillColor(Color.orange);
			System.out.println(varCtArr[i].getCityName()+" "+(varCtArr[i].getCityPop()*0.000060));
			varFrame.addToPlot(grp1rect[i]);

			grp1rect[i+varCtArr.length] = new Rectangle();
			grp1rect[i+varCtArr.length].setHeight(18);
			grp1rect[i+varCtArr.length].setWidth((varCtArr[i].getCityPop()*0.000060)*varCtArr[i].getCityForResPer()/100);
			tmppt.setX(920+grp1rect[i].getWidth()-grp1rect[i+varCtArr.length].getWidth());
			tmppt.setY(310+(i*18));
			grp1rect[i+varCtArr.length].setTopLPt(tmppt);
			grp1rect[i+varCtArr.length].setFillColor(Color.green);
			varFrame.addToPlot(grp1rect[i+varCtArr.length]);
			
			lbl[i+varCtArr.length] = new Label();
			lbl[i+varCtArr.length].setText(varCtArr[i].getCityForResPer()+" % / "+varCtArr[i].getCityPop());
			tmppt.setX(880+(varCtArr[i].getCityPop()*0.000060));
			tmppt.setY(330+(i*18));
			lbl[i+varCtArr.length].setPosition(tmppt);	//actual lbl positions are altered for aesthetics
			lbl[i+varCtArr.length].setStrokeColor(Color.red);
			varFrame.addToPlot(lbl[i+varCtArr.length]);
			
		}
	}

	public static void f_plotDemoData3(City varCtArr[], SimpleFrame varFrame)
	{
		/*
		 * Created on 29.12.17
		 * Desc - This function plots the 3rd demographic data, it plots the buffer circle around the city point with radius corresponding to the Area 
		 * 			and the circle color saturation proportional to the density of the city. 
		 * I/P - City array and frame to plot the buffer on
		 * O/P - Buffer plot for each city
		 * 
		 */
		Circle cirArr[] = new Circle[varCtArr.length];
		
		for (int i=0;i<varCtArr.length;i++)
		{
			cirArr[i] = new Circle();
			//System.out.println(varCtArr[i].getCityName()+" "+varCtArr[i].getCityArea()+" Pop Den:  "+varCtArr[i].getCityPopDen());
			cirArr[i].setDiameter(varCtArr[i].getCityArea()*1.2/10);
			cirArr[i].setStrokeWidth(2);
			cirArr[i].setStrokeColor(Color.black);
			Point pt = new Point();
			pt.setX(varCtArr[i].getCityCoord().getX()-(cirArr[i].getDiameter()/2));
			pt.setY(varCtArr[i].getCityCoord().getY()-(cirArr[i].getDiameter()/2));
			cirArr[i].setTopLeftPoint(pt);
			
			//code for fill color saturation
			//using ternary operator to fit the density value that are too high and making they saturation the max thats available
			// (varCtArr[i].getCityPopDen()/10000 > 1) ? 1 : varCtArr[i].getCityPopDen()/8000...8k is adjustment to make cities apart from Paris less saturated
			cirArr[i].setFillColor(Color.getHSBColor(0.24f, (float) ((varCtArr[i].getCityPopDen()/8000 > 1) ? 1.0 : varCtArr[i].getCityPopDen()/8000), 0.98f ));
			System.out.println(varCtArr[i].getCityName()+" "+varCtArr[i].getCityPopDen()+" "+(float) ((varCtArr[i].getCityPopDen()/8000 > 1) ? 1.0 : varCtArr[i].getCityPopDen()/8000));
			varFrame.addToPlot(cirArr[i]);
			
		}
		
	}
	
}
