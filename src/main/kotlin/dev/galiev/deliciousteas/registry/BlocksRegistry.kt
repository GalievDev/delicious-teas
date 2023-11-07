package dev.galiev.deliciousteas.registry

import dev.galiev.deliciousteas.DeliciousTeas.DELICIOUS_TEAS
import dev.galiev.deliciousteas.DeliciousTeas.MOD_ID
import dev.galiev.deliciousteas.block.Couple
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlocksRegistry {
    val BLOCKS: MutableMap<Block, Identifier> = mutableMapOf()

    val COUPLE = Couple().create("couple")

    init {
        BLOCKS.keys.forEach { block ->
            Registry.register(Registries.BLOCK, BLOCKS[block], block)
            Registry.register(Registries.ITEM, BLOCKS[block], BlockItem(block, Item.Settings()))
            ItemGroupEvents.modifyEntriesEvent(DELICIOUS_TEAS).register {
                it.add(block)
            }
        }
    }

    private fun Block.create(id: String): Block = this.apply {
        BLOCKS[this] = Identifier(MOD_ID, id)
    }
}