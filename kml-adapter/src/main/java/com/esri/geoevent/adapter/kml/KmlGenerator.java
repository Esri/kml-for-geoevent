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

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.geoevent.adapter.kml.CdataXmlStreamWriter;

public class KmlGenerator extends KmlGeneratorBase implements KmlGeneratorService
{
  private static final Log      LOG                             = LogFactory.getLog(KmlGenerator.class);
  
  @Override
  public byte[] generateInitialKml(KmlRequestParameters params) throws JAXBException, XMLStreamException
  {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    doGenerateInitialKml(output, params.getOutputName(), params, params.getUrl());
    return output.toByteArray();
  }

  @Override
  public byte[] generateKml(Map<String, List<GeoEvent>> geoevents, KmlRequestParameters params) throws JAXBException, XMLStreamException
  {

    ByteArrayOutputStream kmlOutput = new ByteArrayOutputStream();

//      String assetsDir = params.getAssetDir();

      if (params.isUpdateMode())
      {
        doGenerateUpdateKml(geoevents, kmlOutput, params.getUrl(), params);
      }
      else
      {
        doGenerateRefreshKml(geoevents, kmlOutput, params);
      }

      //getKMZ(outputName + ".kml", kmlOutput.toString(), output);

      return kmlOutput.toByteArray();
  }
  
  private void doGenerateInitialKml(OutputStream output, String outboundStreamName, KmlRequestParameters params, String uriAbsolutePath) throws JAXBException, XMLStreamException
  {
    JAXBContext context;

    Kml kml = new Kml();
    Folder folder = new Folder();
    folder.setName(outboundStreamName);
    kml.setFolder(folder);

    Link link1 = new Link();
    link1.setHref(createHref(uriAbsolutePath));
    link1.setRefreshMode("onInterval");
    link1.setRefreshInterval( Integer.toString(params.getRefreshInterval()) );
    link1.setHttpQuery(params.getHttpQueryString(false));

    NetworkLink networkLink1 = new NetworkLink();
    networkLink1.setName("Track List");
    networkLink1.setDescription(params.getDescription());
    networkLink1.setLink(link1);

    Link link2 = new Link();
    link2.setHref(createHref(uriAbsolutePath));
    link2.setRefreshMode("onInterval");
    link2.setRefreshInterval( Integer.toString(params.getUpdateInterval()) );
    link2.setViewRefreshMode("onStop");
    link2.setViewRefreshTime("1");
    link2.setHttpQuery(params.getHttpQueryString(true));

    NetworkLink networkLink2 = new NetworkLink();
    networkLink2.setName("NetworkLink Update");
    networkLink2.setDescription(params.getDescription());
    networkLink2.setLink(link2);

    ArrayList<NetworkLink> networkLinks = new ArrayList<NetworkLink>();
    networkLinks.add(networkLink1);
    networkLinks.add(networkLink2);

    folder.setNetworkLink(networkLinks);

    context = JAXBContext.newInstance(Kml.class);
    Marshaller marshaller = context.createMarshaller();
    XMLOutputFactory xof = XMLOutputFactory.newInstance();
    XMLStreamWriter streamWriter;
    streamWriter = xof.createXMLStreamWriter(output);
    CdataXmlStreamWriter cdataStreamWriter = new CdataXmlStreamWriter(streamWriter);
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    marshaller.marshal(kml, cdataStreamWriter);
    cdataStreamWriter.flush();
    cdataStreamWriter.close();
  }

