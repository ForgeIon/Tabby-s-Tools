package net.mcreator.kurostools;

import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.Capability;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Direction;
import net.minecraft.network.PacketBuffer;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.client.Minecraft;

import java.util.function.Supplier;

public class KurosToolsModVariables {
	public KurosToolsModVariables(KurosToolsModElements elements) {
		elements.addNetworkMessage(PlayerVariablesSyncMessage.class, PlayerVariablesSyncMessage::buffer, PlayerVariablesSyncMessage::new,
				PlayerVariablesSyncMessage::handler);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::init);
	}

	private void init(FMLCommonSetupEvent event) {
		CapabilityManager.INSTANCE.register(PlayerVariables.class, new PlayerVariablesStorage(), PlayerVariables::new);
	}

	@CapabilityInject(PlayerVariables.class)
	public static Capability<PlayerVariables> PLAYER_VARIABLES_CAPABILITY = null;

	@SubscribeEvent
	public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event) {
		if (event.getObject() instanceof PlayerEntity && !(event.getObject() instanceof FakePlayer))
			event.addCapability(new ResourceLocation("kuros_tools", "player_variables"), new PlayerVariablesProvider());
	}

	private static class PlayerVariablesProvider implements ICapabilitySerializable<INBT> {
		private final LazyOptional<PlayerVariables> instance = LazyOptional.of(PLAYER_VARIABLES_CAPABILITY::getDefaultInstance);

		@Override
		public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
			return cap == PLAYER_VARIABLES_CAPABILITY ? instance.cast() : LazyOptional.empty();
		}

		@Override
		public INBT serializeNBT() {
			return PLAYER_VARIABLES_CAPABILITY.getStorage().writeNBT(PLAYER_VARIABLES_CAPABILITY, this.instance.orElseThrow(RuntimeException::new),
					null);
		}

		@Override
		public void deserializeNBT(INBT nbt) {
			PLAYER_VARIABLES_CAPABILITY.getStorage().readNBT(PLAYER_VARIABLES_CAPABILITY, this.instance.orElseThrow(RuntimeException::new), null,
					nbt);
		}
	}

	private static class PlayerVariablesStorage implements Capability.IStorage<PlayerVariables> {
		@Override
		public INBT writeNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side) {
			CompoundNBT nbt = new CompoundNBT();
			nbt.putDouble("Coord1", instance.Coord1);
			nbt.putDouble("Coord2", instance.Coord2);
			nbt.putDouble("Coord3", instance.Coord3);
			nbt.putDouble("Coord4", instance.Coord4);
			nbt.putDouble("Coord5", instance.Coord5);
			nbt.putDouble("Coord6", instance.Coord6);
			nbt.putDouble("UPID", instance.UPID);
			nbt.putDouble("Oxygen", instance.Oxygen);
			nbt.putBoolean("Pressure", instance.Pressure);
			return nbt;
		}

		@Override
		public void readNBT(Capability<PlayerVariables> capability, PlayerVariables instance, Direction side, INBT inbt) {
			CompoundNBT nbt = (CompoundNBT) inbt;
			instance.Coord1 = nbt.getDouble("Coord1");
			instance.Coord2 = nbt.getDouble("Coord2");
			instance.Coord3 = nbt.getDouble("Coord3");
			instance.Coord4 = nbt.getDouble("Coord4");
			instance.Coord5 = nbt.getDouble("Coord5");
			instance.Coord6 = nbt.getDouble("Coord6");
			instance.UPID = nbt.getDouble("UPID");
			instance.Oxygen = nbt.getDouble("Oxygen");
			instance.Pressure = nbt.getBoolean("Pressure");
		}
	}

	public static class PlayerVariables {
		public double Coord1 = 0;
		public double Coord2 = 0;
		public double Coord3 = 0;
		public double Coord4 = 0;
		public double Coord5 = 0;
		public double Coord6 = 0;
		public double UPID = 0;
		public double Oxygen = 0;
		public boolean Pressure = false;

		public void syncPlayerVariables(Entity entity) {
			if (entity instanceof ServerPlayerEntity)
				KurosToolsMod.PACKET_HANDLER.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) entity),
						new PlayerVariablesSyncMessage(this));
		}
	}

	@SubscribeEvent
	public void onPlayerLoggedInSyncPlayerVariables(PlayerEvent.PlayerLoggedInEvent event) {
		if (!event.getPlayer().world.isRemote())
			((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncPlayerVariables(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerRespawnedSyncPlayerVariables(PlayerEvent.PlayerRespawnEvent event) {
		if (!event.getPlayer().world.isRemote())
			((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncPlayerVariables(event.getPlayer());
	}

	@SubscribeEvent
	public void onPlayerChangedDimensionSyncPlayerVariables(PlayerEvent.PlayerChangedDimensionEvent event) {
		if (!event.getPlayer().world.isRemote())
			((PlayerVariables) event.getPlayer().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()))
					.syncPlayerVariables(event.getPlayer());
	}

	@SubscribeEvent
	public void clonePlayer(PlayerEvent.Clone event) {
		PlayerVariables original = ((PlayerVariables) event.getOriginal().getCapability(PLAYER_VARIABLES_CAPABILITY, null)
				.orElse(new PlayerVariables()));
		PlayerVariables clone = ((PlayerVariables) event.getEntity().getCapability(PLAYER_VARIABLES_CAPABILITY, null).orElse(new PlayerVariables()));
		clone.UPID = original.UPID;
		if (!event.isWasDeath()) {
			clone.Coord1 = original.Coord1;
			clone.Coord2 = original.Coord2;
			clone.Coord3 = original.Coord3;
			clone.Coord4 = original.Coord4;
			clone.Coord5 = original.Coord5;
			clone.Coord6 = original.Coord6;
			clone.Oxygen = original.Oxygen;
			clone.Pressure = original.Pressure;
		}
	}

	public static class PlayerVariablesSyncMessage {
		public PlayerVariables data;

		public PlayerVariablesSyncMessage(PacketBuffer buffer) {
			this.data = new PlayerVariables();
			new PlayerVariablesStorage().readNBT(null, this.data, null, buffer.readCompoundTag());
		}

		public PlayerVariablesSyncMessage(PlayerVariables data) {
			this.data = data;
		}

		public static void buffer(PlayerVariablesSyncMessage message, PacketBuffer buffer) {
			buffer.writeCompoundTag((CompoundNBT) new PlayerVariablesStorage().writeNBT(null, message.data, null));
		}

		public static void handler(PlayerVariablesSyncMessage message, Supplier<NetworkEvent.Context> contextSupplier) {
			NetworkEvent.Context context = contextSupplier.get();
			context.enqueueWork(() -> {
				if (!context.getDirection().getReceptionSide().isServer()) {
					PlayerVariables variables = ((PlayerVariables) Minecraft.getInstance().player.getCapability(PLAYER_VARIABLES_CAPABILITY, null)
							.orElse(new PlayerVariables()));
					variables.Coord1 = message.data.Coord1;
					variables.Coord2 = message.data.Coord2;
					variables.Coord3 = message.data.Coord3;
					variables.Coord4 = message.data.Coord4;
					variables.Coord5 = message.data.Coord5;
					variables.Coord6 = message.data.Coord6;
					variables.UPID = message.data.UPID;
					variables.Oxygen = message.data.Oxygen;
					variables.Pressure = message.data.Pressure;
				}
			});
			context.setPacketHandled(true);
		}
	}
}
