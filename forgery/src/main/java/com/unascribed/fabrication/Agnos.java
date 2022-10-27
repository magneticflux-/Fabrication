package com.unascribed.fabrication;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.KeyMapping;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import java.util.List;

// Forge implementation of Agnos. For linguistic and philosophical waffling, see the Fabric version.
public final class Agnos {

	public interface CommandRegistrationCallback {
		void register(CommandDispatcher<CommandSourceStack> commandDispatcher,CommandBuildContext context, boolean dedicated);
	}

	public interface TooltipRenderCallback {
		void render(ItemStack stack, List<Component> lines);
	}

	public interface HudRenderCallback {
		void render(PoseStack matrixStack, float tickDelta);
	}

	public static void runForCommandRegistration(CommandRegistrationCallback r) {
		MinecraftForge.EVENT_BUS.addListener((RegisterCommandsEvent e) -> {
			r.register(e.getDispatcher(), e.getBuildContext(), true);
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void runForTooltipRender(TooltipRenderCallback r) {
		MinecraftForge.EVENT_BUS.addListener((ItemTooltipEvent e) -> {
			r.render(e.getItemStack(), e.getToolTip());
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void runForHudRender(HudRenderCallback r) {
		MinecraftForge.EVENT_BUS.addListener((RenderGuiOverlayEvent.Post e) -> {
			r.render(e.getPoseStack(), e.getPartialTick());
		});
	}

	@OnlyIn(Dist.CLIENT)
	public static void registerKeyBinding(KeyMapping kb) {
		MinecraftForge.EVENT_BUS.addListener((RegisterKeyMappingsEvent event) -> {
			event.register(kb);
		});
	}
}
