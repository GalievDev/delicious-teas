package dev.galiev.deliciousteas.block

import dev.galiev.deliciousteas.item.custom.TeaIngredient
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.BlockMirror
import net.minecraft.util.BlockRotation
import net.minecraft.util.Hand
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World
import java.util.stream.Stream

class Kettle(settings: FabricBlockSettings = FabricBlockSettings.create().liquid()): Block(settings) {
    private val ingredients: ArrayList<TeaIngredient> = arrayListOf()

    companion object {
        val FACING = Properties.FACING
    }

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.NORTH))
    }

    val SHAPE_N = Stream.of(
        createCuboidShape(7.0, 5.5, 1.75, 9.0, 6.0, 3.25),
        createCuboidShape(7.0, 2.0, 1.75, 9.0, 2.5, 3.25),
        createCuboidShape(7.0, 2.5, 1.25, 9.0, 5.5, 1.75),
        createCuboidShape(7.25, 3.5, 12.25, 8.75, 5.0, 13.25),
        createCuboidShape(7.0, 2.5, 11.25, 9.0, 4.5, 12.25),
        createCuboidShape(7.5, 4.5, 13.25, 8.5, 5.5, 14.25),
        createCuboidShape(7.5, 5.5, 13.75, 8.5, 6.0, 14.75),
        createCuboidShape(7.5, 5.0, 14.25, 8.5, 5.5, 14.75),
        createCuboidShape(7.5, 4.0, 13.25, 8.5, 4.5, 13.75),
        createCuboidShape(6.5, 5.0, 10.25, 9.5, 5.5, 10.75),
        createCuboidShape(6.5, 1.5, 10.25, 9.5, 2.0, 10.75),
        createCuboidShape(4.5, 1.0, 4.25, 5.5, 7.0, 9.25),
        createCuboidShape(4.5, 2.0, 3.25, 5.5, 6.0, 4.25),
        createCuboidShape(4.5, 2.0, 9.25, 5.5, 6.0, 10.25),
        createCuboidShape(10.5, 2.0, 9.25, 11.5, 6.0, 10.25),
        createCuboidShape(5.0, 1.0, 3.75, 5.5, 2.0, 4.25),
        createCuboidShape(5.0, 6.0, 3.75, 5.5, 7.0, 4.25),
        createCuboidShape(10.5, 6.0, 3.75, 11.0, 7.0, 4.25),
        createCuboidShape(10.5, 1.0, 3.75, 11.0, 2.0, 4.25),
        createCuboidShape(10.5, 1.0, 9.25, 11.0, 2.0, 9.75),
        createCuboidShape(10.5, 6.0, 9.25, 11.0, 7.0, 9.75),
        createCuboidShape(5.0, 1.0, 9.25, 5.5, 2.0, 9.75),
        createCuboidShape(5.0, 6.0, 9.25, 5.5, 7.0, 9.75),
        createCuboidShape(10.5, 2.0, 3.25, 11.5, 6.0, 4.25),
        createCuboidShape(10.5, 1.0, 4.25, 11.5, 7.0, 9.25),
        createCuboidShape(5.5, 1.0, 3.25, 10.5, 7.0, 4.25),
        createCuboidShape(5.5, 1.0, 9.25, 10.5, 7.0, 10.25),
        createCuboidShape(6.5, 2.0, 10.25, 9.5, 5.0, 11.25),
        createCuboidShape(5.5, 0.0, 4.25, 10.5, 1.0, 9.25),
        createCuboidShape(5.5, 7.0, 4.25, 10.5, 7.5, 9.25),
        createCuboidShape(7.5, 7.5, 6.25, 8.5, 8.5, 7.25)
    ).reduce { v1, v2 ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR)
    }.get()

    val SHAPE_W = Stream.of(
        createCuboidShape(1.75, 5.5, 7.0, 3.25, 6.0, 9.0),
        createCuboidShape(1.75, 2.0, 7.0, 3.25, 2.5, 9.0),
        createCuboidShape(1.25, 2.5, 7.0, 1.75, 5.5, 9.0),
        createCuboidShape(12.25, 3.5, 7.25, 13.25, 5.0, 8.75),
        createCuboidShape(11.25, 2.5, 7.0, 12.25, 4.5, 9.0),
        createCuboidShape(13.25, 4.5, 7.5, 14.25, 5.5, 8.5),
        createCuboidShape(13.75, 5.5, 7.5, 14.75, 6.0, 8.5),
        createCuboidShape(14.25, 5.0, 7.5, 14.75, 5.5, 8.5),
        createCuboidShape(13.25, 4.0, 7.5, 13.75, 4.5, 8.5),
        createCuboidShape(10.25, 5.0, 6.5, 10.75, 5.5, 9.5),
        createCuboidShape(10.25, 1.5, 6.5, 10.75, 2.0, 9.5),
        createCuboidShape(4.25, 1.0, 10.5, 9.25, 7.0, 11.5),
        createCuboidShape(3.25, 2.0, 10.5, 4.25, 6.0, 11.5),
        createCuboidShape(9.25, 2.0, 10.5, 10.25, 6.0, 11.5),
        createCuboidShape(9.25, 2.0, 4.5, 10.25, 6.0, 5.5),
        createCuboidShape(3.75, 1.0, 10.5, 4.25, 2.0, 11.0),
        createCuboidShape(3.75, 6.0, 10.5, 4.25, 7.0, 11.0),
        createCuboidShape(3.75, 6.0, 5.0, 4.25, 7.0, 5.5),
        createCuboidShape(3.75, 1.0, 5.0, 4.25, 2.0, 5.5),
        createCuboidShape(9.25, 1.0, 5.0, 9.75, 2.0, 5.5),
        createCuboidShape(9.25, 6.0, 5.0, 9.75, 7.0, 5.5),
        createCuboidShape(9.25, 1.0, 10.5, 9.75, 2.0, 11.0),
        createCuboidShape(9.25, 6.0, 10.5, 9.75, 7.0, 11.0),
        createCuboidShape(3.25, 2.0, 4.5, 4.25, 6.0, 5.5),
        createCuboidShape(4.25, 1.0, 4.5, 9.25, 7.0, 5.5),
        createCuboidShape(3.25, 1.0, 5.5, 4.25, 7.0, 10.5),
        createCuboidShape(9.25, 1.0, 5.5, 10.25, 7.0, 10.5),
        createCuboidShape(10.25, 2.0, 6.5, 11.25, 5.0, 9.5),
        createCuboidShape(4.25, 0.0, 5.5, 9.25, 1.0, 10.5),
        createCuboidShape(4.25, 7.0, 5.5, 9.25, 7.5, 10.5),
        createCuboidShape(6.25, 7.5, 7.5, 7.25, 8.5, 8.5)
    ).reduce { v1, v2 ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR)
    }.get()

    val SHAPE_S = Stream.of(
        createCuboidShape(7.0, 5.5, 12.75, 9.0, 6.0, 14.25),
        createCuboidShape(7.0, 2.0, 12.75, 9.0, 2.5, 14.25),
        createCuboidShape(7.0, 2.5, 14.25, 9.0, 5.5, 14.75),
        createCuboidShape(7.25, 3.5, 2.75, 8.75, 5.0, 3.75),
        createCuboidShape(7.0, 2.5, 3.75, 9.0, 4.5, 4.75),
        createCuboidShape(7.5, 4.5, 1.75, 8.5, 5.5, 2.75),
        createCuboidShape(7.5, 5.5, 1.25, 8.5, 6.0, 2.25),
        createCuboidShape(7.5, 5.0, 1.25, 8.5, 5.5, 1.75),
        createCuboidShape(7.5, 4.0, 2.25, 8.5, 4.5, 2.75),
        createCuboidShape(6.5, 5.0, 5.25, 9.5, 5.5, 5.75),
        createCuboidShape(6.5, 1.5, 5.25, 9.5, 2.0, 5.75),
        createCuboidShape(10.5, 1.0, 6.75, 11.5, 7.0, 11.75),
        createCuboidShape(10.5, 2.0, 11.75, 11.5, 6.0, 12.75),
        createCuboidShape(10.5, 2.0, 5.75, 11.5, 6.0, 6.75),
        createCuboidShape(4.5, 2.0, 5.75, 5.5, 6.0, 6.75),
        createCuboidShape(10.5, 1.0, 11.75, 11.0, 2.0, 12.25),
        createCuboidShape(10.5, 6.0, 11.75, 11.0, 7.0, 12.25),
        createCuboidShape(5.0, 6.0, 11.75, 5.5, 7.0, 12.25),
        createCuboidShape(5.0, 1.0, 11.75, 5.5, 2.0, 12.25),
        createCuboidShape(5.0, 1.0, 6.25, 5.5, 2.0, 6.75),
        createCuboidShape(5.0, 6.0, 6.25, 5.5, 7.0, 6.75),
        createCuboidShape(10.5, 1.0, 6.25, 11.0, 2.0, 6.75),
        createCuboidShape(10.5, 6.0, 6.25, 11.0, 7.0, 6.75),
        createCuboidShape(4.5, 2.0, 11.75, 5.5, 6.0, 12.75),
        createCuboidShape(4.5, 1.0, 6.75, 5.5, 7.0, 11.75),
        createCuboidShape(5.5, 1.0, 11.75, 10.5, 7.0, 12.75),
        createCuboidShape(5.5, 1.0, 5.75, 10.5, 7.0, 6.75),
        createCuboidShape(6.5, 2.0, 4.75, 9.5, 5.0, 5.75),
        createCuboidShape(5.5, 0.0, 6.75, 10.5, 1.0, 11.75),
        createCuboidShape(5.5, 7.0, 6.75, 10.5, 7.5, 11.75),
        createCuboidShape(7.5, 7.5, 8.75, 8.5, 8.5, 9.75)
    ).reduce { v1, v2 ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR)
    }.get()

    val SHAPE_E = Stream.of(
        createCuboidShape(12.75, 5.5, 7.0, 14.25, 6.0, 9.0),
        createCuboidShape(12.75, 2.0, 7.0, 14.25, 2.5, 9.0),
        createCuboidShape(14.25, 2.5, 7.0, 14.75, 5.5, 9.0),
        createCuboidShape(2.75, 3.5, 7.25, 3.75, 5.0, 8.75),
        createCuboidShape(3.75, 2.5, 7.0, 4.75, 4.5, 9.0),
        createCuboidShape(1.75, 4.5, 7.5, 2.75, 5.5, 8.5),
        createCuboidShape(1.25, 5.5, 7.5, 2.25, 6.0, 8.5),
        createCuboidShape(1.25, 5.0, 7.5, 1.75, 5.5, 8.5),
        createCuboidShape(2.25, 4.0, 7.5, 2.75, 4.5, 8.5),
        createCuboidShape(5.25, 5.0, 6.5, 5.75, 5.5, 9.5),
        createCuboidShape(5.25, 1.5, 6.5, 5.75, 2.0, 9.5),
        createCuboidShape(6.75, 1.0, 4.5, 11.75, 7.0, 5.5),
        createCuboidShape(11.75, 2.0, 4.5, 12.75, 6.0, 5.5),
        createCuboidShape(5.75, 2.0, 4.5, 6.75, 6.0, 5.5),
        createCuboidShape(5.75, 2.0, 10.5, 6.75, 6.0, 11.5),
        createCuboidShape(11.75, 1.0, 5.0, 12.25, 2.0, 5.5),
        createCuboidShape(11.75, 6.0, 5.0, 12.25, 7.0, 5.5),
        createCuboidShape(11.75, 6.0, 10.5, 12.25, 7.0, 11.0),
        createCuboidShape(11.75, 1.0, 10.5, 12.25, 2.0, 11.0),
        createCuboidShape(6.25, 1.0, 10.5, 6.75, 2.0, 11.0),
        createCuboidShape(6.25, 6.0, 10.5, 6.75, 7.0, 11.0),
        createCuboidShape(6.25, 1.0, 5.0, 6.75, 2.0, 5.5),
        createCuboidShape(6.25, 6.0, 5.0, 6.75, 7.0, 5.5),
        createCuboidShape(11.75, 2.0, 10.5, 12.75, 6.0, 11.5),
        createCuboidShape(6.75, 1.0, 10.5, 11.75, 7.0, 11.5),
        createCuboidShape(11.75, 1.0, 5.5, 12.75, 7.0, 10.5),
        createCuboidShape(5.75, 1.0, 5.5, 6.75, 7.0, 10.5),
        createCuboidShape(4.75, 2.0, 6.5, 5.75, 5.0, 9.5),
        createCuboidShape(6.75, 0.0, 5.5, 11.75, 1.0, 10.5),
        createCuboidShape(6.75, 7.0, 5.5, 11.75, 7.5, 10.5),
        createCuboidShape(8.75, 7.5, 7.5, 9.75, 8.5, 8.5)
    ).reduce { v1, v2 ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR)
    }.get()


    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return when (state?.get(FACING)) {
            Direction.DOWN -> SHAPE_S
            Direction.UP -> SHAPE_S
            Direction.NORTH -> SHAPE_N
            Direction.SOUTH -> SHAPE_S
            Direction.WEST -> SHAPE_W
            Direction.EAST -> SHAPE_E
            null -> SHAPE_W
        }
    }

    override fun getPlacementState(ctx: ItemPlacementContext?): BlockState? {
        return defaultState.with(FACING, ctx?.player?.horizontalFacing?.opposite)
    }

    override fun rotate(state: BlockState?, rotation: BlockRotation?): BlockState {
        return state?.with(FACING, rotation?.rotate(state.get(FACING)))!!
    }

    override fun mirror(state: BlockState?, mirror: BlockMirror?): BlockState {
        return state?.rotate(mirror?.getRotation(state.get(FACING)))!!
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING)
    }

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