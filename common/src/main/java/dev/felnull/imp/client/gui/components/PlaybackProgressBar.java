package dev.felnull.imp.client.gui.components;

import dev.felnull.imp.client.gui.screen.monitor.music_manager.MusicManagerMonitor;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class PlaybackProgressBar extends AbstractButton {
    private final Supplier<Float> progressGetter;
    private final Consumer<Float> playbackProgressControl;

    public PlaybackProgressBar(int x, int y, Component component, Supplier<Float> progressGetter, Consumer<Float> playbackProgressControl) {
        super(x, y, 153, 3, component);
        this.progressGetter = progressGetter;
        this.playbackProgressControl = playbackProgressControl;
    }

    @Override
    public void onClick(double d, double e) {
        super.onClick(d, e);
        if (isHoveredOrFocused()) {
            if (e >= getY() && e <= (getY() + getHeight()) && d >= getX() && d <= (getX() + getWidth()))
                playbackProgressControl.accept((float) ((d - getX()) / getWidth()));
        }
    }

    @Override
    public void onPress() {
    }

    /*@Override
    public void renderWidget(PoseStack poseStack, int i, int j, float f) {
        OERenderUtils.drawTexture(MusicManagerMonitor.WIDGETS_TEXTURE, poseStack, getX(), getY(), 52, 54 + (isHoveredOrFocused() ? 3 : 0), getWidth(), getHeight());
        OERenderUtils.drawTexture(MusicManagerMonitor.WIDGETS_TEXTURE, poseStack, getX(), getY(), 52, 48 + (isHoveredOrFocused() ? 3 : 0), (float) getWidth() * progressGetter.get(), getHeight());
    }*/

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int i, int j, float f) {
        OERenderUtils.drawTexture(MusicManagerMonitor.WIDGETS_TEXTURE, guiGraphics.pose(), getX(), getY(), 52, 54 + (isHoveredOrFocused() ? 3 : 0), getWidth(), getHeight());
        OERenderUtils.drawTexture(MusicManagerMonitor.WIDGETS_TEXTURE, guiGraphics.pose(), getX(), getY(), 52, 48 + (isHoveredOrFocused() ? 3 : 0), (float) getWidth() * progressGetter.get(), getHeight());
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        narrationElementOutput.add(NarratedElementType.TITLE, this.createNarrationMessage());
    }
}
