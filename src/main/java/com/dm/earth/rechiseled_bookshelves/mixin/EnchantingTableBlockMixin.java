package com.dm.earth.rechiseled_bookshelves.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.ChiseledBookshelfBlockEntity;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnchantingTableBlock.class)
public class EnchantingTableBlockMixin {
    @Inject(method = "canAccessBookshelf", at = @At("RETURN"), cancellable = true)
    private static void canAccessBookshelf(World world, BlockPos tablePos, BlockPos bookshelfOffset, CallbackInfoReturnable<Boolean> cir) {
        BlockPos targetPos = tablePos.add(bookshelfOffset);
        BlockState targetState = world.getBlockState(targetPos);

        //Check middle places
        BlockPos middlePos = tablePos.add(bookshelfOffset.getX() / 2, bookshelfOffset.getY(), bookshelfOffset.getZ() / 2);
        BlockState middleState = world.getBlockState(middlePos);
        if (!(middleState.getOutlineShape(world, middlePos).isEmpty() || middleState.isAir() || middleState.getBlock() instanceof FluidBlock)) {
            cir.setReturnValue(false);
            return;
        }

        //Check target block
        if (targetState.isOf(Blocks.BOOKSHELF)) {
            cir.setReturnValue(true);
        } else if (targetState.getBlock() instanceof ChiseledBookshelfBlock bookshelf) cir.setReturnValue(isBookEnough(world, targetPos, bookshelf));
    }

    private static boolean isBookEnough(World world, BlockPos pos, ChiseledBookshelfBlock block) {
        ChiseledBookshelfBlockEntity blockEntity = (ChiseledBookshelfBlockEntity) world.getBlockEntity(pos);
        if (blockEntity.getBookCount() >= 3) return true;
        if (blockEntity.books.stream().anyMatch(book -> book.getItem() instanceof EnchantedBookItem)) return true;
        return false;
    }
}
