package org.jessixperience.jessica.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jessixperience.jessica.utils.AutoOffHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin( PlayerEntity.class )
public class PlayerEntityMixin {
    @Inject( method = "applyDamage", at = @At( "TAIL" ) )
    protected void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        AutoOffHand cheat = new AutoOffHand();
        if ( cheat.isActive() ) cheat.exec();
    }
}
