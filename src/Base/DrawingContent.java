package Base;

import java.util.ArrayList;

import geom.Circle;
import geom.Label;
import geom.Point;
import geom.Polygon;
import geom.Polyline;
import geom.Rectangle;

public class DrawingContent {
	
	private ArrayList<Rectangle> rectangles = new ArrayList<Rectangle>();
	private ArrayList<Circle> circles = new ArrayList<Circle>();
	private ArrayList<Point> points = new ArrayList<Point>();
	private ArrayList<Polyline> polylines = new ArrayList<Polyline>();
	private ArrayList<Polygon> polygons = new ArrayList<Polygon>();
	private ArrayList<Label> labels = new ArrayList<Label>();
	
	
	public ArrayList<Rectangle> getRectangles() {
		return rectangles;
	}
	public void setRectangles(ArrayList<Rectangle> rectangles) {
		this.rectangles = rectangles;
	}
	public ArrayList<Circle> getCircles() {
		return circles;
	}
	public void setCircles(ArrayList<Circle> circles) {
		this.circles = circles;
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}
	public ArrayList<Polyline> getPolylines() {
		return polylines;
	}
	public void setPolylines(ArrayList<Polyline> polylines) {
		this.polylines = polylines;
	}
	public ArrayList<Polygon> getPolygons() {
		return polygons;
	}
	public void setPolylons(ArrayList<Polygon> polylons) {
		this.polygons = polylons;
	}
	public ArrayList<Label> getLabels() {
		return labels;
	}
	public void setLabels(ArrayList<Label> labels) {
		this.labels = labels;
	}

	
	
	
}
