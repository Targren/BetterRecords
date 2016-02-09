package com.codingforcookies.betterrecords;

import com.codingforcookies.betterrecords.items.ItemURLMultiRecord;
import com.codingforcookies.betterrecords.items.ItemURLRecord;

import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class RecipeRecordShuffle implements IRecipe{

	public boolean matches(InventoryCrafting par1InventoryCrafting, World par2World){
		ItemStack record = null;
		boolean shuffle = false;
		for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); ++i){
			ItemStack itemstack = par1InventoryCrafting.getStackInSlot(i);
			if(itemstack != null) {
				if(itemstack.getItem() instanceof ItemURLMultiRecord && itemstack.stackTagCompound != null) if(record != null) return false;
				else record = itemstack;
				else if(itemstack.getItem() == Item.getItemFromBlock(Blocks.redstone_torch)) if(shuffle) return false;
				else shuffle = true;
				else return false;
			}
		}
		return record != null && shuffle;
	}

	public ItemStack getCraftingResult(InventoryCrafting par1InventoryCrafting){
		ItemStack record = null;
		boolean shuffle = false;
		for(int i = 0; i < par1InventoryCrafting.getSizeInventory(); i++){
			ItemStack itemstack = par1InventoryCrafting.getStackInSlot(i);
			if(itemstack != null) {
				if(itemstack.getItem() instanceof ItemURLRecord && itemstack.stackTagCompound != null) if(record != null) return null;
				else record = itemstack;
				else if(itemstack.getItem() == Item.getItemFromBlock(Blocks.redstone_torch)) if(shuffle) return null;
				else shuffle = true;
				else return null;
			}
		}
		if(record == null || !shuffle) return null;
		else{
			ItemStack newRecord = ItemStack.copyItemStack(record);
			if(newRecord.stackTagCompound == null) newRecord.stackTagCompound = new NBTTagCompound();
			newRecord.stackTagCompound.setBoolean("shuffle", true);
			return newRecord;
		}
	}

	public int getRecipeSize(){
		return 10;
	}

	public ItemStack getRecipeOutput(){
		return null;
	}
}