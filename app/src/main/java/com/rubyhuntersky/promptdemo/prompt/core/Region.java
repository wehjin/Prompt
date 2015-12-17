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
    static private int TOP = 0;
    static private int RIGHT = 1;
    static private int BOTTOM = 2;
    static private int LEFT = 3;

    abstract public Dimension getLeft();
    abstract public Dimension getTop();
    abstract public Dimension getWidth();
    abstract public Dimension getHeight();

    public Region inset(final Dimension[] insets) {
        final Region original = this;
        final int indices[] = {0, 0, 0, 0};
        if (insets.length == 2) {
            indices[TOP] = indices[BOTTOM] = 0;
            indices[RIGHT] = indices[LEFT] = 1;
        } else if (insets.length == 3) {
            indices[TOP] = 0;
            indices[RIGHT] = indices[LEFT] = 1;
            indices[BOTTOM] = 2;
        } else if (insets.length == 4) {
            indices[TOP] = 0;
            indices[RIGHT] = 1;
            indices[BOTTOM] = 2;
            indices[LEFT] = 3;
        }
        return create(new DimensionSource() {
            @Override
            public Dimension getLeft() {
                return original.getLeft().offset(insets[indices[LEFT]]);
            }

            @Override
            public Dimension getTop() {
                return original.getTop().offset(insets[indices[TOP]]);
            }

            @Override
            public Dimension getWidth() {
                return original.getWidth()
                               .offset(insets[indices[LEFT]].negate())
                               .offset(insets[indices[RIGHT]].negate());
            }

            @Override
            public Dimension getHeight() {
                return original.getHeight()
                               .offset(insets[indices[TOP]].negate())
                               .offset(insets[indices[BOTTOM]].negate());
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
