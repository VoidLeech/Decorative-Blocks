package lilypuree.decorative_blocks.platform;

import lilypuree.decorative_blocks.Constants;
import lilypuree.decorative_blocks.entity.DummyEntityForSitting;
import lilypuree.decorative_blocks.fluid.ForgeThatchFluid;
import lilypuree.decorative_blocks.fluid.ForgeThatchFluidBlock;
import lilypuree.decorative_blocks.fluid.ThatchFluid;
import lilypuree.decorative_blocks.platform.services.IPlatformHelper;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.renderer.item.ItemPropertyFunction;
import net.minecraft.core.Registry;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.DeferredRegister;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ForgePlatformHelper implements IPlatformHelper {
    public static Map<Registry<?>, DeferredRegister<?>> registries = new HashMap<>();

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public <I, T extends I> Supplier<T> register(Registry<I> registry, String name, Supplier<T> sup) {
        int count = registries.size();
        DeferredRegister<I> deferredRegister = (DeferredRegister<I>) registries.computeIfAbsent(registry, reg -> DeferredRegister.create(reg.key(), Constants.MOD_ID));
        if (count < registries.size()){
            deferredRegister.register(FMLJavaModLoadingContext.get().getModEventBus());
        }
        return deferredRegister.register(name, sup);
    }

    @Override
    public GameRules.Key<GameRules.BooleanValue> registerGameRule(String name, GameRules.Category category, boolean defaultValue) {
        return GameRules.register(name, category, GameRules.BooleanValue.create(defaultValue));
    }

    @Override
    public DummyEntityForSitting createDummyEntity(EntityType<DummyEntityForSitting> type, Level level) {
        return new DummyEntityForSitting(type, level) {

            @Override
            public Packet<ClientGamePacketListener> getAddEntityPacket() {
                return NetworkHooks.getEntitySpawningPacket(this);
            }
        };
    }

    @Override
    public LiquidBlock createThatchFluidBlock(Supplier<ThatchFluid.Source> fluid, BlockBehaviour.Properties properties) {
        return new ForgeThatchFluidBlock(fluid::get, properties);
    }

    @Override
    public ThatchFluid.Flowing createThatchFlowingFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
        return new ForgeThatchFluid.Flowing(referenceHolder);
    }

    @Override
    public ThatchFluid.Source createThatchStillFluid(ThatchFluid.FluidReferenceHolder referenceHolder) {
        return new ForgeThatchFluid.Source(referenceHolder);
    }

    @Override
    public CreativeModeTab.Builder createModTab() {
        return CreativeModeTab.builder();
    }

    @Override
    public void setRenderLayer(Block block, RenderType renderType) {
        ItemBlockRenderTypes.setRenderLayer(block, renderType);
    }

    @Override
    public void registerItemFunc(Item item, ResourceLocation name, ItemPropertyFunction func) {
        ItemProperties.register(item, name, func);
    }

    @Override
    public TagKey<Item> getShearTag() {
        return Tags.Items.SHEARS;
    }


}
