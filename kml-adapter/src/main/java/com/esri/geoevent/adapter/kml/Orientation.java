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
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "heading", "tilt", "roll" })
public class Orientation
{
  private String id;
  private double heading;
  private double tilt;
  private double roll;
  
  @XmlAttribute
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public double getHeading()
  {
    return heading;
  }
  public void setHeading(double heading)
  {
    this.heading = heading;
  }
  public double getTilt()
  {
    return tilt;
  }
  public void setTilt(double tilt)
  {
    this.tilt = tilt;
  }
  public double getRoll()
  {
    return roll;
  }
  public void setRoll(double roll)
  {
    this.roll = roll;
  }
  
  
}
