package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/12/15.
 */
abstract public class Region {

    public static final Region SPACE_ALL = create(new DimensionSource() {
        @Override
        public Dimension getLeft() {
            return Dimension.SPACE_START;
        }

        @Override
        public Dimension getTop() {
            return Dimension.SPACE_START;
        }

        @Override
        public Dimension getWidth() {
            return Dimension.SPACE_END;
        }

        @Override
        public Dimension getHeight() {
            return Dimension.SPACE_END;
        }
    });

    abstract public Dimension getLeft();
    abstract public Dimension getTop();
    abstract public Dimension getWidth();
    abstract public Dimension getHeight();

    public Region inset(final Dimension inset) {
        final Region original = this;
        final Dimension endInset = inset.multiply(-2);
        return create(new DimensionSource() {
            @Override
            public Dimension getLeft() {
                return original.getLeft().offset(inset);
            }

            @Override
            public Dimension getTop() {
                return original.getTop().offset(inset);
            }

            @Override
            public Dimension getWidth() {
                return original.getWidth().offset(endInset);
            }

            @Override
            public Dimension getHeight() {
                return original.getHeight().offset(endInset);
            }
        });
    }

    public static Region create(final DimensionSource dimensionSource) {
        return new Region() {
            @Override
            public Dimension getLeft() {
                return dimensionSource.getLeft();
            }

            @Override
            public Dimension getTop() {
                return dimensionSource.getTop();
            }

            @Override
            public Dimension getWidth() {
                return dimensionSource.getWidth();
            }

            @Override
            public Dimension getHeight() {
                return dimensionSource.getHeight();
            }
        };
    }

    public interface DimensionSource {
        Dimension getLeft();
        Dimension getTop();
        Dimension getWidth();
        Dimension getHeight();
    }
}
