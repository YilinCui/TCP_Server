# TCP_Server(Virtual Device)
This virtual device is based on TCP communication protocol for our Android app. This project is authorized by Singular Medical (Irvine, USA).

# Description
This Virtual Device aims at simulating a real Device in terms of sending data(in byte[] format) back to Android programmer. Virtual device is using TCP/IP communication protocol instead of BLE in real running environment.


## Copyright and Permissions
All rights reserved. This project and accompanying materials are owned by Singular Medical. The content is protected by international copyright laws. 

No part of this project may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of Singular Medical. 

This project is intended for use solely by authorized individuals or entities, and may not be used for commercial purposes without the express written consent of Singular Medical. Unauthorized use of this project for commercial purposes is strictly prohibited and may result in severe civil and criminal penalties under applicable laws.

By accessing or using this project, you agree to abide by the above terms.

### TCP Server-Client Interaction and Data Processing Workflow
- There are 2 ways of deploying the server:
  1. Remote Server: 43.153.84.250(Sillicon Valley, California)
  2. Local Server: localhost
- Start
- TCP Server Listens on port 8888(Command Channel) and port 8889(ECG Channel)
- Client Connects -> Server creates new Thread (Classical BIO Model)
- Thread Creates Controller.ICDDevice instance
- Two types of Incoming data, one is testing environment configuration, another is real data from programmer
- Call DecodingPacket class to decode the packet into: packetHeader(3 bytes) + payload + CRC32
- Packetheader contains: packet length + sequenceNumber + commandId.
- Perform Corresponding I/O operations based on commandId
- Each ICDDevice Process corresponding data
- Server sends encoded packet back to client
- Repeat from Step V. (until client disconnects)
- End
