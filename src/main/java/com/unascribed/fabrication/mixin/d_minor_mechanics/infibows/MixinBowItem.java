package com.unascribed.fabrication.mixin.d_minor_mechanics.infibows;

import com.unascribed.fabrication.FabConf;
import com.unascribed.fabrication.support.injection.FabModifyVariable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.unascribed.fabrication.support.EligibleIf;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

@Mixin(BowItem.class)
@EligibleIf(configAvailable="*.infibows", classNotPresent="net.parker8283.bif.BowInfinityFix")
public class MixinBowItem {

	@FabModifyVariable(at=@At("STORE"), method="use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;")
	private boolean infiBow(boolean hasArrows, World world, PlayerEntity player, Hand hand) {
		if (FabConf.isEnabled("*.infibows") && EnchantmentHelper.getLevel(Enchantments.INFINITY, player.getStackInHand(hand)) > 0)
			return true;
		return hasArrows;
	}

}
