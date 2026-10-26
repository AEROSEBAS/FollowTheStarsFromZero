package aero.ftsfz.block;

import aero.ftsfz.Ftsfz;
import aero.ftsfz.entity.FireflyEntity;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

/** Bloque de media altura: al hacer click derecho sube al jugador encima e invoca luciérnagas alrededor. */
public class FireflyAltarBlock extends Block {
    public static final MapCodec<FireflyAltarBlock> CODEC = simpleCodec(FireflyAltarBlock::new);
    private static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 8, 16); // igual que un slab inferior

    private static final int FIREFLIES_PER_USE = 8;
    private static final int MAX_FIREFLIES = 24; // evita llenar el mundo si se hace spam de clicks
    private static final double RADIUS = 2.5;

    public FireflyAltarBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState state) {
        return true;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!level.isClientSide) {
            Vec3 top = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5,pos.getZ() + 0.5);

            // El jugador queda parado en el centro del bloque
            player.teleportTo(top.x, top.y, top.z);
            player.setDeltaMovement(Vec3.ZERO);

            int existing = level.getEntitiesOfClass(FireflyEntity.class, new AABB(pos).inflate(RADIUS * 2)).size();
            int toSpawn = Math.min(FIREFLIES_PER_USE, MAX_FIREFLIES - existing);
            for (int i = 0; i < toSpawn; i++) {
                double angle = (Math.PI * 2 * i) / FIREFLIES_PER_USE;
                FireflyEntity firefly = Ftsfz.FIREFLY.get().create(level);
                if (firefly == null) continue;
                firefly.setPos(top.x + Mth.cos((float) angle) * RADIUS,
                        top.y + 0.5 + level.random.nextDouble() * 1.5,
                        top.z + Mth.sin((float) angle) * RADIUS);
                firefly.setAnchor(top);
                level.addFreshEntity(firefly);
            }

            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0f, 1.2f);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
