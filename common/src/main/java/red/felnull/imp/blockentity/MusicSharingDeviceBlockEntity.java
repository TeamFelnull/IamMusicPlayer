package red.felnull.imp.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import red.felnull.imp.inventory.MusicSharingDeviceMenu;
import red.felnull.imp.util.ItemHelper;
import red.felnull.imp.util.NbtUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MusicSharingDeviceBlockEntity extends IMPEquipmentBaseBlockEntity {
    private final Map<UUID, Screen> playerScreens = new HashMap<>();
    private Screen currentScreen;
    private long antennaProgress;

    public MusicSharingDeviceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(IMPBlockEntitys.MUSIC_SHARING_DEVICE, blockPos, blockState);
    }

    @Override
    protected Component getDefaultName() {
        return new TranslatableComponent("container.music_sharing_device");
    }

    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new MusicSharingDeviceMenu(i, getBlockPos(), this, inventory);
    }

    public long getAntennaProgress() {
        return antennaProgress;
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        NbtUtils.writeMSDPlayerScreenData(tag, "PlayerScreens", playerScreens);
        tag.putLong("AntennaProgress", antennaProgress);
        return super.save(tag);
    }

    @Override
    public void load(CompoundTag tag) {
        NbtUtils.readMSDPlayerScreenData(tag, "PlayerScreens", playerScreens);
        this.antennaProgress = tag.getLong("AntennaProgress");
        super.load(tag);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public CompoundTag clientSyncbleData(ServerPlayer player, CompoundTag tag) {
        ContainerHelper.saveAllItems(tag, this.getItems());
        tag.putString("CurrentScreen", getCurrentScreen(player.getGameProfile().getId()).getSerializedName());
        tag.putLong("AntennaProgress", antennaProgress);
        return tag;
    }

    @Override
    public void clientSyncble(CompoundTag tag) {
        this.getItems().clear();
        ContainerHelper.loadAllItems(tag, this.getItems());
        this.currentScreen = Screen.getScreenByName(tag.getString("CurrentScreen"));
        this.antennaProgress = tag.getLong("AntennaProgress");
    }

    @Override
    public CompoundTag instructionFromClient(ServerPlayer player, String name, CompoundTag data) {
        if (name.equals("Screen")) {
            Screen sc = Screen.getScreenByName(data.getString("Name"));
            if (isPowerOn() || sc == Screen.OFF)
                setCurrentScreen(player.getGameProfile().getId(), sc);
        } else if (name.equals("Open")) {
            if (!playerScreens.containsKey(player.getGameProfile().getId()))
                setCurrentScreen(player.getGameProfile().getId(), Screen.OFF);
        }
        return super.instructionFromClient(player, name, data);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level.isClientSide()) {
            if (isPowerOn()) {
                playerScreens.forEach((n, m) -> {
                    if (getCurrentScreen(n) == Screen.OFF)
                        setCurrentScreen(n, Screen.PLAYLIST);

                    if (!isExistAntenna())
                        setCurrentScreen(n, Screen.NO_ANTENNA);
                    else if (getCurrentScreen(n) == Screen.NO_ANTENNA)
                        setCurrentScreen(n, Screen.PLAYLIST);

                });
                antennaProgress++;
            } else {
                setCurrentScreen(Screen.OFF);
            }
        }
    }

    public void setCurrentScreen(Screen screen) {
        playerScreens.keySet().forEach(n -> {
            setCurrentScreen(n, screen);
        });
    }

    public boolean isExistAntenna() {
        return ItemHelper.isAntenna(getItem(0));
    }

    public ItemStack getAntenna() {
        return getItem(0);
    }

    public void setCurrentScreen(UUID playerUUID, Screen screen) {
        playerScreens.put(playerUUID, screen);
    }

    public Screen getCurrentScreen(UUID playerUUID) {
        if (getLevel().isClientSide()) {
            if (currentScreen != null)
                return currentScreen;
        } else {
            if (playerScreens.containsKey(playerUUID))
                return playerScreens.get(playerUUID);
        }
        return isPowerOn() ? isExistAntenna() ? Screen.PLAYLIST : Screen.NO_ANTENNA : Screen.OFF;
    }

    public static enum Screen implements StringRepresentable {
        OFF("off"),
        DEBUG("debug"),
        PLAYLIST("playlist"),
        NO_ANTENNA("no_antenna"),
        ADD_PLAYLIST("add_playlist"),
        CREATE_PLAYLIST("create_playlist"),
        CREATE_MUSIC("create_music"),
        PLAYLIST_DETAILS("playlist_details");

        private final String name;

        Screen(String name) {
            this.name = name;
        }

        public static Screen getScreenByName(String name) {
            for (Screen sc : values()) {
                if (sc.getSerializedName().equals(name))
                    return sc;
            }
            return OFF;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }
}
