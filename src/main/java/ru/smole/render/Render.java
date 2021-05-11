package ru.smole.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;
import ru.smole.mod.MinecraftMod;
import ru.smole.mod.Mod;
import ru.smole.utils.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Render implements RenderProvider {

    private Minecraft minecraft;
    private FontRenderer fontRenderer;
    private ScaledResolution resolution;
    private List<Renderer> renderers;

    public Render(Mod mod) {
        if (!MinecraftMod.isInit())
            throw new RuntimeException("MinecraftMod not initialize");
        minecraft = mod.getMinecraft();
        renderers = new ArrayList<>();
        mod.registerEvents(this);
    }

    @SubscribeEvent
    public void render(RenderGameOverlayEvent.Text e) {
        resolution = e.getResolution();
        fontRenderer = minecraft.fontRenderer;
        for (Renderer renderer : renderers) {
            renderer.on(e.getResolution());
        }
    }

    public FontRenderer getFontRenderer() {
        return fontRenderer = minecraft.fontRenderer;
    }

    public ScaledResolution getResolution() {
        return new ScaledResolution(minecraft);
    }

    public void registerRenderer(Renderer renderer) {
        renderers.add(renderer);
    }

    public void clear() {
        GL11.glColor4f(1, 1, 1, 1);
    }

    public void renderRect(int x, int y, int weight, int height, RColor color) {
        Gui.drawRect(x, y, x + weight, y + height, color == null ? 0 : color.getRGB());
        clear();
    }

    public void renderGradientRect(int x, int y, int weight, int height, RColor start, RColor next) {
        int startColor = start.getRGB();
        int endColor = next.getRGB();
        float f = (float)(startColor >> 24 & 255) / 255.0F;
        float f1 = (float)(startColor >> 16 & 255) / 255.0F;
        float f2 = (float)(startColor >> 8 & 255) / 255.0F;
        float f3 = (float)(startColor & 255) / 255.0F;
        float f4 = (float)(endColor >> 24 & 255) / 255.0F;
        float f5 = (float)(endColor >> 16 & 255) / 255.0F;
        float f6 = (float)(endColor >> 8 & 255) / 255.0F;
        float f7 = (float)(endColor & 255) / 255.0F;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel(7425);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x + weight, y, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(x, y, 0).color(f1, f2, f3, f).endVertex();
        bufferbuilder.pos(x, y + height, 0).color(f5, f6, f7, f4).endVertex();
        bufferbuilder.pos(x + weight, y + height, 0).color(f5, f6, f7, f4).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel(7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        clear();
    }

    public void renderText(String text, float x, float y, RColor color) {
        fontRenderer.drawStringWithShadow(text, x, y, color == null ? 0 : color.getRGB());
    }

    public void renderCenteredText(String text, float y, RColor color) {
        getFontRenderer().drawStringWithShadow(text,
                resolution.getScaledWidth() / 2
                - fontRenderer.getStringWidth(ChatColor.getUnformatted(text)) / 2
                - ChatColor.getCodesCount(text, "l"), y, color == null ? 0 : color.getRGB());
    }

    public void renderSplitText(String[] text, float x, float y, RColor color) {
        for (String s : text) {
            renderText(s, x, y, color);
            y += fontRenderer.FONT_HEIGHT;
        }
    }
}
