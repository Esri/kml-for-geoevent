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

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Update")
@XmlType(propOrder = { "targetHref", "placemark"})
public class Update {
	
	private String targetHref;
	private ArrayList<Placemark> placemark;
	
	public String getTargetHref() {
		return targetHref;
	}
	public void setTargetHref(String targetHref) {
		this.targetHref = targetHref;
	}
	
	@XmlElementWrapper(name = "Change")
	@XmlElement(name = "Placemark")
	public ArrayList<Placemark> getPlacemark() {
		return placemark;
	}

	public void setPlacemark(ArrayList<Placemark> placemark) {
		this.placemark = placemark;
	}
	
	

}
