package red.felnull.imp.client.gui.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import red.felnull.imp.IamMusicPlayer;
import red.felnull.imp.blockentity.MusicSharingDeviceBlockEntity;
import red.felnull.imp.client.gui.screen.monitor.*;
import red.felnull.imp.inventory.MusicSharingDeviceMenu;
import red.felnull.imp.music.resource.MusicPlayList;
import red.felnull.imp.music.resource.simple.SimpleMusicPlayList;
import red.felnull.otyacraftengine.api.OtyacraftEngineAPI;
import red.felnull.otyacraftengine.client.util.IKSGRenderUtil;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Supplier;

public class MusicSharingDeviceScreen extends IMPEquipmentBaseScreen<MusicSharingDeviceMenu> {
    private static final ResourceLocation MSD_LOCATION = new ResourceLocation(IamMusicPlayer.MODID, "textures/gui/container/music_sharing_device.png");
    private static final Map<MusicSharingDeviceBlockEntity.Screen, Supplier<MSDBaseMonitor>> SCREEN_CRATER = new HashMap<>();
    public final UUID uuid = UUID.randomUUID();
    public List<MusicSharingDeviceBlockEntity.Screen> screenHistory = new ArrayList();
    private MSDBaseMonitor currentScreens;
    public SimpleMusicPlayList selectPlayList = MusicPlayList.ALL.getSimple();

    public MusicSharingDeviceScreen(MusicSharingDeviceMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
        this.imageWidth = 215;
        this.imageHeight = 242;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
        insFristOpen();
        this.currentScreens = null;
        SCREEN_CRATER.clear();
        addScreens();

        if (OtyacraftEngineAPI.getInstance().isDebugMode())
            this.addRenderableWidget(new Button(leftPos + imageWidth - 34, topPos + imageHeight - 40 + 20, 20, 20, new TextComponent("Debug"), n -> insMonitorScreen(MusicSharingDeviceBlockEntity.Screen.DEBUG)));


    }

