package ru.smole.autoclicker;

import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;

import java.util.ArrayList;
import java.util.List;

public class AutoClicker implements IAutoClicker {

    private Minecraft mc = Minecraft.getMinecraft();
    private List<Integer> clicks;

    public AutoClicker() {
       clicks = new ArrayList<>();
    }

    @Override
    public void click() {
        if (mc.player != null && mc.playerController != null) {
            RayTraceResult rayTrace = mc.objectMouseOver;
            if (mc.player.getCooledAttackStrength(0.0F) == 1.0F) {
                if (rayTrace.typeOfHit == RayTraceResult.Type.BLOCK) {
                    BlockPos pos = rayTrace.getBlockPos();

                    if (mc.world != null && mc.world.isAirBlock(pos)) {
                        mc.playerController.clickBlock(pos, rayTrace.sideHit);
                    }
                }

                if (rayTrace.typeOfHit == RayTraceResult.Type.ENTITY && mc.pointedEntity != null) {
                    mc.playerController.attackEntity(mc.player, mc.pointedEntity);
                }

                clicks.add(0);
                mc.player.swingArm(EnumHand.MAIN_HAND);
            }
        }
    }

    @Override
    public List<Integer> getClicks() {
        return clicks;
    }
}
