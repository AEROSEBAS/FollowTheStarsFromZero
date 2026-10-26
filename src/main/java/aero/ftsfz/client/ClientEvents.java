package aero.ftsfz.client;

import aero.ftsfz.Ftsfz;
import aero.ftsfz.client.screen.PlayerMenuScreen;
import aero.ftsfz.client.ModKeyBindings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

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

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        // La luciérnaga no tiene modelo: solo se ven sus partículas
        event.registerEntityRenderer(Ftsfz.FIREFLY.get(), NoopRenderer::new);
    }
}