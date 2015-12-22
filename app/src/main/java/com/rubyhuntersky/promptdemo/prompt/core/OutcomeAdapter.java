package com.rubyhuntersky.promptdemo.prompt.core;

/**
 * @author wehjin
 * @since 12/22/15.
 */
public interface OutcomeAdapter<O1, O2, O3> {
    OutcomeAdapter<Void, Void, Void> VOID = new OutcomeAdapter<Void, Void, Void>() {
        @Override
        public Void adaptFirst(Void outcome) {
            return outcome;
        }

        @Override
        public Void adaptSecond(Void outcome) {
            return outcome;
        }
    };

    O3 adaptFirst(O1 outcome);
    O3 adaptSecond(O2 outcome);
}
