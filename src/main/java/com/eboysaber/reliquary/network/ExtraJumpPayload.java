package com.eboysaber.reliquary.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ExtraJumpPayload(int jumpIndex) implements CustomPacketPayload {

    public static final Type<ExtraJumpPayload> TYPE = 
        new Type<>(ResourceLocation.fromNamespaceAndPath("reliquary", "extra_jump"));

    public static final StreamCodec<ByteBuf, ExtraJumpPayload> STREAM_CODEC =
        ByteBufCodecs.INT.map(ExtraJumpPayload::new, ExtraJumpPayload::jumpIndex);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
