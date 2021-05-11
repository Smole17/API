package ru.smole.notify;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import ru.smole.mod.Mod;
import ru.smole.render.RColor;
import ru.smole.render.RenderProvider;
import ru.smole.render.Renderer;

import java.util.LinkedList;
import java.util.List;

public class Notifications implements NotificationProvider, Renderer {

    private List<Notify> notifies;
    private RenderProvider render;

    private final int
            SPLIT_Y = 7,
            SPLIT_X = 7,
            H_SIZE = 50,
            W_SIZE = 120;

    public Notifications(Mod mod) {
        render = mod.getRender();
        notifies = new LinkedList<>();
        render.registerRenderer(this);
        mod.registerEvents(this);
    }

    public Notify notify(Notify.Type type, String name, int duration, String... msg) {
        Notify n = new Notify(type, name, duration, msg);
        n.x = render.getResolution().getScaledWidth();
        n.y = SPLIT_Y;
        notifies.add(n);
        return n;
    }

    @SubscribeEvent
    public void disconnect(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        notifies.clear();
    }

    public void remove(Notify notify) {
        notifies.remove(notify);
    }

    public void on(ScaledResolution rs) {
        int y = SPLIT_Y;

        int w = rs.getScaledWidth();

        for (int i = 0; i < notifies.size(); i++) {
            Notify n = notifies.get(i);


            int max = Math.min(rs.getScaledWidth() - W_SIZE - SPLIT_X, rs.getScaledWidth() - 15 - render.getFontRenderer().getStringWidth(n.name) - SPLIT_X);
            int maxSize = Math.max(W_SIZE, 15 + render.getFontRenderer().getStringWidth(n.name));
            for (String s : n.msg) {
                max = Math.min(max, rs.getScaledWidth() - 10 - s.length() - SPLIT_X);
                maxSize = Math.max(10 + s.length(), maxSize);
            }

            if (!n.enabled) {
                if ((n.x -= 4) <= max) {
                    n.x = max;
                    n.enabled = true;
                }
            }

            boolean is = n.cd.is();

            double percent = n.enabled ? n.cd.getPercent() : 1;

            if (is && (n.x += 4) >= w) {
                remove(n);
                continue;
            }

            if (n.y < y) {
                n.y += Math.min(3, y - n.y);
                if (n.y > y)
                    n.y = y;
            }
            if (n.y > y) {
                n.y -= Math.min(3, n.y - y);
                if (n.y < y)
                    n.y = y;
            }

            int hsize = 10 + render.getFontRenderer().FONT_HEIGHT * (n.msg.length + 1);

            if (y <= rs.getScaledHeight()) {
                render.renderGradientRect(n.x, n.y, maxSize, hsize, RColor.of(136, 6, 194), RColor.of(175, 6, 209));
                render.renderRect(n.x, n.y, 4, hsize, n.type.color);

                render.renderRect(n.x + 4, n.y, (int) (maxSize * percent), 4, RColor.of(129, 6, 138));

                render.renderText(n.name, n.x + 10, n.y + 5, null);
                render.renderSplitText(n.msg, n.x + 15, n.y + 6 + render.getFontRenderer().FONT_HEIGHT, null);
            }

            y += SPLIT_Y + hsize;
        }
    }

}














