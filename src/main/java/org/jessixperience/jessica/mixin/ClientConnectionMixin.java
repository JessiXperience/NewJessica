package org.jessixperience.jessica.mixin;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.packet.Packet;
import org.jessixperience.jessica.NewJessica;
import org.jessixperience.jessica.utils.interfaces.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

/*

net.minecraft.network.packet.c2s.play.CreativeInventoryActionC2SPacket

// Возможно придётся открывать/закрывать инвентарь
net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket

// Атака
net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket
net.minecraft.network.packet.c2s.play.HandSwingC2SPacket

net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket

 */

@Mixin( ClientConnection.class )
public class ClientConnectionMixin
{
    @Unique
    private List<String> ignorePackets = Arrays.asList(
            // Client
            "net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$PositionAndOnGround",
            "net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket",
            "net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$LookAndOnGround",
            "net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket$Full",
            // SERVER
            "net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntityS2CPacket$MoveRelative",
            "net.minecraft.network.packet.s2c.play.EntityS2CPacket$RotateAndMoveRelative",
            "net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket",
            "net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket",
            "net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket",
            "net.minecraft.network.packet.s2c.play.BundleS2CPacket",
            "net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntityS2CPacket$Rotate",
            "net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket",
            "net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket",
            "net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket",
            // Interesting packets
            "net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket",
            "net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket",
            "net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket",
            "net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket"
    );

    @Inject( method = "channelRead0(Lio/netty/channel/ChannelHandlerContext;Lnet/minecraft/network/packet/Packet;)V", at = @At( "HEAD" ) )
    private void readPackages( ChannelHandlerContext channelHandlerContext, Packet<?> packet, CallbackInfo ci ) {
        String packetClass = packet.getClass().getName();

        if ( ignorePackets.contains( packetClass ) ) return;
        if ( NewJessica.DEBUG_MODE ) NewJessica.INSTANCE.getLogger().info( packetClass );

        for ( Util tool : NewJessica.INSTANCE.getUtils() ) {
            if ( !packetClass.equals( tool.getTriggerPacket() ) ) continue;
            tool.exec();
        }
     }

    @Inject( method = "send(Lnet/minecraft/network/packet/Packet;)V", at = @At( "TAIL" ))
    public void sendPackages( Packet<?> packet, CallbackInfo ci ) {
        String packetClass = packet.getClass().getName();

        boolean initPacket = packetClass.equals("net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket");
        if ( NewJessica.DEBUG_MODE && initPacket ) NewJessica.INSTANCE.getLogger().info( "Got init packet" );

        if ( ignorePackets.contains( packetClass ) ) return;
        if ( NewJessica.DEBUG_MODE ) NewJessica.INSTANCE.getLogger().info( packetClass );

        for ( Util tool : NewJessica.INSTANCE.getUtils() ) {
            if ( initPacket ) tool.init();
        }
    }

}
