package aero.ftsfz.client;

import aero.ftsfz.Ftsfz;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = Ftsfz.MODID, value = Dist.CLIENT)
public class ModKeyBindings {

    public static final KeyMapping OPEN_MENU = new KeyMapping(
            "key." + Ftsfz.MODID + ".open_menu", // nombre traducible
            KeyConflictContext.IN_GAME,                           // solo dentro del mundo
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_R,                                      // tecla por defecto: R
            "key.categories." + Ftsfz.MODID      // categoría en Controles
    );

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(OPEN_MENU);
    }
}