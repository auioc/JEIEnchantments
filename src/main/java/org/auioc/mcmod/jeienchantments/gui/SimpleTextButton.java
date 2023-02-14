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
public class SimpleTextButton extends AbstractWidget {

    private final Minecraft minecraft;
    private final Font font;

    public SimpleTextButton(int x, int y, int w, int h, Component message, Minecraft minecraft) {
        super(x, y, w, h, message);
        this.minecraft = minecraft;
        this.font = minecraft.font;
    }

    public SimpleTextButton(int x, int y, int w, int h, Component message) {
        this(x, y, w, h, message, Minecraft.getInstance());
    }

    public SimpleTextButton(int x, int y, Component message) {
        this(x, y, 0, 0, message);
        this.setWidth(font.width(message));
        this.setHeight(font.lineHeight);
    }

    public void render(PoseStack poseStack, double mouseX, double mouseY) {
        this.render(poseStack, (int) mouseX, (int) mouseY, 0);
    }

    @Override
    public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        Utils.drawCenteredText(poseStack, font, this.getMessage(), (x + (width / 2)), y, 0);
        if (this.isHovered) {
            var tooltips = getTooltips();
            var screen = minecraft.screen;
            if (tooltips != null && screen != null) {
                screen.renderComponentTooltip(poseStack, tooltips, mouseX, mouseY);
            }
        }
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
