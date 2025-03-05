package com.finchy.pipeorgans.block.pipes.posaune;

import com.finchy.pipeorgans.block.Generic;
import com.finchy.pipeorgans.block.pipes.generic.GenericPipeBlock;
import com.finchy.pipeorgans.block.pipes.generic.PedalPipeBlock;
import com.finchy.pipeorgans.init.AllBlockEntities;
import com.finchy.pipeorgans.init.AllBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.Direction;

public class PosauneBlock extends PedalPipeBlock {

    public PosauneBlock(Properties pProperties) {
        super(pProperties);
        this.baseBlock = AllBlocks.POSAUNE;
        this.extensionBlock = AllBlocks.POSAUNE_EXTENSION;
        this.blockEntity = AllBlockEntities.POSAUNE_BLOCK_ENTITY;
    }

    @Override
    // increase length of whistle
    public void incrementSize(LevelAccessor pLevel, BlockPos pPos) {
        BlockState base = pLevel.getBlockState(pPos);
        if (!base.hasProperty(SIZE))
            return;

        Generic.WhistleSize size = base.getValue(SIZE);
        SoundType soundtype = base.getSoundType();
        BlockPos currentPos = pPos.above();
        Direction facing = base.getValue(FACING).getOpposite();

        float pVolume = (soundtype.getVolume() + 1.0F) / 2.0F;
        SoundEvent growSound = SoundEvents.NOTE_BLOCK_XYLOPHONE.get();
        SoundEvent hitSound = soundtype.getHitSound();

        for (int i = 1; i <= 12; i+=1) {
            BlockState blockState = pLevel.getBlockState(currentPos);

            if (blockState.getBlock() instanceof PosauneExtensionBlock) {
                currentPos = currentPos.above();
                continue;
            }

            // if pos is not extension (air)
            if (!blockState.canBeReplaced())
                return;

            pLevel.setBlock(currentPos, AllBlocks.POSAUNE_EXTENSION.get().defaultBlockState()
                    .setValue(SIZE, size)
                    .setValue(GenericPipeBlock.FACING, facing), 3);
            if (soundtype != null) {
                float pPitch = (float) Math.pow(2, -i / 12.0);
                pLevel.playSound(null, currentPos, growSound, SoundSource.BLOCKS, pVolume / 4f, pPitch);
                pLevel.playSound(null, currentPos, hitSound, SoundSource.BLOCKS, pVolume, pPitch);
            }
            return;
        }
    }

}
