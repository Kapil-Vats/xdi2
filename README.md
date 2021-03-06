<a href="http://projectdanube.org/" target="_blank"><img src="http://projectdanube.github.com/xdi2/images/projectdanube_logo.png" align="right"></a>
<img src="http://projectdanube.github.com/xdi2/images/logo64.png"><br>
[![Build Status](https://secure.travis-ci.org/projectdanube/xdi2.png)](http://travis-ci.org/projectdanube/xdi2)

[XDI2](http://github.com/projectdanube/xdi2) is a general purpose XDI library for Java, supporting both a traditional client/server model and distributed peer-to-peer data exchange. 

A sample deployment of XDI2 is available at http://xdi2.projectdanube.org.

### Components

* [xdi2-core](https://github.com/projectdanube/xdi2/wiki/xdi2-core) - Implementation of the XDI graph model and basic features [.jar]
* [xdi2-messaging](https://github.com/projectdanube/xdi2/wiki/xdi2-messaging) - Implementation of XDI messaging functionality [.jar]
* [xdi2-client](https://github.com/projectdanube/xdi2/wiki/xdi2-client) - An XDI client can send messages to an XDI server, including discovery [.jar]
* [xdi2-transport](https://github.com/projectdanube/xdi2/wiki/xdi2-transport) - A transport (server) can receive XDI message and process them [.jar]
* [xdi2-transport-http](https://github.com/projectdanube/xdi2/wiki/xdi2-transport-http) - An HTTP transport (server) that exposes XDI endpoints at URIs [.jar]
* [xdi2-transport-http-embedded](https://github.com/projectdanube/xdi2/wiki/xdi2-transport-http-embedded) - The HTTP transport (server) embedded in another application [.jar]
* [xdi2-transport-http-standalone](https://github.com/projectdanube/xdi2/wiki/xdi2-transport-http-standalone) - The HTTP transport (server) as a standalone application [.jar]
* [xdi2-transport-http-war](https://github.com/projectdanube/xdi2/wiki/xdi2-transport-http-war) - The HTTP transport (server) as a web application [.war]
* [xdi2-webtools](https://github.com/projectdanube/xdi2/wiki/xdi2-webtools) - A collection of web-based XDI tools for testing [.war]
* [xdi2-samples](https://github.com/projectdanube/xdi2/wiki/xdi2-samples) - Various samples on how to work with the XDI2 library [.jar]

### How to build

Just run

    mvn clean install

To build all components.

### How to run the XDI web tools

    cd webtools
    mvn jetty:run

Then go to:

    http://localhost:8080/

### Community

Google Group: http://groups.google.com/group/xdi2

Javadoc: http://projectdanube.github.com/xdi2/apidocs

### Virtual machine and screencasts

To give you a quick start into XDI2, you may download a VirtualBox image with all the components, or watch a screencast.

* VirtualBox: <a href="http://files.projectdanube.org/XDI2-VirtualBox.zip">Download</a>
* Screencast: <a href="http://vimeo.com/52763525">XDI Personal Cloud Demo</a> from <a href="http://vimeo.com/user3934958">Markus Sabadello</a> on <a href="http://vimeo.com">Vimeo</a>

### Plugins

* [xdi2-connector-facebook](https://github.com/projectdanube/xdi2-connector-facebook) - Facebook -> XDI Connector
* [xdi2-connector-personal](https://github.com/projectdanube/xdi2-connector-personal) - Personal.com -> XDI Connector
* [xdi2-connector-allfiled](https://github.com/projectdanube/xdi2-connector-allfiled) - Allfiled -> XDI Connector
* [xdi2-connector-google-calendar](https://github.com/projectdanube/xdi2-connector-google-calendar) - Google Calendar -> XDI Connector

### Tests

See [Testing](https://github.com/projectdanube/xdi2/wiki/Testing) for a description of unit tests, etc.
