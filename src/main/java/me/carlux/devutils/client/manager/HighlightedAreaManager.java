package me.carlux.devutils.client.manager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HighlightedAreaManager {

    public enum Color {
        RED(255, 0, 0),
        BLUE(0, 0, 255),
        GREEN(0, 255, 0),
        YELLOW(255, 255, 0),
        AQUA(0, 255, 255),
        PURPLE(255, 0, 255);

        private final int red;
        private final int green;
        private final int blue;
        Color(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }

    static class Area {
        private final AxisAlignedBB box;
        private final Color color;
        public Area(AxisAlignedBB box, Color color) {
            this.box = box;
            this.color = color;
        }

        public AxisAlignedBB getBox() {
            return this.box;
        }

        public Color getColor() {
            return this.color;
        }
    }

    public static final HighlightedAreaManager INSTANCE = new HighlightedAreaManager();
    private final List<String> availableColors;
    private final Map<String, Area> areas = new HashMap<>();

    public HighlightedAreaManager() {
        this.availableColors = Arrays
            .stream(Color.values()).map(e -> e.name().toLowerCase())
            .collect(Collectors.toList());
    }

    public void set(String name, AxisAlignedBB box, Color color) {
        this.areas.put(name, new Area(box, color));
    }

    public boolean remove(String name) {
        return this.areas.remove(name) != null;
    }

    public void render(double playerX, double playerY, double playerZ) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-playerX, -playerY, -playerZ);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth(4.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        this.areas.forEach((name, area) -> {
            final Color color = area.getColor();
            RenderGlobal.drawOutlinedBoundingBox(area.getBox(), color.red, color.green, color.blue, 255);
        });
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    public List<String> getAvailableColors() {
        return this.availableColors;
    }

    public void clear() {
        this.areas.clear();
    }

}
