package org.jessixperience.jessica.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ClickableWidget.class)
public class ClickableWidgetMixin extends DrawableHelper {

    @Inject( method = "drawScrollableText(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIII)V", at = @At("HEAD"), cancellable = true )
    private static void drawScrollableText(MatrixStack matrices, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, int color, CallbackInfo ci) {

        int i = textRenderer.getWidth(text);
        int var10000 = top + bottom;
        Objects.requireNonNull(textRenderer);
        int j = (var10000 - 9) / 2 + 2;
        int k = right - left;
        OrderedText orderedText = text.asOrderedText();

        if (i > k) {
            int l = i - k;
            double d = (double) Util.getMeasuringTimeMs() / 1000.0;
            double e = Math.max((double)l * 0.5, 3.0);
            double f = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * d / e)) / 2.0 + 0.5;
            double g = MathHelper.lerp(f, 0.0, (double)l);
            enableScissor(left, top, right, bottom);
            float x = left - (int)g;
            float y = j;
            textRenderer.draw( matrices, orderedText, x, y, color );
            disableScissor();
        } else {
            float centerX = (left + right) / 2;
            textRenderer.draw( matrices, orderedText, (float)(centerX - textRenderer.getWidth(orderedText) / 2), j, color );
        }
        ci.cancel();

    }

}
