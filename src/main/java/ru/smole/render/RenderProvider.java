package ru.smole.render;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;

public interface RenderProvider {

    FontRenderer getFontRenderer();

    ScaledResolution getResolution();

    void renderRect(int x, int y, int weight, int height, RColor color);

    void renderGradientRect(int x, int y, int weight, int height, RColor start, RColor next);

    void renderText(String text, float x, float y, RColor color);

    void renderCenteredText(String text, float y, RColor color);

    void renderSplitText(String[] text, float x, float y, RColor color);

    void clear();

    void registerRenderer(Renderer renderer);

}
