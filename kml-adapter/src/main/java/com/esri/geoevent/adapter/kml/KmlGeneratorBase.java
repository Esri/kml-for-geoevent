/*
  Copyright 1995-2014 Esri

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

  For additional information, contact:
  Environmental Systems Research Institute, Inc.
  Attn: Contracts Dept
  380 New York Street
  Redlands, California, USA 92373

  email: contracts@esri.com
*/

package com.esri.geoevent.adapter.kml;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esri.ges.core.geoevent.FieldDefinition;
import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.ges.core.geoevent.GeoEventDefinition;
import com.esri.ges.spatial.Geometry;

public class KmlGeneratorBase
{
  private static final Log      LOG                             = LogFactory.getLog(KmlGeneratorBase.class);
  
  private String altitudeTag;
  private String kmlLabelFieldTag;
  private String headingTag;
  private String rollTag;
  private String tiltTag;
  private String styleUrl;
  private String modelUrl;
  
  protected Placemark createPlacemark(GeoEvent geoevent, boolean updateMode, KmlRequestParameters params, String styleUrl, String modelUrl)
  {
    GeoEventDefinition geoEventDefinition = geoevent.getGeoEventDefinition();
    String pointStyleId = params.getPointStyleId();

    Placemark pm = new Placemark();
//    pm.setName(geoevent.getTrackId());
    pm.setName(getLabelFieldValue(geoevent));
    if (updateMode)
      pm.setTargetId(geoevent.getTrackId());
    else
      pm.setId(geoevent.getTrackId());

    Point pt = new Point();
    ArrayList<Data> extendedData = new ArrayList<Data>();
    Data data;

    Object[] attributes = geoevent.getAllFields();
    for (int i = 0; i < attributes.length; i++)
    {
      Object obj = attributes[i];
      if (obj != null)
      {
        FieldDefinition fieldDefinition = geoEventDefinition.getFieldDefinitions().get(i);
        String fieldName = fieldDefinition.getName();

        if (i == geoevent.getGeoEventDefinition().getGeometryId())
        {
          Geometry geom = geoevent.getGeometry();
          if (geom instanceof com.esri.ges.spatial.Point)
          {
            com.esri.ges.spatial.Point point = (com.esri.ges.spatial.Point) geom;

            if(params.isUse3DModel())
            {
              Model model = createModel(geoevent,point, params, modelUrl);
              pm.setModel(model);
            }
            else
            {
              double zvalue = point.getZ();
              int index = geoevent.getGeoEventDefinition().getIndexOf(altitudeTag);
              if(index > -1)
              {
                zvalue=Double.parseDouble(geoevent.getAllFields()[index].toString());
              }
              pt.setCoordinates(point.getX() + "," + point.getY() + "," + zvalue);
              pt.setAltitudeMode(params.getAltitudeMode());
              pm.setPoint(pt);
            }
          }
          else
          {
            LOG.equals("Unsupported Geometry Type.");
          }
        }
        else if (obj instanceof Date)
        {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
          data = new Data(fieldName, formatter.format((Date) obj));
          extendedData.add(data);
        }
        else
        {
          String attribute = obj.toString();
          if (attribute.contains(","))
            attribute = "\"" + attribute + "\"";
          data = new Data(fieldName, attribute.toString());
          extendedData.add(data);
          if (params.getPointStyleField().length() > 0 && params.getPointStyleField().equals(fieldName))
          {
            pointStyleId = attribute.toString();
          }
        }
      }
    }
    pm.setStyleUrl(styleUrl + pointStyleId);
    pm.setData(extendedData);
    

    return pm;
  }

