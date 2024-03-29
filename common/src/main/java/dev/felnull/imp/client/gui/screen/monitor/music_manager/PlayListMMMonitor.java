package dev.felnull.imp.client.gui.screen.monitor.music_manager;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.felnull.imp.IamMusicPlayer;
import dev.felnull.imp.blockentity.MusicManagerBlockEntity;
import dev.felnull.imp.client.gui.IIMPSmartRender;
import dev.felnull.imp.client.gui.components.MusicsFixedListWidget;
import dev.felnull.imp.client.gui.components.MyPlayListFixedListWidget;
import dev.felnull.imp.client.gui.components.SmartButton;
import dev.felnull.imp.client.gui.components.SortButton;
import dev.felnull.imp.client.gui.screen.MusicManagerScreen;
import dev.felnull.imp.client.music.MusicSyncManager;
import dev.felnull.imp.client.renderer.PlayImageRenderer;
import dev.felnull.imp.music.resource.Music;
import dev.felnull.imp.music.resource.MusicPlayList;
import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlayListMMMonitor extends MusicManagerMonitor {
    private static final ResourceLocation PLAY_LIST_TEXTURE = new ResourceLocation(IamMusicPlayer.MODID, "textures/gui/container/music_manager/monitor/play_list.png");
    private static final Component ADD_PLAYLIST_TEXT = Component.translatable("imp.button.addPlaylist");
    private static final Component ADD_MUSIC_TEXT = Component.translatable("imp.button.addMusic");
    private static final Component SORT_TYPE_NAME_TEXT = Component.translatable("imp.sortType." + SortButton.SortType.NAME.getName());
    private static final Component ORDER_TYPE_DESCENDING_TEXT = Component.translatable("imp.orderType." + SortButton.OrderType.DESCENDING.getName());
    private static final Component DETAIL_TEXT = Component.translatable("imp.button.detailPlaylist");
    private final List<MusicPlayList> musicPlayLists = new ArrayList<>();
    private final List<Music> musics = new ArrayList<>();
    private List<MusicPlayList> musicPlayListsCash;
    private List<Music> musicsCash;
    private MusicSyncManager.PlayListInfo lastPlayListInfo;
    private Component INFO_TEXT;
    private SortButton.SortTypeButton playlistSortButton;
    private SortButton.OrderTypeButton playlistOrderButton;
    private SortButton.SortTypeButton musicSortButton;
    private SortButton.OrderTypeButton musicOrderButton;
    private SmartButton addPlaylistButton;
    private SmartButton addMusic;
    private SmartButton detailButton;
    private MusicsFixedListWidget musicsFixedButtonsList;
    private MyPlayListFixedListWidget myPlayListFixedButtonsList;

    public PlayListMMMonitor(MusicManagerBlockEntity.MonitorType type, MusicManagerScreen screen) {
        super(type, screen);
    }

    @Override
    public void init(int leftPos, int topPos) {
        super.init(leftPos, topPos);
        this.myPlayListFixedButtonsList = addRenderWidget(new MyPlayListFixedListWidget(getStartX() + 1, getStartY() + 20, musicPlayLists, (widget, item) -> setSelectedPlayList(item.getUuid()), this.myPlayListFixedButtonsList, n -> n.getUuid().equals(getSelectedPlayList())));

        this.addPlaylistButton = addRenderWidget(new SmartButton(getStartX() + 1, getStartY() + 189, 72 + 9, 9, ADD_PLAYLIST_TEXT, n -> {
            insMonitor(MusicManagerBlockEntity.MonitorType.ADD_PLAY_LIST);
        }));
        this.addPlaylistButton.setIcon(WIDGETS_TEXTURE, 73, 14, 5, 5);

        this.addMusic = addRenderWidget(new SmartButton(getStartX() + 102, getStartY() + 189, 72, 9, ADD_MUSIC_TEXT, n -> {
            insMonitor(MusicManagerBlockEntity.MonitorType.ADD_MUSIC);
        }));
        this.addMusic.setIcon(WIDGETS_TEXTURE, 73, 14, 5, 5);
        this.addMusic.active = getSelectedMusicPlayList() != null && getSelectedMusicPlayList().getAuthority().getAuthorityType(IIMPSmartRender.mc.player.getGameProfile().getId()).isMoreMember();

        this.musicSortButton = addRenderWidget(new SortButton.SortTypeButton(getStartX() + 174, getStartY() + 189, n -> updateMusics(), true, getScreen()));
        this.musicOrderButton = addRenderWidget(new SortButton.OrderTypeButton(getStartX() + 271, getStartY() + 189, n -> updateMusics(), true, getScreen()));

        this.musicsFixedButtonsList = this.addRenderWidget(new MusicsFixedListWidget(getStartX() + 102, getStartY() + 40, 267, 148, Component.translatable("imp.fixedList.musics"), 4, musics, (widget, item) -> {
            setSelectedMusic(item.getUuid());
            insMonitor(MusicManagerBlockEntity.MonitorType.DETAIL_MUSIC);
        }, false, this.musicsFixedButtonsList));

        this.detailButton = this.addRenderWidget(new SmartButton(getStartX() + 336, getStartY() + 20, 33, 9, DETAIL_TEXT, n -> insMonitor(MusicManagerBlockEntity.MonitorType.DETAIL_PLAY_LIST)));
        this.detailButton.visible = getSelectedMusicPlayList() != null;

        this.playlistOrderButton = addRenderWidget(new SortButton.OrderTypeButton(getStartX() + 82 + 9, getStartY() + 189, n -> updateList(), false, getScreen()));
        this.playlistSortButton = addRenderWidget(new SortButton.SortTypeButton(getStartX() + 73 + 9, getStartY() + 189, n -> updateList(), false, getScreen()));
    }

    @Override
    public void render(GuiGraphics guiGraphics, float f, int mouseX, int mouseY) {
        super.render(guiGraphics, f, mouseX, mouseY);
        PoseStack poseStack = guiGraphics.pose();

        OERenderUtils.drawTexture(PLAY_LIST_TEXTURE, poseStack, getStartX(), getStartY(), 0f, 0f, width, height, width, height);
        if (INFO_TEXT != null)
            drawSmartText(guiGraphics, INFO_TEXT, getStartX() + width - IIMPSmartRender.mc.font.width(INFO_TEXT) - 3, getStartY() + 11);

        var pl = getSelectedMusicPlayList();
        if (pl != null) {
            int plsty;
            var au = pl.getAuthority().getPlayersAuthority().entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().getLevel())).map(Map.Entry::getKey).filter(n -> !n.equals(pl.getAuthority().getOwner())).toList();
            if (au.size() == 0) {
                OERenderUtils.drawPlayerFace(poseStack, pl.getAuthority().getOwner(), getStartX() + 328, getStartY() + 21, 7);
                plsty = 14;
            } else if (au.size() == 1) {
                OERenderUtils.drawPlayerFace(poseStack, au.get(0), getStartX() + 328, getStartY() + 21, 7);
                OERenderUtils.drawPlayerFace(poseStack, pl.getAuthority().getOwner(), getStartX() + 319, getStartY() + 21, 7);
                plsty = 20;
            } else {
                var tx = Component.literal("+" + au.size());
                int txw = mc.font.width(tx);
                drawSmartText(guiGraphics, tx, getStartX() + 336 - txw - 2, getStartY() + 21);
                OERenderUtils.drawPlayerFace(poseStack, pl.getAuthority().getOwner(), getStartX() + 336 - txw - 2 - 8, getStartY() + 21, 7);
                plsty = 18 + txw;
            }

            drawSmartText(guiGraphics, Component.literal(OEClientUtils.getWidthOmitText(pl.getName(), 240 - plsty, "...")), getStartX() + 103, getStartY() + 21);
        }
    }

    @Override
    public void renderAppearance(MusicManagerBlockEntity blockEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j, float f, float monitorWidth, float monitorHeight) {
        super.renderAppearance(blockEntity, poseStack, multiBufferSource, i, j, f, monitorWidth, monitorHeight);
        float onPxW = monitorWidth / (float) width;
        float onPxH = monitorHeight / (float) height;
        OERenderUtils.renderTextureSprite(PLAY_LIST_TEXTURE, poseStack, multiBufferSource, 0, 0, OERenderUtils.MIN_BREADTH * 2, 0, 0, 0, monitorWidth, monitorHeight, 0, 0, width, height, width, height, i, j);
        renderSmartButtonSprite(poseStack, multiBufferSource, 1, 189, OERenderUtils.MIN_BREADTH * 2, 72 + 9, 9, i, j, onPxW, onPxH, monitorHeight, ADD_PLAYLIST_TEXT, WIDGETS_TEXTURE, 73, 14, 5, 5, 256, 256);
        renderSmartButtonSprite(poseStack, multiBufferSource, 102, 189, OERenderUtils.MIN_BREADTH * 2, 72, 9, i, j, onPxW, onPxH, monitorHeight, ADD_MUSIC_TEXT, WIDGETS_TEXTURE, 73, 14, 5, 5, 256, 256, getSelectedPlayList(blockEntity) == null);

        renderSmartButtonSprite(poseStack, multiBufferSource, 73 + 9, 189, OERenderUtils.MIN_BREADTH * 2, 9, 9, i, j, onPxW, onPxH, monitorHeight, WIDGETS_TEXTURE, 73, 0, 7, 7, 256, 256);
        renderSmartButtonSprite(poseStack, multiBufferSource, 82 + 9, 189, OERenderUtils.MIN_BREADTH * 2, 9, 9, i, j, onPxW, onPxH, monitorHeight, WIDGETS_TEXTURE, 80, 7, 7, 7, 256, 256);

        renderSmartButtonSprite(poseStack, multiBufferSource, 174, 189, OERenderUtils.MIN_BREADTH * 2, 97, 9, i, j, onPxW, onPxH, monitorHeight, SORT_TYPE_NAME_TEXT, WIDGETS_TEXTURE, 73, 0, 7, 7, 256, 256);
        renderSmartButtonSprite(poseStack, multiBufferSource, 271, 189, OERenderUtils.MIN_BREADTH * 2, 88, 9, i, j, onPxW, onPxH, monitorHeight, ORDER_TYPE_DESCENDING_TEXT, WIDGETS_TEXTURE, 80, 7, 7, 7, 256, 256);

        var pls = getSyncManager().getMyPlayList();
        int plsc = 0;
        if (pls != null) {
            plsc = pls.size();
            for (int k = 0; k < Math.min(8, pls.size()); k++) {
                var playList = pls.get(k);
                renderSmartButtonBoxSprite(poseStack, multiBufferSource, 1, 20 + (k * 21), OERenderUtils.MIN_BREADTH * 2, 90, 21, i, j, onPxW, onPxH, monitorHeight, playList.getUuid().equals(getSelectedPlayList(blockEntity)));
                float sx = 1;
                if (!playList.getImage().isEmpty()) {
                    sx += 18 + 2;
                    PlayImageRenderer.getInstance().renderSprite(playList.getImage(), poseStack, multiBufferSource, 3 * onPxW, monitorHeight - (20 + (k * 21) + 2 + 17) * onPxH, OERenderUtils.MIN_BREADTH * 4, 17 * onPxH, i, j);
                }

                renderSmartTextSprite(poseStack, multiBufferSource, Component.literal(OEClientUtils.getWidthOmitText(playList.getName(), 80 - sx, "...")), sx + 3, 20 + (k * 21) + 3, OERenderUtils.MIN_BREADTH * 4, onPxW, onPxH, monitorHeight, i);
                renderSmartTextSprite(poseStack, multiBufferSource, Component.literal(MyPlayListFixedListWidget.dateFormat.format(new Date(playList.getCreateDate()))), sx + 3, 20 + (k * 21) + 12, OERenderUtils.MIN_BREADTH * 4, onPxW, onPxH, monitorHeight, 0.7f, i);
            }
        }
        renderScrollbarSprite(poseStack, multiBufferSource, 92, 20, OERenderUtils.MIN_BREADTH * 2, 168, i, j, onPxW, onPxH, monitorHeight, plsc, 8);

        updateInfoText();
        if (INFO_TEXT != null)
            renderSmartTextSprite(poseStack, multiBufferSource, INFO_TEXT, width - IIMPSmartRender.mc.font.width(INFO_TEXT) - 3, 11, OERenderUtils.MIN_BREADTH * 2, onPxW, onPxH, monitorHeight, i);

        var ms = getSyncManager().getMusics(getSelectedPlayList(blockEntity));
        int msc = 0;
        if (ms != null) {
            msc = ms.size();
            for (int k = 0; k < Math.min(4, ms.size()); k++) {
                var music = ms.get(k);
                renderSmartButtonBoxSprite(poseStack, multiBufferSource, 102, 40 + (k * 37), OERenderUtils.MIN_BREADTH * 2, 267 - 10, 37, i, j, onPxW, onPxH, monitorHeight, false);
                float sx = 2;
                if (!music.getImage().isEmpty()) {
                    sx += 37 + 2;
                    PlayImageRenderer.getInstance().renderSprite(music.getImage(), poseStack, multiBufferSource, 103 * onPxW, monitorHeight - (40 + (k * 37) + 1 + 35) * onPxH, OERenderUtils.MIN_BREADTH * 4, 35 * onPxH, i, j);
                }
                var nt = Component.literal(music.getName());
                renderSmartTextSprite(poseStack, multiBufferSource, nt, sx + 3 + 102, 40 + (k * 37) + 3, OERenderUtils.MIN_BREADTH * 4, onPxW, onPxH, monitorHeight, Math.min(1, (267f - 9f - sx - 15f) / (float) IIMPSmartRender.mc.font.width(nt)), i);

                renderSmartTextSprite(poseStack, multiBufferSource, Component.literal(music.getAuthor()), sx + 3 + 102, 40 + (k * 37) + 14, OERenderUtils.MIN_BREADTH * 4, onPxW, onPxH, monitorHeight, i);

                var pname = OEClientUtils.getPlayerNameByUUID(music.getOwner()).map(n -> (Component) Component.literal(n)).orElse(MusicsFixedListWidget.UNKNOWN_PLAYER_TEXT);

                poseStack.pushPose();
                poseStack.translate((sx + 102 + 2) * onPxW, monitorHeight - (40 + (k * 37) + 23 + 9) * onPxH, OERenderUtils.MIN_BREADTH * 4);
                OERenderUtils.renderPlayerFaceSprite(poseStack, multiBufferSource, music.getOwner(), onPxH * 9, i, j);
                poseStack.popPose();

                renderSmartTextSprite(poseStack, multiBufferSource, pname, sx + 3 + 102 + 12, 40 + (k * 37) + 26, OERenderUtils.MIN_BREADTH * 4, onPxW, onPxH, monitorHeight, i);

                renderSmartTextSprite(poseStack, multiBufferSource, Component.literal(MyPlayListFixedListWidget.dateFormat.format(new Date(music.getCreateDate()))), sx + 3 + 102 + 12 + 88, 40 + (k * 37) + 26, OERenderUtils.MIN_BREADTH * 4, onPxW, onPxH, monitorHeight, i);
            }
        }
        renderScrollbarSprite(poseStack, multiBufferSource, 360, 40, OERenderUtils.MIN_BREADTH * 2, 148, i, j, onPxW, onPxH, monitorHeight, msc, 4);

        var pl = getSelectedMusicPlayList(blockEntity);
        if (pl != null) {
            renderSmartButtonSprite(poseStack, multiBufferSource, 336, 20, OERenderUtils.MIN_BREADTH * 2, 33, 9, i, j, onPxW, onPxH, monitorHeight, DETAIL_TEXT, true);
            int plsty;
            var au = pl.getAuthority().getPlayersAuthority().entrySet().stream().sorted(Comparator.comparingInt(o -> o.getValue().getLevel())).map(Map.Entry::getKey).filter(n -> !n.equals(pl.getAuthority().getOwner())).toList();
            if (au.size() == 0) {
                renderPlayerFaceSprite(poseStack, multiBufferSource, pl.getAuthority().getOwner(), 328, 20.5f, OERenderUtils.MIN_BREADTH * 2, 7, i, j, onPxW, onPxH, monitorHeight);
                plsty = 14;
            } else if (au.size() == 1) {
                renderPlayerFaceSprite(poseStack, multiBufferSource, au.get(0), 328, 20.5f, OERenderUtils.MIN_BREADTH * 2, 7, i, j, onPxW, onPxH, monitorHeight);
                renderPlayerFaceSprite(poseStack, multiBufferSource, pl.getAuthority().getOwner(), 319, 20.5f, OERenderUtils.MIN_BREADTH * 2, 7, i, j, onPxW, onPxH, monitorHeight);
                plsty = 20;
            } else {
                var tx = Component.literal("+" + au.size());
                int txw = mc.font.width(tx);
                renderPlayerFaceSprite(poseStack, multiBufferSource, au.get(0), 336 - txw - 2 - 8, 20.5f, OERenderUtils.MIN_BREADTH * 2, 7, i, j, onPxW, onPxH, monitorHeight);
                renderSmartTextSprite(poseStack, multiBufferSource, tx, 336 - txw - 2, 22, OERenderUtils.MIN_BREADTH * 2, onPxW, onPxH, monitorHeight, i);
                plsty = 18 + txw;
            }
            renderSmartTextSprite(poseStack, multiBufferSource, Component.literal(OEClientUtils.getWidthOmitText(pl.getName(), 235 - plsty, "...")), 103, 22, OERenderUtils.MIN_BREADTH * 2, onPxW, onPxH, monitorHeight, i);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (musicPlayListsCash != getSyncManager().getMyPlayList()) {
            musicPlayListsCash = getSyncManager().getMyPlayList();
            updateList();
        }

        if (musicsCash != getSyncManager().getMusics(getSelectedPlayList())) {
            musicsCash = getSyncManager().getMusics(getSelectedPlayList());
            updateMusics();
        }

        updateInfoText();

        addMusic.active = getSelectedMusicPlayList() != null && getSelectedMusicPlayList().getAuthority().getAuthorityType(IIMPSmartRender.mc.player.getGameProfile().getId()).isMoreMember();

        detailButton.visible = getSelectedMusicPlayList() != null;
    }

    private void updateList() {
        musicPlayLists.clear();
        if (musicPlayListsCash != null)
            musicPlayLists.addAll(playlistSortButton.sort(musicPlayListsCash, playlistOrderButton));

        if (getSelectedMusicPlayList() == null) setSelectedPlayList(null);
    }

    private void updateMusics() {
        musics.clear();
        if (musicsCash != null) musics.addAll(musicSortButton.sort(musicsCash, musicOrderButton));
    }

    private void updateInfoText() {
        var pls = getSyncManager();
        if (pls.getMyPlayListInfo() != lastPlayListInfo) {
            lastPlayListInfo = pls.getMyPlayListInfo();
            if (lastPlayListInfo != null) {
                INFO_TEXT = Component.translatable("imp.text.playlistInfo", lastPlayListInfo.playListCount(), lastPlayListInfo.musicCount());
            } else {
                INFO_TEXT = null;
            }
        }
    }

    public void setSelectedMusic(@Nullable UUID uuid) {
        getScreen().insSelectedMusic(uuid);
    }

    @Nullable
    public UUID getSelectedMusicRaw() {
        if (getScreen().getBlockEntity() instanceof MusicManagerBlockEntity musicManagerBlockEntity)
            return getSelectedMusicRaw(musicManagerBlockEntity);
        return null;
    }

    @Nullable
    public UUID getSelectedMusicRaw(MusicManagerBlockEntity musicManagerBlockEntity) {
        return musicManagerBlockEntity.getSelectedMusic(mc.player);
    }

    public MusicPlayList getSelectedMusicPlayList(MusicManagerBlockEntity musicManagerBlockEntity) {
        var pls = getSyncManager().getMyPlayList();
        if (pls == null) return null;
        return getSyncManager().getMyPlayList().stream().filter(n -> n.getUuid().equals(getSelectedPlayList(musicManagerBlockEntity))).findFirst().orElse(null);
    }

    public MusicPlayList getSelectedMusicPlayList() {
        return musicPlayLists.stream().filter(n -> n.getUuid().equals(getSelectedPlayList())).findFirst().orElse(null);
    }

    public UUID getSelectedPlayList() {
        if (getScreen().getBlockEntity() instanceof MusicManagerBlockEntity musicManagerBlockEntity)
            return getSelectedPlayList(musicManagerBlockEntity);
        return null;
    }

    public UUID getSelectedPlayList(MusicManagerBlockEntity musicManagerBlockEntity) {
        return musicManagerBlockEntity.getSelectedPlayList(mc.player);
    }

    private void setSelectedPlayList(UUID selectedPlayList) {
        getScreen().insSelectedPlayList(selectedPlayList);
    }

    @Override
    public void onUpdateSelectedPlayList(UUID playListId) {
        updateMusics();
    }
}
