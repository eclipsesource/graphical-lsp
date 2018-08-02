# ChilliSP

This is a prototypical implementation of an web-based Diagram modeling editor. For client server communication an adapted version of the Graphical Server Protocol proposed by Obeo is used (https://github.com/ObeoNetwork/GraphicalServerProtocol)
The web client is based on Theia and reuses the Sprotty framework.

## Cloning the repository

It's recommended to clone the repository recursively. Otherwise submodules have to be cloned and initialized separatly

    git clone --recursive git@github.com:tortmayr/chilliSP.git
    cd chilliSP
    
## Building the client component
	cd client
	yarn prepare
	
## Building the server component
	cd server
	mvn clean install
	
## 1. Starting the GLSP Server
The example server can be started by executing the main method of the class ExampleServerLauncher.java (server/examaples/workflow-example)

## 2. Starting the theia client
	cd client/examples/browser-app
	yarn start
and then open http://localhost:3000 in the browser.
