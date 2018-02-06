package serverjsh.Domein.Commands;


import serverjsh.Domein.Exceptions.MyExceptionBadCommand;
import serverjsh.Network.Exceptions.MyExceptionOfNetworkMessage;
import serverjsh.Network.NetworkMessage;
import com.pi4j.system.NetworkInfo;
import com.pi4j.system.*;

import java.io.IOException;
import java.text.ParseException;


public class InfoCommand implements ICommand {

    @Override
    public NetworkMessage Execute(NetworkMessage nm) throws MyExceptionBadCommand, MyExceptionOfNetworkMessage {

        StringBuilder text = new StringBuilder("Информация о платформе '-raspberry', информация о сервере '-server'");

        String parameter = nm.getParameter();
        if (parameter != null) {
            switch (parameter) {
                case "raspberry": {
                    try {
                        text.append(printHwDetails());
                    } catch (Exception e) {
                        System.err.println("You're not running on The PI Platform");
                    }

                    break;
                }

                case "server": {
                    text.append("/-server STUB");
                    break;
                }

                default: {
                    throw new MyExceptionBadCommand("ERROR: Bad argument: " + parameter, 1);
                }
            }
        }


        nm.setText(text.toString());
        return nm;

    }

    private static String printHwDetails() throws IOException, InterruptedException, ParseException {

        StringBuilder str;
        // display a few of the available system information properties
        str = new StringBuilder(
                "\n----------------------------------------------------\n" +
                        "HARDWARE INFO\n" +
                        "----------------------------------------------------" + "\n" +
                        "Serial Number     :  " + SystemInfo.getSerial() + "\n" +
                        "CPU Revision      :  " + SystemInfo.getCpuRevision() + "\n" +
                        "CPU Architecture  :  " + SystemInfo.getCpuArchitecture() + "\n" +
                        "CPU Part          :  " + SystemInfo.getCpuPart() + "\n" +
                        "CPU Temperature   :  " + SystemInfo.getCpuTemperature() + "\n" +
                        "CPU Core Voltage  :  " + SystemInfo.getCpuVoltage() + "\n" +
                        "CPU Model Name    :  " + SystemInfo.getModelName() + "\n" +
                        "Processor         :  " + SystemInfo.getProcessor() + "\n" +
                        "Hardware Revision :  " + SystemInfo.getRevision() + "\n" +
                        "Is Hard Float ABI :  " + SystemInfo.isHardFloatAbi() + "\n" +
                        "Board Type        :  " + SystemInfo.getBoardType().name() + "\n" +

                        "----------------------------------------------------" + "\n" +
                        "MEMORY INFO" + "\n" +
                        "----------------------------------------------------" + "\n" +
                        "Total Memory      :  " + SystemInfo.getMemoryTotal() + "\n" +
                        "Used Memory       :  " + SystemInfo.getMemoryUsed() + "\n" +
                        "Free Memory       :  " + SystemInfo.getMemoryFree() + "\n" +
                        "Shared Memory     :  " + SystemInfo.getMemoryShared() + "\n" +
                        "Memory Buffers    :  " + SystemInfo.getMemoryBuffers() + "\n" +
                        "Cached Memory     :  " + SystemInfo.getMemoryCached() + "\n" +
                        "SDRAM_C Voltage   :  " + SystemInfo.getMemoryVoltageSDRam_C() + "\n" +
                        "SDRAM_I Voltage   :  " + SystemInfo.getMemoryVoltageSDRam_I() + "\n" +
                        "SDRAM_P Voltage   :  " + SystemInfo.getMemoryVoltageSDRam_P() + "\n" +

                        "----------------------------------------------------" + "\n" +
                        "OPERATING SYSTEM INFO" + "\n" +
                        "----------------------------------------------------" + "\n" +
                        "OS Name           :  " + SystemInfo.getOsName() + "\n" +
                        "OS Version        :  " + SystemInfo.getOsVersion() + "\n" +
                        "OS Architecture   :  " + SystemInfo.getOsArch() + "\n" +
                        "OS Firmware Build :  " + SystemInfo.getOsFirmwareBuild() + "\n" +
                        "OS Firmware Date  :  " + SystemInfo.getOsFirmwareDate() + "\n" +

                        "----------------------------------------------------" + "\n" +
                        "JAVA ENVIRONMENT INFO" + "\n" +
                        "----------------------------------------------------" + "\n" +
                        "Java Vendor       :  " + SystemInfo.getJavaVendor() + "\n" +
                        "Java Vendor URL   :  " + SystemInfo.getJavaVendorUrl() + "\n" +
                        "Java Version      :  " + SystemInfo.getJavaVersion() + "\n" +
                        "Java VM           :  " + SystemInfo.getJavaVirtualMachine() + "\n" +
                        "Java Runtime      :  " + SystemInfo.getJavaRuntime() + "\n" +

                        "----------------------------------------------------" + "\n" +
                        "NETWORK INFO" + "\n" +
                        "----------------------------------------------------" + "\n" +

                        // display some of the network information
                        "Hostname          :  " + NetworkInfo.getHostname() + "\n");

        for (String ipAddress : NetworkInfo.getIPAddresses())
            str.append("IP Addresses      :  ").append(ipAddress).append("\n");
        for (String fqdn : NetworkInfo.getFQDNs())
            str.append("FQDN              :  ").append(fqdn).append("\n");
        for (String nameserver : NetworkInfo.getNameservers())
            str.append("Nameserver        :  ").append(nameserver).append("\n");

        str.append("----------------------------------------------------" + "\n" +
                "CODEC INFO" + "\n" +
                "----------------------------------------------------" + "\n" +
                "H264 Codec Enabled:  " + SystemInfo.getCodecH264Enabled() + "\n" +
                "MPG2 Codec Enabled:  " + SystemInfo.getCodecMPG2Enabled() + "\n" +
                "WVC1 Codec Enabled:  " + SystemInfo.getCodecWVC1Enabled() + "\n" +
                "----------------------------------------------------" + "\n" +
                "CLOCK INFO" + "\n" +
                "----------------------------------------------------" + "\n" +
                "ARM Frequency     :  " + SystemInfo.getClockFrequencyArm() + "\n" +
                "CORE Frequency    :  " + SystemInfo.getClockFrequencyCore() + "\n" +
                "H264 Frequency    :  " + SystemInfo.getClockFrequencyH264() + "\n" +
                "ISP Frequency     :  " + SystemInfo.getClockFrequencyISP() + "\n" +
                "V3D Frequency     :  " + SystemInfo.getClockFrequencyV3D() + "\n" +
                "UART Frequency    :  " + SystemInfo.getClockFrequencyUART() + "\n" +
                "PWM Frequency     :  " + SystemInfo.getClockFrequencyPWM() + "\n" +
                "EMMC Frequency    :  " + SystemInfo.getClockFrequencyEMMC() + "\n" +
                "Pixel Frequency   :  " + SystemInfo.getClockFrequencyPixel() + "\n" +
                "VEC Frequency     :  " + SystemInfo.getClockFrequencyVEC() + "\n" +
                "HDMI Frequency    :  " + SystemInfo.getClockFrequencyHDMI() + "\n" +
                "DPI Frequency     :  " + SystemInfo.getClockFrequencyDPI() + "\n");


        return str.toString();
    }
}


