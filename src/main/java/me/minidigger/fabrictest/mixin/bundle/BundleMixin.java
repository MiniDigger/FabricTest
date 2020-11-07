package me.minidigger.fabrictest.mixin.bundle;

import net.minecraft.world.item.BundleItem;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BundleItem.class)
public class BundleMixin {

    private static int toAdd = 0;

//    @Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;")
//    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
//        System.out.println("use " + user.getEntityName() + " " + hand.name());
//    }

    @Inject(at = @At(value = "HEAD"), method = "add(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V")
    private static void add(ItemStack itemStack, ItemStack itemStack2, CallbackInfo ci) {
        System.out.println("trying to add " + itemStack2 + " to " + itemStack);
        toAdd = itemStack2.getCount();
    }

    @ModifyVariable(method = "add(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)V", at = @At("STORE"), ordinal = 2)
    private static int inject(int k) {
        k = toAdd;
        toAdd = 0;
        return k;
    }
}
