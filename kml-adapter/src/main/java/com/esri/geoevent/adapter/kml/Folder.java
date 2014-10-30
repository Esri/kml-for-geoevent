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
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "name", "networkLink", "placemark"})
public class Folder {

	private String name;
	private ArrayList<NetworkLink> networkLink;
	private ArrayList<Placemark> placemark;

	@XmlElement(name = "NetworkLink")
	public ArrayList<NetworkLink> getNetworkLink() {
		return networkLink;
	}

	public void setNetworkLink(ArrayList<NetworkLink> networkLink) {
		this.networkLink = networkLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@XmlElement(name = "Placemark")
	public ArrayList<Placemark> getPlacemark() {
		return placemark;
	}

	public void setPlacemark(ArrayList<Placemark> placemark) {
		this.placemark = placemark;
	}
	
	
}
