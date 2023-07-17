# TCP_Server
TCP Server for Android app

   Start
     |
     v
TCP Server Listens on port 8888
     |
     v
Client Connects -> Server creates new Thread
     |
     v
Thread Creates ICDDevice instance
     |
     v
DecodingPacket class decodes incoming data
     |
     v
Perform Corresponding I/O operations
     |
     v
 +---Brady Parameters module processing---+
 |                                        |
 v                                        v
Tachy Detection module processing <----> Tachy Therapy module processing
 |                                        |
 v                                        v
 +---EncodingPacket class encodes data for client---+
     |
     v
Server sends data back to client
     |
     v
  Repeat (until client disconnects)
     |
     v
   End
