package com.finchy.pipeorgans.block.pipes.voxhumana;

import com.finchy.pipeorgans.block.Generic;
import com.finchy.pipeorgans.block.pipes.voxhumana.VoxHumanaBlock;
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

public class VoxHumanaRenderer extends SafeBlockEntityRenderer<VoxHumanaBlockEntity> {

    public VoxHumanaRenderer(BlockEntityRendererProvider.Context context) {}

    @Override
    protected void renderSafe(VoxHumanaBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource bufferSource, int light, int overlay) {
        BlockState blockState = be.getBlockState();
        if (!(blockState.getBlock() instanceof VoxHumanaBlock))
            return;

        Direction direction = blockState.getValue(VoxHumanaBlock.FACING);
        Generic.WhistleSize size = blockState.getValue(VoxHumanaBlock.SIZE);

        PartialModel mouth = size == Generic.WhistleSize.TINY ? AllPartialModels.VOXHUMANA_MOUTH_TINY :
                size == Generic.WhistleSize.SMALL ? AllPartialModels.VOXHUMANA_MOUTH_SMALL :
                size == Generic.WhistleSize.MEDIUM ? AllPartialModels.VOXHUMANA_MOUTH_MEDIUM :
                        size == Generic.WhistleSize.LARGE ? AllPartialModels.VOXHUMANA_MOUTH_LARGE : AllPartialModels.VOXHUMANA_MOUTH_HUGE;

        float chaseTarget = be.animation.getChaseTarget();

        CachedBufferer.partial(mouth, blockState)
                .centre()
                .rotateY(AngleHelper.horizontalAngle(direction))
                .unCentre()
                .scale(chaseTarget)
                .light(light)
                .renderInto(ms, bufferSource.getBuffer(RenderType.solid()));

    }
}
