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


class Couple(settings: FabricBlockSettings = FabricBlockSettings.create().liquid().noCollision()): Block(settings) {
    companion object {
        val FACING = Properties.HOPPER_FACING
    }
    private val ingredients: ArrayList<TeaIngredient> = arrayListOf()

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.SOUTH))
    }

    private val SHAPE_N = Stream.of(
        createCuboidShape(7.0, 1.0, 6.0, 10.0, 4.0, 7.0),
        createCuboidShape(7.0, 1.0, 10.0, 10.0, 4.0, 11.0),
        createCuboidShape(6.0, 1.0, 7.0, 7.0, 4.0, 10.0),
        createCuboidShape(10.0, 1.0, 7.0, 11.0, 4.0, 10.0),
        createCuboidShape(7.0, 0.0, 7.0, 10.0, 1.0, 10.0),
        createCuboidShape(8.25, 3.0, 11.0, 8.75, 3.5, 12.0),
        createCuboidShape(6.5, 0.5, 7.0, 7.0, 1.0, 10.0),
        createCuboidShape(10.0, 0.5, 7.0, 10.5, 1.0, 10.0),
        createCuboidShape(7.0, 0.5, 6.5, 10.0, 1.0, 7.0),
        createCuboidShape(7.0, 0.5, 10.0, 10.0, 1.0, 10.5),
        createCuboidShape(8.25, 1.5, 12.0, 8.75, 3.0, 12.5),
        createCuboidShape(10.0, 1.0, 10.0, 10.5, 4.0, 10.5),
        createCuboidShape(10.0, 1.0, 6.5, 10.5, 4.0, 7.0),
        createCuboidShape(6.5, 1.0, 6.5, 7.0, 4.0, 7.0),
        createCuboidShape(6.5, 1.0, 10.0, 7.0, 4.0, 10.5),
        createCuboidShape(8.25, 1.0, 11.0, 8.75, 1.5, 12.0)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    private val SHAPE_E = Stream.of(
        createCuboidShape(6.0, 1.0, 7.0, 7.0, 4.0, 10.0),
        createCuboidShape(10.0, 1.0, 7.0, 11.0, 4.0, 10.0),
        createCuboidShape(7.0, 1.0, 10.0, 10.0, 4.0, 11.0),
        createCuboidShape(7.0, 1.0, 6.0, 10.0, 4.0, 7.0),
        createCuboidShape(7.0, 0.0, 7.0, 10.0, 1.0, 10.0),
        createCuboidShape(11.0, 3.0, 8.25, 12.0, 3.5, 8.75),
        createCuboidShape(7.0, 0.5, 10.0, 10.0, 1.0, 10.5),
        createCuboidShape(7.0, 0.5, 6.5, 10.0, 1.0, 7.0),
        createCuboidShape(6.5, 0.5, 7.0, 7.0, 1.0, 10.0),
        createCuboidShape(10.0, 0.5, 7.0, 10.5, 1.0, 10.0),
        createCuboidShape(12.0, 1.5, 8.25, 12.5, 3.0, 8.75),
        createCuboidShape(10.0, 1.0, 6.5, 10.5, 4.0, 7.0),
        createCuboidShape(6.5, 1.0, 6.5, 7.0, 4.0, 7.0),
        createCuboidShape(6.5, 1.0, 10.0, 7.0, 4.0, 10.5),
        createCuboidShape(10.0, 1.0, 10.0, 10.5, 4.0, 10.5),
        createCuboidShape(11.0, 1.0, 8.25, 12.0, 1.5, 8.75)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    private val SHAPE_S = Stream.of(
        createCuboidShape(7.0, 1.0, 10.0, 10.0, 4.0, 11.0),
        createCuboidShape(7.0, 1.0, 6.0, 10.0, 4.0, 7.0),
        createCuboidShape(10.0, 1.0, 7.0, 11.0, 4.0, 10.0),
        createCuboidShape(6.0, 1.0, 7.0, 7.0, 4.0, 10.0),
        createCuboidShape(7.0, 0.0, 7.0, 10.0, 1.0, 10.0),
        createCuboidShape(8.25, 3.0, 5.0, 8.75, 3.5, 6.0),
        createCuboidShape(10.0, 0.5, 7.0, 10.5, 1.0, 10.0),
        createCuboidShape(6.5, 0.5, 7.0, 7.0, 1.0, 10.0),
        createCuboidShape(7.0, 0.5, 10.0, 10.0, 1.0, 10.5),
        createCuboidShape(7.0, 0.5, 6.5, 10.0, 1.0, 7.0),
        createCuboidShape(8.25, 1.5, 4.5, 8.75, 3.0, 5.0),
        createCuboidShape(6.5, 1.0, 6.5, 7.0, 4.0, 7.0),
        createCuboidShape(6.5, 1.0, 10.0, 7.0, 4.0, 10.5),
        createCuboidShape(10.0, 1.0, 10.0, 10.5, 4.0, 10.5),
        createCuboidShape(10.0, 1.0, 6.5, 10.5, 4.0, 7.0),
        createCuboidShape(8.25, 1.0, 5.0, 8.75, 1.5, 6.0)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    private val SHAPE_W = Stream.of(
        createCuboidShape(10.0, 1.0, 7.0, 11.0, 4.0, 10.0),
        createCuboidShape(6.0, 1.0, 7.0, 7.0, 4.0, 10.0),
        createCuboidShape(7.0, 1.0, 6.0, 10.0, 4.0, 7.0),
        createCuboidShape(7.0, 1.0, 10.0, 10.0, 4.0, 11.0),
        createCuboidShape(7.0, 0.0, 7.0, 10.0, 1.0, 10.0),
        createCuboidShape(5.0, 3.0, 8.25, 6.0, 3.5, 8.75),
        createCuboidShape(7.0, 0.5, 6.5, 10.0, 1.0, 7.0),
        createCuboidShape(7.0, 0.5, 10.0, 10.0, 1.0, 10.5),
        createCuboidShape(10.0, 0.5, 7.0, 10.5, 1.0, 10.0),
        createCuboidShape(6.5, 0.5, 7.0, 7.0, 1.0, 10.0),
        createCuboidShape(4.5, 1.5, 8.25, 5.0, 3.0, 8.75),
        createCuboidShape(6.5, 1.0, 10.0, 7.0, 4.0, 10.5),
        createCuboidShape(10.0, 1.0, 10.0, 10.5, 4.0, 10.5),
        createCuboidShape(10.0, 1.0, 6.5, 10.5, 4.0, 7.0),
        createCuboidShape(6.5, 1.0, 6.5, 7.0, 4.0, 7.0),
        createCuboidShape(5.0, 1.0, 8.25, 6.0, 1.5, 8.75)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get();

    override fun getOutlineShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return when (state?.get(FACING)) {
            Direction.DOWN -> SHAPE_N
            Direction.UP -> SHAPE_N
            Direction.NORTH -> SHAPE_N
            Direction.SOUTH -> SHAPE_S
            Direction.WEST -> SHAPE_W
            Direction.EAST -> SHAPE_E
            null -> SHAPE_N
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