package dev.galiev.deliciousteas.item

import dev.galiev.deliciousteas.registry.BlocksRegistry
import dev.galiev.deliciousteas.utils.NbtUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.UseAction
import net.minecraft.world.World

class CoupleItem(block: Block = BlocksRegistry.COUPLE, settings: FabricItemSettings = FabricItemSettings().maxCount(1)): BlockItem(block, settings) {

    override fun getUseAction(stack: ItemStack?): UseAction {
        return UseAction.DRINK
    }

    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user?.getStackInHand(hand)
        if (NbtUtils.getBoolean(stack, "tea")) {

        }
        return super.use(world, user, hand)
    }
}