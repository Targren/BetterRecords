package com.codingforcookies.betterrecords.crafting.recipe

import com.codingforcookies.betterrecords.item.ItemRecord
import net.minecraft.init.Items
import net.minecraft.inventory.InventoryCrafting
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.IRecipe
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class RecipeRecordRepeatable : IRecipe {

    override fun matches(inventoryCrafting: InventoryCrafting, world: World): Boolean {
        var record: ItemStack? = null
        var comparator = false

        for (i in 0..inventoryCrafting.sizeInventory - 1) {
            val itemstack = inventoryCrafting.getStackInSlot(i)
            if (itemstack != null) {
                if (itemstack.item is ItemRecord && itemstack.tagCompound != null)
                    if (record != null)
                        return false
                    else
                        record = itemstack
                else if (itemstack.item === Items.COMPARATOR)
                    if (comparator)
                        return false
                    else
                        comparator = true
                else
                    return false
            }
        }

        return record != null && comparator
    }

    override fun getCraftingResult(inventoryCrafting: InventoryCrafting): ItemStack? {
        var record: ItemStack? = null
        var comparator = false

        for (i in 0..inventoryCrafting.sizeInventory - 1) {
            val itemstack = inventoryCrafting.getStackInSlot(i)
            if (itemstack != null) {
                if (itemstack.item is ItemRecord && itemstack.tagCompound != null)
                    if (record != null)
                        return null
                    else
                        record = itemstack
                else if (itemstack.item === Items.COMPARATOR)
                    if (comparator)
                        return null
                    else
                        comparator = true
                else
                    return null
            }
        }

        if (record == null || !comparator)
            return null
        else {
            val newRecord = ItemStack.copyItemStack(record)

            if (newRecord.tagCompound == null)
                newRecord.tagCompound = NBTTagCompound()

            newRecord.tagCompound!!.setBoolean("repeat", true)

            return newRecord
        }
    }

    override fun getRecipeSize(): Int {
        return 10
    }

    override fun getRecipeOutput(): ItemStack? {
        return null
    }

    override fun getRemainingItems(inv: InventoryCrafting): Array<ItemStack> {
        inv.clear()
        return arrayOf()
    }
}
