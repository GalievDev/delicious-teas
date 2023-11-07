package dev.galiev.deliciousteas

import com.mojang.logging.LogUtils
import dev.galiev.deliciousteas.block.Couple
import dev.galiev.deliciousteas.item.CoupleItem
import dev.galiev.deliciousteas.registry.BlocksRegistry
import dev.galiev.deliciousteas.registry.ItemsRegistry
import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.registry.RegistryKeys
import net.minecraft.text.Text
import net.minecraft.util.Identifier

object DeliciousTeas: ModInitializer {

    const val MOD_ID = "deliciousteas"
    val LOGGER = LogUtils.getLogger();

    val DELICIOUS_TEAS: RegistryKey<ItemGroup> = RegistryKey.of(RegistryKeys.ITEM_GROUP, Identifier(MOD_ID, MOD_ID))

    override fun onInitialize() {
        ItemsRegistry
        BlocksRegistry
        Registry.register(Registries.ITEM_GROUP, DELICIOUS_TEAS, FabricItemGroup.builder().icon {
            ItemStack(BlocksRegistry.BLOCKS.keys.first())
        }.displayName(Text.literal(MOD_ID.uppercase())).build())
    }

    private fun registries() {
        Registry.register(Registries.BLOCK, "couple", Couple())
        Registry.register(Registries.ITEM, "couple", CoupleItem())
    }
}