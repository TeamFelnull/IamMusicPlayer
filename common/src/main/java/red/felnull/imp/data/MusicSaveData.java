package red.felnull.imp.data;

import net.minecraft.nbt.CompoundTag;
import red.felnull.imp.handler.SyncResourceRequestMessageHandler;
import red.felnull.imp.music.resource.Music;
import red.felnull.imp.music.resource.MusicPlayList;
import red.felnull.imp.util.NbtUtils;
import red.felnull.otyacraftengine.data.save.IkisugiSaveData;

import java.util.*;

public class MusicSaveData extends IkisugiSaveData {
    private final Map<UUID, Music> MUSICS = new HashMap<>();
    private final Map<UUID, MusicPlayList> MUSIC_PLAYLISTS = new HashMap<>();

    @Override
    public void load(CompoundTag tag) {
        List<Music> musics = new ArrayList<>();
        NbtUtils.readMusics(tag, "Music", musics);
        musics.forEach(n -> MUSICS.put(n.getUUID(), n));

        List<MusicPlayList> playLists = new ArrayList<>();
        NbtUtils.readMusicPlayLists(tag, "PlayList", playLists);
        playLists.forEach(n -> MUSIC_PLAYLISTS.put(n.getUUID(), n));
    }

    @Override
    public void clear() {
        MUSICS.clear();
        MUSIC_PLAYLISTS.clear();
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        List<Music> musics = new ArrayList<>(MUSICS.values());
        NbtUtils.writeMusics(tag, "Music", musics);

        List<MusicPlayList> playLists = new ArrayList<>(MUSIC_PLAYLISTS.values());
        NbtUtils.writeMusicPlayLists(tag, "PlayList", playLists);
        return tag;
    }

    public Map<UUID, Music> getMusics() {
        return MUSICS;
    }

    public Map<UUID, MusicPlayList> getMusicPlaylists() {
        return MUSIC_PLAYLISTS;
    }

    @Override
    public void setDirty() {
        super.setDirty();
        SyncResourceRequestMessageHandler.resetMusicPlayList();
    }
}
