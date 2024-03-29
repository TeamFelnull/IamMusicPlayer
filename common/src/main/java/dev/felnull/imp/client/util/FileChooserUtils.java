package dev.felnull.imp.client.util;

import dev.felnull.otyacraftengine.client.util.OEClientUtils;
import dev.felnull.otyacraftengine.client.util.OENativeUtils;
import net.minecraft.client.resources.language.I18n;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Path;

public class FileChooserUtils {

    public static File[] openMusicFileChooser(boolean multiSelect) {
        return trayOpenFileChooser("music", OENativeUtils.getMyMusicFolder(), multiSelect);
    }

    public static File[] openImageFileChooser(boolean multiSelect) {
        return trayOpenFileChooser("image", OENativeUtils.getMyPicturesFolder(), multiSelect, "*.png", "*.jpg", "*.jpeg", "*.gif");
    }

    @Nullable
    private static File[] trayOpenFileChooser(String name, Path initPath, boolean multiSelect, @Nullable String... filterPatterns) {
        return OEClientUtils.openFilterFileChooser(I18n.get("imp.fileChooser.title." + name), initPath, null, multiSelect, filterPatterns);
    }
}
