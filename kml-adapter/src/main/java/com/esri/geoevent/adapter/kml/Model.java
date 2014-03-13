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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "altitudeMode", "location", "orientation", "scale", "link" })
public class Model
{
  private String id;
  private String altitudeMode;
  private Location location;
  private Orientation orientation;
  private Scale scale;
  private Link link;
  
  @XmlAttribute
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getAltitudeMode()
  {
    return altitudeMode;
  }
  public void setAltitudeMode(String altitudeMode)
  {
    this.altitudeMode = altitudeMode;
  }
  
  @XmlElement(name = "Location")
  public Location getLocation()
  {
    return location;
  }
  public void setLocation(Location location)
  {
    this.location = location;
  }
  
  @XmlElement(name = "Orientation")
  public Orientation getOrientation()
  {
    return orientation;
  }
  public void setOrientation(Orientation orientation)
  {
    this.orientation = orientation;
  }
  
  @XmlElement(name = "Scale")
  public Scale getScale()
  {
    return scale;
  }
  public void setScale(Scale scale)
  {
    this.scale = scale;
  }
  
  @XmlElement(name = "Link")
  public Link getLink()
  {
    return link;
  }
  public void setLink(Link link)
  {
    this.link = link;
  }
  
  
}
