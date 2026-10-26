package aero.ftsfz.client.screen.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class StatsTab extends MenuTab {
    public StatsTab() { super(Component.translatable("tab.ftsfz.stats")); }

    @Override
    public void init(TabContext ctx) {}

    @Override
    public void render(GuiGraphics g, TabContext ctx, int mx, int my, float pt) {
        Player p = Minecraft.getInstance().player;
        if (p == null) return;
        int x = ctx.left(), y = ctx.top();
        g.drawString(ctx.font(), "Vida: " + (int) p.getHealth() + " / " + (int) p.getMaxHealth(), x, y, 0xFFFFFF);
        g.drawString(ctx.font(), "Nivel XP: " + p.experienceLevel, x, y + 12, 0xFFFFFF);

        // Barra de vida simple
        int barW = ctx.width();
        int filled = (int) (barW * p.getHealth() / p.getMaxHealth());
        g.fill(x, y + 28, x + barW, y + 34, 0xFF400000);
        g.fill(x, y + 28, x + filled, y + 34, 0xFFD03030);
    }
}