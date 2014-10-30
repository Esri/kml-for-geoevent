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

public class KmlRequestParameters
{
  public static final String OUTPUTFORMAT_PARAM_NAME          = "f";
  public static final String GEOEVENTDEFINITION_PARAM_NAME    = "geoEventDefinitions";
  public static final String POINTSTYLEFILENAME_PARAM_NAME    = "pointStyleFilename";
  public static final String POINTSTYLEID_PARAM_NAME          = "pointStyleId";
  public static final String POINTSTYLEFIELD_PARAM_NAME       = "pointStyleField";
  public static final String LINESTYLEFILENAME_PARAM_NAME     = "lineStyleFilename";
  public static final String LINESTYLEID_PARAM_NAME           = "lineStyleId";
  public static final String LINESTYLEFIELD_PARAM_NAME        = "lineStyleField";
  public static final String ALTITUDEMODE_PARAM_NAME          = "altitudeMode";
  public static final String SHOWTRACK_PARAM_NAME             = "showTrack";
  public static final String UPDATEMODE_PARAM_NAME            = "updateMode";
  public static final String REFRESHINTERVAL_PARAM_NAME       = "refreshInterval";
  public static final String UPDATEREFRESHINTERVAL_PARAM_NAME = "updateRefreshInterval";
  public static final String DESCRIPTION_PARAM_NAME           = "description";
  public static final String MODELPATH_PARAM_NAME             = "modelPath";
  public static final String MODELID_PARAM_NAME               = "modelId";
  public static final String MODELFIELD_PARAM_NAME            = "modelField";
  public static final String MODELSCALE_PARAM_NAME            = "modelScale";
  public static final String COMPRESSKML_PARAM_NAME           = "compression";
  public static final String LINKSONLY_PARAM_NAME             = "linksOnly";
  public static final String USE3DMODEL_PARAM_NAME             = "use3DModel";

  private boolean            showTrack;
  private String             outputFormat;
  private String             description;
  private String             pointStyleFilename;
  private String             pointStyleId;
  private String             pointStyleField;
  private String             lineStyleFilename;
  private String             lineStyleId;
  private String             lineStyleField;
  private String             altitudeMode;
  private int                refreshInterval;
  private int                updateInterval;
  private String             geoeventDefinition;
  private String             modelPath;
  private String             modelId;
  private String             modelField;
  private int                modelScale;
  private boolean            updateMode;
  private boolean            linksOnly;
  private String             outputName;
  private String             url;
  private boolean            use3DModel;
  private boolean            compressKml;

  public KmlRequestParameters()
  {
    showTrack = false;
    updateMode = false;
    description = "";
    pointStyleFilename = "";
    pointStyleId = "";
    pointStyleField = "";
    lineStyleFilename = "";
    lineStyleId = "";
    lineStyleField = "";
    altitudeMode = "";
    refreshInterval = 10;
    updateInterval = 2;
    geoeventDefinition = "";
    modelPath = "";
    modelId = "";
    modelField = "";
    modelScale = 2;
    compressKml = false;
  }

  public String getHttpQueryString(boolean forUpdate)
  {
    StringBuffer buf = new StringBuffer();
    buf.append(OUTPUTFORMAT_PARAM_NAME).append("=").append(outputFormat);
    buf.append("&").append(LINKSONLY_PARAM_NAME).append("=false");
    if (geoeventDefinition.length() > 0)
    {
      buf.append("&").append(GEOEVENTDEFINITION_PARAM_NAME).append("=").append(geoeventDefinition);
    }

    buf.append("&").append(POINTSTYLEFILENAME_PARAM_NAME).append("=").append(pointStyleFilename);

    if (pointStyleField.length() > 0)
    {
      buf.append("&").append(POINTSTYLEFIELD_PARAM_NAME).append("=").append(pointStyleField);
    }
    else
    {
      buf.append("&").append(POINTSTYLEID_PARAM_NAME).append("=").append(pointStyleId);
    }

    buf.append("&").append(LINESTYLEFILENAME_PARAM_NAME).append("=").append(lineStyleFilename);

    if (lineStyleField.length() > 0)
    {
      buf.append("&").append(LINESTYLEFIELD_PARAM_NAME).append("=").append(lineStyleField);
    }
    else
    {
      buf.append("&").append(LINESTYLEID_PARAM_NAME).append("=").append(lineStyleId);
    }

    buf.append("&").append(ALTITUDEMODE_PARAM_NAME).append("=").append(altitudeMode);

    if (showTrack)
    {
      buf.append("&").append(SHOWTRACK_PARAM_NAME).append("=").append("true");
    }
    else
    {
    	buf.append("&").append(SHOWTRACK_PARAM_NAME).append("=").append("false");
    }

    if (use3DModel)
    {
      buf.append("&").append(USE3DMODEL_PARAM_NAME).append("=").append("true");
      if (modelPath.length() > 0)
      {
        buf.append("&").append(MODELPATH_PARAM_NAME).append("=").append(modelPath);
      }

      if (modelField.length() > 0)
      {
        buf.append("&").append(MODELFIELD_PARAM_NAME).append("=").append(modelField);
      }
      else
      {
        buf.append("&").append(MODELID_PARAM_NAME).append("=").append(modelId);
      }
      buf.append("&").append(MODELSCALE_PARAM_NAME).append("=").append(modelScale);
    }

    if (forUpdate)
    {
      buf.append("&").append(UPDATEMODE_PARAM_NAME).append("=").append(true);
    }
    
    if (compressKml)
    {
      buf.append("&").append(COMPRESSKML_PARAM_NAME).append("=").append(true);
    }
    return buf.toString();
  }

