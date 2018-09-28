# Graphical Language Server Protocol Framework [![Build Status](https://travis-ci.org/eclipsesource/graphical-lsp.svg?branch=master)](https://travis-ci.org/eclipsesource/graphical-lsp)

The Graphical Language Server Protocol Framework provides extensible components to enable the development of *diagram editors including edit functionality* in (distributed) web-applications via a client-server protocol.
This [Graphical Language Server Protocol (GLSP)](https://github.com/eclipsesource/GraphicalServerProtocol) is work in progress and developed in collaboration among TypeFox, Obeo, and EclipseSource.
It follows the same architectural pattern as the [Language Server Protocol](https://github.com/Microsoft/language-server-protocol) for textual languages, but applies it to graphical modeling for browser/cloud-based deployments.
The protocol as well as the client implementation is heavily based on [Sprotty](https://github.com/theia-ide/sprotty) but extends it with editing functionality and GLSP-specific communication with the server.

## Getting started
### Prerequisites
You’ll need node in version 8:

	curl -o- https://raw.githubusercontent.com/creationix/nvm/v0.33.5/install.sh | bash
	nvm install 8
and yarn

	npm install -g yarn

and lerna

	npm install -g lerna

### Cloning the repository

It's recommended to clone the repository recursively. Otherwise submodules have to be cloned and initialized separatly

    git clone --recursive git@github.com:eclipsesource/graphical-lsp.git
    cd graphical-lsp
    
    
### Building the client components
	cd client
	yarn 
	
### Building the server components
	cd server
	mvn clean install
	
### 1. Starting the GLSP Server
The example server can be started by executing the main method of the class ExampleServerLauncher.java (server/examaples/workflow-example)

### 2. Starting the theia client
	cd client/examples/browser-app
	yarn start
and then open http://localhost:3000 in the browser.

### Testing the example
The example workspace should be opend automatically on Theia launch. This workspace constains the file "example1.wf". You can display this file in the Workflow Digram editor via context menu (Open with -> Workflow Diagram)

## Contributing to the Graphical LSP project
We'd be trhilled to receive your contribution! Please feel free to open issues, fork this repo, and/or open pull requests. Note that we will ask you to sign a [CLA](https://cla-assistant.io/eclipsesource/graphical-lsp) to ensure all contributions can be distributed under the terms of the following open-source licenses: Apache License 2.0, BSD 2/3 License, MIT License, and Eclipse Public License v2.0.

## Tips & Tricks
### Typescript MonoRepo Import Fixer
This repository is a lerna mono repository which means that VS Code might have some troubles to correctly auto-generate import statements. It's recommended to install the "Typescript MonoRepo Import Fixer" extension.

	Launch VS Code
	Open the "Quick Open" Window (ctrl + p)
	
Paste the following commmand and press enter

	ext install q.typescript-mono-repo-import-helper
