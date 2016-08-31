package com.codingforcookies.betterrecords.common.block.tile;

import com.codingforcookies.betterrecords.api.connection.RecordConnection;
import com.codingforcookies.betterrecords.api.record.IRecordAmplitude;
import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Random;

public class TileEntityLazerCluster extends BetterTile implements IRecordWire, IRecordAmplitude, ITickable {
    public ArrayList<RecordConnection> connections = null;
    public ArrayList<RecordConnection> getConnections() { return connections; }

    public float bass = 0;
    public float r, g, b;

    public void setTreble(float amplitude) { }
    public void setTreble(float amplitude, float r, float g, float b) { }
    public float getTreble() { return 0; }

    public void setBass(float amplitude) {
        this.bass = amplitude;

        r = new Random((long)amplitude + System.nanoTime()).nextFloat();
        g = new Random((long)amplitude + System.nanoTime()).nextFloat();
        b = new Random((long)amplitude + System.nanoTime()).nextFloat();

        int colorNum = new Random().nextInt(2);
        r += colorNum == 0 ? .3F : -.1F;
        g += colorNum == 1 ? .3F : -.1F;
        b += colorNum == 2 ? .3F : -.1F;

        if(r < .2F)
            r += r;
        if(g < .2F)
            g += g;
        if(b < .2F)
            b += b;
    }
    public void setBass(float amplitude, float r, float g, float b) {
        this.bass = amplitude;
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public float getBass() { return bass; }

    public String getName() { return "Lazer Cluster"; }
    public float getSongRadiusIncrease() { return 0F; }

    public TileEntityLazerCluster() {
        connections = new ArrayList<RecordConnection>();
    }

    @SideOnly(Side.SERVER)
    public boolean canUpdate() {
        return false;
    }

    @Override
    public void update() {
        if(bass > 0F)
            bass--;
        if(bass < 0F)
            bass = 0F;
    }

    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);

        if(compound.hasKey("connections"))
            connections = ConnectionHelper.unserializeConnections(compound.getString("connections"));
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);

        compound.setString("connections", ConnectionHelper.serializeConnections(connections));

        return compound;
    }
}
