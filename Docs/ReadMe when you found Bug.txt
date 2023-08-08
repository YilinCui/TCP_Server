1. 小心little Endian！！ChargeLog 0x0000。0x2610实际读取顺序为0x1026
2. 除了batteryLog和ChargeLog 以外，其他log的读取方式都是从下往上塞。也就是说byte[]前段内容对应的Log在最底下
3. ChargeLog 里packet2 的第一个居然对应chargeLog6。Packet1 的第一个对应ChargeLog9？？？