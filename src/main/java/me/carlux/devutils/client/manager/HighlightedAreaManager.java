package me.carlux.devutils.client.manager;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
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

    public enum Type {
        OUTLINE,
        BOX;
    }

    static class Area {
        private final AxisAlignedBB box;
        private final Color color;
        private final Type type;
        public Area(AxisAlignedBB box, Color color, Type type) {
            this.box = box;
            this.color = color;
            this.type = type;
        }

        public AxisAlignedBB getBox() {
            return this.box;
        }

        public Color getColor() {
            return this.color;
        }

        public Type getType() {
            return this.type;
        }
    }

    public static final HighlightedAreaManager INSTANCE = new HighlightedAreaManager();
    private final List<String> availableColors;
    private final List<String> availableTypes;
    private final Map<String, Area> areas = new HashMap<>();

    public HighlightedAreaManager() {
        this.availableColors = Arrays
            .stream(Color.values()).map(e -> e.name().toLowerCase())
            .collect(Collectors.toList());
        this.availableTypes = Arrays
            .stream(Type.values()).map(e -> e.name().toLowerCase())
            .collect(Collectors.toList());
    }

    public void set(String name, AxisAlignedBB box, Color color, Type type) {
        this.areas.put(name, new Area(box, color, type));
    }

    public boolean remove(String name) {
        return this.areas.remove(name) != null;
    }

    public void render(double playerX, double playerY, double playerZ) {
        this.areas.forEach((name, area) -> {
            switch (area.type) {
                case OUTLINE:
                    this.renderAreaOutline(playerX, playerY, playerZ, area);
                    break;
                case BOX:
                    this.renderAreaBox(playerX, playerY, playerZ, area);
                    break;
            }
        });
    }

    private void renderAreaOutline(double playerX, double playerY, double playerZ, Area area) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-playerX, -playerY, -playerZ);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GL11.glLineWidth(4.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        final Color color = area.getColor();
        RenderGlobal.drawOutlinedBoundingBox(area.getBox(), color.red, color.green, color.blue, 255);
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void renderAreaBox(double playerX, double playerY, double playerZ, Area area) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(-playerX, -playerY, -playerZ);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.disableTexture2D();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.disableCull();
        final Color color = area.getColor();
        final AxisAlignedBB box = area.getBox();
        GlStateManager.color(color.red / 255.0f, color.green / 255.0f, color.blue / 255.0f, 0.25f);
        this.renderFilledBoundingBox(box);
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    private void renderFilledBoundingBox(AxisAlignedBB box) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.minX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.maxZ).endVertex();
        worldRenderer.pos(box.minX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.maxZ).endVertex();
        worldRenderer.pos(box.maxX, box.minY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.minZ).endVertex();
        worldRenderer.pos(box.maxX, box.maxY, box.maxZ).endVertex();
        tessellator.draw();
    }

    public List<String> getAvailableColors() {
        return this.availableColors;
    }

    public List<String> getAvailableTypes() {
        return this.availableTypes;
    }

    public void clear() {
        this.areas.clear();
    }

}
