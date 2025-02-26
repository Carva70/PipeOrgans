package com.finchy.pipeorgans.block.pipes.englishhorn;

import com.finchy.pipeorgans.block.pipes.generic.GenericExtensionBlock;
import com.finchy.pipeorgans.init.AllBlocks;
import com.finchy.pipeorgans.init.AllShapes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnglishHornExtensionBlock extends GenericExtensionBlock {

    public EnglishHornExtensionBlock(Properties pProperties) {
        super(pProperties);
        this.baseBlock = AllBlocks.ENGLISHHORN;
    }

}
