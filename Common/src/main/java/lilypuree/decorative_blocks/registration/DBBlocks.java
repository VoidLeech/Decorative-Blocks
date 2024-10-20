package lilypuree.decorative_blocks.registration;

import com.google.common.collect.ImmutableMap;
import lilypuree.decorative_blocks.blocks.*;
import lilypuree.decorative_blocks.blocks.types.IWoodType;
import lilypuree.decorative_blocks.blocks.types.VanillaWoodTypes;
import lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes;
import lilypuree.decorative_blocks.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static lilypuree.decorative_blocks.blocks.types.WoodDecorativeBlockTypes.*;

public class DBBlocks {

    public static final BlockWrapper<BonfireBlock> BONFIRE;
    public static final BlockWrapper<ChandelierBlock> CHANDELIER;
    public static final BlockWrapper<BrazierBlock> BRAZIER;
    public static final BlockWrapper<ChandelierBlock> SOUL_CHANDELIER;
    public static final BlockWrapper<BrazierBlock> SOUL_BRAZIER;
    public static final BlockWrapper<BonfireBlock> SOUL_BONFIRE;
    public static final BlockWrapper<BarPanelBlock> BAR_PANEL;
    public static final BlockWrapper<LatticeBlock> LATTICE;
    public static final BlockWrapper<ChainBlock> CHAIN;
    public static final BlockWrapper<PillarBlock> STONE_PILLAR;
    public static final BlockWrapper<RockyDirtBlock> ROCKY_DIRT;
    public static final BlockWrapper<LiquidBlock> THATCH;

    public static final ImmutableMap<IWoodType, BlockWrapper<BeamBlock>> BEAMS;
    public static final ImmutableMap<IWoodType, BlockWrapper<PalisadeBlock>> PALISADES;
    public static final ImmutableMap<IWoodType, BlockWrapper<SupportBlock>> SUPPORTS;
    public static final ImmutableMap<IWoodType, BlockWrapper<SeatBlock>> SEATS;

    static {
        BlockBehaviour.Properties chainProperties = Block.Properties.of().mapColor(MapColor.METAL).strength(4.3F).sound(SoundType.METAL).noOcclusion();
        BlockBehaviour.Properties thatchProperties = Block.Properties.of().liquid().replaceable().noCollission().randomTicks().noLootTable()
                .mapColor(MapColor.COLOR_YELLOW).pushReaction(PushReaction.DESTROY).strength(100.0F);

        BlockBehaviour.Properties bonfire = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0).mapColor(MapColor.FIRE).pushReaction(PushReaction.DESTROY).replaceable().noCollission().lightLevel(state -> 15).noLootTable();
        BlockBehaviour.Properties soul_bonfire = BlockBehaviour.Properties.of().sound(SoundType.WOOL).strength(0).mapColor(MapColor.COLOR_CYAN).pushReaction(PushReaction.DESTROY).replaceable().noCollission().lightLevel(state -> 14).noLootTable();
        BlockBehaviour.Properties chandelier = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f).pushReaction(PushReaction.DESTROY).replaceable().noCollission().noOcclusion().lightLevel(state -> 15);
        BlockBehaviour.Properties soul_chandelier = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(0.3f).pushReaction(PushReaction.DESTROY).replaceable().noCollission().noOcclusion().lightLevel(state -> 11);
        BlockBehaviour.Properties brazier = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3.0f).mapColor(MapColor.METAL).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 15 : 0);
        BlockBehaviour.Properties soul_brazier = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(3.0f).mapColor(MapColor.METAL).noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.LIT) ? 10 : 0);
        BlockBehaviour.Properties bar_panel = BlockBehaviour.Properties.of().sound(SoundType.METAL).strength(5.0f).mapColor(MapColor.METAL).noOcclusion();
        BlockBehaviour.Properties lattice = BlockBehaviour.Properties.of().sound(SoundType.WOOD).strength(1.2f).mapColor(MapColor.WOOD).noOcclusion();
        BlockBehaviour.Properties pillar = BlockBehaviour.Properties.of().sound(SoundType.STONE).strength(1.5F, 6.5F).mapColor(MapColor.STONE);

        BONFIRE = registerBlock("bonfire", () -> new BonfireBlock(bonfire));
        CHANDELIER = registerBlock("chandelier", () -> new ChandelierBlock(chandelier, false));
        BRAZIER = registerBlock("brazier", () -> new BrazierBlock(brazier, false));
        SOUL_BONFIRE = registerBlock("soul_bonfire", () -> new BonfireBlock(soul_bonfire));
        SOUL_CHANDELIER = registerBlock("soul_chandelier", () -> new ChandelierBlock(soul_chandelier, true));
        SOUL_BRAZIER = registerBlock("soul_brazier", () -> new BrazierBlock(soul_brazier, true));
        BAR_PANEL = registerBlock("bar_panel", () -> new BarPanelBlock(bar_panel));
        LATTICE = registerBlock("lattice", () -> new LatticeBlock(lattice));
        CHAIN = registerBlock("chain", () -> new ChainBlock(chainProperties));
        STONE_PILLAR = registerBlock("stone_pillar", () -> new PillarBlock(pillar));
        ROCKY_DIRT = registerBlock("rocky_dirt", RockyDirtBlock::new);
        THATCH = registerBlock("thatch", () -> Services.PLATFORM.createThatchFluidBlock(Registration.STILL_THATCH, thatchProperties));

        ImmutableMap.Builder<IWoodType, BlockWrapper<BeamBlock>> beams = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockWrapper<PalisadeBlock>> palisades = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockWrapper<SupportBlock>> supports = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<IWoodType, BlockWrapper<SeatBlock>> seats = new ImmutableMap.Builder<>();
        for (IWoodType woodType : VanillaWoodTypes.values()) {
            if (woodType != VanillaWoodTypes.BAMBOO)
                beams.put(woodType, registerBlock(DBNames.name(woodType, BEAM), () -> (BeamBlock) createDecorativeBlock(woodType, BEAM)));
            palisades.put(woodType, registerBlock(DBNames.name(woodType, PALISADE), () -> (PalisadeBlock) createDecorativeBlock(woodType, PALISADE)));
            supports.put(woodType, registerBlock(DBNames.name(woodType, SUPPORT), () -> (SupportBlock) createDecorativeBlock(woodType, SUPPORT)));
            seats.put(woodType, registerBlock(DBNames.name(woodType, SEAT), () -> (SeatBlock) createDecorativeBlock(woodType, SEAT)));
        }
        BEAMS = beams.build();
        PALISADES = palisades.build();
        SUPPORTS = supports.build();
        SEATS = seats.build();

    }

    public static void init() {
    }

    public static Block createDecorativeBlock(IWoodType wood, WoodDecorativeBlockTypes woodDecorativeBlockType) {
        BlockBehaviour.Properties woodProperty = wood.getProperties().strength(1.2F);
        BlockBehaviour.Properties palisadeProperty = wood.getProperties().strength(2.0F, 4.0F);

        return switch (woodDecorativeBlockType) {
            case BEAM -> new BeamBlock(woodProperty, wood);
            case SEAT -> new SeatBlock(woodProperty, wood);
            case SUPPORT -> new SupportBlock(woodProperty, wood);
            case PALISADE -> new PalisadeBlock(palisadeProperty, wood);
        };
    }

    private static <T extends Block> BlockWrapper<T> registerBlock(String name, Supplier<T> blockSupplier) {
        return new BlockWrapper<>(Services.PLATFORM.register(BuiltInRegistries.BLOCK, name, blockSupplier));
    }

}
