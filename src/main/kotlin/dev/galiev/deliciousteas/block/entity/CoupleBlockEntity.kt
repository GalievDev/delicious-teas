package dev.galiev.deliciousteas.block.entity

import dev.galiev.deliciousteas.block.Couple
import dev.galiev.deliciousteas.block.LiquidBlock
import dev.galiev.deliciousteas.registry.BlockEntityRegistry
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.nbt.NbtCompound
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class CoupleBlockEntity(pos: BlockPos?, state: BlockState?) : BlockEntity(BlockEntityRegistry.COUPLE_ENTITY, pos, state), LiquidBlock {

    var hasTea: Boolean = false

    companion object {
        fun tick(world: World, blockPos: BlockPos, blockState: BlockState, entity: CoupleBlockEntity) {

        }
    }


    override fun writeNbt(nbt: NbtCompound) {
        super.writeNbt(nbt)
        nbt.putBoolean("couple.tea", hasTea)
    }

    override fun readNbt(nbt: NbtCompound) {
        super.readNbt(nbt)
        hasTea = nbt.getBoolean("couple.tea")
    }

    override fun setFluid(fluid: LiquidBlock.State) {
        world?.setBlockState(getPos(), cachedState.with(Couple.FLUID, fluid))
    }

    override fun getFluid(): LiquidBlock.State {
        return cachedState.get(Couple.FLUID)
    }
}