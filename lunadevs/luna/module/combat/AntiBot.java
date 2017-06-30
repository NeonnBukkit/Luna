package lunadevs.luna.module.combat;

import java.util.ArrayList;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.passive.EntitySheep;

import com.darkmagician6.eventapi.EventTarget;
import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.EventPacket;
import lunadevs.luna.module.Module;
import lunadevs.luna.option.Option;
import lunadevs.luna.utils.faithsminiutils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;

public class AntiBot extends Module {

	public static boolean active;
	@Option.Op(name = "Watchdog")
	public static boolean Watchdog = false;
	@Option.Op(name = "G.W.E.N")
	public static boolean GWEN = true;
	@Option.Op(name = "Advanced")
	public static boolean Advanced = false;
	@Option.Op(name = "Extreme")
	public static boolean Extreme = false;
	@Option.Op(name = "Nuddles")
	public static boolean Nuddles = false;
	public AntiBot() {
		super("AntiBot", 0, Category.COMBAT, true);
		bots = new ArrayList<Entity>();
		time = new Timer();
	}
	
	public static ArrayList<Entity> bots;
	private Timer time;
	int boostNuddles = 0;
	public static String modname;
	int nuddlesBotCount = 0;

	@Override
	public void onUpdate() {
		if (!this.isEnabled)
			return;
		if (this.Watchdog == true) {
			watchdog();
			if (this.GWEN == true) {
				this.GWEN = false;
				this.Advanced = false;
				this.Extreme = false;
				this.Nuddles = false;
			}
			modname = "Watchdog";
		} else if (this.GWEN == true) {
			
			if (this.Watchdog == true) {
				this.Watchdog = false;
				this.Advanced=false;
				this.Extreme = false;
				this.Nuddles = false;
			}
			modname = "G.W.E.N";
         } else if (this.Extreme == true) {
			
			if (this.Watchdog == true) {
				this.Watchdog = false;
				this.Advanced=false;
				this.GWEN = false;
				this.Nuddles = false;
			}
			modname = "Extreme";
		} else if (this.Advanced == true) {
			extreme();
			if (this.Watchdog == true) {
				this.Watchdog = false;
				this.GWEN = false;
				this.Nuddles = false;
				/** Thanks Jordan & Italicz for the Advanced Bot Mode. */
			}
			modname = "Advanced";
		} else if (this.Nuddles){
			this.Nuddles = true;
			nuddles();
		}
		if (this.Watchdog == true) {
			watchdog();
		}
		super.onUpdate();
	}

	public void nuddles(){
		if (this.Nuddles == true) {
			if (time.hasReached((double) 15000)) {
				bots.clear();
				time.resetDouble();
			}
			EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
		
		for (Object entity : zCore.mc().theWorld.loadedEntityList) {
		float reasonsToRemoveEntity = 0;
		int airTicks = 0;
		if ((((Entity) entity).isInvisible()) && (entity != Minecraft.thePlayer)) {
			reasonsToRemoveEntity += 0.5;
		}
		if((((Entity) entity).isInvisible()) || (((Entity) entity).ticksExisted < 2)){
			reasonsToRemoveEntity += 0.5;
		}
		if(((Entity) entity).getDisplayName().getFormattedText().contains("bot")){
			reasonsToRemoveEntity += 0.1;
		}
		if(!((Entity) entity).onGround){
			airTicks += 1;
			if(airTicks > 900){
				reasonsToRemoveEntity += 1;
			}
		}else if(((Entity) entity).onGround){
			airTicks = 0;
		}
		
		if (!((Entity) entity).getDisplayName().getFormattedText().contains("�a")
				&& !((Entity) entity).getDisplayName().getFormattedText().contains("�9")
				&& !((Entity) entity).getDisplayName().getFormattedText().contains("�c")
				&& !((Entity) entity).getDisplayName().getFormattedText().contains("�e") && entity != p) {
			bots.add((Entity) entity);
		if ((((Entity) entity).isInvisible()) && (entity != Minecraft.thePlayer)) {
			reasonsToRemoveEntity += 3;
		}
	}
		if ((((Entity) entity).motionX > 1.1) || (((Entity) entity).motionZ > 1.1) || (((Entity) entity).motionY > 0.6)){
			reasonsToRemoveEntity += 0.75;
			if((((Entity) entity).motionY > 0.5)){
				reasonsToRemoveEntity += 1.25;
			}
		}
		if(entity instanceof EntityGuardian){
			reasonsToRemoveEntity += 0.5;
		}
		if(entity != zCore.mc().thePlayer && !(entity instanceof NpcMerchant) && !(entity instanceof EntityArmorStand) && !(entity instanceof EntityPig) && !(entity instanceof EntityCow) && !(entity instanceof EntitySheep)){
			if(reasonsToRemoveEntity > 3.25){
				mc.theWorld.removeEntity((Entity) entity);
				((Entity) entity).setInvisible(false);
				((Entity) entity).setInvisible(true);
				reasonsToRemoveEntity = 0;
				airTicks = 0;
				nuddlesBotCount += 1;
				zCore.addChatMessageP("�7Nuddles AntiBot removed the bot: " + ((Entity) entity).getName() + "! " + 
				"�7 Total Bots removed: " + String.valueOf(nuddlesBotCount));
			}
		}
		}
	}
	}
	
