package dev.galiev.deliciousteas.block

import dev.galiev.deliciousteas.block.entity.CoupleBlockEntity
import dev.galiev.deliciousteas.item.KettleItem
import dev.galiev.deliciousteas.registry.BlockEntityRegistry
import dev.galiev.deliciousteas.utils.NbtUtils
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.BlockWithEntity
import net.minecraft.block.ShapeContext
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.state.property.EnumProperty
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


class Couple(settings: FabricBlockSettings = FabricBlockSettings.create()): BlockWithEntity(settings) {
    companion object {
        val FLUID: EnumProperty<LiquidBlock.State> = EnumProperty.of("fluid", LiquidBlock.State::class.java)
        val FACING = Properties.FACING
    }

    init {
        defaultState = ((stateManager.defaultState as BlockState).with(FACING, Direction.SOUTH).with(FLUID, LiquidBlock.State.EMPTY))
    }

    val SHAPE_N = Stream.of(
        createCuboidShape(6.5, 1.0, 10.375, 9.5, 4.0, 11.375),
        createCuboidShape(6.5, 1.0, 6.375, 9.5, 4.0, 7.375),
        createCuboidShape(9.5, 1.0, 7.375, 10.5, 4.0, 10.375),
        createCuboidShape(5.5, 1.0, 7.375, 6.5, 4.0, 10.375),
        createCuboidShape(6.5, 0.0, 7.375, 9.5, 1.0, 10.375),
        createCuboidShape(7.75, 3.0, 5.375, 8.25, 3.5, 6.375),
        createCuboidShape(9.5, 0.5, 7.375, 10.0, 1.0, 10.375),
        createCuboidShape(6.0, 0.5, 7.375, 6.5, 1.0, 10.375),
        createCuboidShape(6.5, 0.5, 10.375, 9.5, 1.0, 10.875),
        createCuboidShape(6.5, 0.5, 6.875, 9.5, 1.0, 7.375),
        createCuboidShape(7.75, 1.5, 4.875, 8.25, 3.0, 5.375),
        createCuboidShape(6.0, 1.0, 6.875, 6.5, 4.0, 7.375),
        createCuboidShape(6.0, 1.0, 10.375, 6.5, 4.0, 10.875),
        createCuboidShape(9.5, 1.0, 10.375, 10.0, 4.0, 10.875),
        createCuboidShape(9.5, 1.0, 6.875, 10.0, 4.0, 7.375),
        createCuboidShape(7.75, 1.0, 5.375, 8.25, 1.5, 6.375)
    ).reduce { v1, v2 ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR)
    }.get()

    val SHAPE_W = Stream.of(
        createCuboidShape(10.375, 1.0, 6.5, 11.375, 4.0, 9.5),
        createCuboidShape(6.375, 1.0, 6.5, 7.375, 4.0, 9.5),
        createCuboidShape(7.375, 1.0, 5.5, 10.375, 4.0, 6.5),
        createCuboidShape(7.375, 1.0, 9.5, 10.375, 4.0, 10.5),
        createCuboidShape(7.375, 0.0, 6.5, 10.375, 1.0, 9.5),
        createCuboidShape(5.375, 3.0, 7.75, 6.375, 3.5, 8.25),
        createCuboidShape(7.375, 0.5, 6.0, 10.375, 1.0, 6.5),
        createCuboidShape(7.375, 0.5, 9.5, 10.375, 1.0, 10.0),
        createCuboidShape(10.375, 0.5, 6.5, 10.875, 1.0, 9.5),
        createCuboidShape(6.875, 0.5, 6.5, 7.375, 1.0, 9.5),
        createCuboidShape(4.875, 1.5, 7.75, 5.375, 3.0, 8.25),
        createCuboidShape(6.875, 1.0, 9.5, 7.375, 4.0, 10.0),
        createCuboidShape(10.375, 1.0, 9.5, 10.875, 4.0, 10.0),
        createCuboidShape(10.375, 1.0, 6.0, 10.875, 4.0, 6.5),
        createCuboidShape(6.875, 1.0, 6.0, 7.375, 4.0, 6.5),
        createCuboidShape(5.375, 1.0, 7.75, 6.375, 1.5, 8.25)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    val SHAPE_S = Stream.of(
        createCuboidShape(6.5, 1.0, 4.625, 9.5, 4.0, 5.625),
        createCuboidShape(6.5, 1.0, 8.625, 9.5, 4.0, 9.625),
        createCuboidShape(5.5, 1.0, 5.625, 6.5, 4.0, 8.625),
        createCuboidShape(9.5, 1.0, 5.625, 10.5, 4.0, 8.625),
        createCuboidShape(6.5, 0.0, 5.625, 9.5, 1.0, 8.625),
        createCuboidShape(7.75, 3.0, 9.625, 8.25, 3.5, 10.625),
        createCuboidShape(6.0, 0.5, 5.625, 6.5, 1.0, 8.625),
        createCuboidShape(9.5, 0.5, 5.625, 10.0, 1.0, 8.625),
        createCuboidShape(6.5, 0.5, 5.125, 9.5, 1.0, 5.625),
        createCuboidShape(6.5, 0.5, 8.625, 9.5, 1.0, 9.125),
        createCuboidShape(7.75, 1.5, 10.625, 8.25, 3.0, 11.125),
        createCuboidShape(9.5, 1.0, 8.625, 10.0, 4.0, 9.125),
        createCuboidShape(9.5, 1.0, 5.125, 10.0, 4.0, 5.625),
        createCuboidShape(6.0, 1.0, 5.125, 6.5, 4.0, 5.625),
        createCuboidShape(6.0, 1.0, 8.625, 6.5, 4.0, 9.125),
        createCuboidShape(7.75, 1.0, 9.625, 8.25, 1.5, 10.625)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    val SHAPE_E = Stream.of(
        createCuboidShape(4.625, 1.0, 6.5, 5.625, 4.0, 9.5),
        createCuboidShape(8.625, 1.0, 6.5, 9.625, 4.0, 9.5),
        createCuboidShape(5.625, 1.0, 9.5, 8.625, 4.0, 10.5),
        createCuboidShape(5.625, 1.0, 5.5, 8.625, 4.0, 6.5),
        createCuboidShape(5.625, 0.0, 6.5, 8.625, 1.0, 9.5),
        createCuboidShape(9.625, 3.0, 7.75, 10.625, 3.5, 8.25),
        createCuboidShape(5.625, 0.5, 9.5, 8.625, 1.0, 10.0),
        createCuboidShape(5.625, 0.5, 6.0, 8.625, 1.0, 6.5),
        createCuboidShape(5.125, 0.5, 6.5, 5.625, 1.0, 9.5),
        createCuboidShape(8.625, 0.5, 6.5, 9.125, 1.0, 9.5),
        createCuboidShape(10.625, 1.5, 7.75, 11.125, 3.0, 8.25),
        createCuboidShape(8.625, 1.0, 6.0, 9.125, 4.0, 6.5),
        createCuboidShape(5.125, 1.0, 6.0, 5.625, 4.0, 6.5),
        createCuboidShape(5.125, 1.0, 9.5, 5.625, 4.0, 10.0),
        createCuboidShape(8.625, 1.0, 9.5, 9.125, 4.0, 10.0),
        createCuboidShape(9.625, 1.0, 7.75, 10.625, 1.5, 8.25)
    ).reduce { v1: VoxelShape?, v2: VoxelShape? ->
        VoxelShapes.combineAndSimplify(
            v1,
            v2,
            BooleanBiFunction.OR
        )
    }.get()

    @Deprecated("Deprecated in Java")
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

    @Deprecated("Deprecated in Java")
    override fun rotate(state: BlockState?, rotation: BlockRotation?): BlockState {
        return state?.with(FACING, rotation?.rotate(state.get(FACING)))!!
    }

    @Deprecated("Deprecated in Java")
    override fun mirror(state: BlockState?, mirror: BlockMirror?): BlockState {
        return state?.rotate(mirror?.getRotation(state.get(FACING)))!!
    }

    override fun onUse(
        state: BlockState?,
        world: World?,
        pos: BlockPos?,
        player: PlayerEntity?,
        hand: Hand?,
        hit: BlockHitResult?
    ): ActionResult {
        val stack = player?.getStackInHand(hand)
        val blockEntity = world?.getBlockEntity(pos)

        if (stack?.item is KettleItem && blockEntity is CoupleBlockEntity) {
            if (NbtUtils.getBoolean(stack, "water")) {
                var liters = NbtUtils.getDouble(stack, "liters")
                if (liters >= 0.25 && !blockEntity.hasTea) {
                    liters -= 0.25
                    blockEntity.hasTea = true
                    NbtUtils.setDouble(stack, "liters", liters)
                    return ActionResult.SUCCESS
                } else {
                    return ActionResult.FAIL
                }
            } else {
                return ActionResult.FAIL
            }
        }
        return super.onUse(state, world, pos, player, hand, hit)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>?) {
        builder?.add(FACING)
        builder?.add(FLUID)
    }

    override fun createBlockEntity(pos: BlockPos?, state: BlockState?): BlockEntity = CoupleBlockEntity(pos, state)

    override fun <T : BlockEntity?> getTicker(
        world: World?,
        state: BlockState?,
        type: BlockEntityType<T>?
    ): BlockEntityTicker<T>? = checkType(type, BlockEntityRegistry.COUPLE_ENTITY, CoupleBlockEntity::tick)
}