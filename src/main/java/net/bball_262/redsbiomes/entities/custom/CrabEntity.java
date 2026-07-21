package net.bball_262.redsbiomes.entities.custom;

import net.bball_262.redsbiomes.entities.CrabVariant;
import net.bball_262.redsbiomes.entities.ModEntities;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.ResetUniversalAngerTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrabEntity extends Animal implements NeutralMob {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> DATA_REMAINING_ANGER_TIME = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    private static final UniformInt PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 30);
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    @Nullable
    private UUID persistentAngerTarget;

    public CrabEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE_VARIANT, 0);
        builder.define(DATA_REMAINING_ANGER_TIME, 0);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2F, true));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.1F));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.1F, stack -> stack.is(Items.KELP), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1F));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.ARMOR, 4d)
                .add(Attributes.ARMOR_TOUGHNESS, 2d)
                .add(Attributes.ATTACK_DAMAGE, 2.0)
                .add(Attributes.ENTITY_INTERACTION_RANGE, 2.0)
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.MOVEMENT_SPEED, .2d)
                .add(Attributes.FOLLOW_RANGE, 24d);
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.getEntity() != null) {
            this.setTarget((LivingEntity) source.getEntity());
            this.setPersistentAngerTarget(source.getEntity().getUUID());
        }

        return super.hurt(source, amount);
    }

    @Override
    protected void customServerAiStep() {
        if (this.getTarget() != null) {
            this.alertOthers();
        }

        if (this.isAngry()) {
            this.lastHurtByPlayerTime = this.tickCount;
        }

        super.customServerAiStep();
    }

    private void alertOthers() {
        double d0 = this.getAttributeValue(Attributes.FOLLOW_RANGE);
        AABB aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(d0, 10.0, d0);
        this.level()
                .getEntitiesOfClass(CrabEntity.class, aabb, EntitySelector.NO_SPECTATORS)
                .stream()
                .filter(entity -> entity != this)
                .filter(entity -> entity.getTarget() == null)
                .filter(entity -> !entity.isAlliedTo(this.getTarget()))
                .forEach(entity -> entity.setTarget(this.getTarget()));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.KELP);
    }
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        CrabEntity baby = ModEntities.CRAB_ENTITY_TYPE.get().create(level);

        if (otherParent instanceof CrabEntity) {
            List<CrabVariant> variants = new ArrayList<>();
            variants.add(this.getVariant());
            variants.add(((CrabEntity) otherParent).getVariant());

            baby.setVariant(Util.getRandom(variants, baby.getRandom()));
        }

        return baby;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = 85;
            this.idleAnimationState.start(this.tickCount);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.level().isClientSide) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Variant", this.entityData.get(DATA_ID_TYPE_VARIANT));
        compound.putInt("Remaining_Anger_Time", this.entityData.get(DATA_REMAINING_ANGER_TIME));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setRemainingPersistentAngerTime(compound.getInt("Remaining_Anger_Time"));
    }

    private void setVariant(int typeVariant) {
        this.entityData.set(DATA_ID_TYPE_VARIANT, typeVariant & 255);
    }

    private void setVariant(CrabVariant variant) {
        this.setVariant(variant.getId());
    }

    public CrabVariant getVariant() {
        return CrabVariant.BY_ID.apply(this.entityData.get(DATA_ID_TYPE_VARIANT));
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, @Nullable SpawnGroupData spawnGroupData) {
        CrabVariant variant = Util.getRandom(CrabVariant.values(), this.random);

        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return this.entityData.get(DATA_REMAINING_ANGER_TIME);
    }

    @Override
    public void setRemainingPersistentAngerTime(int i) {
        this.entityData.set(DATA_REMAINING_ANGER_TIME, i);
    }

    @Nullable
    @Override
    public UUID getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID uuid) {
        this.persistentAngerTarget = uuid;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(PERSISTENT_ANGER_TIME.sample(this.random));
    }
}
