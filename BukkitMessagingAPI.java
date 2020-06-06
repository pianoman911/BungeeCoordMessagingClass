import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;

/**
 * Klasse Muss als Listener Registriert werden: this.getProxy().getPluginManager().registerListener(this, new BukkitMessagingAPI());
 * Der Channel muss Registriert werden: this.getProxy().registerChannel("DataAPI");
 * Die Message wird über den DataAPI channel geschickt und kommt als String als input an
 *
 */



public class BukkitMessagingAPI implements Listener {

@EventHandler
    public void onPluginMessage(PluginMessageEvent event){

    if(!event.getTag().equalsIgnoreCase("BungeeCord")){
        return;
    }
    DataInput stream = new DataInputStream(new ByteArrayInputStream(event.getData()));

    try {
        String channel = stream.readUTF();
        if(channel.equalsIgnoreCase("data")){
            String input = stream.readUTF();
            //Code hier mit String input
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public static void SendMessageToBukkit(String Channel, String Message, ServerInfo TargetServer){
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    DataOutputStream outputStream = new DataOutputStream(stream);
    try {
        outputStream.writeUTF(Channel);
        outputStream.writeUTF(Message);
    } catch (IOException e) {
        e.printStackTrace();
    }
    TargetServer.sendData("DataAPI",stream.toByteArray());


}
    public static void SendMessageToAllBukkit(String Channel, String Message){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(stream);
        try {
            outputStream.writeUTF(Channel);
            outputStream.writeUTF(Message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ProxyServer.getInstance().getServers().values().stream().forEach((serverInfo -> {
            serverInfo.sendData("DataAPI",stream.toByteArray());
        }));

    }

}