    protected void addScreens() {
        addScreen(MusicSharingDeviceBlockEntity.Screen.OFF, () -> new OffMonitor(MusicSharingDeviceBlockEntity.Screen.OFF, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.DEBUG, () -> new DebugMonitor(MusicSharingDeviceBlockEntity.Screen.DEBUG, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.PLAYLIST, () -> new PlayListMonitor(MusicSharingDeviceBlockEntity.Screen.PLAYLIST, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.NO_ANTENNA, () -> new NoAntennaMonitor(MusicSharingDeviceBlockEntity.Screen.NO_ANTENNA, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.ADD_PLAYLIST, () -> new AddPlaylistMonitor(MusicSharingDeviceBlockEntity.Screen.ADD_PLAYLIST, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.CREATE_PLAYLIST, () -> new CreatePlaylistMonitor(MusicSharingDeviceBlockEntity.Screen.CREATE_PLAYLIST, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.CREATE_MUSIC, () -> new CreateMusicMonitor(MusicSharingDeviceBlockEntity.Screen.CREATE_MUSIC, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));
        addScreen(MusicSharingDeviceBlockEntity.Screen.PLAYLIST_DETAILS, () -> new PlaylistDetailsMonitor(MusicSharingDeviceBlockEntity.Screen.PLAYLIST_DETAILS, this, getMonitorLeftPos(), getMonitorTopPos(), getMonitorWidth(), getMonitorHeight()));

    }

    @Override
    public void tick() {
        super.tick();

        if (getCurrentScreen() == null || getCurrentScreen().getMSDScreen() != getCurrentMonitorScreen()) {
            if (getCurrentScreen() != null) {
                getCurrentScreen().disable();
                removeWidget(getCurrentScreen());
            }

            Monitor<?> bef = currentScreens;
            currentScreens = SCREEN_CRATER.get(getCurrentMonitorScreen()).get();
            getCurrentScreen().init();
            getCurrentScreen().setBeforeMonitor(bef);
            addWidget(getCurrentScreen());
        }

        if (getCurrentScreen().isActive()) {
            getCurrentScreen().tick();
        }

        if (getCurrentMonitorScreen() == MusicSharingDeviceBlockEntity.Screen.OFF || getCurrentMonitorScreen() == MusicSharingDeviceBlockEntity.Screen.PLAYLIST) {
            screenHistory.clear();
        }
    }

    @Override
    protected ResourceLocation getBackGrandTextuer() {
        return MSD_LOCATION;
    }

    protected void addScreen(MusicSharingDeviceBlockEntity.Screen msdscreen, Supplier<MSDBaseMonitor> screen) {
        SCREEN_CRATER.put(msdscreen, screen);
    }

    protected MusicSharingDeviceBlockEntity.Screen getCurrentMonitorScreen() {
        return getMSDEntity().getCurrentScreen(null);
    }

    protected MSDBaseMonitor getCurrentScreen() {
        return currentScreens;
    }


    protected MusicSharingDeviceBlockEntity getMSDEntity() {
        return (MusicSharingDeviceBlockEntity) getBlockEntity();
    }


    public void insMonitorScreen(MusicSharingDeviceBlockEntity.Screen screen) {
        if (screen == MusicSharingDeviceBlockEntity.Screen.PLAYLIST) {
            screenHistory.clear();
        } else {
            if (screen != getCurrentMonitorScreen() && (screenHistory.isEmpty() || screenHistory.get(screenHistory.size() - 1) != getCurrentMonitorScreen())) {
                screenHistory.add(getCurrentMonitorScreen());
            }
        }
        insMonitorScreenNoHistory(screen);
    }

    public void insMonitorScreenNoHistory(MusicSharingDeviceBlockEntity.Screen screen) {
        CompoundTag tag = new CompoundTag();
        tag.putString("Name", screen.getSerializedName());
        this.instruction("Screen", tag);
    }

    public void insFristOpen() {
        this.instruction("Open", new CompoundTag());
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        super.renderBg(poseStack, f, i, j);
        if (getCurrentScreen() != null && getCurrentScreen().isActive()) {
            getCurrentScreen().render(poseStack, i, j, f);
        } else {
            if (isPowerOn())
                IKSGRenderUtil.drawTexture(MSDBaseMonitor.MSD_BACKGROUND, poseStack, getMonitorLeftPos(), getMonitorTopPos(), 0, 0, getMonitorWidth(), getMonitorHeight(), getMonitorWidth(), getMonitorHeight());
        }
    }

    @Override
    public boolean mouseScrolled(double d, double e, double f) {
        boolean f1 = super.mouseScrolled(d, e, f);
        boolean f2 = getCurrentScreen() == null || !getCurrentScreen().isActive() || getCurrentScreen().mouseScrolled(d, e, f);
        return f1 && f2;
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        boolean f1 = super.mouseClicked(d, e, i);
        //   boolean f2 = getCurrentScreen() == null || !getCurrentScreen().isActive() || getCurrentScreen().mouseClicked(d, e, i);
        return f1;//&& f2;
    }

    @Override
    public boolean charTyped(char c, int i) {

        if (getCurrentScreen() != null)
            return getCurrentScreen().charTyped(c, i) || super.charTyped(c, i);

        return super.charTyped(c, i);
    }

    public int getMonitorLeftPos() {
        return leftPos + 8;
    }

    public int getMonitorTopPos() {
        return topPos + 20;
    }

    public int getMonitorWidth() {
        return 199;
    }

    public int getMonitorHeight() {
        return 122;
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {

        if (i == 256) {
            this.minecraft.player.closeContainer();
            return super.keyPressed(i, j, k);
        }

        if (getCurrentScreen() != null && getCurrentScreen().keyPressed(i, j, k)) {
            return true;
        }

        return super.keyPressed(i, j, k);
    }

    @Override
    public void onClose() {
        super.onClose();
        if (getCurrentScreen() != null)
            getCurrentScreen().disable();
    }

    @Override
    public boolean keyReleased(int i, int j, int k) {
        if (getCurrentScreen() != null)
            return getCurrentScreen().keyReleased(i, j, k) || super.keyReleased(i, j, k);
        return super.keyReleased(i, j, k);
    }

    @Override
    public void onFilesDrop(List<Path> list) {
        if (getCurrentScreen() != null)
            getCurrentScreen().onFilesDrop(list);

        super.onFilesDrop(list);
    }
}
