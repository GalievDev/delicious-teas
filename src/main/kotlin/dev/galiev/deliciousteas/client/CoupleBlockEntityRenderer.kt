package dev.galiev.deliciousteas.client

import dev.galiev.deliciousteas.block.entity.CoupleBlockEntity
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.block.entity.BlockEntityRenderer
import net.minecraft.client.util.math.MatrixStack

class CoupleBlockEntityRenderer: BlockEntityRenderer<CoupleBlockEntity> {

    override fun render(
        entity: CoupleBlockEntity,
        tickDelta: Float,
        matrices: MatrixStack,
        vertexConsumers: VertexConsumerProvider,
        light: Int,
        overlay: Int
    ) {
        matrices.push()
        matrices.translate(0.5, 1.25, 0.5)
    }
}