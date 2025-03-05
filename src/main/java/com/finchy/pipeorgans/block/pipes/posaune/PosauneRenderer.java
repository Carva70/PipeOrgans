package com.finchy.pipeorgans.block.pipes.posaune;

import com.finchy.pipeorgans.block.Generic;
import com.finchy.pipeorgans.init.AllPartialModels;
import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.blockEntity.renderer.SafeBlockEntityRenderer;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class PosauneRenderer extends SafeBlockEntityRenderer<PosauneBlockEntity> {

    public PosauneRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    protected void renderSafe(PosauneBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        BlockState blockState = be.getBlockState();
        if (!(blockState.getBlock() instanceof PosauneBlock))
            return;

        Direction direction = blockState.getValue(PosauneBlock.FACING);
        Generic.WhistleSize size = blockState.getValue(PosauneBlock.SIZE);

        PartialModel mouth = size == Generic.WhistleSize.SMALL ? AllPartialModels.POSAUNE_MOUTH_SMALL :
                size == Generic.WhistleSize.MEDIUM ? AllPartialModels.POSAUNE_MOUTH_MEDIUM :
                size == Generic.WhistleSize.LARGE ? AllPartialModels.POSAUNE_MOUTH_LARGE : AllPartialModels.POSAUNE_MOUTH_HUGE;

        PartialModel cover = size == Generic.WhistleSize.SMALL ? AllPartialModels.POSAUNE_COVER_SMALL :
                size == Generic.WhistleSize.MEDIUM ? AllPartialModels.POSAUNE_COVER_MEDIUM :
                size == Generic.WhistleSize.LARGE ? AllPartialModels.POSAUNE_COVER_LARGE : AllPartialModels.POSAUNE_COVER_HUGE;


        float chaseTarget = be.animation.getChaseTarget();

        float offset = be.animation.getValue(partialTicks);
        if (be.animation.getChaseTarget() > 0 && be.animation.getValue() > 0.5f) {
            float wiggleProgress = (AnimationTickHolder.getTicks(be.getLevel()) + partialTicks) /8f;
            offset -= (Math.sin(wiggleProgress * (2 * Mth.PI) * (4)) / 16f);
        }

        CachedBufferer.partial(cover, blockState)
                .centre()
                .unCentre()
                .translateY((double) 4 /16 + be.getPitch() + 0.75)
                .translateZ((double) switch (size) {
                    case TINY -> 6;
                    case SMALL -> 5;
                    case MEDIUM -> 4;
                    case LARGE -> 3;
                    case HUGE -> 2;
                } /16)
                .rotateX(-offset*16 - 15)
                .light(light)
                .renderInto(ms, bufferSource.getBuffer(RenderType.solid()));

        CachedBufferer.partial(mouth, blockState)
                .centre()
                .rotateY(AngleHelper.horizontalAngle(direction))
                .unCentre()
                .scale(chaseTarget)
                .light(light)
                .renderInto(ms, bufferSource.getBuffer(RenderType.solid()));

    }
}
