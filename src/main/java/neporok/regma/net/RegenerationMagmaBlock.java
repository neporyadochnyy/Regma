package neporok.regma.net;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class RegenerationMagmaBlock extends Block {
    public RegenerationMagmaBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    public boolean WAIT = false;
    public boolean STARTED = false;

    public void clock () {
        Thread one = new Thread() {
            public void run() {
                try {
                    Thread.sleep(450);
                    WAIT = false;
                    STARTED = false;
                } catch(InterruptedException v) {
                    System.out.println();
                }
            }
        };
        one.start();
    }
    public Holder<Attribute> maxHealthAttribute = Attributes.MAX_HEALTH;

    @Override
    public void stepOn(Level level, BlockPos blockPos, BlockState blockState, Entity entity) {
        double maxHealth = ((LivingEntity) entity).getAttributeValue(maxHealthAttribute);
        double currentHealth = ((LivingEntity) entity).getHealth();
        if (entity.isSteppingCarefully() && entity instanceof LivingEntity && maxHealth != currentHealth) {
            if (WAIT == false) {
                WAIT = true;
                ((LivingEntity) entity).heal(1.0F);
                entity.playSound(SoundEvents.LAVA_POP, 1.0F, 0.8F);
            }
            if (WAIT == true && STARTED == false) {
                STARTED = true;
                clock();
            }
        }
        super.stepOn(level, blockPos, blockState, entity);
    }
}