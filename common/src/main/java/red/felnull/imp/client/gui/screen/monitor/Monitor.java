package red.felnull.imp.client.gui.screen.monitor;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import red.felnull.otyacraftengine.client.gui.components.IIkisugibleWidget;
import red.felnull.otyacraftengine.client.gui.screen.IkisugiContainerScreen;
import red.felnull.otyacraftengine.util.IKSGColorUtil;

import java.util.ArrayList;
import java.util.List;

public class Monitor<PS extends IkisugiContainerScreen<?>> extends AbstractContainerEventHandler implements NarratableEntry, IIkisugibleWidget {
    protected final List<GuiEventListener> children = new ArrayList<>();
    protected final List<AbstractWidget> buttons = new ArrayList<>();
    protected final PS parentScreen;
    protected final Component title;
    public int x;
    public int y;
    public int width;
    public int height;
    private boolean active;

    public Monitor(Component component, PS parentScreen, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.parentScreen = parentScreen;
        this.title = component;
    }

    public void init() {
        active = true;
    }

    public void render(PoseStack poseStack, int mousX, int mousY, float parTick) {
        buttons.forEach(n -> n.render(poseStack, mousX, mousY, parTick));
    }

    public Component getTitle() {
        return this.title;
    }

    public String getNarrationMessage() {
        return this.getTitle().getString();
    }

    protected <T extends GuiEventListener> T addWidget(T guiEventListener) {
        this.children.add(guiEventListener);
        return guiEventListener;
    }

    protected <T extends AbstractWidget> T addRenderableWidget(T abstractWidget) {
        this.buttons.add(abstractWidget);
        return this.addWidget(abstractWidget);
    }

    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }

    public PS getParentScreen() {
        return parentScreen;
    }

    /*public void enabled() {
        active = true;
        buttons.forEach(n -> {
            n.visible = true;
            n.active = true;
        });
    }*/

    public void disable() {
        active = false;
        buttons.forEach(n -> {
            n.visible = false;
            n.active = false;
        });
    }

    public boolean isActive() {
        return active;
    }

    public void tick() {

    }

    @Override
    public NarrationPriority narrationPriority() {
        return NarrationPriority.NONE;
    }

    @Override
    public void updateNarration(NarrationElementOutput narrationElementOutput) {

    }

    protected void fillXGrayLine(PoseStack ps, int x, int y, int l) {
        fill(ps, x, y, x + l, y + 1, IKSGColorUtil.toSRGB(14474460));
    }

    protected void fillYGrayLine(PoseStack ps, int x, int y, int l) {
        fill(ps, x, y, x + 1, y + l, IKSGColorUtil.toSRGB(14474460));
    }

    protected void fillLightGray(PoseStack poseStack, int x, int y, int w, int h) {
        fill(poseStack, x, y, x + w, y + h, IKSGColorUtil.toSRGB(16119543));
    }
}