package aero.ftsfz.client.screen.tab;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public class SkillsTab extends MenuTab {
    public SkillsTab() { super(Component.translatable("tab.ftsfz.skills")); }

    @Override
    public void init(TabContext ctx) {
        ctx.addWidget().accept(Button.builder(Component.literal("Aprender habilidad"),
                        btn -> Minecraft.getInstance().player.displayClientMessage(
                                Component.literal("Próximamente ✦"), true))
                .bounds(ctx.left(), ctx.top(), 120, 20)
                .build());
    }

    @Override
    public void render(GuiGraphics g, TabContext ctx, int mx, int my, float pt) {
        g.drawString(ctx.font(), "Árbol de habilidades", ctx.left(), ctx.top() + 28, 0xAAAAAA);
    }
}