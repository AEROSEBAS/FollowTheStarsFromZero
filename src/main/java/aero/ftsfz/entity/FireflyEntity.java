package aero.ftsfz.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Luciérnaga provisional: no tiene modelo, se ve como partículas brillantes.
 * Revolotea alrededor de un punto (el altar) y desaparece después de un rato.
 */
public class FireflyEntity extends Entity {
    private static final double WANDER_RADIUS = 3.0;
    private static final double SPEED = 0.04;

    private Vec3 anchor;
    private Vec3 target;
    private int lifetime;

    public FireflyEntity(EntityType<? extends FireflyEntity> type, Level level) {
        super(type, level);
        this.noPhysics = true;
        this.setNoGravity(true);
        this.lifetime = 300 + level.random.nextInt(200); // 15-25 segundos
    }

    public void setAnchor(Vec3 anchor) {
        this.anchor = anchor;
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            // Parpadeo: no emite partícula en todos los ticks
            if (random.nextInt(3) == 0) {
                level().addParticle(ParticleTypes.GLOW, getX(), getY(), getZ(), 0, 0, 0);
            }
            return;
        }

        if (tickCount > lifetime || anchor == null) {
            discard();
            return;
        }

        // Elige un nuevo punto alrededor del altar cada tanto, o al llegar al actual
        if (target == null || tickCount % 40 == 0 || position().distanceToSqr(target) < 0.1) {
            target = anchor.add(
                    (random.nextDouble() * 2 - 1) * WANDER_RADIUS,
                    0.5 + random.nextDouble() * 2.0,
                    (random.nextDouble() * 2 - 1) * WANDER_RADIUS);
        }

        // Acelera suavemente hacia el objetivo para que el vuelo no sea recto
        Vec3 desired = target.subtract(position()).normalize().scale(SPEED);
        Vec3 motion = getDeltaMovement().lerp(desired, 0.1);
        setDeltaMovement(motion);
        setPos(getX() + motion.x, getY() + motion.y, getZ() + motion.z);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    // No se guardan en disco (el EntityType usa noSave), así que no hay datos que leer/escribir
    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {}
}
