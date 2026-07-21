package net.bball_262.redsbiomes.entities.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.bball_262.redsbiomes.entities.custom.CrabEntity;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

import static net.bball_262.redsbiomes.RedsBiomes.MOD_ID;

public class CrabModel<T extends CrabEntity> extends HierarchicalModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "crab"), "main");
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart eye_stalks;
    private final ModelPart L;
    private final ModelPart R;
    private final ModelPart legs;
    private final ModelPart leg;
    private final ModelPart shin;
    private final ModelPart leg2;
    private final ModelPart shin2;
    private final ModelPart leg3;
    private final ModelPart shin3;
    private final ModelPart leg4;
    private final ModelPart shin4;
    private final ModelPart leg9;
    private final ModelPart shin9;
    private final ModelPart leg10;
    private final ModelPart shin10;
    private final ModelPart leg11;
    private final ModelPart shin11;
    private final ModelPart leg12;
    private final ModelPart shin12;
    private final ModelPart arms;
    private final ModelPart big;
    private final ModelPart small;

    public CrabModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.eye_stalks = this.body.getChild("eye_stalks");
        this.L = this.eye_stalks.getChild("L");
        this.R = this.eye_stalks.getChild("R");
        this.legs = this.root.getChild("legs");
        this.leg = this.legs.getChild("leg");
        this.shin = this.leg.getChild("shin");
        this.leg2 = this.legs.getChild("leg2");
        this.shin2 = this.leg2.getChild("shin2");
        this.leg3 = this.legs.getChild("leg3");
        this.shin3 = this.leg3.getChild("shin3");
        this.leg4 = this.legs.getChild("leg4");
        this.shin4 = this.leg4.getChild("shin4");
        this.leg9 = this.legs.getChild("leg9");
        this.shin9 = this.leg9.getChild("shin9");
        this.leg10 = this.legs.getChild("leg10");
        this.shin10 = this.leg10.getChild("shin10");
        this.leg11 = this.legs.getChild("leg11");
        this.shin11 = this.leg11.getChild("shin11");
        this.leg12 = this.legs.getChild("leg12");
        this.shin12 = this.leg12.getChild("shin12");
        this.arms = this.root.getChild("arms");
        this.big = this.arms.getChild("big");
        this.small = this.arms.getChild("small");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -2.0F, -3.0F, 7.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition eye_stalks = body.addOrReplaceChild("eye_stalks", CubeListBuilder.create(), PartPose.offset(-0.1F, -2.0F, -2.0F));

        PartDefinition L = eye_stalks.addOrReplaceChild("L", CubeListBuilder.create().texOffs(6, 20).addBox(-1.0F, -1.75F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(3.1F, 0.0F, 0.0F));

        PartDefinition R = eye_stalks.addOrReplaceChild("R", CubeListBuilder.create().texOffs(4, 20).addBox(-1.0F, -1.75F, 0.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.9F, 0.0F, 0.0F));

        PartDefinition legs = root.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(0.1604F, -0.0492F, -1.2057F));

        PartDefinition leg = legs.addOrReplaceChild("leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.5787F, -1.6948F, 3.0901F, 0.0F, 0.2618F, 0.1745F));

        PartDefinition cube_r1 = leg.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 20).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.4984F, -1.4946F, 0.538F, 0.0F, 0.0F, 1.9635F));

        PartDefinition shin = leg.addOrReplaceChild("shin", CubeListBuilder.create(), PartPose.offset(-1.8586F, -0.5844F, 0.1978F));

        PartDefinition cube_r2 = shin.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 16).addBox(0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6398F, 0.0898F, -0.6598F, 0.0F, 0.0F, 0.3927F));

        PartDefinition leg2 = legs.addOrReplaceChild("leg2", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.6085F, -1.5099F, 1.9239F, 0.0F, 0.0873F, 0.1745F));

        PartDefinition cube_r3 = leg2.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(12, 18).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2752F, -1.508F, 0.3965F, 0.0F, 0.0F, 1.9635F));

        PartDefinition shin2 = leg2.addOrReplaceChild("shin2", CubeListBuilder.create(), PartPose.offset(-1.6319F, -0.5983F, -0.1035F));

        PartDefinition cube_r4 = shin2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(4, 16).addBox(0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6433F, 0.0903F, -0.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition leg3 = legs.addOrReplaceChild("leg3", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.3104F, -1.0508F, 0.4057F, 0.0F, -0.0873F, 0.1745F));

        PartDefinition cube_r5 = leg3.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(14, 18).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.7353F, -1.9054F, 0.5366F, 0.0F, 0.0F, 1.9635F));

        PartDefinition shin3 = leg3.addOrReplaceChild("shin3", CubeListBuilder.create(), PartPose.offset(-2.092F, -0.9957F, 0.0366F));

        PartDefinition cube_r6 = shin3.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(6, 16).addBox(0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6433F, 0.0903F, -0.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition leg4 = legs.addOrReplaceChild("leg4", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.714F, -1.3343F, -0.9497F, 0.0F, -0.2618F, 0.1745F));

        PartDefinition cube_r7 = leg4.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 14).addBox(1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.2896F, -1.8034F, 0.8208F, 0.0F, 0.0F, 1.9635F));

        PartDefinition shin4 = leg4.addOrReplaceChild("shin4", CubeListBuilder.create(), PartPose.offset(-1.6463F, -0.8937F, 0.3208F));

        PartDefinition cube_r8 = shin4.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(12, 14).addBox(0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.6433F, 0.0903F, -0.5F, 0.0F, 0.0F, 0.3927F));

        PartDefinition leg9 = legs.addOrReplaceChild("leg9", CubeListBuilder.create(), PartPose.offsetAndRotation(3.2951F, -1.5072F, -0.7639F, 0.0F, 0.2618F, -0.1745F));

        PartDefinition cube_r9 = leg9.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(16, 17).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.402F, -1.6161F, 0.6586F, 0.0F, 0.0F, -1.9635F));

        PartDefinition shin9 = leg9.addOrReplaceChild("shin9", CubeListBuilder.create(), PartPose.offset(1.8012F, -0.6764F, 0.0092F));

        PartDefinition cube_r10 = shin9.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(14, 14).addBox(-0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6008F, 0.0603F, -0.3506F, 0.0F, 0.0F, -0.3927F));

        PartDefinition leg10 = legs.addOrReplaceChild("leg10", CubeListBuilder.create(), PartPose.offsetAndRotation(3.3255F, -1.4363F, 0.4685F, 0.0F, 0.0873F, -0.1745F));

        PartDefinition cube_r11 = leg10.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(18, 14).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3906F, -1.6391F, 0.6136F, 0.0F, 0.0F, -1.9635F));

        PartDefinition shin10 = leg10.addOrReplaceChild("shin10", CubeListBuilder.create(), PartPose.offset(1.7473F, -0.6294F, 0.1136F));

        PartDefinition cube_r12 = shin10.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(8, 16).addBox(-0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6433F, -0.0097F, -0.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition leg11 = legs.addOrReplaceChild("leg11", CubeListBuilder.create(), PartPose.offsetAndRotation(3.2356F, -1.6438F, 2.1453F, 0.0F, -0.0873F, -0.1745F));

        PartDefinition cube_r13 = leg11.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(18, 17).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.2838F, -1.3671F, 0.1736F, 0.0F, 0.0F, -1.9635F));

        PartDefinition shin11 = leg11.addOrReplaceChild("shin11", CubeListBuilder.create(), PartPose.offset(1.6037F, -0.3502F, -0.2707F));

        PartDefinition cube_r14 = shin11.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(10, 16).addBox(-0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6801F, -0.0169F, -0.5557F, 0.0F, 0.0F, -0.3927F));

        PartDefinition leg12 = legs.addOrReplaceChild("leg12", CubeListBuilder.create(), PartPose.offsetAndRotation(3.2579F, -1.3948F, 3.1901F, 0.0F, -0.2618F, -0.1745F));

        PartDefinition cube_r15 = leg12.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(2, 20).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5229F, -1.79F, 0.4279F, 0.0F, 0.0F, -1.9635F));

        PartDefinition shin12 = leg12.addOrReplaceChild("shin12", CubeListBuilder.create(), PartPose.offset(1.8796F, -0.7803F, -0.0721F));

        PartDefinition cube_r16 = shin12.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(2, 16).addBox(-0.5412F, -0.3066F, 0.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.6433F, -0.0097F, -0.5F, 0.0F, 0.0F, -0.3927F));

        PartDefinition arms = root.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition big = arms.addOrReplaceChild("big", CubeListBuilder.create(), PartPose.offset(-3.0F, -3.0F, -3.0F));

        PartDefinition cube_r17 = big.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 10).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -2.0F, 0.0611F, -0.9599F, 0.0F));

        PartDefinition small = arms.addOrReplaceChild("small", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r18 = small.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(12, 10).addBox(0.25F, -2.0F, 0.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, -4.0F, 0.1745F, 0.9599F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
        root.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        this.animateWalk(CrabAnimations.ANIM_CRAB_WALK, limbSwing, limbSwingAmount, 2F, 20F);
        this.animate(entity.idleAnimationState, CrabAnimations.ANIM_CRAB_IDLE, ageInTicks, 1f);
    }
}
