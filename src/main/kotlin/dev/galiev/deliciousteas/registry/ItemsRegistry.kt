package dev.galiev.deliciousteas.registry

import dev.galiev.deliciousteas.DeliciousTeas.DELICIOUS_TEAS
import dev.galiev.deliciousteas.DeliciousTeas.MOD_ID
import dev.galiev.deliciousteas.item.CoupleItem
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ItemsRegistry {
    private val ITEMS: MutableMap<Item, Identifier> = mutableMapOf()

    val COUPLE_ITEM = CoupleItem().create("couple_item")

    init {
        ITEMS.keys.forEach{ item ->
            Registry.register(Registries.ITEM, ITEMS[item], item)
            ItemGroupEvents.modifyEntriesEvent(DELICIOUS_TEAS).register{
                it.add(item)
            }
        }
    }

    private fun Item.create(id: String): Item = this.apply {
        ITEMS[this] = Identifier(MOD_ID, id)
    }
}