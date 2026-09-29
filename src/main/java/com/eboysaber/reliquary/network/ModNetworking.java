package com.eboysaber.reliquary.network;

import top.theillusivec4.curios.api.CuriosApi;
import com.eboysaber.reliquary.util.JumpAbility;
import com.eboysaber.reliquary.item.ModItems;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = "reliquary", bus = EventBusSubscriber.Bus.MOD)
public final class ModNetworking {

    private ModNetworking() {
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                ExtraJumpPayload.TYPE,
                ExtraJumpPayload.STREAM_CODEC,
                ModNetworking::handleExtraJump);
    }

    private static void handleExtraJump(ExtraJumpPayload payload, IPayloadContext context) {
        context.enqueueWork(() -> {
            Player player = context.player();
            if (JumpAbility.count(player) == 0) {
                return;
            }
            player.fallDistance = 0;

            boolean hasStormBottle = CuriosApi.getCuriosInventory(player)
                .map(inv -> !inv.findCurios(ModItems.STORM_IN_A_BOTTLE.get()).isEmpty())
                .orElse(false);

            if (player.level() instanceof ServerLevel level) {
                if (payload.jumpIndex() == 1 || payload.jumpIndex() >= 2 && !hasStormBottle) { // cloud in bottle
                    level.sendParticles(ParticleTypes.CLOUD,
                            player.getX(), player.getY(), player.getZ(),
                            8, 0.25, 0.05, 0.25, 0.02);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.WOOL_FALL, SoundSource.PLAYERS, 0.8F, 1.4F);
                }
                else if (payload.jumpIndex() >= 2) { // storm in bottle
                    level.sendParticles(ParticleTypes.SPLASH,
                            player.getX(),player.getY(), player.getZ(),
                            20, 0.3, 0.05, 0.3, 0.05);
                    level.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.PLAYERS, 0.8F, 1.4F);
                }
            }
        });
    }
}
