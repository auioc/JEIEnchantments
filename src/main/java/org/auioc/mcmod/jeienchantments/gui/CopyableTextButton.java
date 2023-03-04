package org.auioc.mcmod.jeienchantments.gui;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * @see <a href="https://github.com/auioc/arnicalib-mcmod/blob/203877c65e99f3b942fd4df3f7e5c97e6e33ca4b/src/main/java/org/auioc/mcmod/arnicalib/game/gui/component/CopyableTextWidget.java">ArnicaLib: CopyableTextWidget.java</a>
 */
@OnlyIn(Dist.CLIENT)
public class CopyableTextButton extends SimpleTextButton {

    private String value;

    public CopyableTextButton(int x, int y, int w, int h, Component message, String value) {
        super(x, y, w, h, message);
        this.value = value;
    }

    public CopyableTextButton(int x, int y, int w, Component message, String value) {
        super(x, y, w, message);
        this.value = value;
    }

    public CopyableTextButton(int x, int y, Component message, String value) {
        super(x, y, message);
        this.value = value;
    }

    public CopyableTextButton(int x, int y, int w, int h, Component message) {
        this(x, y, w, h, message, message.getString());
    }

    public CopyableTextButton(int x, int y, int w, Component message) {
        this(x, y, w, message, message.getString());
    }

    public CopyableTextButton(int x, int y, Component message) {
        this(x, y, x, y, message, message.getString());
    }

    // ====================================================================== //

    public String getValue() { return this.value; }

    public void setValue(String value) { this.value = value; }

    // ====================================================================== //

    public void update(int x, int y, int w, int h, Component message, String value) {
        super.update(x, y, w, h, message);
        this.setValue(value);
    }

    public void update(int x, int y, int w, Component message, String value) {
        super.update(x, y, w, message);
        this.setValue(value);
    }

    @Override
    public void update(int x, int y, int w, int h, Component message) {
        this.update(x, y, w, h, message, message.getString());
    }

    @Override
    public void update(int x, int y, int w, Component message) {
        this.update(x, y, w, message, message.getString());
    }

    // ====================================================================== //

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.minecraft.keyboardHandler.setClipboard(getValue());
    }

    @Override
    public List<Component> getTooltips() {
        return List.of(new TranslatableComponent("chat.copy.click"));
    }

}
