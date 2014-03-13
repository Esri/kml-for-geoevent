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

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "name", "styleUrl", "data", "point", "lineString", "model" })
public class Placemark
{

  private String          id;
  private String          targetId;
  private String          name;
  private String          styleUrl;

  private ArrayList<Data> data;
  private Point           point;
  private LineString      lineString;

  private Model           model;

  @XmlAttribute(name = "id")
  public String getId()
  {
    return id;
  }

  public void setId(String id)
  {
    this.id = id;
  }

  @XmlAttribute(name = "targetId")
  public String getTargetId()
  {
    return targetId;
  }

  public void setTargetId(String targetId)
  {
    this.targetId = targetId;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getStyleUrl()
  {
    return styleUrl;
  }

  public void setStyleUrl(String styleUrl)
  {
    this.styleUrl = styleUrl;
  }

  @XmlElementWrapper(name = "ExtendedData")
  @XmlElement(name = "Data")
  public ArrayList<Data> getData()
  {
    return data;
  }

  public void setData(ArrayList<Data> extendedData)
  {
    this.data = extendedData;
  }

  @XmlElement(name = "Point")
  public Point getPoint()
  {
    return point;
  }

  public void setPoint(Point point)
  {
    this.point = point;
  }

  @XmlElement(name = "LineString")
  public LineString getLineString()
  {
    return lineString;
  }

  public void setLineString(LineString lineString)
  {
    this.lineString = lineString;
  }

  @XmlElement(name = "Model")
  public Model getModel()
  {
    return model;
  }

  public void setModel(Model model)
  {
    this.model = model;
  }
}
