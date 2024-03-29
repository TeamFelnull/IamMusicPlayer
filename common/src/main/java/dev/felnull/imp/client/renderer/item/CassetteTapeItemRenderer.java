package dev.felnull.imp.client.renderer.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.felnull.imp.client.gui.components.MyPlayListFixedListWidget;
import dev.felnull.imp.client.model.IMPModels;
import dev.felnull.imp.client.renderer.PlayImageRenderer;
import dev.felnull.imp.item.CassetteTapeItem;
import dev.felnull.imp.music.resource.Music;
import dev.felnull.otyacraftengine.client.model.ModelHolder;
import dev.felnull.otyacraftengine.client.renderer.item.BEWLItemRenderer;
import dev.felnull.otyacraftengine.client.util.OERenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.Date;

public class CassetteTapeItemRenderer implements BEWLItemRenderer {
    private static final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(ItemStack itemStack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, float v, int i, int i1) {
        float par = CassetteTapeItem.getTapePercentage(itemStack);
        VertexConsumer ivb = ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.cutoutBlockSheet(), true, itemStack.hasFoil());//multiBufferSource.getBuffer(Sheets.cutoutBlockSheet());

        renderBase(poseStack, ivb, multiBufferSource, itemStack, i, i1);

        BakedModel glassModel = IMPModels.CASSETTE_TAPE_GLASS_MODEL.get();
        poseStack.pushPose();
        OERenderUtils.poseTrans16(poseStack, 3, 0, 2.25d);
        OERenderUtils.renderModel(poseStack, ivb, glassModel, i, i1);
        poseStack.popPose();

        renderTapeRoll(poseStack, par * 10f, 1 - par, ivb, 7.25d, -0.01f, 2.5d, i, i1);
        renderTapeRoll(poseStack, par * 10f, par, ivb, 1.75d, -0.01f, 2.5d, i, i1);

        BakedModel tapeModel = IMPModels.CASSETTE_TAPE_MODEL.get();
        poseStack.pushPose();
        OERenderUtils.poseTrans16(poseStack, 0.975d, 0.25d, 0.275d);
        OERenderUtils.renderModel(poseStack, ivb, tapeModel, i, i1);
        poseStack.popPose();

        renderTapeConecter(poseStack, 22 - 46 * par, ivb, 0.975d, 0.25d, 0.8d, i, i1);
        renderTapeConecter(poseStack, 22 - 46 * par, ivb, 9d, 0.25d, 0.8d, i, i1);