  public String getOutputName()
  {
    return outputName;
  }

  public void setOutputName(String outputName)
  {
    this.outputName = outputName;
  }

  public String getUrl()
  {
    return url;
  }

  public void setUrl(String url)
  {
    this.url = url;
  }
  
  public boolean isShowTrack()
  {
    return showTrack;
  }

  public void setShowTrack(boolean showTrack)
  {
    this.showTrack = showTrack;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getPointStyleFilename()
  {
    return pointStyleFilename;
  }

  public void setPointStyleFilename(String pointStyleFilename)
  {
    this.pointStyleFilename = pointStyleFilename;
  }

  public String getPointStyleId()
  {
    return pointStyleId;
  }

  public void setPointStyleId(String pointStyleId)
  {
    this.pointStyleId = pointStyleId;
  }

  public String getPointStyleField()
  {
    return pointStyleField;
  }

  public void setPointStyleField(String pointStyleField)
  {
    this.pointStyleField = pointStyleField;
  }

  public String getLineStyleFilename()
  {
    return lineStyleFilename;
  }

  public void setLineStyleFilename(String lineStyleFilename)
  {
    this.lineStyleFilename = lineStyleFilename;
  }

  public String getLineStyleId()
  {
    return lineStyleId;
  }

  public void setLineStyleId(String lineStyleId)
  {
    this.lineStyleId = lineStyleId;
  }

  public String getLineStyleField()
  {
    return lineStyleField;
  }

  public void setLineStyleField(String lineStyleField)
  {
    this.lineStyleField = lineStyleField;
  }

  public String getAltitudeMode()
  {
    return altitudeMode;
  }

  public void setAltitudeMode(String altitudeMode)
  {
    this.altitudeMode = altitudeMode;
  }

  public int getRefreshInterval()
  {
    return refreshInterval;
  }

  public void setRefreshInterval(int refreshInterval)
  {
    this.refreshInterval = refreshInterval;
  }

  public int getUpdateInterval()
  {
    return updateInterval;
  }

  public void setUpdateInterval(int updateInterval)
  {
    this.updateInterval = updateInterval;
  }

  public String getGeoeventDefinition()
  {
    return geoeventDefinition;
  }

  public void setGeoeventDefinition(String geoeventDefinition)
  {
    this.geoeventDefinition = geoeventDefinition;
  }

  public String getModelPath()
  {
    return modelPath;
  }

  public void setModelPath(String modelPath)
  {
    this.modelPath = modelPath;
  }

  public String getModelId()
  {
    return modelId;
  }

  public void setModelId(String modelId)
  {
    this.modelId = modelId;
  }

  public String getModelField()
  {
    return modelField;
  }

  public void setModelField(String modelField)
  {
    this.modelField = modelField;
  }

  public int getModelScale()
  {
    return modelScale;
  }

  public void setModelScale(int modelScale)
  {
    this.modelScale = modelScale;
  }

  public boolean isUpdateMode()
  {
    return updateMode;
  }

  public void setUpdateMode(boolean updateMode)
  {
    this.updateMode = updateMode;
  }

  public String getOutputFormat()
  {
    return outputFormat;
  }

  public void setOutputFormat(String outputFormat)
  {
    this.outputFormat = outputFormat;
  }

//  public String getStyleFileSubDir()
//  {
//    return styleFileSubDir;
//  }
//
//  public void setStyleFileSubDir(String styleFileSubDir)
//  {
//    this.styleFileSubDir = styleFileSubDir;
//  }

  public boolean isLinksOnly()
  {
    return linksOnly;
  }

  public void setLinksOnly(boolean linksOnly)
  {
    this.linksOnly = linksOnly;
  }

//  public String getModelSubDir()
//  {
//    return modelSubDir;
//  }
//
//  public void setModelSubDir(String modelSubDir)
//  {
//    this.modelSubDir = modelSubDir;
//  }

  public boolean isUse3DModel()
  {
    return use3DModel;
  }

  public void setUse3DModel(boolean use3dModel)
  {
    use3DModel = use3dModel;
  }

  public boolean isCompressKml()
  {
    return compressKml;
  }

  public void setCompressKml(boolean compressKml)
  {
    this.compressKml = compressKml;
  }
}
