package com.finchy.pipeorgans.block.pipes.generic;

import com.finchy.pipeorgans.block.Generic;
import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import com.simibubi.create.content.kinetics.steamEngine.SteamJetParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

public class RegalBlockEntity extends GenericPipeBlockEntity {

    public RegalBlockEntity(BlockPos pos, BlockState blockState, RegistryObject<BlockEntityType> blockEntity) {
        super(pos, blockState, blockEntity);
    }

    @Override
    public void updatePitch() {
        BlockPos currentPos = worldPosition.above();
        int newPitch;
        for (newPitch = 0; newPitch <= 12; newPitch += 4) {
            BlockState blockState = level.getBlockState(currentPos);
            if (!(blockState.getBlock() instanceof QuadrupleExtensionBlock))
                break;
            if (blockState.getValue(QuadrupleExtensionBlock.SHAPE) == Generic.QuadrupleExtensionShape.SINGLE) {
                newPitch++;
                break;
            }
            if (blockState.getValue(QuadrupleExtensionBlock.SHAPE) == Generic.QuadrupleExtensionShape.DOUBLE) {
                newPitch+=2;
                break;
            }
            if (blockState.getValue(QuadrupleExtensionBlock.SHAPE) == Generic.QuadrupleExtensionShape.TRIPLE) {
                newPitch+=3;
                break;
            }
            currentPos = currentPos.above();
        }
        if (pitch == newPitch)
            return;
        pitch = newPitch;

        notifyUpdate();

        FluidTankBlockEntity tank = getTank();
        if (tank != null && tank.boiler != null)
            tank.boiler.checkPipeOrganAdvancement(tank);
    }

    @Override
    public void createSteamJet(Generic.WhistleSize size) {
        float yOffset = 0.125f;
        double yPos = ((double) pitch /4) + 1 + yOffset;
        Vec3 v = new Vec3(0, yPos, 0).add(Vec3.atBottomCenterOf(worldPosition));
        Vec3 m = new Vec3(0, 1, 0);
        level.addParticle(new SteamJetParticleData(1), v.x, v.y, v.z, m.x, m.y, m.z);
    }
}