        renderMusicInfo(poseStack, ivb, multiBufferSource, itemStack, i, i1);
    }

    private static void renderMusicInfo(PoseStack poseStack, VertexConsumer ivb, MultiBufferSource multiBufferSource, ItemStack stack, int i, int i1) {

        Music music = CassetteTapeItem.getMusic(stack);

        if (music == null)
            return;

        BakedModel labelModel = music.getImage().isEmpty() ? IMPModels.CASSETTE_TAPE_LABEL_NO_IMAGE_MODEL.get() : IMPModels.CASSETTE_TAPE_LABEL_MODEL.get();
        poseStack.pushPose();
        OERenderUtils.poseTrans16(poseStack, 3d, 1d, 4d);
        OERenderUtils.renderModel(poseStack, ivb, labelModel, i, i1);
        poseStack.translate(0, (1f / 16f) * 0.025f + OERenderUtils.MIN_BREADTH, 0);

        if (!music.getImage().isEmpty()) {
            float size = (1f / 16f) * 1.025f;
            float x = 2.8f;
            float y = 0.55f;
            poseStack.pushPose();
            OERenderUtils.poseRotateAll(poseStack, -90, 0, 180);
            PlayImageRenderer.getInstance().renderSprite(music.getImage(), poseStack, multiBufferSource, -(size + (1f / 16f) * x), (1f / 16f) * y, 0, size, i, i1);
            poseStack.popPose();
        }

        poseStack.pushPose();
        OERenderUtils.poseRotateX(poseStack, 90f);
        OERenderUtils.poseRotateY(poseStack, 180);
        float x = music.getImage().isEmpty() ? 3.85f : 2.65f;
        OERenderUtils.renderTextSprite(poseStack, multiBufferSource, Component.literal(MyPlayListFixedListWidget.dateFormat.format(new Date(music.getCreateDate()))), -(1f / 16f) * 3.85f, (1f / 16f) * 0.18f, 0, 0.15f, 0, 0, i);
        Component namec = Component.literal(music.getName());
        int ms = music.getImage().isEmpty() ? 120 : 80;
        String dot = "...";
        if (mc.font.width(namec) >= ms) {
            StringBuilder sb = new StringBuilder();
            for (char c : music.getName().toCharArray()) {
                sb.append(c);
                if (mc.font.width(sb.toString()) >= ms - mc.font.width(dot)) {
                    sb.deleteCharAt(sb.length() - 1);
                    break;
                }
            }
            sb.append(dot);
            namec = Component.literal(sb.toString());
        }
        OERenderUtils.renderTextSprite(poseStack, multiBufferSource, namec, -(1f / 16f) * x, (1f / 16f) * 1.2f, 0, 0.18f, 0, 0, i);
        Component authorc = Component.literal(music.getAuthor());
        if (mc.font.width(authorc) >= ms) {
            StringBuilder sb = new StringBuilder();
            for (char c : music.getAuthor().toCharArray()) {
                sb.append(c);
                if (mc.font.width(sb.toString()) >= ms - mc.font.width(dot)) {
                    sb.deleteCharAt(sb.length() - 1);
                    break;
                }
            }
            sb.append(dot);
            authorc = Component.literal(sb.toString());
        }
        OERenderUtils.renderTextSprite(poseStack, multiBufferSource, authorc, -(1f / 16f) * x, (1f / 16f) * 0.575f, 0, 0.18f, 0, 0, i);
        poseStack.popPose();

        poseStack.popPose();
    }

    private static void renderBase(PoseStack poseStack, VertexConsumer ivb, MultiBufferSource multiBufferSource, ItemStack stack, int i, int i1) {
        if (stack.getItem() instanceof CassetteTapeItem) {
            CassetteTapeItem.BaseType type = ((CassetteTapeItem) stack.getItem()).getType();
            ModelHolder holder = IMPModels.CASSETTE_TAPE_BASE_NORMAL_MODEL;
            int color = ((CassetteTapeItem) stack.getItem()).hasCustomColor(stack) ? ((CassetteTapeItem) stack.getItem()).getColor(stack) : 0x1a1a1a;
            if (type == CassetteTapeItem.BaseType.GLASS) {
                holder = ((CassetteTapeItem) stack.getItem()).hasCustomColor(stack) ? IMPModels.CASSETTE_TAPE_BASE_GLASS_COLOR_MODEL : IMPModels.CASSETTE_TAPE_BASE_GLASS_MODEL;
                ivb = ItemRenderer.getFoilBufferDirect(multiBufferSource, Sheets.translucentCullBlockSheet(), true, stack.hasFoil());//multiBufferSource.getBuffer(Sheets.translucentCullBlockSheet());
            }
            BakedModel bakedModel = holder.get();
            OERenderUtils.renderModel(poseStack, ivb, bakedModel, i, i1, color);
        }
    }

    private static void renderTapeConecter(PoseStack poseStack, float angle, VertexConsumer ivb, double x, double y, double z, int i, int i1) {
        BakedModel tapeConecterModel = IMPModels.CASSETTE_TAPE_CONECTER.get();
        poseStack.pushPose();
        OERenderUtils.poseTrans16(poseStack, x, y, z);
        float f = 0.025f / 2f;
        OERenderUtils.poseTrans16(poseStack, f, f, f);
        OERenderUtils.poseRotateY(poseStack, angle);
        OERenderUtils.poseTrans16(poseStack, -f, -f, -f);
        OERenderUtils.renderModel(poseStack, ivb, tapeConecterModel, i, i1);
        poseStack.popPose();
    }

    private static void renderTapeRoll(PoseStack poseStack, float par, float roll, VertexConsumer ivb, double x, double y, double z, int i, int i1) {
        roll = Mth.clamp(roll, 0, 1);
        BakedModel tapeCoreModel = IMPModels.CASSETTE_TAPE_CORE_MODEL.get();
        BakedModel tapeCoreAroundModel = IMPModels.CASSETTE_TAPE_CORE_AROUND_MODEL.get();
        BakedModel tapeRollModel = IMPModels.CASSETTE_TAPE_ROLL_MODEL.get();

        poseStack.pushPose();
        OERenderUtils.poseTrans16(poseStack, x, y, z);
        poseStack.scale(1.25f, 1.05f, 1.25f);
        OERenderUtils.poseTrans16(poseStack, 0.4, 0, 0.4);
        OERenderUtils.poseRotateY(poseStack, par * 360f);
        OERenderUtils.poseTrans16(poseStack, -0.4, 0, -0.4);
        OERenderUtils.renderModel(poseStack, ivb, tapeCoreModel, i, i1);
        OERenderUtils.poseTrans16(poseStack, 0, 0.25, 0);
        OERenderUtils.renderModel(poseStack, ivb, tapeCoreAroundModel, i, i1);
        OERenderUtils.poseTrans16(poseStack, 0, 0.125f / 2f, 0);
        float rollPar = roll + 0.5f;
        for (int j = 1; j <= Math.ceil(rollPar); j++) {
            poseStack.pushPose();
            OERenderUtils.poseTrans16(poseStack, 0.4, 0, 0.4);
            float sc = Math.min(rollPar, j);
            poseStack.scale(sc, 1, sc);
            OERenderUtils.poseTrans16(poseStack, -0.4, 0, -0.4);
            OERenderUtils.renderModel(poseStack, ivb, tapeRollModel, i, i1);
            poseStack.popPose();
        }

        poseStack.popPose();
    }

}
