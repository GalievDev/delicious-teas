package dev.galiev.deliciousteas

import com.mojang.logging.LogUtils
import dev.galiev.deliciousteas.registry.BlocksRegistry
import dev.galiev.deliciousteas.registry.ItemsRegistry
import net.fabricmc.api.ModInitializer

object DeliciousTeas: ModInitializer {
    const val MOD_ID = "deliciousteas"
    val LOGGER = LogUtils.getLogger();
    override fun onInitialize() {
        ItemsRegistry
        BlocksRegistry
    }
}