package aero.ftsfz.client.screen.tab;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import java.util.function.Consumer;

/** Área de contenido disponible para la pestaña y forma de añadir widgets. */
public record TabContext(int left, int top, int width, int height,
                         Font font, Consumer<AbstractWidget> addWidget) {}