  private void doGenerateRefreshKml(Map<String, List<GeoEvent>> cache, OutputStream output, KmlRequestParameters params) throws JAXBException
  {
//    String pointStyleUrl = getStyleUrl(uriString, assetsDir, styleFileSubDir, params.getPointStyleFilename());
//    String lineStyleUrl = getStyleUrl(uriString, assetsDir, styleFileSubDir, params.getLineStyleFilename());
//    String modelUrl = getModelUrl(uriString, assetsDir, modelSubDir, params.getModelPath());
    String pointStyleUrl = getStyleFileUrl(params.getPointStyleFilename());
    String lineStyleUrl = getStyleFileUrl(params.getLineStyleFilename());
    String modelUrl = getModelUrl( params.getModelPath());
    
    ArrayList<Placemark> placemarks = new ArrayList<Placemark>();
    Kml kml = new Kml();
    Folder folder = new Folder();
    folder.setPlacemark(placemarks);
    kml.setFolder(folder);
    JAXBContext context2;
    Marshaller m2;

    Iterator it = cache.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry pairs = (Map.Entry) it.next();
      List<GeoEvent> eventList = (List<GeoEvent>) pairs.getValue();
      if(eventList.size()>0)
      {
        GeoEvent lastEvent = eventList.get(0);
        Placemark pm = createPlacemark(lastEvent, false, params, pointStyleUrl, modelUrl);
        placemarks.add(pm);
        if (params.isShowTrack())
        {
          Placemark pmt = createPlacemarkForTrack(lastEvent, eventList, false, params.getAltitudeMode(), lineStyleUrl, params.getLineStyleId(), params.getLineStyleField());
          placemarks.add(pmt);
        }
      }
    }
    context2 = JAXBContext.newInstance(Kml.class);
    m2 = context2.createMarshaller();
    m2.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    // baOut = new ByteArrayOutputStream();
    m2.marshal(kml, output);
  }

  public void doGenerateUpdateKml(Map<String, List<GeoEvent>> cache, OutputStream output, String uriString, KmlRequestParameters params) throws JAXBException, XMLStreamException
  {
//    String pointStyleUrl = getStyleUrl(uriString, assetsDir, styleFileSubDir, params.getPointStyleFilename());
//    String lineStyleUrl = getStyleUrl(uriString, assetsDir, styleFileSubDir, params.getLineStyleFilename());
//    String modelUrl = getModelUrl(uriString, assetsDir, modelSubDir, params.getModelPath());
    
    String pointStyleUrl = getStyleFileUrl(params.getPointStyleFilename());
    String lineStyleUrl = getStyleFileUrl(params.getLineStyleFilename());
    String modelUrl = getModelUrl(params.getModelPath());
    String targetHref = uriString + "?" + params.getHttpQueryString(false);

    JAXBContext context;
    Marshaller marshaller;
    ArrayList<Placemark> placemarks = new ArrayList<Placemark>();
    Kml kml = new Kml();
    NetworkLinkControl networkLinkControl = new NetworkLinkControl();

    networkLinkControl.setCookie("190979328531077");
    networkLinkControl.setLinkName("NetworkLink Update");
    networkLinkControl.setLinkDescription("Esri GeoEvent Server KML Service Update");
    Update update = new Update();
    update.setTargetHref(targetHref);
    update.setPlacemark(placemarks);
    networkLinkControl.setUpdate(update);
    kml.setNetworkLinkControl(networkLinkControl);

    Iterator it = cache.entrySet().iterator();
    while (it.hasNext())
    {
      Map.Entry pairs = (Map.Entry) it.next();
      List<GeoEvent> eventList = (List<GeoEvent>) pairs.getValue();
      if(eventList.size()>0)
      {
        GeoEvent lastEvent = eventList.get(0);
        Placemark pm = createPlacemark(lastEvent, true, params, pointStyleUrl, modelUrl);
        placemarks.add(pm);
        if (params.isShowTrack())
        {
          Placemark pmt = createPlacemarkForTrack(lastEvent, eventList, true, params.getAltitudeMode(), lineStyleUrl, params.getLineStyleId(), params.getLineStyleField());
          placemarks.add(pmt);
        }
      }
    }

    context = JAXBContext.newInstance(Kml.class);
    marshaller = context.createMarshaller();
    XMLOutputFactory xof = XMLOutputFactory.newInstance();
    XMLStreamWriter streamWriter;
    streamWriter = xof.createXMLStreamWriter(output);
    CdataXmlStreamWriter cdataStreamWriter = new CdataXmlStreamWriter(streamWriter);
    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
    marshaller.marshal(kml, cdataStreamWriter);
    cdataStreamWriter.flush();
    cdataStreamWriter.close();
  }
  
  private String createHref(String uriString)
  {
    return uriString + "?";
  }

  public void setAltitudeTag(String altitudeTag)
  {
    super.setAltitudeTag(altitudeTag);
  }
  
  public void setKmlLabelFieldTag(String kmlLabelFieldTag)
  {
    super.setKmlLabelFieldTag(kmlLabelFieldTag);
  }
  
  public void setHeadingTag(String headingTag)
  {
    super.setHeadingTag(headingTag);
  }
  
  public void setRollTag(String rollTag)
  {
    super.setRollTag(rollTag);
  }
  
  public void setTiltTag(String tiltTag)
  {
    super.setTiltTag(tiltTag);
  }

}
