package com.me.funmod.wandstation;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public class WandStationBlock extends Block implements BlockEntityProvider {
   public WandStationBlock(Settings settings) {
       super(settings);
   }

   @Override
   public BlockEntity createBlockEntity(BlockView block) {
       return new WandStationBlockEntity();
   }
}
