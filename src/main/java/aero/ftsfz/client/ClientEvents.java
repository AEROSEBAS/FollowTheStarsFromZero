package aero.ftsfz.client;

import aero.ftsfz.Ftsfz;
import aero.ftsfz.client.screen.PlayerMenuScreen;
import aero.ftsfz.client.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = Ftsfz.MODID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        // consumeClick() devuelve true una vez por cada pulsación
        while (ModKeyBindings.OPEN_MENU.consumeClick()) {
            if (mc.player != null && mc.screen == null) {
                mc.setScreen(new PlayerMenuScreen());
            }
        }
    }
}