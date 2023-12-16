package dev.galiev.deliciousteas.registry

import dev.galiev.deliciousteas.DeliciousTeas
import dev.galiev.deliciousteas.block.entity.CoupleBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object BlockEntityRegistry {
    val COUPLE_ENTITY: BlockEntityType<CoupleBlockEntity> =
        Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier(DeliciousTeas.MOD_ID, "couple_block_entity"),
            FabricBlockEntityTypeBuilder.create(
                ::CoupleBlockEntity,
                BlocksWithCustomItemRegistry.COUPLE
            ).build()
        )
}