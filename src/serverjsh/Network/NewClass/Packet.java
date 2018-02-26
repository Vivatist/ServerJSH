package serverjsh.Network.NewClass;


import serverjsh.Network.NetworkMessage;

public abstract class Packet {

    public boolean error;

    public int crc;

    public abstract void Execute();

    void GenerateCrc() {
        //TODO реализовать вычисление crc
        crc = 100;
    }

}
