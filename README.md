# Peer-to-Peer File Sharing
Peer-to-peer file sharing applications supporting chat functionality over a TCP/IP connection on a localhost. 

## Build
```
$ javac Peer.java GUI.java ClientHandler.java ServerHandler.java
$ java Peer <your-port> <optional-connect-to-port>
```
- To only run as a server, only enter your port. To connect to another peer, enter another active port to establish a TCP connection.

## Development Notes
1. Server loads up with given port.
2. Client loads up and requests other peer's port.
3. Client connects to other peer. 
4. 

- Server needs to automatically respond to client messages.
- 


Peer A  -->  Peer B
Peer B start client-server tcp connection
But: peer B still allows new connections to be made