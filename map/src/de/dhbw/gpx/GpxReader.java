package de.dhbw.gpx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;


public class GpxReader
{
private static final SimpleDateFormat gpxDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

public static List<Location> getPoints(File gpxFile)
{
    List<Location> points = null;
    try
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        FileInputStream fis = new FileInputStream(gpxFile);
        Document dom = builder.parse(fis);
        Element root = dom.getDocumentElement();
        NodeList items = root.getElementsByTagName("trkpt");

        points = new ArrayList<Location>();
        
        for(int j = 0; j < items.getLength(); j++)
        {
            Node item = items.item(j);
            NamedNodeMap attrs = item.getAttributes();
            NodeList props = item.getChildNodes();

            Location pt = new Location("test");

            pt.setLatitude(Double.parseDouble(attrs.getNamedItem("lat").getTextContent()));
            pt.setLongitude(Double.parseDouble(attrs.getNamedItem("lon").getTextContent()));
            // set distance from beginning for each point
            Double distance = 0.0;
            if(j>0){
              Double pointDistance = calculateDistance(points.get(j-1), pt);
              distance += pointDistance;
              Bundle extras = new Bundle(1);
              extras.putDouble("distance", distance);
              pt.setExtras(extras);
            }
            else{
            	Bundle extras = new Bundle(1);
                extras.putFloat("distance", 0);
                pt.setExtras(extras);
            }

            for(int k = 0; k<props.getLength(); k++)
            {
                Node item2 = props.item(k);
                String name = item2.getNodeName();
                if(!name.equalsIgnoreCase("time")) continue;
                try
                {
                    pt.setTime((getDateFormatter().parse(item2.getFirstChild().getNodeValue())).getTime());
                }

                catch(ParseException ex)
                {
                    ex.printStackTrace();
                }
            }

            for(int y = 0; y<props.getLength(); y++)
            {
                Node item3 = props.item(y);
                String name = item3.getNodeName();
                if(!name.equalsIgnoreCase("ele")) continue;
                pt.setAltitude(Double.parseDouble(item3.getFirstChild().getNodeValue()));
            }
            
            points.add(pt);

        }

        fis.close();
    }

    catch(FileNotFoundException e)
    {
        e.printStackTrace();
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }

    catch(ParserConfigurationException ex)
    {

    }

    catch (SAXException ex) {
    }

    return points;
}

public static double calculateDistance(Location startPoint, Location endPoint){
	double theta = startPoint.getLongitude() - endPoint.getLongitude();
	  double dist = Math.sin(deg2rad(startPoint.getLatitude())) * Math.sin(deg2rad(endPoint.getLatitude())) + Math.cos(deg2rad(startPoint.getLatitude())) * Math.cos(deg2rad(endPoint.getLatitude())) * Math.cos(deg2rad(theta));
	  dist = Math.acos(dist);
	  dist = rad2deg(dist);
	  dist = dist * 60 * 1.1515;
	  return (dist);
	
}

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts decimal degrees to radians             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double deg2rad(double deg) {
  return (deg * Math.PI / 180.0);
}

/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
/*::  This function converts radians to decimal degrees             :*/
/*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
private static double rad2deg(double rad) {
  return (rad * 180 / Math.PI);
}

public static SimpleDateFormat getDateFormatter()
  {
    return (SimpleDateFormat)gpxDate.clone();
  }

}