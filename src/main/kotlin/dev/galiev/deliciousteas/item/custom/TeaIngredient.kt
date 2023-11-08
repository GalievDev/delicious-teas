package dev.galiev.deliciousteas.item.custom

import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.item.Item

class TeaIngredient(settings: FabricItemSettings = FabricItemSettings(), val effect: StatusEffect): Item(settings) {
}