  protected Placemark createPlacemarkForTrack(GeoEvent geoevent, List<GeoEvent> geoevents, boolean updateMode, String altitudeMode, String styleUrl, String defaultLineStyleId, String lineStyleField)
  {
    GeoEventDefinition geoEventDefinition = geoevent.getGeoEventDefinition();
    String lineStyleId = defaultLineStyleId;

    Placemark pm = new Placemark();
//    pm.setName(geoevent.getTrackId() + "_track");
    pm.setName(getLabelFieldValue(geoevent) + "_track");
    if (updateMode)
      pm.setTargetId(geoevent.getTrackId() + "_track");
    else
      pm.setId(geoevent.getTrackId() + "_track");

    LineString line = new LineString();
    ArrayList<Data> extendedData = new ArrayList<Data>();
    Data data;

    Object[] attributes = geoevent.getAllFields();
    for (int i = 0; i < attributes.length; i++)
    {
      Object obj = attributes[i];
      if (obj != null)
      {
        FieldDefinition fieldDefinition = geoEventDefinition.getFieldDefinitions().get(i);
        String fieldName = fieldDefinition.getName();

        if (i == geoevent.getGeoEventDefinition().getGeometryId())
        {
          line.setExtrude(true);
          line.setTessellate(true);
          line.setAltitudeMode(altitudeMode);
          line.setCoordinates(getTrackLineString(geoevents, true));
        }
        else if (obj instanceof Date)
        {
          SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss aa");
          data = new Data(fieldName, formatter.format((Date) obj));
          extendedData.add(data);
        }
        else
        {
          String attribute = obj.toString();
          if (attribute.contains(","))
            attribute = "\"" + attribute + "\"";
          data = new Data(fieldName, attribute.toString());
          extendedData.add(data);
          if (lineStyleField.length() > 0 && lineStyleField.equals(fieldName))
          {
            lineStyleId = attribute.toString();
          }
        }
      }
    }
    pm.setStyleUrl(styleUrl + lineStyleId);
    pm.setData(extendedData);
    pm.setLineString(line);

    return pm;
  }

  protected String getTrackLineString(List<GeoEvent> geoevents, boolean useZValue)
  {
    String strCoords = "";
    String ptStr = "";
    for (int j = geoevents.size() - 1; j >= 0; j--)
    {
      if (geoevents.get(j).getGeometry() instanceof com.esri.ges.spatial.Point)
      {
        com.esri.ges.spatial.Point pt = (com.esri.ges.spatial.Point) geoevents.get(j).getGeometry();
        if (useZValue)
        {
          // Workaround: currently geomtry doesn't give us z value.  We have to make sure that 
          // the geoevent has a field that is tagged as ALTITUDE which supplies the z value
          double zvalue = pt.getZ();
          int index = geoevents.get(j).getGeoEventDefinition().getIndexOf(altitudeTag);
          if(index > -1)
          {
            zvalue=Double.parseDouble(geoevents.get(j).getAllFields()[index].toString());
          }
          ptStr = pt.getX() + "," + pt.getY() + "," + zvalue;
        }
        else
        {
          ptStr = pt.getX() + "," + pt.getY() + ",NaN";
        }

        if (strCoords.length() == 0)
        {
          strCoords = ptStr;
        }
        else
        {
          strCoords = strCoords + " " + ptStr;
        }
      }
    }
    return strCoords;
  }

  
  protected boolean validateAltitudeMode(String input)
  {
    if (input.equalsIgnoreCase(AltitudeMode.ABSOLUTE) || input.equalsIgnoreCase(AltitudeMode.CLAMPTOSEAFLOOR) || input.equalsIgnoreCase(AltitudeMode.CLAMTOGROUND) || input.equalsIgnoreCase(AltitudeMode.RELATIVETOGROUND) || input.equalsIgnoreCase(AltitudeMode.RELATIVETOSEAFLOOR))
    {
      return true;
    }
    return false;
  }

  protected String getStyleFileUrl(String styleFilename)
  {
    try
    {
      URL url = new URL(styleFilename);
      return styleFilename + "#";
    }
    catch (MalformedURLException e)
    {
      URI u;
      try
      {
        u = new URI(styleUrl);
        return u.toString() + "/" + styleFilename + ".xml#";

      }
      catch (URISyntaxException e1)
      {
        LOG.error(e1);
      }

    }
    catch (Exception e2)
    {
      LOG.error(e2);
    }
    return "";
  }

  protected String getModelUrl(String modelPath)
  {
    try
    {
      URL url = new URL(modelPath);
      return modelPath;
    }
    catch (MalformedURLException e)
    {
      URI u;
      try
      {
        u = new URI(modelUrl);
        return u.toString();

      }
      catch (URISyntaxException e1)
      {
        LOG.error(e1);
      }

    }
    catch (Exception e2)
    {
      LOG.error(e2);
    }
    return "";
  }

