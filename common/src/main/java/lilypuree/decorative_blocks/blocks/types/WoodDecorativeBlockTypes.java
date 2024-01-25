package lilypuree.decorative_blocks.blocks.types;

public enum WoodDecorativeBlockTypes {
    BEAM("beam"), PALISADE("palisade"), SEAT("seat"), SUPPORT("support");

    private final String name;

    private WoodDecorativeBlockTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.getName();
    }

    public String getName() {
        return this.name;
    }
}
