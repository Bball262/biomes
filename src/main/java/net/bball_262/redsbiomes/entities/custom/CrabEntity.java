package net.bball_262.redsbiomes.entities.custom;

import net.bball_262.redsbiomes.entities.CrabVariant;
import net.bball_262.redsbiomes.entities.ModEntities;
import net.bball_262.redsbiomes.items.ModItems;
import net.bball_262.redsbiomes.tags.ModTags;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class CrabEntity extends Animal implements Bucketable {
    private static final EntityDataAccessor<Integer> DATA_ID_TYPE_VARIANT = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(CrabEntity.class, EntityDataSerializers.BOOLEAN);
    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;

    public CrabEntity(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_ID_TYPE_VARIANT, 0);
        builder.define(FROM_BUCKET, false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.1F));
        this.goalSelector.addGoal(2, new TemptGoal(this, 1.1F, stack -> stack.is(Items.KELP), false));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.1F));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.ARMOR, 4d)
                .add(Attributes.ARMOR_TOUGHNESS, 2d)
                .add(Attributes.MAX_HEALTH, 6)
                .add(Attributes.MOVEMENT_SPEED, .2d)
                .add(Attributes.FOLLOW_RANGE, 24d);
    }

    public static boolean checkCrabSpawnRules(EntityType<? extends Animal> animal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean flag = MobSpawnType.ignoresLightRequirements(spawnType) || isBrightEnoughToSpawn(level, pos);
        return level.getBlockState(pos.below()).is(ModTags.Blocks.CRAB_SPAWNABLE_ON) && flag;
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
            Set<CrabVariant> variants = new HashSet<>();
            variants.add(this.getVariant());
            variants.add(((CrabEntity) otherParent).getVariant());

            if (this.random.nextIntBetweenInclusive(0, 1) == 0) {
                Set<CrabVariant> excludedVariants = new HashSet<>(Arrays.stream(CrabVariant.values()).toList());
                excludedVariants.removeAll(variants);

                baby.setVariant(Util.getRandom(excludedVariants.stream().toList(), baby.getRandom()));
            } else {
                baby.setVariant(Util.getRandom(variants.stream().toList(), baby.getRandom()));
            }
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
        compound.putBoolean("From_Bucket", this.fromBucket());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setVariant(compound.getInt("Variant"));
        this.setFromBucket(compound.getBoolean("From_Bucket"));
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

        if (!variant.isCommon()) {
            variant = CrabVariant.GRAY;
        }

        this.setVariant(variant);
        return super.finalizeSpawn(level, difficulty, spawnType, spawnGroupData);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        return Bucketable.bucketMobPickup(player, hand, this).orElse(super.mobInteract(player, hand));
    }

    @Override
    public boolean fromBucket() {
        return this.entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean b) {
        this.entityData.set(FROM_BUCKET, b);
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        this.saveDataToBucketTag(this, stack);
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        this.loadDataFromBucketTag(this, tag);
    }

    @Override
    public ItemStack getBucketItemStack() {
        return ModItems.CRAB_BUCKET.toStack();
    }

    @Override
    public SoundEvent getPickupSound() {
        return SoundEvents.BUCKET_FILL_FISH;
    }

    private void saveDataToBucketTag(CrabEntity crab, ItemStack bucket) {
        bucket.set(DataComponents.CUSTOM_NAME, crab.getCustomName());
        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, bucket, (tag) -> {
            if (crab.isNoAi()) {
                tag.putBoolean("NoAI", crab.isNoAi());
            }

            if (crab.isSilent()) {
                tag.putBoolean("Silent", crab.isSilent());
            }

            if (crab.isNoGravity()) {
                tag.putBoolean("NoGravity", crab.isNoGravity());
            }

            if (crab.hasGlowingTag()) {
                tag.putBoolean("Glowing", crab.hasGlowingTag());
            }

            if (crab.isInvulnerable()) {
                tag.putBoolean("Invulnerable", crab.isInvulnerable());
            }

            if (crab.isBaby()) {
                tag.putBoolean("Baby", crab.isBaby());
                tag.putInt("Age", crab.getAge());
            }

            tag.putFloat("Health", crab.getHealth());
            tag.putInt("Variant", crab.getVariant().getId());
        });
    }

    private void loadDataFromBucketTag(CrabEntity crab, CompoundTag tag) {
        if (tag.contains("NoAI")) {
            crab.setNoAi(tag.getBoolean("NoAI"));
        }

        if (tag.contains("Silent")) {
            crab.setSilent(tag.getBoolean("Silent"));
        }

        if (tag.contains("NoGravity")) {
            crab.setNoGravity(tag.getBoolean("NoGravity"));
        }

        if (tag.contains("Glowing")) {
            crab.setGlowingTag(tag.getBoolean("Glowing"));
        }

        if (tag.contains("Invulnerable")) {
            crab.setInvulnerable(tag.getBoolean("Invulnerable"));
        }

        if (tag.contains("Health")) {
            crab.setHealth(tag.getFloat("Health"));
        }

        if (tag.contains("Variant")) {
            crab.setVariant(tag.getInt("Variant"));
        }

        if (tag.contains("Baby")) {
            crab.setBaby(tag.getBoolean("Baby"));
            crab.setAge(tag.getInt("Age"));
        }
    }
}