  protected boolean isGeoEventDefinitionInRequest(String requestString, String geDef)
  {
    String[] geDefs = requestString.split(",");
    if (geDefs.length > 0)
    {
      for (int i = 0; i < geDefs.length; i++)
      {
        if (geDefs[i].equals(geDef))
        {
          return true;
        }
      }
    }
    return false;
  }
  
  protected Model createModel(GeoEvent geoevent, com.esri.ges.spatial.Point point, KmlRequestParameters params, String modelPath)
  {
    Model model = new Model();
    Location location = new Location();
    Orientation orientation = new Orientation();
    Scale scale = new Scale();
    Link link = new Link();
    
    //location.setId("Location_" + geoevent.getTrackId());
    location.setLongitude(point.getX());
    location.setLatitude(point.getY());
    // Workaround: currently geomtry doesn't give us z value.  We have to make sure that 
    // the geoevent has a field that is tagged as ALTITUDE which supplies the z value
    double zvalue = point.getZ();
    int index = geoevent.getGeoEventDefinition().getIndexOf(altitudeTag);
    if(index > -1)
    {
      zvalue=Double.parseDouble(geoevent.getAllFields()[index].toString());
    }
    location.setAltitude(zvalue);
    
    int i;
    i=geoevent.getGeoEventDefinition().getIndexOf(headingTag);
    if(i>-1)
    {
      orientation.setHeading(Double.parseDouble(geoevent.getAllFields()[i].toString()));
    }
    else
    {
      orientation.setHeading(0);
    }
    i=geoevent.getGeoEventDefinition().getIndexOf(rollTag);
    if(i>-1)
    {
      orientation.setRoll(Double.parseDouble(geoevent.getAllFields()[i].toString()));
    }
    else
    {
      orientation.setRoll(0);
    }
    i=geoevent.getGeoEventDefinition().getIndexOf(tiltTag);
    if(i>-1)
    {
      orientation.setTilt(Double.parseDouble(geoevent.getAllFields()[i].toString()));
    }
    else
    {
      orientation.setTilt(0);
    }
    //orientation.setId("Orientation_" + geoevent.getTrackId());
    
    //scale.setId("Scale_" + geoevent.getTrackId());
    scale.setX(params.getModelScale());
    scale.setY(params.getModelScale());
    scale.setZ(params.getModelScale());
    
    if(params.getModelField().length()>0)
    {
      int indexModelField = geoevent.getGeoEventDefinition().getIndexOf(params.getModelField());
      if(indexModelField>-1 && indexModelField<geoevent.getAllFields().length)
      {
        String value = geoevent.getAllFields()[geoevent.getGeoEventDefinition().getIndexOf(params.getModelField())].toString();
        link.setHref(modelPath + "/" + value + ".dae");
      }
//      else
//      {
//        link.setHref(modelPath + "/" + params.getModelId() + ".dae");
//      }
    }
    else
    {
      link.setHref(modelPath + "/" + params.getModelId() + ".dae");
    }
    //link.setId("Link_" + geoevent.getTrackId());
    
    model.setAltitudeMode(params.getAltitudeMode());
    model.setLocation(location);
    model.setOrientation(orientation);
    model.setScale(scale);
    model.setLink(link);
    //model.setId("Model_" + geoevent.getTrackId());
    return model;
    
  }
  
  private String getLabelFieldValue(GeoEvent geoevent)
  {
    String value = "";
    int i;
    i = geoevent.getGeoEventDefinition().getIndexOf(kmlLabelFieldTag);
    if (i > -1)
    {
      value = geoevent.getAllFields()[i].toString();
    }
    else
    {
      value = geoevent.getTrackId();
    }

    return value==null?"":value;
  }
  
  public void setAltitudeTag(String altitudeTag)
  {
    this.altitudeTag = altitudeTag;
  }

  public void setKmlLabelFieldTag(String kmlLabelFieldTag)
  {
    this.kmlLabelFieldTag = kmlLabelFieldTag;
  }

  public void setHeadingTag(String headingTag)
  {
    this.headingTag = headingTag;
  }

  public void setRollTag(String rollTag)
  {
    this.rollTag = rollTag;
  }

  public void setTiltTag(String tiltTag)
  {
    this.tiltTag = tiltTag;
  }

  public void setModelUrl(String modelUrl)
  {
    this.modelUrl = modelUrl;
  }

  public void setStyleUrl(String styleUrl)
  {
    this.styleUrl = styleUrl;
  }
  
  
}
