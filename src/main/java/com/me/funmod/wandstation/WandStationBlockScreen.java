package com.me.funmod.wandstation;

import io.github.cottonmc.cotton.gui.client.CottonInventoryScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

public class WandStationBlockScreen  extends CottonInventoryScreen<WandStationGuiDescription> {
    public WandStationBlockScreen(WandStationGuiDescription gui, PlayerEntity player, Text title) {
        super(gui, player, title);
    }
}
