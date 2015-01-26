# kml-for-geoevent

ArcGIS 10.3 GeoEvent Extension for Server KML Outbound Adapter for sending GeoEvents in the KML format.

![App](kml-for-geoevent.png?raw=true)

## Features
* KML Outbound Adapter

## Instructions

Building the source code:

1. Make sure Maven and ArcGIS GeoEvent Extension SDK are installed on your machine.
2. Run 'mvn install -Dcontact.address=[YourContactEmailAddress]'

Installing the cfg file and the built jar file:

1. Copy the com.esri.geoevent.adapter.kml.cfg file from kml-adapter/src/main/resources folder to the [ArcGIS-GeoEvent-Extension-Install-Directory]/deploy folder.
2. Modify the styleFolderUrl and the modelUrl properties in the com.esri.geoevent.adapter.kml.cfg file so that they point to an online folder that contains a style xml and 3-D models respectively.
3. Copy the *.jar files under the 'target' sub-folder into the [ArcGIS-GeoEvent-Extension-Install-Directory]/deploy folder.

## Requirements

* ArcGIS GeoEvent Extension for Server (Certified with version 10.3.x).
* ArcGIS GeoEvent Extension SDK.
* Java JDK 1.7 or greater.
* Maven.

## Resources

* [ArcGIS GeoEvent Extension Gallery](http://links.esri.com/geovent-gallery) 
* [ArcGIS GeoEvent Extension for Server Resources](http://links.esri.com/geoevent)
* [ArcGIS Blog](http://blogs.esri.com/esri/arcgis/)
* [KML Reference](https://developers.google.com/kml/documentation/kmlreference)
* [twitter@esri](http://twitter.com/esri)

## Issues

Find a bug or want to request a new feature?  Please let us know by submitting an issue.

## Contributing

Esri welcomes contributions from anyone and everyone. Please see our [guidelines for contributing](https://github.com/esri/contributing).

## Licensing
Copyright 2013 Esri

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the license is available in the repository's [license.txt](license.txt?raw=true) file.

[](ArcGIS, GeoEvent, Processor)
[](Esri Tags: ArcGIS GeoEvent Extension for Server)
[](Esri Language: Java)
