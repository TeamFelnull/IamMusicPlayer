package dev.felnull.imp.client.gui.components;

import dev.felnull.imp.client.gui.IIMPSmartRender;
import dev.felnull.imp.client.gui.screen.monitor.music_manager.MusicManagerMonitor;
import dev.felnull.otyacraftengine.client.gui.TextureRegion;
import dev.felnull.otyacraftengine.client.gui.components.RadioButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SmartRadioButton extends RadioButton implements IIMPSmartRender {
    private static final TextureRegion SRDIO_TEXTURE = TextureRegion.relative(MusicManagerMonitor.WIDGETS_TEXTURE, 18, 65, 20, 20);

    public SmartRadioButton(int x, int y, @NotNull Component title, @Nullable Consumer<RadioButton> onPress, @NotNull Supplier<Set<RadioButton>> group) {
        super(x, y, 20, 20, title, onPress, group, true, SRDIO_TEXTURE);
    }

    @Override
    public void drawTextBase(GuiGraphics guiGraphics, Component text, int x, int y, int color) {
        drawSmartText(guiGraphics, text, x, y);
    }
}
