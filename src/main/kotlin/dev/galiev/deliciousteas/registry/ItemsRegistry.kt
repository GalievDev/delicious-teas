package dev.galiev.deliciousteas.registry

import dev.galiev.deliciousteas.DeliciousTeas.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ItemsRegistry {
    private val ITEMS: MutableMap<Item, Identifier> = mutableMapOf()

    init {
        ITEMS.keys.forEach{ item ->
            Registry.register(Registries.ITEM, ITEMS[item], item)
            ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register{
                it.add(item)
            }
        }
    }

    private fun Item.create(id: String): Item = this.apply {
        ITEMS[this] = Identifier(MOD_ID, id)
    }
}