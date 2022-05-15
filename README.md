# Emote format tools
**some tools to convert different emotecraft format into each other.**


## CLI - Not done, not planned
Command line tools to convert files, won't be needed as the open service (if it can support itself) will have conversion abilities


## Socket Daemon 
This application will listen to socket connection and response to them.  

Using socket is not user-friendly, but this is the best way to implement it in [Emotes Open Collection](https://github.com/KosmX/emotes-open-collection)  
Java has terrible startup time (compared to other compiled languages), but insane performance (once loaded).  

The daemon will need to be started only once, and will be able to parallel serve multiple requests through sockets.  


