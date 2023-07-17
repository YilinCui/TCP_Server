# TCP_Server
TCP Server for Android app. This project is authorized by Singular Medical (Irvine, USA).

## Copyright and Permissions
All rights reserved. This project and accompanying materials are owned by Singular Medical. The content is protected by international copyright laws. 

No part of this project may be reproduced, distributed, or transmitted in any form or by any means, including photocopying, recording, or other electronic or mechanical methods, without the prior written permission of Singular Medical. 

This project is intended for use solely by authorized individuals or entities, and may not be used for commercial purposes without the express written consent of Singular Medical. Unauthorized use of this project for commercial purposes is strictly prohibited and may result in severe civil and criminal penalties under applicable laws.

By accessing or using this project, you agree to abide by the above terms.


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