	public void watchdog() {
		if (this.Watchdog == true) {
			for (Object entity : mc.theWorld.loadedEntityList) {
				if ((((Entity) entity).isInvisible()) && (entity != Minecraft.thePlayer)) {
					mc.theWorld.removeEntity((Entity) entity);
					((Entity) entity).setInvisible(false);
				}
			}
		}
	}
	
	public void extreme() {
		if (this.Extreme == true) {
			if (time.hasReached((double) 15000)) {
				bots.clear();
				time.resetDouble();
			}
			EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
			for (Object entity : mc.theWorld.loadedEntityList) { //Idk whats wrong with Unicode on diff computers, �
				if (!((Entity) entity).getDisplayName().getFormattedText().contains("�a")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�9")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�c")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�e") && entity != p) {
					bots.add((Entity) entity);
				if ((((Entity) entity).isInvisible()) && (entity != Minecraft.thePlayer)) {
					mc.theWorld.removeEntity((Entity) entity);
					((Entity) entity).setInvisible(false);
					((Entity) entity).setInvisible(true);
				}
			}
		}
	}
}
	
	public void nuddlesisjustgoingtoborrowitaliczandjordansantibotkthx() {
		if (this.Nuddles == true) {
			if (time.hasReached((double) 15000)) {
				bots.clear();
				time.resetDouble();
			}
			EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
			for (Object entity : mc.theWorld.loadedEntityList) { //Idk whats wrong with Unicode on diff computers, �
				if (!((Entity) entity).getDisplayName().getFormattedText().contains("�a")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�9")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�c")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�e") && entity != p) {
					bots.add((Entity) entity);
				if ((((Entity) entity).isInvisible()) && (entity != Minecraft.thePlayer)) {
					mc.theWorld.removeEntity((Entity) entity);
					((Entity) entity).setInvisible(false);
					((Entity) entity).setInvisible(true);
				}
			}
		}
	}
}
	
	public void advanced() {
		if (this.Advanced == true) {
			if (time.hasReached((double) 15000)) {
				bots.clear();
				time.resetDouble();
			}
			EntityPlayerSP p = Minecraft.getMinecraft().thePlayer;
			for (Object entity : mc.theWorld.loadedEntityList) { //Idk whats wrong with Unicode on diff computers, �
				if (!((Entity) entity).getDisplayName().getFormattedText().contains("�a")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�9")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�c")
						&& !((Entity) entity).getDisplayName().getFormattedText().contains("�e") && entity != p) {
					bots.add((Entity) entity);
				}
			}
		}
	}
	@EventTarget
	public void receivePackets(EventPacket e) {
		if (!this.isEnabled)
			return;
		if (this.GWEN == true || this.Extreme == true || this.Nuddles == true) {
			for (Object entity : mc.theWorld.loadedEntityList) {
				if ((e.getPacket() instanceof S0CPacketSpawnPlayer)) {
					S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer) e.getPacket();
					double posX = packet.func_148942_f() / 32.0D;
					double posY = packet.func_148949_g() / 32.0D;
					double posZ = packet.func_148946_h() / 32.0D;

					double difX = z.player().posX - posX;
					double difY = z.player().posY - posY;
					double difZ = z.player().posZ - posZ;

					double dist = Math.sqrt(difX * difX + difY * difY + difZ * difZ);
					if ((dist <= 17.0D) && (posX != z.player().posX) && (posY != z.player().posY)
							&& (posZ != z.player().posZ)) {
						e.setCancelled(true);
					}
				}
			}
		}
	}

	@Override
	public void onDisable() {
		bots.clear();
		nuddlesBotCount = 0;
		time.resetDouble();
		super.onDisable();
		active = false;
	}

	@Override
	public void onEnable() {
		nuddlesBotCount = 0;
		active = true;
		super.onEnable();
	}

	@Override
	public String getValue() {
		return modname;
	}

}
