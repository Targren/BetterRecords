package com.codingforcookies.betterrecords.common.block;

import com.codingforcookies.betterrecords.api.wire.IRecordWire;
import com.codingforcookies.betterrecords.client.core.ClientProxy;
import com.codingforcookies.betterrecords.client.core.handler.BetterEventHandler;
import com.codingforcookies.betterrecords.common.block.tile.TileEntityStrobeLight;
import com.codingforcookies.betterrecords.common.core.helper.ConnectionHelper;
import com.codingforcookies.betterrecords.common.util.BetterUtils;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStrobeLight extends BetterBlock {

    public BlockStrobeLight(String name){
        super(Material.IRON, name);
        setHardness(2.75F);
        setResistance(4F);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess block, BlockPos pos) {
        return new AxisAlignedBB(0.25F, 0F, 0.25F, 0.75F, 0.75F, 0.74F);
    }

    //TODO
//    @Override
//    public int getLightValue(IBlockAccess world, BlockPos pos){
//        TileEntity te = world.getTileEntity(pos);
//        if(te == null || !(te instanceof IRecordWire) || !(te instanceof IRecordAmplitude)) return 0;
//        BetterUtils.markBlockDirty(te.getWorld(), te.getPos());
//        return(((IRecordWire) te).getConnections().size() > 0 ? 5 : 0);
//    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
        super.onBlockAdded(world, pos, state);
        world.notifyBlockUpdate(pos, state, state, 3);
    }

    @Override
    public void onBlockPlacedBy(World world, net.minecraft.util.math.BlockPos pos, IBlockState state, EntityLivingBase entityLiving, ItemStack itemStack){
        if(world.isRemote && !ClientProxy.tutorials.get("strobelight")) {
            BetterEventHandler.tutorialText = BetterUtils.getTranslatedString("tutorial.strobelight");
            BetterEventHandler.tutorialTime = System.currentTimeMillis() + 10000;
            ClientProxy.tutorials.put("strobelight", true);
        }
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, net.minecraft.util.math.BlockPos pos, EntityPlayer player, boolean willHarvest){
        if(world.isRemote) return super.removedByPlayer(state, world, pos, player, willHarvest);
        TileEntity te = world.getTileEntity(pos);
        if(te != null && te instanceof IRecordWire) ConnectionHelper.clearConnections(world, (IRecordWire) te, state);
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2){
        return new TileEntityStrobeLight();
    }
}