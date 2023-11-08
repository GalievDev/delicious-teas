package dev.galiev.deliciousteas.block

import dev.galiev.deliciousteas.item.custom.TeaIngredient
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class Couple(settings: FabricBlockSettings = FabricBlockSettings.create().liquid()): Block(settings) {
    private val ingredients: ArrayList<TeaIngredient> = arrayListOf()

    @Deprecated("Deprecated in Java")
    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        val stack = player?.getStackInHand(hand)
        if (hasIngredients() && stack?.isEmpty!!) {
            removeIngredient(ingredients[ingredients.lastIndex])
        } else if (stack?.item is TeaIngredient) {
            addIngredient(stack.item as TeaIngredient)
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    fun getIngredients(): ArrayList<TeaIngredient> {
        return ingredients
    }

    fun addIngredient(ingredient: TeaIngredient) {
        ingredients.add(ingredient)
    }

    fun removeIngredient(ingredient: TeaIngredient) {
        ingredients.remove(ingredient)
    }

    fun hasIngredients(): Boolean {
        return ingredients.size != 0
    }
}