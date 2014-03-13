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

@XmlType(propOrder = { "href", "refreshMode", "refreshInterval", "viewRefreshMode", "viewRefreshTime", "httpQuery"})
public class Link {
	private String href;
	private String refreshMode;
	private String refreshInterval;
	private String viewRefreshMode;
	private String viewRefreshTime;
	private String httpQuery;
	private String id;
	
	@XmlAttribute
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRefreshMode() {
		return refreshMode;
	}
	public void setRefreshMode(String refreshMode) {
		this.refreshMode = refreshMode;
	}
	public String getRefreshInterval() {
		return refreshInterval;
	}
	public void setRefreshInterval(String refreshInterval) {
		this.refreshInterval = refreshInterval;
	}
	public String getHttpQuery() {
		return httpQuery;
	}
	public void setHttpQuery(String httpQuery) {
		this.httpQuery = httpQuery;
	}
	public String getViewRefreshMode() {
		return viewRefreshMode;
	}
	public void setViewRefreshMode(String viewRefreshMode) {
		this.viewRefreshMode = viewRefreshMode;
	}
	public String getViewRefreshTime() {
		return viewRefreshTime;
	}
	public void setViewRefreshTime(String viewRefreshTime) {
		this.viewRefreshTime = viewRefreshTime;
	}
	
	

}
