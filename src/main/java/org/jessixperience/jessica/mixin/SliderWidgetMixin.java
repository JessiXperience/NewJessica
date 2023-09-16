package org.jessixperience.jessica.mixin;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin( SliderWidget.class )
public class SliderWidgetMixin
{
    @Shadow @Final private static Identifier TEXTURE = new Identifier( "textures/gui/black_slide.png" );
}
