package dev.galiev.deliciousteas.item

import dev.galiev.deliciousteas.registry.BlocksWithCustomItemRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.item.BlockItem

class KettleItem(block: Block = BlocksWithCustomItemRegistry.KETTLE, settings: FabricItemSettings = FabricItemSettings().maxCount(1)): BlockItem(block, settings) {
}