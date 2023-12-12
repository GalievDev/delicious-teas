package dev.galiev.deliciousteas.item

import dev.galiev.deliciousteas.registry.BlocksWithCustomItemRegistry
import dev.galiev.deliciousteas.utils.NbtUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.util.hit.HitResult
import net.minecraft.world.RaycastContext
import net.minecraft.world.World

class KettleItem(block: Block = BlocksWithCustomItemRegistry.KETTLE, settings: FabricItemSettings = FabricItemSettings().maxCount(1)): BlockItem(block, settings) {

    override fun use(world: World?, user: PlayerEntity?, hand: Hand?): TypedActionResult<ItemStack> {
        val stack = user?.getStackInHand(hand)
        NbtUtils.setBoolean(stack, "water", false)
        val trace = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY)

        if (trace.type != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(stack)
        }

        val pos = trace.blockPos
        val state = world?.getBlockState(pos)

        if (!NbtUtils.getBoolean(stack, "water")) {
            if (state?.fluidState == Blocks.WATER.defaultState.fluidState) {
                NbtUtils.setDouble(stack, "liters", 1.0)
                NbtUtils.setBoolean(stack, "water", true)
                user?.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F)
            }
        }

        return TypedActionResult.pass(stack)
    }

    override fun appendTooltip(
        stack: ItemStack?,
        world: World?,
        tooltip: MutableList<Text>?,
        context: TooltipContext?
    ) {
        val water = NbtUtils.getDouble(stack, "liters")
        if (Screen.hasShiftDown()) {
            tooltip?.add(Text.literal("$water/1.0 Litters").formatted(Formatting.AQUA))
        } else {
            tooltip?.add(Text.literal("Press Shift for more info").formatted(Formatting.YELLOW))
        }
        super.appendTooltip(stack, world, tooltip, context)
    }

}