package dev.galiev.deliciousteas.block

import net.minecraft.block.entity.BlockEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.Fluids
import net.minecraft.util.StringIdentifiable
import java.util.*

interface LiquidBlock {
    enum class State: StringIdentifiable {
        EMPTY,
        WATER,
        TEA;

        override fun asString(): String {
            return name.lowercase(Locale.ROOT)
        }

        fun asVanilla(): Fluid {
            return when(this) {
                EMPTY -> Fluids.EMPTY
                WATER -> Fluids.WATER
                TEA -> Fluids.WATER
            }
        }

    }

    fun setFluid(fluid: State)

    fun getFluid(): State

    fun blockEntity(): BlockEntity = (this as BlockEntity)
}