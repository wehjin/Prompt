package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */

abstract public class Dimension {

    public static final Dimension SPACE_START = create(Unit.SPACE, 0);
    public static final Dimension SPACE_END = create(Unit.SPACE, 1);
    public static final Dimension READABLE = create(Unit.READABLE, 1);
    public static final Dimension TAPPABLE = create(Unit.TAPPABLE, 1);

    abstract public float convert(float perReadable, float perTappable, float perSpace);

    public Dimension negate() {
        return multiply(-1);
    }

    public Dimension multiply(final float factor) {
        return create(new OnConvert() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                return Dimension.this.convert(perReadable, perTappable, perSpace) * factor;
            }
        });
    }

    public Dimension offset(final Dimension offset) {
        return create(new OnConvert() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                return Dimension.this.convert(perReadable, perTappable, perSpace)
                      + offset.convert(perReadable, perTappable, perSpace);
            }
        });
    }

    public static Dimension create(final Unit unit, final float amount) {
        return create(new OnConvert() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                switch (unit) {
                    case READABLE:
                        return perReadable * amount;
                    case TAPPABLE:
                        return perTappable * amount;
                    case SPACE:
                        return perSpace * amount;
                }
                throw new IllegalArgumentException();
            }
        });
    }

    private static Dimension create(final OnConvert onConvert) {
        return new Dimension() {
            @Override
            public float convert(float perReadable, float perTappable, float perSpace) {
                return onConvert.convert(perReadable, perTappable, perSpace);
            }
        };
    }

    public interface OnConvert {
        float convert(float perReadable, float perTappable, float perSpace);
    }
}
