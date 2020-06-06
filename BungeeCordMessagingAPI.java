package pianorpgsystem;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.jetbrains.annotations.NotNull;

import java.io.*;


/**
 * Der ChannelListener muss regestriert werden:
 *                                              private BungeeCordMessagingAPI bungeeCordMessagingAPI;
 *                                              this.bungeeCordMessagingAPI = new BungeeCordMessagingAPI();
 *                                              Bukkit.getMessenger().registerIncomingPluginChannel(this,"DataAPI",bungeeCordMessagingAPI);
 * Strings werden über den DataAPI channel übertragen
 */

public class BungeeCordMessagingAPI implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String Channel, Player player, byte[] Message) {
        DataInputStream stream = new DataInputStream(new ByteArrayInputStream(Message));
        try {
            String subChannel = stream.readUTF();
            if(subChannel.equalsIgnoreCase("DataAPI")){
                String input = stream.readUTF();

                notifyAll();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public void sendMessageToBungeeCord(Player player, String Channel, String information, Plugin plugin){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(stream);

        try {
            outputStream.writeUTF(Channel);
            outputStream.writeUTF(information);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(plugin,"BungeeCord",stream.toByteArray());

    }
    public void sendPlayerToServer(Player player, String TargetServer, Plugin plugin){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream outputStream = new DataOutputStream(stream);

        try {
            outputStream.writeUTF("Connect");
            outputStream.writeUTF(TargetServer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.sendPluginMessage(plugin,"BungeeCord",stream.toByteArray());

    }


}
