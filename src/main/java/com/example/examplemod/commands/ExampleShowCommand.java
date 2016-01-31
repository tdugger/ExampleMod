package com.example.examplemod.commands;

import com.example.examplemod.ExampleMod;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
//import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Cindy on 3/20/2015.
 */
public class ExampleShowCommand implements ICommand {

    private List aliases;
    private Timer timer;
    private ChunkCoordinates coords;
    
    public ExampleShowCommand() {
        this.aliases = new ArrayList();
        this.aliases.add("home");
        timer = new Timer();
    }
    
    class MoveTask extends TimerTask {
    	public void run() {
    	}
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "home";
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    @Override
	public List addTabCompletionOptions(ICommandSender arg0, String[] arg1) {
		return null;
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender arg0) {
		return true;
	}

	@Override
	public List getCommandAliases() {
		return this.aliases;
	}

	@Override
	public String getCommandName() {
		return "home";
	}

	@Override
	public void processCommand(ICommandSender arg0, String[] arg1) {
        if (arg0 instanceof EntityPlayerMP) {
    		final EntityPlayerMP player = (EntityPlayerMP) arg0;
            World world = DimensionManager.getWorld(0);
            ChunkCoordinates location = player.getBedLocation(world.provider.dimensionId);
            if (location != null) {
            	player.addChatMessage(new ChatComponentText("Moving you to your bed in 3 seconds."));
            } else {
            	player.addChatMessage(new ChatComponentText("Bed not found. Moving you to spawn in 3 seconds."));
            	location = world.getSpawnPoint();
            }
            final ChunkCoordinates coords = location;
		    final TimerTask timerTask = new TimerTask() {
		        @Override
		        public void run() {
		            player.setLocationAndAngles(coords.posX, coords.posY+1.5, coords.posZ, 0, 0);
		        }
		    };
		    timer.schedule(timerTask, 3 * 1000);
        }
	}
}
