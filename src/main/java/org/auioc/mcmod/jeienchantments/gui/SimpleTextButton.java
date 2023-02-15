package org.auioc.mcmod.jeienchantments.gui;

import java.util.List;
import javax.annotation.Nullable;
import org.auioc.mcmod.jeienchantments.utils.Utils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class SimpleTextButton extends AbstractWidget {

    protected final Minecraft minecraft;
    protected final Font font;

    public SimpleTextButton(int x, int y, int w, int h, Component message) {
        super(x, y, w, h, message);
        this.minecraft = Minecraft.getInstance();
        this.font = this.minecraft.font;
    }

    public SimpleTextButton(int x, int y, int w, Component message) {
        this(x, y, w, 0, message);
        this.setHeight(font.split(message, w).size() * font.lineHeight);
    }

    public SimpleTextButton(int x, int y, Component message) {
        this(x, y, 0, 0, message);
        this.setWidth(font.width(message));
        this.setHeight(font.lineHeight);
    }

    // ====================================================================== //

    public void render(PoseStack poseStack, double mouseX, double mouseY) {
        this.render(poseStack, (int) mouseX, (int) mouseY, 0);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Utils.drawTextWarp(poseStack, font, this.getMessage(), x, y, width, 0);
        if (this.isHovered) {
            var tooltips = getTooltips();
            var screen = minecraft.screen;
            if (tooltips != null && screen != null) {
                screen.renderComponentTooltip(poseStack, tooltips, mouseX, mouseY);
            }
        }
    }

    // ====================================================================== //

    public void update(int x, int y, int w, int h, Component message) {
        this.x = x;
        this.y = y;
        this.setWidth(w);
        this.setHeight(h);
        this.setMessage(message);
    }

    public void update(int x, int y, int w, Component message) {
        update(x, y, w, (font.split(message, w).size() * font.lineHeight), message);
    }

    // ====================================================================== //

    @Override
    public void onClick(double mouseX, double mouseY) {}

    @Nullable
    public List<Component> getTooltips() { return null; }

    // ====================================================================== //

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }

}
