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
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import com.esri.ges.adapter.AdapterDefinition;
import com.esri.ges.adapter.OutboundAdapterBase;
import com.esri.ges.core.component.ComponentException;
import com.esri.ges.core.geoevent.GeoEvent;
import com.esri.ges.util.Converter;

public class KmlOutboundAdapter extends OutboundAdapterBase
{

  private KmlRequestParameters  params      = new KmlRequestParameters();
  private KmlGeneratorService   kmlGenerator;
  private boolean               compressKml = false;

  public KmlOutboundAdapter(AdapterDefinition definition) throws ComponentException
  {
    super(definition);

  }

  @Override
  public void afterPropertiesSet()
  {
  }

  @Override
  public void receive(GeoEvent geoEvent)
  {
    // TODO Auto-generated method stub

  }

  @Override
  public byte[] processData(Map<String, List<GeoEvent>> geoevents) throws ComponentException
  {
    byte[] output = null;
    loadProperties();
    compressKml = getProperty("compression").getValueAsString().equalsIgnoreCase("true") ? true : false;

    try
    {
      if (params.isLinksOnly())
        output = getKmlGenerator().generateInitialKml(params);
      else
        output = getKmlGenerator().generateKml(geoevents, params);

      if (compressKml)
        output = zipBytes(params.getOutputName() + ".kml", output);
    }
    catch (IOException e)
    {
      throw new ComponentException("KmlOutboundAdapter encountered an error in processData.  " + e.getMessage());
    }
    catch (JAXBException e)
    {
      throw new ComponentException("KmlOutboundAdapter encountered an error in processData.  " + e.getMessage());
    }
    catch (XMLStreamException e)
    {
      throw new ComponentException("KmlOutboundAdapter encountered an error in processData.  " + e.getMessage());
    }

    return output;
  }

  public static byte[] zipBytes(String filename, byte[] input) throws IOException
  {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ZipOutputStream zos = new ZipOutputStream(baos);
    ZipEntry entry = new ZipEntry(filename);
    entry.setSize(input.length);
    zos.putNextEntry(entry);
    zos.write(input);
    zos.closeEntry();
    zos.close();
    return baos.toByteArray();
  }

  private void loadProperties()
  {
    params.setAltitudeMode(getProperty("altitudeMode").getValueAsString());
    params.setDescription(getProperty("description").getValueAsString());
    params.setGeoeventDefinition(getProperty("geoEventDefinitions").getValueAsString());
    params.setLineStyleField(getProperty("lineStyleField").getValueAsString());
    params.setLineStyleFilename(getProperty("lineStyleFilename").getValueAsString());
    params.setLineStyleId(getProperty("lineStyleId").getValueAsString());
    params.setModelField(getProperty("modelField").getValueAsString());
    params.setModelId(getProperty("modelId").getValueAsString());
//    params.setModelPath(getProperty("modelFileSubDir").getValueAsString());
    params.setModelScale(Converter.convertToInteger(getProperty("modelScale").getValueAsString()));
    params.setPointStyleField(getProperty("pointStyleField").getValueAsString());
    params.setPointStyleFilename(getProperty("pointStyleFilename").getValueAsString());
    params.setPointStyleId(getProperty("pointStyleId").getValueAsString());
    params.setRefreshInterval(Converter.convertToInteger(getProperty("refreshInterval").getValueAsString()));
    params.setShowTrack(getProperty("showTrack").getValueAsString().equalsIgnoreCase("false") ? false : true);
    params.setUpdateInterval(Converter.convertToInteger(getProperty("updateInterval").getValueAsString()));
    params.setUpdateMode(getProperty("updateMode").getValueAsString().equalsIgnoreCase("true") ? true : false);
    params.setLinksOnly(getProperty("linksOnly").getValueAsString().equalsIgnoreCase("true") ? true : false);
    params.setPointStyleId(getProperty("pointStyleId").getValueAsString());
    params.setPointStyleId(getProperty("pointStyleId").getValueAsString());
    params.setUrl(getProperty("url").getValueAsString());
    params.setOutputName(getProperty("outputName").getValueAsString());
//    params.setStyleFileSubDir(getProperty("styleFileSubDir").getValueAsString());
//    params.setModelSubDir(getProperty("modelFileSubDir").getValueAsString());
    params.setOutputFormat(getProperty(KmlRequestParameters.OUTPUTFORMAT_PARAM_NAME).getValueAsString());
    params.setUse3DModel(getProperty("use3DModel").getValueAsString().equalsIgnoreCase("true") ? true : false);
    params.setCompressKml(getProperty("compression").getValueAsString().equalsIgnoreCase("true") ? true : false);
  }

  public KmlGeneratorService getKmlGenerator()
  {
    return kmlGenerator;
  }

  public void setKmlGenerator(KmlGeneratorService kmlGenerator)
  {
    this.kmlGenerator = kmlGenerator;
  }

  @Override
  public String getMIMEType()
  {
    if (getProperty("compression").getValueAsString().equalsIgnoreCase("true"))
      return getProperty("mimeTypeKmz").getValueAsString();
    else
      return getProperty("mimeTypeKml").getValueAsString();
  }
}
