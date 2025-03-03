package com.finchy.pipeorgans.block.pipes.voxhumana;

import com.finchy.pipeorgans.block.Generic;
import com.finchy.pipeorgans.block.pipes.generic.QuadruplePipeBlock;
import com.finchy.pipeorgans.init.AllBlockEntities;
import com.finchy.pipeorgans.init.AllBlocks;
import com.finchy.pipeorgans.init.AllShapes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoxHumanaBlock extends QuadruplePipeBlock {
    public VoxHumanaBlock(Properties pProperties) {
        super(pProperties);
        this.baseBlock = AllBlocks.VOXHUMANA;
        this.extensionBlock = AllBlocks.VOXHUMANA_EXTENSION;
        this.blockEntity = AllBlockEntities.VOXHUMANA_BLOCK_ENTITY;
        registerDefaultState(defaultBlockState().setValue(SIZE, Generic.WhistleSize.SMALL));
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        VoxelShape whistle = AllShapes.getTrompetteBase(pState.getValue(SIZE));
        return Shapes.or(whistle,
                !pState.getValue(WALL) ?
                        AllShapes.BASE_FLOOR : AllShapes.getBase(pState.getValue(FACING)));
    }
}
