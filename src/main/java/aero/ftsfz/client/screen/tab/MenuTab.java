package aero.ftsfz.client.screen.tab;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public abstract class MenuTab {
    private final Component title;

    protected MenuTab(Component title) { this.title = title; }

    public Component getTitle() { return title; }

    /** Crea botones, campos, etc. Se llama al abrir la pestaña o redimensionar. */
    public abstract void init(TabContext ctx);

    /** Dibuja el contenido (texto, barras, imágenes). */
    public void render(GuiGraphics g, TabContext ctx, int mouseX, int mouseY, float partialTick) {}

    /** Lógica por tick (animaciones, refrescar datos). */
    public void tick() {}
}