package com.eboysaber.reliquary.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record ExtraJumpPayload() implements CustomPacketPayload {

    public static final Type<ExtraJumpPayload> TYPE =
        new Type<>(ResourceLocation.fromNamespaceAndPath("reliquary", "extra_jump"));

    public static final StreamCodec<ByteBuf, ExtraJumpPayload> STREAM_CODEC =
        StreamCodec.unit(new ExtraJumpPayload());

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
