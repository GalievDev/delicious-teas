package dev.galiev.deliciousteas.registry

import dev.galiev.deliciousteas.DeliciousTeas.DELICIOUS_TEAS
import dev.galiev.deliciousteas.DeliciousTeas.MOD_ID
import dev.galiev.deliciousteas.block.Kettle
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlocksRegistry {
    val BLOCKS: MutableMap<Block, Identifier> = mutableMapOf()

    val KETTLE = Kettle().create("kettle")

    init {
        BLOCKS.keys.forEach { block ->
            Registry.register(Registries.BLOCK, BLOCKS[block], block)
            Registry.register(Registries.ITEM, BLOCKS[block], BlockItem(block, FabricItemSettings()))
            ItemGroupEvents.modifyEntriesEvent(DELICIOUS_TEAS).register {
                it.add(block)
            }
        }
    }

    private fun Block.create(id: String): Block = this.apply {
        BLOCKS[this] = Identifier(MOD_ID, id)
    }
}