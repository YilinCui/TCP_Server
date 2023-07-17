# TCP_Server
TCP Server for Android app

- Start
- TCP Server Listens on port 8888
- Client Connects -> Server creates new Thread
- Thread Creates ICDDevice instance
- DecodingPacket class decodes incoming data
- Perform Corresponding I/O operations
- Process modules:
    - Brady Parameters module processing 
        - EncodingPacket class encodes data for Brady Parameters
    - Tachy Detection module processing
        - EncodingPacket class encodes data for Tachy Detection
    - Tachy Therapy module processing
        - EncodingPacket class encodes data for Tachy Therapy
- Server sends data back to client
- Repeat (until client disconnects)
- End

