package aero.ftsfz.client.screen;

import aero.ftsfz.client.ModKeyBindings;
import aero.ftsfz.client.screen.tab.*;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

public class PlayerMenuScreen extends Screen {
    private static final int PANEL_W = 240, PANEL_H = 170;
    private static final int TAB_H = 18;

    // Colores ARGB del diseño
    private static final int COLOR_BORDER = 0xFF1B1F2A;
    private static final int COLOR_PANEL  = 0xF02A3142;
    private static final int COLOR_HEADER = 0xFF3B4560;
    private static final int COLOR_ACCENT = 0xFFE8C26A; // dorado "estrellas"

    private static int selected = 0; // recuerda la última pestaña abierta

    private final List<MenuTab> tabs;
    private int left, top;
    private TabContext ctx;

    public PlayerMenuScreen() {
        super(Component.translatable("screen.ftsfz.player_menu"));
        this.tabs = MenuTabs.all().stream().map(Supplier::get).toList();
        if (selected >= tabs.size()) selected = 0;
    }

    @Override
    protected void init() {
        left = (width - PANEL_W) / 2;
        top = (height - PANEL_H) / 2;

        // Área de contenido: debajo de la barra de pestañas, con margen
        ctx = new TabContext(left + 8, top + TAB_H + 22,
                PANEL_W - 16, PANEL_H - TAB_H - 30,
                font, this::addRenderableWidget);

        // Barra de pestañas
        int tabW = (PANEL_W - 8 - (tabs.size() - 1) * 2) / tabs.size();
        for (int i = 0; i < tabs.size(); i++) {
            int index = i;
            Button b = Button.builder(tabs.get(i).getTitle(), btn -> selectTab(index))
                    .bounds(left + 4 + i * (tabW + 2), top + 18, tabW, TAB_H)
                    .build();
            b.active = i != selected; // la pestaña activa queda "presionada"
            addRenderableWidget(b);
        }

        tabs.get(selected).init(ctx);
    }

    private void selectTab(int index) {
        selected = index;
        rebuildWidgets(); // limpia widgets y vuelve a llamar init()
    }

    @Override
    public void renderBackground(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(g, mouseX, mouseY, partialTick); // oscurece el mundo
        // Marco
        g.fill(left - 2, top - 2, left + PANEL_W + 2, top + PANEL_H + 2, COLOR_BORDER);
        g.fill(left, top, left + PANEL_W, top + PANEL_H, COLOR_PANEL);
        // Cabecera
        g.fill(left, top, left + PANEL_W, top + 16, COLOR_HEADER);
        g.fill(left, top + 16, left + PANEL_W, top + 17, COLOR_ACCENT);
    }

    @Override
    public void render(GuiGraphics g, int mouseX, int mouseY, float partialTick) {
        super.render(g, mouseX, mouseY, partialTick); // fondo + widgets
        g.drawString(font, title, left + 6, top + 4, COLOR_ACCENT, false);
        tabs.get(selected).render(g, ctx, mouseX, mouseY, partialTick);
    }

    @Override
    public void tick() {
        tabs.get(selected).tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // La misma tecla que abre el menú también lo cierra
        if (ModKeyBindings.OPEN_MENU.matches(keyCode, scanCode)) {
            onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean isPauseScreen() { return false; }
}