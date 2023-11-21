package dev.galiev.deliciousteas.registry

import dev.galiev.deliciousteas.DeliciousTeas
import dev.galiev.deliciousteas.block.Couple
import dev.galiev.deliciousteas.block.Kettle
import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlocksWithCustomItemRegistry {
    val BLOCKS: MutableMap<Block, Identifier> = mutableMapOf()

    val COUPLE = Couple().create("couple")


    init {
        BLOCKS.keys.forEach { block ->
            Registry.register(Registries.BLOCK, BLOCKS[block], block)
        }
    }

    private fun Block.create(id: String): Block = this.apply {
        BLOCKS[this] = Identifier(DeliciousTeas.MOD_ID, id)
    }
}