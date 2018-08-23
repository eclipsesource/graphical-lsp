# Graphical Language Server Protocol Implementation

This is a prototypical implementation of an web-based Diagram modeling editor. For client server communication an adapted version of the Graphical Server Protocol proposed by Obeo is used (https://github.com/ObeoNetwork/GraphicalServerProtocol)
The web client is based on Theia and reuses the Sprotty framework.

## Prerequisites
You’ll need node in version 8:

	curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash
	nvm install 8
and yarn

	npm install -g yarn

and lerna

	npm install -g lerna

## Cloning the repository

It's recommended to clone the repository recursively. Otherwise submodules have to be cloned and initialized separatly

    git clone git@github.com:tortmayr/graphical-lsp.git
    cd graphical-lsp
    
    
## Building the client components
	cd client
	yarn 
	
## Building the server components
	cd server
	mvn clean install
	
## 1. Starting the GLSP Server
The example server can be started by executing the main method of the class ExampleServerLauncher.java (server/examaples/workflow-example)

## 2. Starting the theia client
	cd client/examples/browser-app
	yarn start
and then open http://localhost:3000 in the browser.


## Testing the example
The example workspace should be opend automatically on Theia launch. THis workspace constains the file "example1.wf". You can display this file in the Workflow Digram editor via context menu (Open with -> Workflow Diagram)